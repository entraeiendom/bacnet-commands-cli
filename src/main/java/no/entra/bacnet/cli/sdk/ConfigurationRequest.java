package no.entra.bacnet.cli.sdk;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationRequest {
    private String id;
    private String observedAt;
    private Map<String, String> properties;

    public ConfigurationRequest() {
        properties = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(String observedAt) {
        this.observedAt = observedAt;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
