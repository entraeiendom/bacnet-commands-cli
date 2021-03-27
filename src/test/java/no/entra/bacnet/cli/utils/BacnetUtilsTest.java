package no.entra.bacnet.cli.utils;

import org.junit.jupiter.api.Test;

import static no.entra.bacnet.cli.utils.BacnetUtils.parseToHex;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BacnetUtilsTest {

    @Test
    void parseToHexTest() {
        String expectedHex = "810b00190120ffff00ff1000c4020000082205c491032201f6";
        String hexFromByteArray = "810b00190120ffff00ff1000c4020000082205c491032201f600000000000000";
        assertEquals(expectedHex, parseToHex(hexFromByteArray));
    }
}