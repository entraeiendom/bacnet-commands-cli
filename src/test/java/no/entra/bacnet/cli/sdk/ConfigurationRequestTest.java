package no.entra.bacnet.cli.sdk;

import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigurationRequestTest {


    @Test
    void getObjectIdentifierTest() {
        ConfigurationRequest configurationRequest = new ConfigurationRequest();
        Map<String, String> source = new HashMap<>();
        source.put("objectId", "Device_8");
        configurationRequest.setSource(source);
        ObjectId objectId = configurationRequest.getObjectIdentifier();
        assertNotNull(objectId);
        assertEquals(ObjectType.Device, objectId.getObjectType());
        assertEquals("8", objectId.getInstanceNumber());
    }
}