package no.entra.bacnet.cli.device;

import no.entra.bacnet.cli.sdk.device.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    void add() {
        assertEquals(0, deviceRepository.list().size());
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
    }

    @Test
    void update() {
    }

    @Test
    void removeById() {
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
        deviceRepository.removeById(deviceId);
        assertEquals(0, deviceRepository.list().size());
    }

    @Test
    void findById() {
        deviceRepository.add(device);
        assertEquals(1, deviceRepository.list().size());
        List<Device> foundDevices = deviceRepository.findById(deviceId);
        assertNotNull(foundDevices);
        assertEquals(1, foundDevices.size());
        Device found = foundDevices.get(0);
        assertEquals(device.getId(), found.getId());
        assertEquals(device.getInstanceNumber(), found.getInstanceNumber());
    }
}