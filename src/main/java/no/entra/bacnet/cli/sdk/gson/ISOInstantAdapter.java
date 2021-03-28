package no.entra.bacnet.cli.sdk.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.Instant;

public final class ISOInstantAdapter implements JsonDeserializer<Instant> {
//        @Override
//        public JsonElement serialize(Instant src, Type srcType, JsonSerializationContext context) {
//            return new JsonPrimitive(src.getMillis());
//        }

        @Override
        public Instant deserialize(JsonElement json, Type type, JsonDeserializationContext context)
    throws JsonParseException {
            return Instant.parse(json.getAsString());
        }
}
