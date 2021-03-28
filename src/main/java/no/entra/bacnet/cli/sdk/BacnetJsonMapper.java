package no.entra.bacnet.cli.sdk;

import com.google.gson.Gson;
import no.entra.bacnet.json.Configuration;
import no.entra.bacnet.json.Observation;
import org.slf4j.Logger;

import static no.entra.bacnet.json.utils.StringUtils.hasValue;
import static org.slf4j.LoggerFactory.getLogger;

public class BacnetJsonMapper {
    private static final Logger log = getLogger(BacnetJsonMapper.class);
    public static Object map(String bacnetJson) {
        Object object = null;
        Gson gson = new Gson();

        if (hasValue(bacnetJson)) {
            if (bacnetJson.startsWith("{\"ConfigurationRequest")) {
                object = gson.fromJson(bacnetJson, Configuration.class);
            } else if (bacnetJson.startsWith("{\"Observation")) {
                object = gson.fromJson(bacnetJson, Observation.class);
            } else {
                log.trace("Failed to detect bacnetJson {}", bacnetJson);
            }
        }
        return object;
    }
}
