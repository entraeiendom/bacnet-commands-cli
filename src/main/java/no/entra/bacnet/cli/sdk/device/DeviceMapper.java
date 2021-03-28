package no.entra.bacnet.cli.sdk.device;

import org.slf4j.Logger;

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
    public static Device parse(String bacnetJson) {
        Device deviceId = new Device();
        String ipAddressKey = "$.sender.ip";
        String ipAddress = getStringFailsafeNull(bacnetJson, ipAddressKey);
        deviceId.setIpAddress(ipAddress);
        String portKey = "$.sender.port";
        String port = getStringFailsafeNull(bacnetJson, portKey);
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
}
