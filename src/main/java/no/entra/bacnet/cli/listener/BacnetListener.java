package no.entra.bacnet.cli.listener;

import picocli.CommandLine;

import java.io.IOException;
import java.net.*;

import static no.entra.bacnet.cli.listener.ByteHexConverter.integersToHex;

@CommandLine.Command(name = "listen", description = "Listen to incoming Bacnet messages")
public class BacnetListener implements Runnable {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port default is 47808")
    private int port = 47808;

    private DatagramSocket socket;
    private byte[] buf = new byte[2048];

    @Override
    public void run() {
        boolean blankLine = true;
        long countMessages = 0;

        loop:
        try {
            socket = new DatagramSocket(null);
            socket.setBroadcast(true);
            socket.setReuseAddress(true);
            SocketAddress inetAddress = new InetSocketAddress(port);
            socket.bind(inetAddress);
            System.out.println(String.format("Listening to %s:%s", inetAddress, port));
            while (true) {
                System.out.println("***3");
                int available;

                while (true) {
                    if (!((available = System.in.available()) == 0)) {
                        System.out.println("***1");
                        break;
//                    } else {
//                        System.out.println("****2");
                    }

                    DatagramPacket packet
                            = new DatagramPacket(buf, buf.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    InetAddress sourceAddress = packet.getAddress();
                    int sourcePort = packet.getPort();
                    packet = new DatagramPacket(buf, buf.length, sourceAddress, sourcePort);
                    byte[] receivedBytes = packet.getData();
                    int lenghtOfData = packet.getLength();
                    String hexString = integersToHex(receivedBytes);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(String.format("Received: %s, length: %s, from: %s:%s", hexString, lenghtOfData, sourceAddress, sourcePort));
                    countMessages++;


                }
                do {
                    switch (System.in.read()) {
                        default:
                            System.out.println("Default");
                            blankLine = false;
                            break;
                        case '\n':
                            System.out.println("Newline");
                            if (blankLine)
                                break loop;
                            blankLine = true;
                            break;
                    }
                } while (--available > 0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(String.format("Received: %s messages.", countMessages));
            System.out.println("Closing");
            socket.close();
        }
    }
}
