package no.entra.bacnet.cli.utils;

import org.junit.jupiter.api.Test;

import static no.entra.bacnet.cli.utils.BacnetUtils.byteArrayToInt;
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
    void parseToHexLongMessage() {
        String unconfirmedWhoHas = "810b00c50120ffff00ff10073db70400470050005300560058004400330045003000350035002d004e00430045003300300031002f00500072006f006700720061006d006d0069006e0067002e0045006e0065007200670069002e00420065007200650067006e0069006e006700650072002e0045006e006500720067006900420065007200650067006e0069006e006700650072003400330032003200300031002e0045006e006500720067006900310032002d00320031002e0055006b006500420076";
        byte[] byteArray = HexUtils.hexStringToByteArray(unconfirmedWhoHas);
        String actual = BacnetUtils.parseToHex(byteArray);
        assertEquals(197*2,BacnetUtils.findMessageLength(unconfirmedWhoHas) );
        assertEquals(unconfirmedWhoHas.length(), actual.length()); //expect 386
        assertEquals(unconfirmedWhoHas, actual);
    }
    @Test
    void parseToHexFromByteBuffer() {
        String longBacnetMessage = "810b010e0120ffff00ff10073db70400470050005300560058004400330045003000350035002d004e00430045003300300031002f00500072006f006700720061006d006d0069006e0067002e0045006e0065007200670069002e00420065007200650067006e0069006e006700650072002e0045006e006500720067006900420065007200650067006e0069006e006700650072003400330032003200300031002e0045006e006500720067006900310032002d00320031002e0055006b006500420076" +
                "ababababababababababababababababababababababaabababbababababababababababababababababababababababababababababababababababababababababababababababcd";
        String byteBufferMessage = longBacnetMessage + "00000000000000";
        byte[] byteArray = HexUtils.hexStringToByteArray(byteBufferMessage);
        String actual = BacnetUtils.parseToHex(byteArray);
        assertEquals(270*2,BacnetUtils.findMessageLength(byteBufferMessage) );
        assertEquals(longBacnetMessage.length(), actual.length()); //expect 386
        assertEquals(longBacnetMessage, actual);
    }

    @Test
    void trimToValidHexTest() {
        String expectedHex = "810b00190120ffff00ff1000c4020000082205c491032201f6";
        String hexFromByteArray = "810b00190120ffff00ff1000c4020000082205c491032201f600000000000000";
        assertEquals(expectedHex, BacnetUtils.trimToValidHex(hexFromByteArray));
    }

    @Test
    void findMessageLengthTest() {
        String unconfirmedWhoHas = "810b00c50120ffff00ff10073db70400470050005300560058004400330045003000350035002d004e00430045003300300031002f00500072006f006700720061006d006d0069006e0067002e0045006e0065007200670069002e00420065007200650067006e0069006e006700650072002e0045006e006500720067006900420065007200650067006e0069006e006700650072003400330032003200300031002e0045006e006500720067006900310032002d00320031002e0055006b006500420076";
        assertEquals(unconfirmedWhoHas.length(), BacnetUtils.findMessageLength(unconfirmedWhoHas));
        assertEquals(197*2, BacnetUtils.findMessageLength(unconfirmedWhoHas));
    }

    @Test
    void byteArrayToIntTest() {
        String hexLength = "010E"; //270 int
        byte[] hexLengthArr = HexUtils.hexStringToByteArray(hexLength);
        int length = byteArrayToInt(hexLengthArr);
        assertEquals(270, length);
    }
}