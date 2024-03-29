package no.entra.bacnet.cli.device;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.net.*;
import java.util.Locale;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;
import static no.entra.bacnet.cli.utils.HexUtils.hexStringToByteArray;

@Command(name = "devices",
        subcommands = { FindObjectNameCommand.class, CommandLine.HelpCommand.class },
        description = "Find and List Devices.")
public class DevicesCommand {
    @Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device", arity = "0.1")
    private String ipAddress = "127.0.0.1";
    @Option(names = {"-p", "--port"}, description = "Bacnet Port. Default is 47808", arity = "0.1")
    private int port = BACNET_DEFAULT_PORT;

    @CommandLine.Command(name = "find", description = "Find Devices")
    void findDevices(@Parameters(paramLabel = "ipAddress", description = "IP Address to the Device", arity="0.1") String ipAddressParam,
                     @Parameters(paramLabel = "port", description = "Bacnet Port. Default is 47808", arity = "0.1") Integer portParam) {
        if (ipAddressParam != null) {
            this.ipAddress = ipAddressParam;
        }
        if (portParam != null) {
            this.port = portParam;
        }

        System.out.println(String.format("Send WhoIs to find devices using ip %s:%s", ipAddress, port));
        try {
            sendWhoIsMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void sendWhoIsMessage() throws IOException, InterruptedException {
        String hexString = "810b000c0120ffff00ff1008";
        send(hexString);
    }
    void send(String hexString) throws IOException, InterruptedException {

        SocketAddress inetAddress = new InetSocketAddress(port);
        InetAddress sendToAddress = InetAddress.getByName(ipAddress);
        DatagramSocket socket = new DatagramSocket(null);
        socket.setBroadcast(true);
        socket.setReuseAddress(true);
        socket.bind(inetAddress);
        byte[] buf = hexStringToByteArray(hexString);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, sendToAddress, BACNET_DEFAULT_PORT);
        System.out.println(String.format("Sending: %s", packet));
        socket.send(packet);
        Thread.sleep(100);
        socket.close();
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

    @CommandLine.Command(name = "hexString", description = "Send supplied Bacnet HexString")
    void sendHex(@CommandLine.Parameters(paramLabel = "ipAddress", description = "IP Address to the Device") String ipAddressParam,
                 @CommandLine.Parameters(paramLabel = "hexString", description = "Bacnet HexString to send") String hexString) {
        //810a001701040275000e0c020000081e094d0962098b1f
        this.ipAddress = ipAddressParam;
        try {
            send(hexString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
