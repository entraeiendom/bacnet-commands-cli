package no.entra.bacnet.cli;

import no.entra.bacnet.cli.device.FindObjectNameCommand;
import no.entra.bacnet.cli.device.FindSupportedServicesCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Bacnet Commands
 *
 */
@Command(name = "bacnet", mixinStandardHelpOptions = true, version = "0.1.0",
        description = "Find devices, read their names and get present value from sensors.",
        subcommands = {
                FindSupportedServicesCommand.class,
                FindObjectNameCommand.class
        })
//class Bacnet implements Callable<Integer> {

//    @Parameters(index = "0", description = "The file whose checksum to calculate.")
//    private File file;
class Bacnet {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port default is 47808")
    private int port = 47808;

//    @Override
//    public Integer call() throws Exception { // your business logic goes here...
//        byte[] fileContents = Files.readAllBytes(file.toPath());
//        byte[] digest = MessageDigest.getInstance(algorithm).digest(fileContents);
//        System.out.printf("%0" + (digest.length*2) + "x%n", new BigInteger(1, digest));
//        return 0;
//    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.

    public static void main(String... args) {
        int exitCode = new CommandLine(new Bacnet()).execute(args);
        System.exit(exitCode);
    }
}
