package no.entra.bacnet.cli.sdk.device;

public class Device {
    private String id;
    private String ipAddress;
    private String portNumber;
    private String objectName;
    private String tfmTag; //aka TverfagligMerkesystem(TFM) in Norwegian RealEstate
    private Integer instanceNumber;
    private String macAdress;
    private String vendorId;
    private boolean supportsReadPropertyMultiple = true;
    private String protocolVersion;
    private String protocolRevision;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getTfmTag() {
        return tfmTag;
    }

    public void setTfmTag(String tfmTag) {
        this.tfmTag = tfmTag;
    }

    public Integer getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(Integer instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public String getMacAdress() {
        return macAdress;
    }

    public void setMacAdress(String macAdress) {
        this.macAdress = macAdress;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public boolean isSupportsReadPropertyMultiple() {
        return supportsReadPropertyMultiple;
    }

    public void setSupportsReadPropertyMultiple(boolean supportsReadPropertyMultiple) {
        this.supportsReadPropertyMultiple = supportsReadPropertyMultiple;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getProtocolRevision() {
        return protocolRevision;
    }

    public void setProtocolRevision(String protocolRevision) {
        this.protocolRevision = protocolRevision;
    }
}
