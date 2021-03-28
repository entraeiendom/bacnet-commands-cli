package no.entra.bacnet.cli.sdk;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BacnetJsonMapperTest {

    @Test
    void mapTest() {
        final String configurationRequestJson = "{\n" +
                "    \"configurationRequest\": {\n" +
                "      \"observedAt\": \"2021-03-28T17:39:11.695947Z\",\n" +
                "      \"id\": \"TODO\",\n" +
                "      \"properties\": {\n" +
                "        \"ObjectType\": \"Device\",\n" +
                "        \"InstanceNumber\": \"8\",\n" +
                "        \"MaxADPULengthAccepted\": \"1476\",\n" +
                "        \"SegmentationSupported\": \"NoSegmentation\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"sender\": \"unknown\",\n" +
                "    \"service\": \"IAm\"\n" +
                "  }";
        BacnetMessage bacnetMessage = BacnetJsonMapper.map(configurationRequestJson);
        assertNotNull(bacnetMessage);
        assertEquals("Device", bacnetMessage.getConfigurationRequest().getProperty("ObjectType"));
        Instant now = Instant.now();
        System.out.println("Now: " + now.toString());
        assertEquals(Instant.parse("2021-03-28T17:39:11.695947Z"),bacnetMessage.getConfigurationRequest().getObservedAt() );
        assertEquals("unknown", bacnetMessage.getSender().getName());
        assertEquals("IAm", bacnetMessage.getService());
//        assertTrue(configurationRequestObject instanceof ConfigurationRequest);
//        ConfigurationRequest configurationRequest = (ConfigurationRequest) configurationRequestObject;
//        Source expectedSource = new Source("adsf", "adsf");
//        Source source = configurationRequest.getSource();
//        assertEquals(expectedSource, source);
    }
}