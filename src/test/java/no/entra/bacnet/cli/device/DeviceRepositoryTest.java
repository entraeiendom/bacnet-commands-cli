package no.entra.bacnet.cli.device;

import no.entra.bacnet.cli.sdk.device.Device;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeviceRepositoryTest {

    private DeviceRepository deviceRepository;
    private Device device;
    private final String deviceId = "456-80";

    @BeforeEach
    void setUp() {
        deviceRepository = DeviceRepository.getInstance();
        device = new Device();
        device.setId(deviceId);
        device.setInstanceNumber(0);
        device.setPortNumber(1234);
        device.setIpAddress("127.0.0.1");
    }

    @AfterEach
    void tearDown() {
        deviceRepository.clearAll();
    }

    @Test
    void add() throws BacnetDuplicateException {
        assertEquals(0, deviceRepository.list().size());
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
    }

    @Test
    void addWithoutInstanceId() throws BacnetDuplicateException {
        assertEquals(0, deviceRepository.list().size());
        device.setInstanceNumber(null);
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
    }

    @Test
    void multipleAddWithoutInstanceId() throws BacnetDuplicateException {
        assertEquals(0, deviceRepository.list().size());
        device.setInstanceNumber(null);
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
        //Expect BacnetDuplicateException
        assertThrows(BacnetDuplicateException.class, () -> {
            deviceRepository.add(device);
        });
    }

    @Test
    void update() throws BacnetDuplicateException {
        Instant anHourAgo = Instant.now().minusSeconds(3600);
        device.setObservedAt(anHourAgo);
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
        assertEquals(anHourAgo, deviceRepository.list().get(0).getObservedAt());
        Instant currentTime = Instant.now();
        Device updatedDevice = new Device();
        updatedDevice.setId(deviceId);
        updatedDevice.setInstanceNumber(0);
        updatedDevice.setPortNumber(1234);
        updatedDevice.setIpAddress("127.0.0.1");
        updatedDevice.setObservedAt(currentTime);
        deviceRepository.updateObservedAt(updatedDevice);
        assertEquals(1, deviceRepository.list().size());
        Instant observedAt = deviceRepository.list().get(0).getObservedAt();
        assertEquals(currentTime, observedAt);
    }

    @Test
    void updateWithoutDeviceId() throws BacnetDuplicateException {
        Instant anHourAgo = Instant.now().minusSeconds(3600);
        device.setObservedAt(anHourAgo);
        device.setId(null);
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
        assertEquals(anHourAgo, deviceRepository.list().get(0).getObservedAt());
        Instant currentTime = Instant.now();
        Device updatedDevice = new Device();
        updatedDevice.setInstanceNumber(0);
        updatedDevice.setPortNumber(1234);
        updatedDevice.setIpAddress("127.0.0.1");
        updatedDevice.setObservedAt(currentTime);
        long updatedCount = deviceRepository.updateObservedAt(updatedDevice);
        assertEquals(1, updatedCount);
        assertEquals(1, deviceRepository.list().size());
        Instant updatedObservedAt = deviceRepository.list().get(0).getObservedAt();
        assertEquals(currentTime, updatedObservedAt);
    }

    @Test
    void updateWithoutInstanceId() throws BacnetDuplicateException {
        Instant anHourAgo = Instant.now().minusSeconds(3600);
        device.setObservedAt(anHourAgo);
        device.setId(null);
        device.setInstanceNumber(null);
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
        assertEquals(anHourAgo, deviceRepository.list().get(0).getObservedAt());
        Instant currentTime = Instant.now();
        Device updatedDevice = new Device();
        updatedDevice.setPortNumber(1234);
        updatedDevice.setIpAddress("127.0.0.1");
        updatedDevice.setObservedAt(currentTime);
        long updatedCount = deviceRepository.updateObservedAt(updatedDevice);
        assertEquals(1, updatedCount);
        assertEquals(1, deviceRepository.list().size());
        Instant updatedObservedAt = deviceRepository.list().get(0).getObservedAt();
        assertEquals(currentTime, updatedObservedAt);
    }

    @Test
    void removeById() throws BacnetDuplicateException {
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
        deviceRepository.removeById(deviceId);
        assertEquals(0, deviceRepository.list().size());
    }

    @Test
    void findById() throws BacnetDuplicateException {
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
        List<Device> foundDevices = deviceRepository.findById(deviceId);
        assertNotNull(foundDevices);
        assertEquals(1, foundDevices.size());
        Device found = foundDevices.get(0);
        assertEquals(device.getId(), found.getId());
        assertEquals(device.getInstanceNumber(), found.getInstanceNumber());
    }

    @Test
    void findByIpPortInstanceNumber() throws BacnetDuplicateException {
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
        List<Device> foundDevices = deviceRepository.findByIpPortInstanceNumber(
                device.getIpAddress(),
                device.getPortNumber(),
                device.getInstanceNumber()
        );
        assertNotNull(foundDevices);
        assertEquals(1, foundDevices.size());

    }
}