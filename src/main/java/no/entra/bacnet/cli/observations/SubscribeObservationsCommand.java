package no.entra.bacnet.cli.observations;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;

@CommandLine.Command(name = "subscribe", mixinStandardHelpOptions = true, version = "4.0",
        description = "Subscribe to observations from one sensor.")
public class SubscribeObservationsCommand {
    @Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device", arity = "1")
    private String ipAddress = "127.0.0.1";
    @Option(names = {"-p", "--port"}, description = "Bacnet Port. Default is 47808", arity = "0.1")
    private int port = BACNET_DEFAULT_PORT;
    @Option(names = {"-i", "--instance"}, description = "Instance number on the BacnetDevice", arity = "0.1")
    private int instanceNumber = 1;

    @CommandLine.Command(name = "analogValue", description = "Subscribe to observations from an analogValue output, " +
            "from a single BacnetDevice")
    void subscribeToObservations(@Parameters(paramLabel = "instance", description = "Instance number on the Bacent Device") Integer instanceNumberParam) {

        if (instanceNumberParam != null) {
            this.instanceNumber = instanceNumberParam;
        }

        System.out.println(String.format("Subscribe to observations from BacnetDevice %s:%s analog-value-%s", ipAddress, port, instanceNumber));
    }
}
