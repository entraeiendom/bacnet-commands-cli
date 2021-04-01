package no.entra.bacnet.cli.device;

import no.entra.bacnet.cli.sdk.commands.properties.ReadPropertyMultipleCommand;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;
import no.entra.bacnet.objects.PropertyIdentifier;
import picocli.CommandLine;

import java.io.IOException;
import java.net.*;

import static no.entra.bacnet.BacnetConstants.BACNET_DEFAULT_PORT;
import static no.entra.bacnet.cli.utils.HexUtils.hexStringToByteArray;

@CommandLine.Command(name = "objectName", mixinStandardHelpOptions = true, version = "4.0",
        description = "Find objectName of the device")
public class FindObjectNameCommand implements Runnable {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port default is 47808")
    private int port = BACNET_DEFAULT_PORT;
    @CommandLine.Option(names = {"-i", "--instanceId"}, description = "Bacnet InstanceId")
    private int instanceNumber = 0;

    @Override
    public void run() {
        InetAddress sendToAddress = null;
        try {
            sendToAddress = InetAddress.getByName(ipAddress);
            ObjectId objectId = new ObjectId(ObjectType.Device, Integer.valueOf(instanceNumber).toString());
            ReadPropertyMultipleCommand objectNameCommand = new ReadPropertyMultipleCommand
                    .ReadPropertyMultipleCommandBuilder(sendToAddress)
                    .withObjectId(objectId)
                    .withInvokeId(0)
                    .withPropertyIdentifier(PropertyIdentifier.ObjectName)
                    .withPropertyIdentifier(PropertyIdentifier.ProtocolVersion)
                    .withPropertyIdentifier(PropertyIdentifier.ProtocolRevision)
                    .build();
            String hexString = objectNameCommand.buildHexString();
//            objectNameCommand.send();
            sendFindObjectNameMessage(hexString);
            System.out.println(String.format("Find ObjectName command is sent to %s, instanceId %s with hexString %s",ipAddress, instanceNumber, hexString));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void sendFindObjectNameMessage(String hexString) throws IOException, InterruptedException {

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
}
