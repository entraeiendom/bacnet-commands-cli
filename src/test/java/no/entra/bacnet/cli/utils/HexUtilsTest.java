package no.entra.bacnet.cli.utils;

import org.junit.jupiter.api.Test;

import static no.entra.bacnet.cli.utils.HexUtils.findMessageLength;
import static no.entra.bacnet.cli.utils.HexUtils.toInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}