package no.entra.bacnet.cli.sdk;

import no.entra.bacnet.json.Observation;
import no.entra.bacnet.json.Source;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;

import java.util.HashMap;
import java.util.Map;

import static no.entra.bacnet.utils.StringUtils.hasValue;

public class BacnetMessage {

    private ConfigurationRequest configurationRequest;
    private Observation observation;
    private Sender sender;
    private String service;
    private Integer invokeId;
    private Map<String, String> properties = new HashMap<>();

    public ConfigurationRequest getConfigurationRequest() {
        return configurationRequest;
    }

    public void setConfigurationRequest(ConfigurationRequest configurationRequest) {
        this.configurationRequest = configurationRequest;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }

    public Sender getSender() {

        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(Integer invokeId) {
        this.invokeId = invokeId;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public boolean hasObservation() {
        return false;
    }

    public boolean hasConfigurationReqest() {
        return this.getConfigurationRequest() != null;
    }

    public ObjectId getObjectIdentifier() {
        ObjectId objectId = null;
        if (configurationRequest != null) {
            objectId = configurationRequest.getObjectIdentifier();
        } else if (observation != null && observation.getSource() != null) {
            Source source = observation.getSource();
            if (source != null && source.getObjectId() != null) {
                String objectIdValue = source.getObjectId();
                if (hasValue(objectIdValue) && objectIdValue.contains("_")) {
                    String[] typeAndInstance = objectIdValue.split("_");
                    if (typeAndInstance.length == 2) {
                        ObjectType objectType = ObjectType.valueOf(typeAndInstance[0]);
                        String instanceNumber = typeAndInstance[1];
                        objectId = new ObjectId(objectType, instanceNumber);
                    }
                }
            }
        }
        return objectId;
    }

    @Override
    public String toString() {
        return "BacnetMessage{" +
                "configurationRequest=" + configurationRequest +
                ", observation=" + observation +
                ", sender=" + sender +
                ", service='" + service + '\'' +
                ", invokeId=" + invokeId +
                ", properties=" + properties +
                '}';
    }
}
