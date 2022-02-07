package no.entra.bacnet.cli.device;

import no.entra.bacnet.sdk.commands.device.FindServicesSupportedCommand;
import picocli.CommandLine;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@CommandLine.Command(name = "findServices", mixinStandardHelpOptions = true, version = "4.0",
        description = "Find which bacnet services the device is supporting.")
public class SupportedServicesCommand implements Runnable {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device, enclosed in \"...\"", arity = "1")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port default is 47808")
    private int port = 47808;

    @CommandLine.Parameters(paramLabel = "deviceId", description = "instance number of the device", arity = "1")
    private int deviceInstanceId;

    @Override
    public void run() {
        System.out.println(String.format("Find Supported services for device %s on ip %s:%s",deviceInstanceId, ipAddress, port));
        InetAddress sendToAddress = null;
        try {
            sendToAddress = InetAddress.getByName(ipAddress);
            FindServicesSupportedCommand command = new FindServicesSupportedCommand(sendToAddress,deviceInstanceId,0);
            command.execute();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
