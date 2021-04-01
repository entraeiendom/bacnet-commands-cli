package no.entra.bacnet.cli.sdk;

import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static no.entra.bacnet.utils.StringUtils.hasValue;

public class ConfigurationRequest {
    private String id;
    private String name;
    private Instant observedAt;
    private Map<String, String > source;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Map<String, String> getSource() {
        return source;
    }

    public void setSource(Map<String, String> source) {
        this.source = source;
    }

    public ObjectId getObjectIdentifier() {
        ObjectId objectId = null;
        if (source != null && source.get("objectId") != null) {
            String objectIdValue = source.get("objectId");
            if (hasValue(objectIdValue) && objectIdValue.contains("_")) {
                String[] typeAndInstance = objectIdValue.split("_");
                if (typeAndInstance.length == 2) {
                    ObjectType objectType = ObjectType.valueOf(typeAndInstance[0]);
                    String instanceNumber = typeAndInstance[1];
                    objectId = new ObjectId(objectType, instanceNumber);
                }
            }
        }
        return objectId;
    }

    @Override
    public String toString() {
        return "ConfigurationRequest{" +
                "id='" + id + '\'' +
                ", observedAt=" + observedAt +
                ", properties=" + properties +
                '}';
    }
}
