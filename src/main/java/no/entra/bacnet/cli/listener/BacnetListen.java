package no.entra.bacnet.cli.listener;

import no.entra.bacnet.cli.device.DeviceRepository;
import no.entra.bacnet.cli.sdk.device.Device;
import org.slf4j.Logger;
import picocli.CommandLine;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static org.slf4j.LoggerFactory.getLogger;

@CommandLine.Command(name = "listen", description = "Listen to incoming Bacnet messages")
public class BacnetListen implements Runnable {
    private static final Logger console = getLogger("CONSOLE");
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port default is 47808")
    private int port = 47808;

    private long messageCount = 0;
    private BlockingDeque<BacnetObservedMessage> messageQueue;

    public BacnetListen() {
    }

    public BacnetListen(InetAddress localIpAddress) {
        if (localIpAddress.toString()!= null && localIpAddress.toString().startsWith("/")) {
            this.ipAddress = localIpAddress.toString().substring(1);
        } else {
            this.ipAddress = localIpAddress.toString();
        }
    }

    @Override
    public void run() {
        messageQueue = new LinkedBlockingDeque<>(1000);

        Thread messageConsumerThread = null;
        Thread messageListenerThread = null;
        Thread localIpMessageListenerThread = null;
        BacnetMessageConsumer messageConsumer = new BacnetMessageConsumer(messageQueue, this);
        BacnetMessageListener messageListener = new BacnetMessageListener(messageQueue, port);

        InetAddress localIpAddress = null;
        try {
            localIpAddress = Inet4Address.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        BacnetMessageListener localIpListener = new BacnetMessageListener(messageQueue, port, localIpAddress);

        loop:
        try {

            messageConsumerThread = new Thread(messageConsumer);
            messageConsumerThread.start();
            messageListenerThread = new Thread(messageListener);
            messageListenerThread.start();
            localIpMessageListenerThread = new Thread(localIpListener);
            localIpMessageListenerThread.start();

            while (true) {
                System.out.println("Type q to exit.");
                console.info("Type q to exit");
                Scanner scanner = new Scanner(System.in);

                List<String> tokens = new ArrayList<>();
                while (scanner.hasNext()) {
                    String next = scanner.nextLine();
                    switch (next.toLowerCase()) {
                        case "exit":
                        case "quit":
                        case "q": {
                            System.out.println("Closing down.");
                            scanner.close();
                            messageConsumer.stop();
                            messageListener.stop();
                            break loop;
                        }
                        case "list":
                            listDevices();
                            break;
                        default: {
                            tokens.add(next);
                            System.out.println(tokens);
                        }
                    }
                }
            }
        } finally {
            System.out.println(String.format("Received: %s messages.", messageCount));
            System.out.println("Closing");
            if (messageConsumerThread != null && messageConsumerThread.isAlive()) {
                messageConsumerThread.interrupt();
            }
            if (messageListenerThread != null && messageListenerThread.isAlive()) {
                messageListenerThread.interrupt();
            }

            if (localIpMessageListenerThread != null && localIpMessageListenerThread.isAlive()) {
                localIpMessageListenerThread.interrupt();
            }
        }
    }

    protected void listDevices() {
        List<Device> devices = DeviceRepository.getInstance().list();
        System.out.println("List of Devices: ");
        for (Device device : devices) {
            System.out.printf("%s instance: %s, ipAddress: %s, port: %s, lastSeen: %s \n",
                    device.getObjectName(), device.getInstanceNumber(), device.getIpAddress(), device.getPortNumber(), device.getObservedAt());
        }
    }

    protected void addCount() {
        messageCount++;
    }

    public static void main(String[] args) {
        InetAddress localIpAddress = null;
        try {
            localIpAddress = Inet4Address.getByName("192.168.2.29");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        BacnetListen bacnetListen = new BacnetListen(localIpAddress);
        bacnetListen.run();
    }
}
