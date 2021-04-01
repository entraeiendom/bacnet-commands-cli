package no.entra.bacnet.cli.sdk;

import no.entra.bacnet.cli.sdk.device.Device;
import no.entra.bacnet.cli.sdk.device.DeviceMapper;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BacnetJsonMapperTest {
    private final String configurationRequestJson = "{\n" +
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

    @Test
    void mapTest() {

        BacnetMessage bacnetMessage = BacnetJsonMapper.map(configurationRequestJson);
        assertNotNull(bacnetMessage);
        assertEquals("Device", bacnetMessage.getConfigurationRequest().getProperty("ObjectType"));
        Instant now = Instant.now();
        System.out.println("Now: " + now.toString());
        assertEquals(Instant.parse("2021-03-28T17:39:11.695947Z"),bacnetMessage.getConfigurationRequest().getObservedAt() );
        assertEquals("unknown", bacnetMessage.getSender().getName());
        assertEquals("IAm", bacnetMessage.getService());
        assertEquals(null, bacnetMessage.getInvokeId());
    }

    @Test
    void readPropertyMinimal() {
        String readPropertyResponse = "{\"invokeId\":0,\"sender\":\"unknown\",\"service\":\"ReadProperty\"}";
        BacnetMessage bacnetMessage = BacnetJsonMapper.map(readPropertyResponse);
        assertNotNull(bacnetMessage);
        assertEquals("ReadProperty", bacnetMessage.getService());
        assertEquals(0, bacnetMessage.getInvokeId());
    }

    @Test
    void mapDevice() {
        BacnetMessage bacnetMessage = BacnetJsonMapper.map(configurationRequestJson);
        assertNotNull(bacnetMessage);
        Sender sender = new Sender();
        sender.setInstanceNumber(8);
        sender.setIpAddress("127.0.0.1");
        sender.setPort(47808);
        Device device = DeviceMapper.mapFromConfigurationRequest(sender, bacnetMessage.getConfigurationRequest());
        assertNotNull(device);
        assertEquals(8, device.getInstanceNumber());
        assertEquals("127.0.0.1", device.getIpAddress());
        assertEquals(47808, device.getPortNumber());
        assertEquals(Instant.parse("2021-03-28T17:39:11.695947Z"),device.getObservedAt() );
    }

    @Test
    void mapFromJsonInstanceNuber() {
        String readPropertiesMultiple = "{\n" +
                "    \"invokeId\": 0,\n" +
                "    \"configurationRequest\": {\n" +
                "      \"observedAt\": \"2021-04-01T13:37:47.908Z\",\n" +
                "      \"name\": \"FWFCU\",\n" +
                "      \"source\": {\n" +
                "        \"deviceId\": \"TODO\",\n" +
                "        \"objectId\": \"Device_8\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"sender\": \"unknown\",\n" +
                "    \"service\": \"ReadPropertyMultiple\"\n" +
                "  }";
        BacnetMessage bacnetMessage = BacnetJsonMapper.map(readPropertiesMultiple);
        assertNotNull(bacnetMessage);
        assertEquals("ReadPropertyMultiple", bacnetMessage.getService());
        ConfigurationRequest configurationRequest = bacnetMessage.getConfigurationRequest();
        assertNotNull(configurationRequest);
        assertNotNull(configurationRequest.getSource());
        assertNotNull(configurationRequest.getProperties());
        assertNotNull(configurationRequest.getObjectIdentifier());
        ObjectId expectedObjectId = new ObjectId(ObjectType.Device, "8");
        assertEquals(expectedObjectId, configurationRequest.getObjectIdentifier());
        assertNotNull(bacnetMessage.getProperties());

    }
}