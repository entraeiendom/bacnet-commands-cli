package no.entra.bacnet.cli.sdk.properties;

import no.entra.bacnet.cli.sdk.ConfigurationRequest;
import no.entra.bacnet.internal.properties.Property;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.entra.bacnet.cli.sdk.properties.PropertiesMapper.mapProperties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PropertiesMapperTest {

    @Test
    void mapPropertiesTest() {
        ConfigurationRequest configurationRequest = new ConfigurationRequest();
        Map<String, String> crprop = new HashMap<>();
        crprop.put("name", "an object name");
        crprop.put("protocolVersion", "1");
        crprop.put("protocolRevision", "14");
        configurationRequest.setProperties(crprop);
        List<Property> properties = mapProperties(configurationRequest);
        assertNotNull(properties);
        assertEquals(3, properties.size());
        Property name = properties.get(0);
        assertEquals("an object name", name.getValue());
    }

}