package com.anyarusova.server.utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

/**
 * Custom parser for gson for java.time.Zoned date into file
 */
public class DateSerializer implements JsonSerializer<ZonedDateTime> {

    @Override
    public JsonElement serialize(ZonedDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("year", Integer.valueOf(src.getYear()).toString());
        obj.addProperty("month", Integer.valueOf(src.getMonthValue()).toString());
        obj.addProperty("day", Integer.valueOf(src.getDayOfMonth()).toString());
        obj.addProperty("hour", Integer.valueOf(src.getHour()).toString());
        obj.addProperty("minute", Integer.valueOf(src.getMinute()).toString());
        obj.addProperty("second", Integer.valueOf(src.getSecond()).toString());
        obj.addProperty("nanoOfSecond", Integer.valueOf(src.getNano()).toString());
        obj.addProperty("zone", src.getZone().toString());
        return obj;
    }
}
