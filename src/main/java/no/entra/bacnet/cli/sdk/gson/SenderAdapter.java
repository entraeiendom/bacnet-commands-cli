package no.entra.bacnet.cli.sdk.gson;

import com.google.gson.*;
import no.entra.bacnet.cli.sdk.Sender;

import java.lang.reflect.Type;

public class SenderAdapter implements JsonDeserializer<Sender> {
    @Override
    public Sender deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Sender sender = null;
        if (!(json instanceof JsonPrimitive)) {
            sender =  new Sender();
            sender.setName(json.toString());
            throw new JsonParseException("The date should be a string value");
        }
        return sender;
    }

//    @Override
//    public JsonElement serialize(Sender sender, Type type, JsonSerializationContext jsonSerializationContext) {
//        return null;
//    }
}
