package no.entra.bacnet.cli.sdk.gson;

import com.google.gson.*;
import no.entra.bacnet.cli.sdk.Sender;

import java.lang.reflect.Type;

public class SenderAdapter implements JsonDeserializer<Sender> {
    @Override
    public Sender deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Sender sender = null;
        if ((json instanceof JsonPrimitive)) {
            sender =  new Sender();
            String name = json.toString();
            if (name.startsWith("\"")) {
                name = name.substring(1);
            }
            if (name.endsWith("\"")) {
                name = name.substring(0,name.length() -1);
            }
            sender.setName(name);
        } else {
            throw new JsonParseException("Parse of sender is not implemented yet.");
        }
        return sender;
    }

//    @Override
//    public JsonElement serialize(Sender sender, Type type, JsonSerializationContext jsonSerializationContext) {
//        return null;
//    }
}
