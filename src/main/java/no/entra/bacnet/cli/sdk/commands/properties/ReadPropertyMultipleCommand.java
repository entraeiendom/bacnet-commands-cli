package no.entra.bacnet.cli.sdk.commands.properties;


import no.entra.bacnet.apdu.Apdu;
import no.entra.bacnet.bvlc.Bvlc;
import no.entra.bacnet.cli.sdk.commands.BaseBacnetIpCommand;
import no.entra.bacnet.internal.apdu.MessageType;
import no.entra.bacnet.internal.apdu.SDContextTag;
import no.entra.bacnet.internal.bvlc.BvlcBuilder;
import no.entra.bacnet.internal.bvlc.BvlcFunction;
import no.entra.bacnet.internal.npdu.NpduBuilder;
import no.entra.bacnet.internal.properties.PropertyIdentifier;
import no.entra.bacnet.json.services.ConfirmedServiceChoice;
import no.entra.bacnet.npdu.Npdu;
import no.entra.bacnet.objects.ObjectId;
import no.entra.bacnet.objects.ObjectType;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static no.entra.bacnet.internal.apdu.ArrayTag.ARRAY1_END;
import static no.entra.bacnet.internal.apdu.ArrayTag.ARRAY1_START;
import static no.entra.bacnet.utils.HexUtils.intToHexString;

public class ReadPropertyMultipleCommand extends BaseBacnetIpCommand {
    private final InetAddress sendToAddress;
    private final int invokeId;
    private final ObjectId objectId;
    private Boolean confirmedNotifications = null;
    private Exception error = null;
    private final List<PropertyIdentifier> propertyIdentifiers;

    public ReadPropertyMultipleCommand(InetAddress sendToAddress, ObjectId objectId) {
        this.sendToAddress = sendToAddress;
        this.objectId = objectId;
        invokeId = 0;
        propertyIdentifiers = new ArrayList<>();
    }

    public ReadPropertyMultipleCommand(InetAddress sendToAddress, int invokeId, ObjectId objectId, List<PropertyIdentifier> propertyIdentifiers) {
        this.sendToAddress = sendToAddress;
        this.invokeId = invokeId;
        this.objectId = objectId;
        this.propertyIdentifiers = propertyIdentifiers;
    }

    public String buildHexString() {
        Apdu apdu = Apdu.ApduBuilder.builder()
                .withApduType(MessageType.ConfirmedRequest)
                .isSegmented(false)
                .hasMoreSegments(false)
                .isSegmentedReplyAllowed(true)
                .withMaxSegmentsAcceptedAbove64()
                .withMaxApduLength1476()
                .build();
        String apduHexString = apdu.toHexString()
                + intToHexString(invokeId,2)
                + ConfirmedServiceChoice.ReadPropertyMultiple.getServiceChoiceHex()
                + SDContextTag.TAG0LENGTH4 + objectId.toHexString();
        String propertyIdentifierArray = mapPropertyArray();
        apduHexString += propertyIdentifierArray;
        int numberOfOctets = (apduHexString.length() /2) + 6;
        Bvlc bvlc = new BvlcBuilder(BvlcFunction.OriginalUnicastNpdu).withTotalNumberOfOctets(numberOfOctets).build();
        Npdu npdu = new NpduBuilder().withExpectingReply().build();
        String hexString = bvlc.toHexString() + npdu.toHexString() + apduHexString;
        return hexString;
    }

    String mapPropertyArray() {
        String propertyIds = ARRAY1_START.toString();
        for (PropertyIdentifier propertyIdentifier : propertyIdentifiers) {
            propertyIds += SDContextTag.TAG0LENGTH1 + propertyIdentifier.getPropertyIdentifierHex();
        }
        propertyIds += ARRAY1_END;
        return propertyIds;
    }

    public static final class ReadPropertyMultipleCommandBuilder {
        private InetAddress sendToAddress;
        private int invokeId;
        private ObjectId objectId;
        private Boolean confirmedNotifications = null;
        private Exception error = null;
        private List<PropertyIdentifier> propertyIdentifiers;

        private ReadPropertyMultipleCommandBuilder() {
        }

        public ReadPropertyMultipleCommandBuilder(InetAddress sendToAddress) {
            this.sendToAddress = sendToAddress;
        }

        public static ReadPropertyMultipleCommandBuilder aReadPropertyMultipleCommand() {
            return new ReadPropertyMultipleCommandBuilder();
        }

        public ReadPropertyMultipleCommandBuilder withSendToAddress(InetAddress sendToAddress) {
            this.sendToAddress = sendToAddress;
            return this;
        }

        public ReadPropertyMultipleCommandBuilder withInvokeId(int invokeId) {
            this.invokeId = invokeId;
            return this;
        }

        public ReadPropertyMultipleCommandBuilder withObjectId(ObjectId objectId) {
            this.objectId = objectId;
            return this;
        }

        public ReadPropertyMultipleCommandBuilder withConfirmedNotifications(Boolean confirmedNotifications) {
            this.confirmedNotifications = confirmedNotifications;
            return this;
        }

        public ReadPropertyMultipleCommandBuilder withError(Exception error) {
            this.error = error;
            return this;
        }

        public ReadPropertyMultipleCommandBuilder withPropertyIdentifiers(List<PropertyIdentifier> propertyIdentifiers) {
            this.propertyIdentifiers = propertyIdentifiers;
            return this;
        }
        public ReadPropertyMultipleCommandBuilder withPropertyIdentifier(PropertyIdentifier propertyIdentifier) {
            if (propertyIdentifiers == null) {
                propertyIdentifiers = new ArrayList<>();
            }
            propertyIdentifiers.add(propertyIdentifier);
            return this;
        }

        public ReadPropertyMultipleCommand build() {
            ReadPropertyMultipleCommand ReadPropertyMultipleCommand = new ReadPropertyMultipleCommand(sendToAddress, invokeId, objectId, propertyIdentifiers);
            ReadPropertyMultipleCommand.withSendToAddress(sendToAddress);
            ReadPropertyMultipleCommand.confirmedNotifications = this.confirmedNotifications;
            ReadPropertyMultipleCommand.error = this.error;
            return ReadPropertyMultipleCommand;
        }
    }

    public static void main(String[] args) throws UnknownHostException {

        Integer instanceNumber = null;
        String ipAddress = null;
        if (args.length > 0) {
            ipAddress = args[0];
            instanceNumber = Integer.valueOf(args[1]);
        } else {
            System.out.println("Please run with ReadPropertyMultipleCommand <ipAddress> <instanceNumber>");
            System.exit(0);
        }

        InetAddress sendToAddress = InetAddress.getByName(ipAddress);
        ObjectId device8 = new ObjectId(ObjectType.Device,instanceNumber);
        ReadPropertyMultipleCommand readPropertyMultipleCommand = new ReadPropertyMultipleCommand.ReadPropertyMultipleCommandBuilder(sendToAddress)
                .withInvokeId(1)
                .withObjectId(device8)
                .withPropertyIdentifier(PropertyIdentifier.ObjectName)
                .withPropertyIdentifier(PropertyIdentifier.ProtocolVersion)
                .withPropertyIdentifier(PropertyIdentifier.ProtocolRevision)
                .build();

        readPropertyMultipleCommand.send();
    }
}
