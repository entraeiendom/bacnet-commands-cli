package no.entra.bacnet.cli.device;

import picocli.CommandLine;

@CommandLine.Command(name = "findServices", mixinStandardHelpOptions = true, version = "4.0",
        description = "Find which bacnet services the device is supporting.")
public class FindSupportedServicesCommand implements Runnable {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port default is 47808")
    private int port = 47808;

    @Override
    public void run() {
        System.out.println(String.format("Find Supported services for ip %s:%s",ipAddress, port));
    }
}
