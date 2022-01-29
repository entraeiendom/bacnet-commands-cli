package no.entra.bacnet.cli.device;

import no.entra.bacnet.cli.sdk.device.Device;
import no.entra.bacnet.internal.properties.Property;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static no.entra.bacnet.json.utils.StringUtils.hasValue;

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

    public synchronized void add(Device device) throws BacnetDuplicateException {
        List<Device> foundDevices = findById(device.getId());
        if (foundDevices.size() > 0) {
            throw new BacnetDuplicateException(device.getId());
        }
        String ipAddress = device.getIpAddress();
        Integer portNumber = device.getPortNumber();
        Integer instanceNumber = device.getInstanceNumber();
        if (hasValue(ipAddress) && portNumber != null && instanceNumber != null) {
            foundDevices = findByIpPortInstanceNumber(ipAddress, portNumber, instanceNumber);
            if (foundDevices.size() > 0) {
                throw new BacnetDuplicateException(ipAddress, portNumber, instanceNumber);
            }
        }
        devices.add(device);
    }

    public synchronized void removeById(String id) {
        devices.removeIf(d -> d.getId().equals(id));
    }

    public synchronized List<Device> findById(String id) {
        if (!hasValue(id)) {
            return new ArrayList<>();
        }
        List<Device> devicesFound = devices
                .stream()
                .filter(d -> d.getId().equals(id))
                .collect(Collectors.toList());
        return devicesFound;
    }

    public synchronized List<Device> findByIpPortInstanceNumber(String ipAddress, int portNumber, int instanceNumber) {
        List<Device> devicesFound = devices
                .stream()
                .filter(d -> d.getIpAddress().equals(ipAddress) && d.getPortNumber() == portNumber && d.getInstanceNumber() == instanceNumber)
                .collect(Collectors.toList());
        return devicesFound;
    }

    public synchronized List<Device> list() {
        return devices;
    }

    public synchronized long updateObservedAt(Device device) {
        Instant observedAt = device.getObservedAt();
        String deviceId = device.getId();
        String ipAddress = device.getIpAddress();
        Integer portNumber = device.getPortNumber();
        Integer instanceNumber = device.getInstanceNumber();
        final long[] updatedCount = {0};

        if (hasValue(deviceId)) {
            devices
                    .stream()
                    .filter(d -> d.getId().equals(deviceId))
                    .forEach(d -> {
                        updatedCount[0]++;
                        d.setObservedAt(observedAt);
                    });
        } else if (hasValue(ipAddress) && portNumber != null && instanceNumber != null) {
            devices
                    .stream()
                    .filter(d -> d.getIpAddress().equals(ipAddress) && d.getPortNumber().equals(portNumber) && d.getInstanceNumber().equals(instanceNumber))
                    .forEach(d -> {
                        updatedCount[0]++;
                        d.setObservedAt(observedAt);
                    });
        } else if (hasValue(ipAddress) && portNumber != null) {
            devices
                    .stream()
                    .filter(d -> d.getIpAddress().equals(ipAddress) && d.getPortNumber().equals(portNumber) && d.getInstanceNumber() == null)
                    .forEach(d -> {
                        updatedCount[0]++;
                        d.setObservedAt(observedAt);
                    });
        }
        if (updatedCount[0] == 0) {
            try {
                updatedCount[0]++;
                add(device);
            } catch (BacnetDuplicateException e) {
                //Based on previous searches this shoul not happen, and may be ignored.
            }
        }
        return updatedCount[0];
    }


    /**
     * Used for testing
     */
    public void clearAll() {
        devices = new ArrayList<>();
    }

    public void updateByIpPortInstanceNumber(String ipAddress, Integer port, Integer instanceNumber, List<Property> properties) {
//        Predicate<Device> deviceMatches = device -> {
//            device.getIpAddress().equals(ipAddress)
//                    && device.getPortNumber().equals(port)
//                    && device.getInstanceNumber().equals(instanceNumber);
//        }
        devices.stream().filter(device -> device.getIpAddress().equals(ipAddress))
                .filter(device -> device.getPortNumber().equals(port))
                .filter(device -> device.getInstanceNumber().equals(instanceNumber))
                .forEach(device -> {
                    for (Property property : properties) {
                        device.updateProperty(property);
                    }
                    device.setObservedAt(Instant.now());
                });
    }
}

