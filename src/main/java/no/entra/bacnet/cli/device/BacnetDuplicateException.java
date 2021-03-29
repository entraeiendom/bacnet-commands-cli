package no.entra.bacnet.cli.device;

public class BacnetDuplicateException extends Throwable {
    public BacnetDuplicateException(String id) {
        super(String.format("Device with id %s already exist in repository. Use update()", id));
    }

    public BacnetDuplicateException(String ipAddress, int portNumber, int instanceNumber) {
        super(String.format("Device with ipAddress %s , portNumber %s and instanceNumber %s already exist in repository. " +
                "Use update()", ipAddress, portNumber, instanceNumber));
    }
}
