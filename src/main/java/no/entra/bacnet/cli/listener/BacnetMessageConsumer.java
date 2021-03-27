package no.entra.bacnet.cli.listener;

import java.util.concurrent.BlockingDeque;

public class BacnetMessageConsumer implements Runnable {

    private final BlockingDeque<BacnetObservedMessage> messageQueue;
    private final BacnetListen bacnetListen;
    private long messageCount = 0;

    public BacnetMessageConsumer(BlockingDeque<BacnetObservedMessage> messageQueue, BacnetListen bacnetListen) {
        this.messageQueue = messageQueue;
        this.bacnetListen = bacnetListen;
    }

    @Override
    public void run() {
        try {
            while (true) {
                BacnetObservedMessage bacnetMessage = messageQueue.take();
                messageCount ++;
                bacnetListen.addCount();
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
