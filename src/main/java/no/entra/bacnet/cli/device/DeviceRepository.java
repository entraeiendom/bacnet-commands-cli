package no.entra.bacnet.cli.device;

import java.util.List;
import java.util.stream.Collectors;

public class DeviceRepository {

    private static DeviceRepository instance;

    private List<Device> devices;

    private DeviceRepository() {

    }

    synchronized public static DeviceRepository getInstance() {
        if (instance == null) {
            instance = new DeviceRepository();
        }
        return instance;
    }

    synchronized void add(Device device) {
        devices.add(device);
    }

    synchronized void update(Device device) {
        removeById(device.getId());
        add(device);
    }

    synchronized void removeById(String id) {
        devices.removeIf(d -> d.getId().equals(id));
    }

    synchronized List<Device> findById(String id) {
        List<Device> devicesFound = devices
                .stream()
                .filter(d -> d.getId().equals(id))
                .collect(Collectors.toList());
        return devicesFound;
    }


}
