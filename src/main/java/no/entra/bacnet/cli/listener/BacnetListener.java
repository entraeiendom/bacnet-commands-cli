package no.entra.bacnet.cli.listener;

import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@CommandLine.Command(name = "listen", description = "Listen to incoming Bacnet messages")
public class BacnetListener implements Runnable {
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

            messageConsumer = new Thread(new BacnetMessageConsumer(messageQueue));
            messageConsumer.start();
            messageListener = new Thread(new BacnetMessageListener(messageQueue, port));
            messageListener.start();

            while (true) {
                System.out.println("***3");
                int available;

                while (true) {
                    if (!((available = System.in.available()) == 0)) {
                        System.out.println("***1");
                        break;
                    }
                }
                do {

                    switch (System.in.read()) {
                        default:
                            System.out.println("Default");
                            blankLine = false;
                            break;
                        case '\n':
                            System.out.println("Newline");
                            if (blankLine)
                                break loop;
                            blankLine = true;
//                            thread.interrupt();
                            break;
                    }
                } while (--available > 0);
            }

        } catch (IOException e) {
            e.printStackTrace();
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

    private void addCount() {
        messageCount++;
    }
}
