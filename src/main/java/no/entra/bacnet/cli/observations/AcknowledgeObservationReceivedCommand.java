package no.entra.bacnet.cli.observations;

import no.entra.bacnet.sdk.commands.acknowledge.AcknowledgeCommand;
import no.entra.bacnet.sdk.commands.cov.SubscribeCovCommand;
import no.entra.bacnet.services.ConfirmedServiceChoice;
import picocli.CommandLine;

import java.io.IOException;
import java.net.InetAddress;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;

@CommandLine.Command(name = "ack", mixinStandardHelpOptions = true, version = "4.0",
        description = "Acknowledge reception of a ConfirmedCOVNotification")
public class AcknowledgeObservationReceivedCommand {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device", arity = "1")
    String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port. Default is 47808", arity = "0.1")
    int port = BACNET_DEFAULT_PORT;

    @CommandLine.Command(name = "single", description = "Acknowledge receipt of a COVNotification containing a single observation")
    void subscribeToObservations(@CommandLine.Parameters(paramLabel = "invokeId", description = "InvokeId received from the Bacnet device.\n" +
            "aka the invokeId in the COVNotification you will acknowledge") Integer invokeId) {
        try {
            InetAddress sendToAddress = SubscribeCovCommand.inetAddressFromString(ipAddress);
            AcknowledgeCommand covCommand = new AcknowledgeCommand(sendToAddress, invokeId, ConfirmedServiceChoice.ConfirmedCovNotification);
            System.out.println("hexString" + covCommand.buildHexString());
            covCommand.execute();
            System.out.println(String.format("Acknowledge observations from BacnetDevice %s:%s invokeId-%s ", ipAddress, port, invokeId));
        } catch (IOException e) {
            System.err.println(String.format("Failed to acknowledge observations from BacnetDevice %s:%s analog-value-%s.\nReason:%s ", ipAddress, port, invokeId, e));

        }
    }

    public static void main(String[] args) {
        AcknowledgeObservationReceivedCommand command = new AcknowledgeObservationReceivedCommand();
        command.ipAddress = "192.168.2.118";
        command.subscribeToObservations(15);
    }

}
