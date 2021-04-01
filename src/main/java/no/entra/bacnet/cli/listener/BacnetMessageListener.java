package no.entra.bacnet.cli.listener;

import no.entra.bacnet.cli.utils.BacnetUtils;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.BlockingDeque;

import static no.entra.bacnet.cli.utils.HexUtils.integersToHex;

public class BacnetMessageListener implements Runnable {

    private InetAddress ipAddress = null;
    private volatile boolean isAlive = true;
    private final BlockingDeque<BacnetObservedMessage> messageQueue;
    private final int port;

    private DatagramSocket socket;
    private byte[] buf = new byte[2048];
    private DatagramChannel channel;

    private long messageCount = 0;

    public BacnetMessageListener(BlockingDeque<BacnetObservedMessage> messageQueue, int port) {
        this.messageQueue = messageQueue;
        this.port = port;
    }

    public BacnetMessageListener(BlockingDeque<BacnetObservedMessage> messageQueue, int port, InetAddress listenToIpAddress) {
        this.messageQueue = messageQueue;
        this.port = port;
        this.ipAddress = listenToIpAddress;
    }

    @Override
    public void run() {
        try {
            channel = DatagramChannel.open();
            channel.setOption(StandardSocketOptions.SO_REUSEPORT, true);
            channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            InetSocketAddress inetAddress = null;
            if (ipAddress == null) {
                inetAddress = new InetSocketAddress(port);
                System.out.println("You will need to setup a new listener on the local ip also.");
            } else {
                inetAddress = new InetSocketAddress(ipAddress, port);
            }
            channel.socket().bind(inetAddress);
            System.out.println(String.format("Listening to %s:%s", inetAddress, port));

            while (isAlive) {

                ByteBuffer byteBuff = ByteBuffer.allocate(2048);
                byteBuff.clear();
                SocketAddress senderAddress = channel.receive(byteBuff);
                byte[] receivedBytes = byteBuff.array();
                String hexString = integersToHex(receivedBytes);
                hexString = BacnetUtils.trimToValidHex(hexString);
                messageCount++;
                System.out.println(String.format("Message %s received: %s from %s", messageCount, hexString, senderAddress));
                BacnetObservedMessage message = new BacnetObservedMessage(senderAddress, hexString);
                messageQueue.add(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(String.format("BacnetMessageListener Received: %s messages.", messageCount));
            System.out.println("Closing BacnetMessageListener");
//            socket.close();
            channel.socket().close();
        }

    }

    public void stop() {
        isAlive = false;
    }
}
