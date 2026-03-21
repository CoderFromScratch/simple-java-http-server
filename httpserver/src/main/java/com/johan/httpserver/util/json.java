package com.johan.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class json {

    private static ObjectMapper myobjectmapper = new ObjectMapper();

    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

    public static JsonNode parse(String jsonSrc) throws IOException {
        return myobjectmapper.readTree(jsonSrc);
    }

    public static <A> A fromJson(JsonNode node, Class<A> c) throws IOException{
        return myobjectmapper.treeToValue(node, c);
    }

    public static JsonNode toJson(Object obj){
        return myobjectmapper.valueToTree(obj);
    }

    public static String stringify (JsonNode node) throws JsonProcessingException{
        return generateJson(node, false);
    }

    public static String stringifyPretty (JsonNode node) throws JsonProcessingException{
        return generateJson(node, true);
    }

    private static String generateJson (Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectwriter = myobjectmapper.writer();
        if(pretty){
            objectwriter = objectwriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectwriter.writeValueAsString(o);
    }
}
