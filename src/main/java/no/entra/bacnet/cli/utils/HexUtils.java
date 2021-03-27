package no.entra.bacnet.cli.utils;

public class HexUtils {

    public static int toInt(String hexString) throws IllegalArgumentException {
        if (hexString == null || hexString.isEmpty()) {
            throw new IllegalArgumentException("hexString may not be null.");
        }
        return Integer.parseInt(hexString, 16);
    }

    public static int findMessageLength(String bacnetMessageInHex) {
        String lenghtHex = bacnetMessageInHex.substring(4, 8);
        int length = HexUtils.toInt(lenghtHex);
        return length * 2;
    }
}
