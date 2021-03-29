package no.entra.bacnet.cli.listener;

import no.entra.bacnet.cli.device.DeviceRepository;
import no.entra.bacnet.cli.sdk.device.Device;
import org.slf4j.Logger;
import picocli.CommandLine;

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

    @Override
    public void run() {
        messageQueue = new LinkedBlockingDeque<>(1000);

        Thread messageConsumerThread = null;
        Thread messageListenerThread = null;
        BacnetMessageConsumer messageConsumer = new BacnetMessageConsumer(messageQueue, this);
        BacnetMessageListener messageListener = new BacnetMessageListener(messageQueue, port);

        loop:
        try {

            messageConsumerThread = new Thread(messageConsumer);
            messageConsumerThread.start();
            messageListenerThread = new Thread(messageListener);
            messageListenerThread.start();

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
        }
    }

    protected void listDevices() {
        List<Device> devices = DeviceRepository.getInstance().list();
        System.out.println("List of Devices: ");
        for (Device device : devices) {
            System.out.printf("instance: %s, ipAddress: %s, port: %s, lastSeen: %s \n",
                    device.getInstanceNumber(), device.getIpAddress(), device.getPortNumber(), device.getObservedAt());
        }
    }

    protected void addCount() {
        messageCount++;
    }

    public static void main(String[] args) {
        BacnetListen bacnetListen = new BacnetListen();
        bacnetListen.run();
    }
}
