package no.entra.bacnet.cli.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BacnetUtilsTest {

    @Test
    void findLengthTest() {
        String expectedHex = "000c";
        byte[] byteArray = new byte[] {-127, 11, 0, 12, 1, 32, -1, -1, 0, -1, 16, 8, 0, 0, 0};
        String lenghtHex = BacnetUtils.findLengthHex(byteArray);
        assertEquals(expectedHex, lenghtHex);
    }
    @Test
    void parseToHex() {
        byte[] byteArray = new byte[] {-127, 11, 0, 12, 1, 32, -1, -1, 0, -1, 16, 8, 0, 0, 0};
        String expectedHex = "810b000c0120ffff00ff1008";
        assertEquals(expectedHex, BacnetUtils.parseToHex(byteArray));
    }

    @Test
    void trimToValidHexTest() {
        String expectedHex = "810b00190120ffff00ff1000c4020000082205c491032201f6";
        String hexFromByteArray = "810b00190120ffff00ff1000c4020000082205c491032201f600000000000000";
        assertEquals(expectedHex, BacnetUtils.trimToValidHex(hexFromByteArray));
    }
}