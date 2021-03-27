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

    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }
}
