package no.entra.bacnet.cli.listener;

import no.entra.bacnet.cli.sdk.BacnetJsonMapper;
import no.entra.bacnet.json.Bacnet2Json;

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
//                System.out.println(String.format("BacnetMessageConsumer Message %s is: %s", messageCount, bacnetMessage));
                messageReceived(bacnetMessage);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void messageReceived(BacnetObservedMessage bacnetMessage) {
        String hexString = bacnetMessage.getHexString();
        try {
            String bacnetJson = Bacnet2Json.hexStringToJson(hexString);
            System.out.println("BacnetJson recieived is " + bacnetJson);
            Object object = BacnetJsonMapper.map(bacnetJson);
            if (object != null) {
                System.out.println("Object: " + object.toString());
            }
        } catch (Exception e) {
            System.err.println(String.format("Failed to format [%s]", hexString));
            e.printStackTrace();
        }
    }

    public long getMessageCount() {
        return messageCount;
    }
}
