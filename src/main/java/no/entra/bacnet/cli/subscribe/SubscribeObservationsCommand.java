package no.entra.bacnet.cli.subscribe;

import picocli.CommandLine;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;

@CommandLine.Command(name = "observation", mixinStandardHelpOptions = true, version = "4.0",
        description = "Subscribe to observations from one sensor.")
public class SubscribeObservationsCommand {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port. Default is 47808")
    private int port = BACNET_DEFAULT_PORT;
    @CommandLine.Option(names = {"-i", "--instance"}, description = "Instance number on the BacnetDevice")
    private int instanceNumber = 1;

    @CommandLine.Command(name = "analogValue", description = "Subscribe to observations from an analogValue output, " +
            "from a single BacnetDevice")
    void subscribeToObservations(@CommandLine.Parameters(paramLabel = "ipAddress", description = "IP Address to the BacnetDevice") String ipAddressParam,
                                 @CommandLine.Parameters(paramLabel = "port", description = "Bacnet Port. Default is 47808", arity = "0.1") Integer portParam,
                                 @CommandLine.Parameters(paramLabel = "instance", description = "Instance number on the Bacent Device") Integer instanceNumberParam) {

        if (ipAddressParam != null) {
            this.ipAddress = ipAddressParam;
        }
        if (portParam != null) {
            this.port = portParam;
        }
        if (instanceNumberParam != null) {
            this.instanceNumber = instanceNumberParam;
        }

        System.out.println(String.format("Subscribe to observations from BacnetDevice %s:%s analog-value-%s", ipAddress, port, instanceNumber));
    }
}
