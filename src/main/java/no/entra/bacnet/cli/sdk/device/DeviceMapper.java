package no.entra.bacnet.cli.sdk.device;

import no.entra.bacnet.cli.sdk.ConfigurationRequest;
import no.entra.bacnet.cli.sdk.Sender;
import org.slf4j.Logger;

import java.time.Instant;
import java.util.Map;

import static no.entra.bacnet.cli.sdk.utils.JsonPathHelper.getStringFailsafeNull;
import static no.entra.bacnet.cli.sdk.utils.JsonPathHelper.hasElement;
import static no.entra.bacnet.json.utils.StringUtils.hasValue;
import static org.slf4j.LoggerFactory.getLogger;

public class DeviceMapper {
    private static final Logger log = getLogger(DeviceMapper.class);

    /**
     * {
     *   "configurationRequest": {
     *     "observedAt": "2020-01-10T12:46:36.087069",
     *     "id": "TODO",
     *     "properties": {
     *       "DeviceInstanceRangeHighLimit": "1966",
     *       "DeviceInstanceRangeLowLimit": "1966"
     *                }*   },
     *   "sender": {
     *     "ip": "127.0.0.1",
     *     "port": "47808",
     *     "instanceNumber": 2002
     *   }    ,
     *   "service ": "WhoIs "
     * }
     * or
     * {
     *   "sender": "unknown",
     *   "service": "GetAlarmSummary",
     *   "observation": {
     *     "unit": "DegreesCelcius",
     *     "observedAt": "2020-01-16T14:38:19.951845",
     *     "name": "tfmtag-example",
     *     "description": "Room 1013, section 1, floor 1",
     *     "source": {
     *       "deviceId": "TODO",
     *       "objectId": "AnalogInput 3000"
     *                },
     *     "value": 22.170061*   }
     * }
     * 
     * @param bacnetJson
     * @return
     */
    public static Device mapFromJson(String bacnetJson) {
        Device deviceId = new Device();
        String ipAddressKey = "$.sender.ip";
        String ipAddress = getStringFailsafeNull(bacnetJson, ipAddressKey);
        deviceId.setIpAddress(ipAddress);
        String portKey = "$.sender.port";
        String portValue = getStringFailsafeNull(bacnetJson, portKey);
        Integer port = parseInteger(portValue);
        deviceId.setPortNumber(port);
        String instanceNumberKey = "$.sender.instanceNumber";
        String instanceNumberString = getStringFailsafeNull(bacnetJson, instanceNumberKey);
        Integer instanceNumber = findIntegerInString(instanceNumberString);
        if (instanceNumber == null) {
            instanceNumber = lookForInstanceNumberProperty(bacnetJson);
        }
        if (instanceNumber != null) {
            deviceId.setInstanceNumber(instanceNumber);
        }
        String observedAtKey = "$.configurationRequest.observedAt";
        String observedAtString = getStringFailsafeNull(bacnetJson, observedAtKey);
        Instant observedAt = findInstantInString(observedAtString);
        deviceId.setObservedAt(observedAt);
        /*
        if (hasElement(bacnetJson, "$.sender.gateway")) {
            String gatwayInstanceNumberKey = "$.sender.gateway.instanceNumber";
            String gatewayInstanceNumberString = getStringFailsafeNull(bacnetJson, gatwayInstanceNumberKey);
            Integer gatewayInstanceNumber = findIntegerInString(gatewayInstanceNumberString);
            if (gatewayInstanceNumber != null) {
                deviceId.setGatewayInstanceNumber(gatewayInstanceNumber);
            }
            String gatwayDeviceIdKey = "$.sender.gateway.deviceId";
            String gatewayDeviceIdString = getStringFailsafeNull(bacnetJson, gatwayDeviceIdKey);
            Integer gatewayDeviceId = findIntegerInString(gatewayDeviceIdString);
            if (gatewayDeviceId != null) {
                deviceId.setGatewayDeviceId(gatewayDeviceId);
            }
        }

         */
        String configurationObjectNameKey = "$.configurationRequest.properties.ObjectName";
        if (hasElement(bacnetJson, configurationObjectNameKey)) {
            String tfmTag = getStringFailsafeNull(bacnetJson, configurationObjectNameKey);
            if (tfmTag != null) {
                deviceId.setTfmTag(tfmTag);
            }
        }
        String observationNameKey = "$.observation.name";
        if (hasElement(bacnetJson, observationNameKey)) {
            String tfmTag = getStringFailsafeNull(bacnetJson, observationNameKey);
            if (tfmTag != null) {
                deviceId.setTfmTag(tfmTag);
            }
        }
        return deviceId;
    }

    private static Instant findInstantInString(String instantString) {
        Instant instant = null;
        try {
            instant = Instant.parse(instantString);
        } catch (Exception e) {
            log.trace("Can not parse Instant from [{}]", instantString);
        }
        return instant;
    }

    private static Integer findIntegerInString(String instanceNumberString) {
        Integer instanceNumber = null;
        if (hasValue(instanceNumberString)) {
            try {
                instanceNumber = Integer.valueOf(instanceNumberString);
            } catch (NumberFormatException e) {
                log.trace("Could not parse {} to integer.", instanceNumberString);
            }
        }
        return instanceNumber;
    }

    static Integer lookForInstanceNumberProperty(String bacnetJson) {
        Integer instanceNumber = null;
        String serviceKey = "$.service";
        String serviceValue = getStringFailsafeNull(bacnetJson,serviceKey);
        if (serviceValue != null && serviceValue.equals("IAm")) {
            String instanceNumberKey = "$.configurationRequest.properties.InstanceNumber";
            String instanceNumberString = getStringFailsafeNull(bacnetJson, instanceNumberKey);
            instanceNumber = findIntegerInString(instanceNumberString);
        }
        return instanceNumber;
    }

    /*
    {
    "configurationRequest": {
      "observedAt": "2021-03-28T18:10:13.328630Z",
      "id": "TODO",
      "properties": {
        "ObjectType": "Device",
        "InstanceNumber": "8",
        "MaxADPULengthAccepted": "1476",
        "SegmentationSupported": "NoSegmentation"
      }
    },
    "sender": "unknown",
    "service": "IAm"
  }
     */
    public static Device mapFromConfigurationRequest(Sender sender, ConfigurationRequest configurationRequest) {
        Device device = null;

        Map<String, String> props = configurationRequest.getProperties();
        String objectType = props.get("ObjectType");
        if (objectType.equals("Device")) {
            device = new Device();
            String instanceNumberVale = props.get("InstanceNumber");
            Integer instanceNumber = parseInteger(instanceNumberVale);
            device.setInstanceNumber(instanceNumber);
            String maxApduLengthValue = props.get("MaxADPULengthAccepted");
            Integer maxApduLength = parseInteger(maxApduLengthValue);
            device.setMaxAPDULengthAccepted(maxApduLength);
            String segmentationSupported = props.get("SegmentationSupported");
            if (segmentationSupported.equalsIgnoreCase("NoSegmentation")) {
                device.setSegmentationSupported(false);
            }
            device.setIpAddress(sender.getIpAddress());
            device.setPortNumber(sender.getPort());
            device.setObservedAt(configurationRequest.getObservedAt());

        }
        return device;
    }

    private static int parseInteger(String integerValue) {
        Integer value = null;
        try {
            value = Integer.parseInt(integerValue);
        } catch (NumberFormatException nfe) {
            log.trace("Could not parse Integer from [{}]", integerValue);
        }
        return value;
    }
}
