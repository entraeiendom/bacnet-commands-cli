package no.entra.bacnet.cli.listener;

import java.net.SocketAddress;

public class BacnetObservedMessage {
    private final SocketAddress senderAddress;
    private final String hexString;

    public BacnetObservedMessage(SocketAddress senderAddress, String hexString) {
        this.senderAddress = senderAddress;
        this.hexString = hexString;
    }

    public SocketAddress getSenderAddress() {
        return senderAddress;
    }

    public String getHexString() {
        return hexString;
    }

    @Override
    public String toString() {
        return "BacnetObservedMessage{" +
                "senderAddress=" + senderAddress +
                ", hexString='" + hexString + '\'' +
                '}';
    }
}
