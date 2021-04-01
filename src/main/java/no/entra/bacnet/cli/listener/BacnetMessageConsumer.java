package no.entra.bacnet.cli.listener;

import no.entra.bacnet.cli.device.DeviceRepository;
import no.entra.bacnet.cli.sdk.BacnetJsonMapper;
import no.entra.bacnet.cli.sdk.BacnetMessage;
import no.entra.bacnet.cli.sdk.ConfigurationRequest;
import no.entra.bacnet.cli.sdk.Sender;
import no.entra.bacnet.cli.sdk.device.Device;
import no.entra.bacnet.cli.sdk.device.DeviceMapper;
import no.entra.bacnet.cli.sdk.observation.ObservationMapper;
import no.entra.bacnet.cli.sdk.properties.PropertiesMapper;
import no.entra.bacnet.json.Bacnet2Json;
import no.entra.bacnet.json.Observation;
import no.entra.bacnet.objects.Property;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.BlockingDeque;

public class BacnetMessageConsumer implements Runnable {

    private volatile boolean isAlive = true;
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
            while (isAlive) {
                BacnetObservedMessage bacnetMessage = messageQueue.take();
                messageCount++;
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
            SocketAddress senderAddress = observedMessage.getSenderAddress();
            if (senderAddress != null && senderAddress instanceof InetSocketAddress) {
                Sender sender = new Sender();
                sender.setPort(((InetSocketAddress) senderAddress).getPort());
                sender.setIpAddress(((InetSocketAddress) senderAddress).getHostName());
                bacnetMessage.setSender(sender);
            }
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
            Device device;
            switch (service) {
                case "WhoIs":
                case "IAm":
                    device = DeviceMapper.mapFromConfigurationRequest(sender, configurationRequest);
                    updateDevice(device);
                    break;
                case "ReadPropertyMultiple":
                    updatePropertiesForDevice(sender, configurationRequest);
                    break;
                default:
                    System.out.println(String.format("Service not implemented yet %s", service));
            }
        } else if (bacnetMessage.getSender() != null) {
            Sender sender = bacnetMessage.getSender();
            Device device = new Device();
            device.setObservedAt(Instant.now());
            device.setIpAddress(sender.getIpAddress());
            device.setPortNumber(sender.getPort());
            device.setInstanceNumber(sender.getInstanceNumber());
            updateDevice(device);
        }
        if (bacnetMessage != null) {
            System.out.println("Object: " + bacnetMessage.toString());
        }
    }

    void updatePropertiesForDevice(Sender sender, ConfigurationRequest configurationRequest) {
        List<Property> properties = PropertiesMapper.mapProperties(configurationRequest);

        DeviceRepository.getInstance()
                .updateByIpPortInstanceNumber(sender.getIpAddress(), sender.getPort(), sender.getInstanceNumber(), properties);
    }

    void updateDevice(Device device) {
        DeviceRepository.getInstance().updateObservedAt(device);
    }

    public long getMessageCount() {
        return messageCount;
    }

    public void stop() {
        isAlive = false;
    }
}
