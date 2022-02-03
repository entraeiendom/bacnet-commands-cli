package no.entra.bacnet.cli.observations;

import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import no.entra.bacnet.sdk.commands.cov.ConfirmedSubscribeCovCommand;
import no.entra.bacnet.sdk.commands.cov.SubscribeCovCommand;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.net.InetAddress;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;

@CommandLine.Command(name = "subscribe", mixinStandardHelpOptions = true, version = "4.0",
        description = "Subscribe to observations from one sensor.")
public class SubscribeObservationsCommand {
    private static final int SUBSCRIBE_10_MINUTES = 10 * 60;
    public static final int SUBSCRIBE_BACNET_DEFAULT = 8 * 60 * 60; //8 hours
    @Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device", arity = "1")
    String ipAddress = "127.0.0.1";
    @Option(names = {"-p", "--port"}, description = "Bacnet Port. Default is 47808", arity = "0.1")
    int port = BACNET_DEFAULT_PORT;
    @Option(names = {"-i", "--instance"}, description = "Instance number on the BacnetDevice", arity = "0.1")
    int instanceNumber = 1;
    int subscriptionLengthSeconds = SUBSCRIBE_10_MINUTES;

    @CommandLine.Command(name = "analogValue", description = "Subscribe to observations from an analogValue output, " +
            "from a single BacnetDevice")
    void analogValue(@Parameters(paramLabel = "instance", description = "Instance number on the Bacnet Device") Integer instanceNumberParam,
                     @Parameters(paramLabel = "subscriptionLength", description = "Subsctiptionlength in seconds. Use 0 for indefinite period of time.", arity = "0.1") Integer subscriptionLengthParam) {
        if (instanceNumberParam != null) {
            this.instanceNumber = instanceNumberParam;
        }
        if (subscriptionLengthParam != null) {
            subscriptionLengthSeconds = subscriptionLengthParam;
        }
        int subscriptionId = 1;
        try {
            ObjectId analogValue1 = new ObjectId(ObjectType.AnalogValue, 1);
            InetAddress sendToAddress = SubscribeCovCommand.inetAddressFromString(ipAddress);
            SubscribeCovCommand covCommand = new ConfirmedSubscribeCovCommand(sendToAddress, subscriptionId, analogValue1);
            covCommand.setLifetimeSeconds(subscriptionLengthSeconds);
            covCommand.setInvokeId(15);
            covCommand.execute();
            System.out.println(String.format("Subscribe to observations from BacnetDevice %s:%s analog-value-%s ", ipAddress, port, instanceNumber));
            System.out.println("Please note to remember the subscriptionId. You will need this one to cancel the subscription later. subscriptionId: " + subscriptionId);
        } catch (IOException e) {
            System.err.println(String.format("Failed to subscribe to observations from BacnetDevice %s:%s analog-value-%s.\nReason:%s ", ipAddress, port, instanceNumber, e));

        }
    }

    @CommandLine.Command(name = "analogInput", description = "Subscribe to observations from an analogInput, " +
            "from a single BacnetDevice")
    void analogInput(@Parameters(paramLabel = "instance", description = "Instance number on the Bacnet Device") Integer instanceNumberParam,
                     @Parameters(paramLabel = "subscriptionLength", description = "Subsctiptionlength in seconds. Use 0 for indefinite period of time.", arity = "0.1") Integer subscriptionLengthParam) {
        if (instanceNumberParam != null) {
            this.instanceNumber = instanceNumberParam;
        }
        if (subscriptionLengthParam != null) {
            subscriptionLengthSeconds = subscriptionLengthParam;
        }
        int subscriptionId = 1;
        try {
            ObjectId analogInput = new ObjectId(ObjectType.AnalogInput, 1);
            InetAddress sendToAddress = SubscribeCovCommand.inetAddressFromString(ipAddress);
            SubscribeCovCommand covCommand = new ConfirmedSubscribeCovCommand(sendToAddress, subscriptionId, analogInput);
            covCommand.setLifetimeSeconds(subscriptionLengthSeconds);
            covCommand.setInvokeId(15);
            covCommand.execute();
            System.out.println(String.format("Subscribe to observations from BacnetDevice %s:%s analog-value-%s ", ipAddress, port, instanceNumber));
            System.out.println("Please note to remember the subscriptionId. You will need this one to cancel the subscription later. subscriptionId: " + subscriptionId);
        } catch (IOException e) {
            System.err.println(String.format("Failed to subscribe to observations from BacnetDevice %s:%s analog-value-%s.\nReason:%s ", ipAddress, port, instanceNumber, e));

        }
    }
}
