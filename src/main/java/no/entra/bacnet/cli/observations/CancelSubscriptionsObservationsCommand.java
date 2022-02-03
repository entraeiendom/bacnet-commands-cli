package no.entra.bacnet.cli.observations;

import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import no.entra.bacnet.sdk.commands.cov.CancelSubscribeCovCommand;
import no.entra.bacnet.sdk.commands.cov.SubscribeCovCommand;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.net.InetAddress;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;

@CommandLine.Command(name = "cancel", mixinStandardHelpOptions = true, version = "4.0",
        description = "Cancel observations from one sensor.")
public class CancelSubscriptionsObservationsCommand {
    @Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device", arity = "1")
    String ipAddress = "127.0.0.1";
    @Option(names = {"-p", "--port"}, description = "Bacnet Port. Default is 47808", arity = "0.1")
    int port = BACNET_DEFAULT_PORT;
    private Integer instanceNumber;
    private Integer subscriptionId;


    @CommandLine.Command(name = "analogValue", description = "Cancel subscription to observations from an analogValue, " +
            "from a single BacnetDevice")
    void cancelAnalogValue(@Parameters(paramLabel = "instance", description = "Instance number on the Bacent Device", arity ="1") Integer instanceNumberParam,
    @Parameters(paramLabel = "subscriptionId", description = "Cancel subscription to observations from analogValue output", arity = "1") Integer subscriptionIdParam) {

        if (instanceNumberParam != null) {
            this.instanceNumber = instanceNumberParam;
        }
        if (subscriptionIdParam != null) {
            this.subscriptionId = subscriptionIdParam;
        }
        try {
            ObjectId analogValue1 = new ObjectId(ObjectType.AnalogValue, instanceNumber);
            InetAddress sendToAddress = SubscribeCovCommand.inetAddressFromString(ipAddress);
            CancelSubscribeCovCommand covCommand = new CancelSubscribeCovCommand(sendToAddress, subscriptionId, analogValue1);
            covCommand.setInvokeId(15);
            covCommand.execute();
            System.out.println(String.format("Canceled subscription on BacnetDevice %s:%s subscriptionId: %s ", ipAddress, port, subscriptionId));
        } catch (IOException e) {
            System.err.println(String.format("Failed to cancel subscription from BacnetDevice %s:%s subscriptionId:%s.\nReason:%s ", ipAddress, port, subscriptionId, e));
        }
    }

    @CommandLine.Command(name = "analogInput", description = "Cancel subscription to observations from an analogInput, " +
            "from a single BacnetDevice")
    void cancelAnalogInput(@Parameters(paramLabel = "instance", description = "Instance number on the Bacent Device", arity ="1") Integer instanceNumberParam,
                           @Parameters(paramLabel = "subscriptionId", description = "Cancel subscription to observations from analogInput output", arity = "1") Integer subscriptionIdParam) {

        if (instanceNumberParam != null) {
            this.instanceNumber = instanceNumberParam;
        }
        if (subscriptionIdParam != null) {
            this.subscriptionId = subscriptionIdParam;
        }
        try {
            ObjectId analogInput = new ObjectId(ObjectType.AnalogInput, instanceNumber);
            InetAddress sendToAddress = SubscribeCovCommand.inetAddressFromString(ipAddress);
            CancelSubscribeCovCommand covCommand = new CancelSubscribeCovCommand(sendToAddress, subscriptionId, analogInput);
            covCommand.setInvokeId(15);
            covCommand.execute();
            System.out.println(String.format("Canceled subscription on BacnetDevice %s:%s subscriptionId: %s ", ipAddress, port, subscriptionId));
        } catch (IOException e) {
            System.err.println(String.format("Failed to cancel subscription from BacnetDevice %s:%s subscriptionId:%s.\nReason:%s ", ipAddress, port, subscriptionId, e));
        }
    }
}
