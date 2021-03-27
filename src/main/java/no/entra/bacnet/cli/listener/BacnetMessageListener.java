package no.entra.bacnet.cli.listener;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.BlockingDeque;

import static no.entra.bacnet.cli.listener.ByteHexConverter.integersToHex;

public class BacnetMessageListener implements Runnable {

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

    @Override
    public void run() {
        try {
            while (true) {
                channel = DatagramChannel.open();
                channel.setOption(StandardSocketOptions.SO_REUSEPORT, true);
                channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                InetSocketAddress inetAddress = new InetSocketAddress(port);
                channel.socket().bind(inetAddress);
                System.out.println(String.format("Listening to %s:%s", inetAddress, port));

                ByteBuffer byteBuff = ByteBuffer.allocate(2048);
                byteBuff.clear();
                SocketAddress senderAddress = channel.receive(byteBuff);
                byte[] receivedBytes = byteBuff.array();
                String hexString = integersToHex(receivedBytes);
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
}
