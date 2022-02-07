package no.entra.bacnet.cli.observations;

import no.entra.bacnet.sdk.commands.cov.ListCovSubscriptionsCommand;
import picocli.CommandLine;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;

@CommandLine.Command(name = "list", mixinStandardHelpOptions = true, version = "4.0",
        description = "List active COV subscriptions on one bacnet device.")
public class ListSubscriptionsCommand implements Runnable {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device", arity = "1")
    String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port. Default is 47808", arity = "0.1")
    int port = BACNET_DEFAULT_PORT;

    @CommandLine.Parameters(paramLabel = "deviceId", description = "instance number of the device", arity = "1")
    private int deviceInstanceId;

    @Override
    public void run() {
        System.out.println(String.format("Find active COV Subscriptions for device %s on ip %s:%s",deviceInstanceId, ipAddress, port));
        InetAddress sendToAddress = null;
        try {
            sendToAddress = InetAddress.getByName(ipAddress);
            ListCovSubscriptionsCommand command = new ListCovSubscriptionsCommand(sendToAddress, deviceInstanceId,0);
            command.execute();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
