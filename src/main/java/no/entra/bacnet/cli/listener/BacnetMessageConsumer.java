package no.entra.bacnet.cli.listener;

import java.util.concurrent.BlockingDeque;

public class BacnetMessageConsumer implements Runnable {

    private final BlockingDeque<BacnetObservedMessage> messageQueue;
    private long messageCount = 0;

    public BacnetMessageConsumer(BlockingDeque<BacnetObservedMessage> messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                BacnetObservedMessage bacnetMessage = messageQueue.take();
                messageCount ++;
                System.out.println(String.format("BacnetMessageConsumer Message %s is: %s", messageCount, bacnetMessage));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public long getMessageCount() {
        return messageCount;
    }
}
