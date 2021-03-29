package no.entra.bacnet.cli.listener;

import no.entra.bacnet.cli.sdk.BacnetJsonMapper;
import no.entra.bacnet.cli.sdk.BacnetMessage;
import no.entra.bacnet.cli.sdk.ConfigurationRequest;
import no.entra.bacnet.cli.sdk.Sender;
import no.entra.bacnet.cli.sdk.device.Device;
import no.entra.bacnet.cli.sdk.device.DeviceMapper;
import no.entra.bacnet.cli.sdk.observation.ObservationMapper;
import no.entra.bacnet.json.Bacnet2Json;
import no.entra.bacnet.json.Observation;

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

    protected void messageReceived(BacnetObservedMessage observedMessage) {
        String hexString = observedMessage.getHexString();
        try {
            String bacnetJson = Bacnet2Json.hexStringToJson(hexString);
            System.out.println("BacnetJson recieived is " + bacnetJson);
            BacnetMessage bacnetMessage = BacnetJsonMapper.map(bacnetJson);
            handleMessageContent(bacnetMessage);
        } catch (Exception e) {
            System.err.println(String.format("Failed to format [%s]", hexString));
            e.printStackTrace();
        }
    }

    void handleMessageContent(BacnetMessage bacnetMessage) {
        if (bacnetMessage.hasObservation()) {
            Observation observation = ObservationMapper.mapObservation(bacnetMessage);
        }
        if (bacnetMessage.hasConfigurationReqest()) {
            ConfigurationRequest configurationRequest = bacnetMessage.getConfigurationRequest();
            String service = bacnetMessage.getService();
            Sender sender = bacnetMessage.getSender();
            switch (service){
                case "IAm":
                    Device device = DeviceMapper.mapFromConfigurationRequest(sender, configurationRequest);
            }
        }
        if (bacnetMessage != null) {
            System.out.println("Object: " + bacnetMessage.toString());
        }
    }

    public long getMessageCount() {
        return messageCount;
    }
}
