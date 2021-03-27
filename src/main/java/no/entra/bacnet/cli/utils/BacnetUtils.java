package no.entra.bacnet.cli.utils;

import java.util.Arrays;

import static no.entra.bacnet.cli.utils.HexUtils.integersToHex;
import static no.entra.bacnet.cli.utils.HexUtils.toInt;

public class BacnetUtils {

    public static String findLengthHex(byte[] bacnetMessage) {
        byte[] lenghtArray = Arrays.copyOfRange(bacnetMessage, 2, 4);
        String hexString = integersToHex(lenghtArray);

        return hexString;
    }
    public static String parseToHex(byte[] bacnetMessage) {
        byte[] lengthArray = Arrays.copyOfRange(bacnetMessage, 2, 4);

        String hexLength = HexUtils.integersToHex(lengthArray);
        byte[] hexLengthArr = HexUtils.hexStringToByteArray(hexLength);
        int length = byteArrayToInt(hexLengthArr);
        byte[] messageArray = Arrays.copyOfRange(bacnetMessage, 0, length);
        String hexString = integersToHex(messageArray);

        return hexString;
    }

    public static int byteArrayToInt(byte[] lengthArray) {
        int length = 0;

        String strNum = integersToHex(lengthArray);
        length = toInt(strNum);
        return length;
    }
    public static String trimToValidHex(String hexString) {
        int messageLength = HexUtils.findMessageLength(hexString);
        hexString = hexString.substring(0, messageLength);
        return hexString;
    }

    public static int findMessageLength(String bacnetMessageInHex) {
        String lenghtHex = bacnetMessageInHex.substring(4, 8);
        int length = HexUtils.toInt(lenghtHex);
        return length * 2;
    }
}
