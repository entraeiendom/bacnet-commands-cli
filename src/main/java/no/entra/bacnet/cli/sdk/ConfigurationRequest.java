package no.entra.bacnet.cli.sdk;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationRequest {
    private String id;
    private Instant observedAt;
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

    public Instant getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(Instant observedAt) {
        this.observedAt = observedAt;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }
}
