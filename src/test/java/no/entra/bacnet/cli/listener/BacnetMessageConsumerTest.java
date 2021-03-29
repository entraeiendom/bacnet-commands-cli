package no.entra.bacnet.cli.listener;

import no.entra.bacnet.cli.device.DeviceRepository;
import no.entra.bacnet.cli.sdk.device.Device;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class BacnetMessageConsumerTest {

    private BacnetMessageConsumer messageConsumer;
    private BlockingDeque<BacnetObservedMessage> messageQueue;
    private BacnetListen bacnetListen;

    @BeforeEach
    void setUp() {
        messageQueue = mock(BlockingDeque.class);
        bacnetListen = mock(BacnetListen.class);
        messageConsumer = new BacnetMessageConsumer(messageQueue, bacnetListen);
    }

    @AfterEach
    void tearDown() {
        DeviceRepository.getInstance().clearAll();
    }

    @Test
    void messageReceived() {
       String hexString = "810b000c0120ffff00ff1008";
        SocketAddress senderAddress = new InetSocketAddress("/127.0.0.1", 47808);
        BacnetObservedMessage observedMessage = new BacnetObservedMessage(senderAddress, hexString);
        assertEquals(0, DeviceRepository.getInstance().list().size());
        messageConsumer.messageReceived(observedMessage);
        List<Device> devices = DeviceRepository.getInstance().list();
        assertEquals(1, devices.size());
    }

    @Test
    void multipleMessagesFromTheSameDeviceWithoutInstanceId() {
        String hexString = "810b000c0120ffff00ff1008";
        SocketAddress senderAddress = new InetSocketAddress("/127.0.0.1", 47808);
        BacnetObservedMessage observedMessage = new BacnetObservedMessage(senderAddress, hexString);
        assertEquals(0, DeviceRepository.getInstance().list().size());
        messageConsumer.messageReceived(observedMessage);
        List<Device> devices = DeviceRepository.getInstance().list();
        assertEquals(1, devices.size());
        BacnetObservedMessage observedMessage2 = new BacnetObservedMessage(senderAddress, hexString);
        messageConsumer.messageReceived(observedMessage);
        devices = DeviceRepository.getInstance().list();
        assertEquals(1, devices.size());
    }
}