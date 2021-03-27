package no.entra.bacnet.cli.utils;

public class BacnetUtils {
    public static String parseToHex(String hexString) {
        int messageLength = HexUtils.findMessageLength(hexString);
        hexString = hexString.substring(0, messageLength);
        return hexString;
    }
}
