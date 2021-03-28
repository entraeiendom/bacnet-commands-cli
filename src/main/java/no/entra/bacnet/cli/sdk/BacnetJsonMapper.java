package no.entra.bacnet.cli.sdk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.entra.bacnet.cli.sdk.gson.SenderAdapter;
import org.slf4j.Logger;

import static no.entra.bacnet.json.utils.StringUtils.hasValue;
import static org.slf4j.LoggerFactory.getLogger;

public class BacnetJsonMapper {
    private static final Logger log = getLogger(BacnetJsonMapper.class);
    public static BacnetMessage map(String bacnetJson) {
        BacnetMessage bacnetMessage = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Sender.class, new SenderAdapter())
                .create();

        if (hasValue(bacnetJson)) {
            /*
            if (bacnetJson.startsWith("{\"configurationRequest")) {
                object = gson.fromJson(bacnetJson, ConfigurationRequest.class);
            } else if (bacnetJson.startsWith("{\"observation")) {
                object = gson.fromJson(bacnetJson, Observation.class);
            } else {
                log.trace("Failed to detect bacnetJson {}", bacnetJson);
            }

             */
            bacnetMessage = gson.fromJson(bacnetJson, BacnetMessage.class);
        }
        return bacnetMessage;
    }
}
