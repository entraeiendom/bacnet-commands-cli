package no.entra.bacnet.cli.listener;

import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@CommandLine.Command(name = "listen", description = "Listen to incoming Bacnet messages")
public class BacnetListen implements Runnable {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port default is 47808")
    private int port = 47808;

    private long messageCount = 0;
    private BlockingDeque<BacnetObservedMessage> messageQueue;

    @Override
    public void run() {
        boolean blankLine = true;
        messageQueue = new LinkedBlockingDeque<>(1000);

        Thread messageConsumer = null;
        Thread messageListener = null;

        loop:
        try {

            messageConsumer = new Thread(new BacnetMessageConsumer(messageQueue, this));
            messageConsumer.start();
            messageListener = new Thread(new BacnetMessageListener(messageQueue, port));
            messageListener.start();

            while (true) {
                System.out.println("Type q to exit.");
                Scanner scanner = new Scanner(System.in);

                List<String> tokens = new ArrayList<>();
                while (scanner.hasNext()) {
                    String next = scanner.nextLine();
                    switch (next.toLowerCase()) {
                        case "exit":
                        case "quit":
                        case "q": {
                            System.out.println("Closing down.");
                            scanner.close();
                            break loop;
                        }
                        default: {
                            tokens.add(next);
                            System.out.println(tokens);
                        }
                    }
                }
            }
        } finally {
            System.out.println(String.format("Received: %s messages.", messageCount));
            System.out.println("Closing");
            if (messageConsumer != null && messageConsumer.isAlive()) {
                messageConsumer.interrupt();
            }
            if (messageListener != null && messageListener.isAlive()) {
                messageListener.interrupt();
            }
        }
    }

    protected void addCount() {
        messageCount++;
    }
}
