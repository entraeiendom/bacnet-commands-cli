package no.entra.bacnet.cli.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BacnetJsonMapperTest {

    @Test
    void mapTest() {
        final String configurationRequestJson = " {\n" +
                "    \"configurationRequest\": {\n" +
                "      \"observedAt\": \"2021-03-28T18:10:13.328\",\n" +
                "      \"id\": \"TODO\"\n" +
                "    },\n" +
                "    \"sender\": \"unknown\",\n" +
                "    \"service\": \"IAm\"\n" +
                "  }";
        BacnetMessage configurationRequestObject = BacnetJsonMapper.map(configurationRequestJson);
        assertNotNull(configurationRequestObject);
//        assertTrue(configurationRequestObject instanceof ConfigurationRequest);
//        ConfigurationRequest configurationRequest = (ConfigurationRequest) configurationRequestObject;
//        Source expectedSource = new Source("adsf", "adsf");
//        Source source = configurationRequest.getSource();
//        assertEquals(expectedSource, source);
    }
}