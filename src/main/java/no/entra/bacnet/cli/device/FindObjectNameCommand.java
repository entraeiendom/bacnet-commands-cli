package no.entra.bacnet.cli.device;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "objectName", mixinStandardHelpOptions = true, version = "4.0",
        description = "Find objectName of the device")
public class FindObjectNameCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return null;
    }
}
