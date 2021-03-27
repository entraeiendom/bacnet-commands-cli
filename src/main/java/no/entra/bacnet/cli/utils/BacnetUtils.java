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
        byte[] lenghtArray = Arrays.copyOfRange(bacnetMessage, 2, 4);

        int length = Byte.toUnsignedInt(lenghtArray[1]);
        byte[] messageArray = Arrays.copyOfRange(bacnetMessage, 0, length);
        String hexString = integersToHex(messageArray);

        return hexString;
    }
    public static String trimToValidHex(String hexString) {
        int messageLength = HexUtils.findMessageLength(hexString);
        hexString = hexString.substring(0, messageLength);
        return hexString;
    }

    int toInt(int[] array) {
        int result = 0;
        int offset = 1;
        for(int i = array.length - 1; i >= 0; i--) {
            result += array[i]*offset;
            offset *= 10;
        }
        return result;
    }
}
