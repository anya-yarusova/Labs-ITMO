package com.anyarusova.server.utilty;

import com.anyarusova.common.data.Organization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.PriorityQueue;

public class JsonParser {

    public String serialize(PriorityQueue<Organization> collectionData) {
        Gson g = new GsonBuilder().registerTypeAdapter(java.time.ZonedDateTime.class, new DateSerializer()).create();
        return g.toJson(collectionData);
    }

    public PriorityQueue<Organization> deSerialize(String strData) throws JsonSyntaxException, IllegalArgumentException {
        Gson g = new GsonBuilder().registerTypeAdapter(java.time.ZonedDateTime.class, new DateDeserializer()).create();
        Type type = new TypeToken<PriorityQueue<Organization>>() {
        }.getType();
        if ("".equals(strData)) {
            return new PriorityQueue<>();
        }
        return g.fromJson(strData, type);
    }
}
