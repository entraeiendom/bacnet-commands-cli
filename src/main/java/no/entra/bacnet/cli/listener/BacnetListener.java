package no.entra.bacnet.cli.listener;

import picocli.CommandLine;

import java.io.IOException;

@CommandLine.Command(name = "listen", description = "Listen to incoming Bacnet messages")
public class BacnetListener implements Runnable {
    @CommandLine.Option(names = {"-ip", "--ipAddress"}, description = "IP Address to the Device")
    private String ipAddress = "127.0.0.1";
    @CommandLine.Option(names = {"-p", "--port"}, description = "Bacnet Port default is 47808")
    private int port = 47808;

    @Override
    public void run() {
        boolean blankLine = true;
        loop:
        try {
            while (true) {
                int available;
                while (true) {
                    if (!((available = System.in.available()) == 0)) break;

                    // Do something
                }
                do {
                    switch (System.in.read()) {
                        default:
                            blankLine = false;
                            break;
                        case '\n':
                            if (blankLine)
                                break loop;
                            blankLine = true;
                            break;
                    }
                } while (--available > 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
