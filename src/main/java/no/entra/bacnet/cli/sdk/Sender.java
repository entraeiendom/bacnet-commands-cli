package no.entra.bacnet.cli.sdk;

public class Sender {
    private String ipAddress;
    private Integer port;
    private Integer instanceNumber;
    private String name;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(Integer instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
