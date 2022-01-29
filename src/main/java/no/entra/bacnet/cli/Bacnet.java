package no.entra.bacnet.cli;

import no.entra.bacnet.cli.device.DevicesCommand;
import no.entra.bacnet.cli.device.FindObjectNameCommand;
import no.entra.bacnet.cli.device.FindSupportedServicesCommand;
import no.entra.bacnet.cli.listener.BacnetListen;
import no.entra.bacnet.cli.subscribe.SubscriptionsCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Bacnet Commands
 */
@Command(name = "bacnet", mixinStandardHelpOptions = true, version = "0.2.3-SNAPSHOT",
        description = "Find devices, read their names and get present value from sensors.",
        subcommands = {
                FindSupportedServicesCommand.class,
                FindObjectNameCommand.class,
                DevicesCommand.class,
                SubscriptionsCommand.class,
                BacnetListen.class
        })
class Bacnet {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port default is 47808")
    private int port = 47808;

    public static void main(String... args) {
        System.out.println("Version is 0.2.3-SNAPSHOT");
        int exitCode = new CommandLine(new Bacnet()).execute(args);
        System.exit(exitCode);
    }
}
