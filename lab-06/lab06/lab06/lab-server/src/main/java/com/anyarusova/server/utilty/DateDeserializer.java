package com.anyarusova.server.utility;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Custom parser for gson for java.time.Zoned date from file
 */
public class DateDeserializer implements JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson g = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> map = g.fromJson(json.toString(), type);
        return ZonedDateTime.of(Integer.parseInt(map.get("year")), Integer.parseInt(map.get("month")),
                Integer.parseInt(map.get("day")), Integer.parseInt(map.get("hour")), Integer.parseInt(map.get("minute")),
                Integer.parseInt(map.get("second")), Integer.parseInt(map.get("nanoOfSecond")), ZoneId.of(map.get("zone")));
    }
}
