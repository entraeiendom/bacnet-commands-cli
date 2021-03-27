package no.entra.bacnet.cli.device;

import picocli.CommandLine;

import java.util.Locale;

@CommandLine.Command(name = "devices",
//        subcommands = { SubcommandAsClass.class, CommandLine.HelpCommand.class },
        description = "Resolves ISO country codes (ISO-3166-1) or language codes (ISO 639-1/-2)")
public class DevicesCommand {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port. Default is 47808")
    private int port = 47808;

    @CommandLine.Command(name = "find", description = "Find Devices")
    void findDevices(@CommandLine.Parameters(paramLabel = "ipAddress", description = "IP Address to the Device") String ipAddressParam,
                     @CommandLine.Parameters(paramLabel = "port", description = "Bacnet Port. Default is 47808", arity = "0.1") Integer portParam ) {

        if (ipAddressParam != ipAddressParam) {
            this.ipAddress = ipAddressParam;
        }
        if (portParam != null) {
            this.port = portParam;
        }

        System.out.println(String.format("Find devices using IP %s:%s", ipAddress, port));

    }
    /*
    @CommandLine.Command(name = "find", description = "Resolves ISO country codes (ISO-3166-1)")
    void findDevices(
            @CommandLine.Parameters(arity = "1..*", paramLabel = "<countryCode>",
                    description = "country code(s) to be resolved") String[] countryCodes) {

        for (String code : countryCodes) {
            System.out.printf("%s: %s",
                    code.toUpperCase(), new Locale("", code).getDisplayCountry());
        }
    }
    */

    @CommandLine.Command(name = "list", description = "Resolves ISO country codes (ISO-3166-1)")
    void listDevices(
            @CommandLine.Parameters(arity = "1..*", paramLabel = "<countryCode>",
                    description = "country code(s) to be resolved") String[] countryCodes) {

        for (String code : countryCodes) {
            System.out.printf("%s: %s",
                    code.toUpperCase(), new Locale("", code).getDisplayCountry());
        }
    }
}
