package no.entra.bacnet.cli.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static no.entra.bacnet.cli.utils.HexUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class HexUtilsTest {

    @Test
    void toIntTest() {
        assertThrows(IllegalArgumentException.class, () -> toInt(""));
        String isnull = null;
        assertThrows(IllegalArgumentException.class, () -> toInt(isnull));
        assertEquals(12, toInt("0c"));
    }

    @Test
    public void findMessageLengthTest() {
        String expectedContent = "810a002a01040005020109121c020003e92c0080000139004e09552e44400000002f096f2e8204002f4f";
        String bacnetMessageAsHex = expectedContent + "00000000000000000000000000000000000";
        assertEquals(expectedContent.length(), findMessageLength(bacnetMessageAsHex));
    }

    @Test
    void hexStringToByteArrayTest() {
        String hexString = "810b000c0120ffff00ff1008";
        byte[] bytesAsIntArr = hexStringToByteArray(hexString);
        byte[] expectedArray = new byte[] {-127, 11, 0, 12, 1, 32, -1, -1, 0, -1, 16, 8};
        assertTrue(Arrays.equals(expectedArray, bytesAsIntArr));
    }
}