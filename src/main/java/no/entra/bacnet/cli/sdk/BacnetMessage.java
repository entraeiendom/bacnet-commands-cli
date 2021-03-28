package no.entra.bacnet.cli.sdk;

import no.entra.bacnet.json.Observation;

import java.util.HashMap;
import java.util.Map;

public class BacnetMessage {

    private ConfigurationRequest configurationRequest;
    private Observation observation;
    private Sender sender;
    private String service;
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

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "BacnetMessage{" +
                "configurationRequest=" + configurationRequest +
                ", observation=" + observation +
                ", sender=" + sender +
                ", service='" + service + '\'' +
                ", properties=" + properties +
                '}';
    }
}
