package no.entra.bacnet.cli.utils;

import java.util.Arrays;

import static no.entra.bacnet.cli.listener.ByteHexConverter.integersToHex;

public class BacnetUtils {

    public static String findLengthHex(byte[] bacnetMessage) {
        byte[] lenghtArray = Arrays.copyOfRange(bacnetMessage, 2, 4);
        String hexString = integersToHex(lenghtArray);

        return hexString;
    }
    public static String parseToHex(byte[] bacnetMessage) {
        byte[] lenghtArray = Arrays.copyOfRange(bacnetMessage, 4, 8);
        String hexString = integersToHex(lenghtArray);

        return hexString;
    }
    public static String trimToValidHex(String hexString) {
        int messageLength = HexUtils.findMessageLength(hexString);
        hexString = hexString.substring(0, messageLength);
        return hexString;
    }
}
