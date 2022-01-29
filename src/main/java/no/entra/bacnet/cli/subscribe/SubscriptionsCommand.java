package no.entra.bacnet.cli.subscribe;

import picocli.CommandLine;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;

@CommandLine.Command(name = "subscriptions",
        subcommands = { SubscribeObservationsCommand.class, CommandLine.HelpCommand.class },
        description = "Subscribe to observations from sensors.")
public class SubscriptionsCommand {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port. Default is 47808")
    private int port = BACNET_DEFAULT_PORT;
}


