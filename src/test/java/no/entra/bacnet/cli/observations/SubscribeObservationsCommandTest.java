package no.entra.bacnet.cli.observations;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SubscribeObservationsCommandTest {

    @Test
    void subscribeToObservations() {
        String[] args = { "-ip=192.168.2.118", "analogValue", "10" };
        SubscribeObservationsCommand subscribeCommand = new SubscribeObservationsCommand();
        new CommandLine(subscribeCommand).parseArgs(args);
        assertEquals("192.168.2.118", subscribeCommand.ipAddress);
        assertEquals(BACNET_DEFAULT_PORT, subscribeCommand.port);
        //Validating subcommand options, parameters and arguments need better understanding of picocli.
    }
}