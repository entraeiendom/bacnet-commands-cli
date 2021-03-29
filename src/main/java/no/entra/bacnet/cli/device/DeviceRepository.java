package no.entra.bacnet.cli.device;

import no.entra.bacnet.cli.sdk.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeviceRepository {

    private static DeviceRepository instance;

    private List<Device> devices;

    private DeviceRepository() {
        devices = new ArrayList<>();
    }

    synchronized public static DeviceRepository getInstance() {
        if (instance == null) {
            instance = new DeviceRepository();
        }
        return instance;
    }

    public synchronized void add(Device device) {
        devices.add(device);
    }

    public synchronized void update(Device device) {
        //FIXME update device by ip, port and instanceId
//        removeById(device.getId());
        add(device);
    }

    public synchronized void removeById(String id) {
        devices.removeIf(d -> d.getId().equals(id));
    }

    public synchronized List<Device> findById(String id) {
        List<Device> devicesFound = devices
                .stream()
                .filter(d -> d.getId().equals(id))
                .collect(Collectors.toList());
        return devicesFound;
    }
    public synchronized List<Device> list() {
        return devices;
    }


}
