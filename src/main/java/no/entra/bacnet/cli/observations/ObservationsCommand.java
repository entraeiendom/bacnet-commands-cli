package no.entra.bacnet.cli.observations;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;

@CommandLine.Command(name = "observations",
        subcommands = {SubscribeObservationsCommand.class, CancelSubscriptionsObservationsCommand.class, ListSubscriptionsCommand.class, AcknowledgeObservationReceivedCommand.class, CommandLine.HelpCommand.class},
        description = "Subscribe to observations from sensors.")
public class ObservationsCommand {
    @Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @Option(names = {"-p", "--port"}, description = "Bacnet Port. Default is 47808")
    private int port = BACNET_DEFAULT_PORT;

}
