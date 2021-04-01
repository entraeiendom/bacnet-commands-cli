package no.entra.bacnet.cli.sdk.device;

import no.entra.bacnet.objects.Property;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Device {
    private String id;
    private String ipAddress;
    private Integer portNumber;
    private String objectName;
    private String tfmTag; //aka TverfagligMerkesystem(TFM) in Norwegian RealEstate
    private Integer instanceNumber;
    private String macAdress;
    private String vendorId;
    private boolean supportsReadPropertyMultiple = true;
    private String protocolVersion;
    private String protocolRevision;
    private Integer maxAPDULengthAccepted;
    private boolean segmentationSupported;
    private Instant observedAt;
    private Map<String, String> properties = new HashMap<>();

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

    public Integer getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(Integer portNumber) {
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

    public void setMaxAPDULengthAccepted(Integer maxAPDULengthAccepted) {
        this.maxAPDULengthAccepted = maxAPDULengthAccepted;
    }

    public Integer getMaxAPDULengthAccepted() {
        return maxAPDULengthAccepted;
    }

    public void setSegmentationSupported(boolean segmentationSupported) {
        this.segmentationSupported = segmentationSupported;
    }

    public boolean getSegmentationSupported() {
        return segmentationSupported;
    }

    public void setObservedAt(Instant observedAt) {
        this.observedAt = observedAt;
    }

    public Instant getObservedAt() {
        return observedAt;
    }

    public void updateProperty(Property property) {
        String key = property.getKey().toLowerCase();
        Object value = property.getValue();
        switch (key) {
            case "objectname":
            case "name":
                setObjectName((String) value);
                break;
            case "protocolversion":
                setProtocolVersion((String) value);
                break;
            case "protocolrevision":
                setProtocolRevision((String) value);
                break;
            default:
                properties.put(key, (String) value);
        }

    }

    public boolean isSegmentationSupported() {
        return segmentationSupported;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
