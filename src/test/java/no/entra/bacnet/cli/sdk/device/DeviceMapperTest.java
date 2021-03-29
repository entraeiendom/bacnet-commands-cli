package no.entra.bacnet.cli.sdk.device;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeviceMapperTest {

    @Test
    void mapFromJson() {
        String json = "{\n" +
                "    \"configurationRequest\": {\n" +
                "      \"observedAt\": \"2021-03-28T18:10:13.328630Z\",\n" +
                "      \"id\": \"TODO\",\n" +
                "      \"properties\": {\n" +
                "        \"ObjectType\": \"Device\",\n" +
                "        \"InstanceNumber\": \"8\",\n" +
                "        \"MaxADPULengthAccepted\": \"1476\",\n" +
                "        \"SegmentationSupported\": \"NoSegmentation\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"sender\": {\n" +
                "      \"instanceNumber\": \"8\",\n" +
                "      \"ip\": \"127.0.0.1\",\n" +
                "      \"port\": 47808\n" +
                "    },\n" +
                "    \"service\": \"IAm\"\n" +
                "  }";
        Device device = DeviceMapper.mapFromJson(json);
        assertNotNull(device);
        assertEquals(8, device.getInstanceNumber());
        assertEquals("127.0.0.1", device.getIpAddress());
        assertEquals(47808, device.getPortNumber());
        assertEquals(Instant.parse("2021-03-28T18:10:13.328630Z"),device.getObservedAt() );
    }

    @Test
    void mapFromConfigurationRequest() {

    }
}