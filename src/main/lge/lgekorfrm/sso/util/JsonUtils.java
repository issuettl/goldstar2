package lgekorfrm.sso.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static ObjectMapper objectMapper = getObjectMapper();

    public JsonUtils() {
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return objectMapper;
    }

    public static String marshal(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }

    public static Map unmarshal(String json) throws IOException {
        return objectMapper.readValue(json, Map.class);
    }

    public static List unmarshalToList(String json) throws IOException {
        return (List) objectMapper.readValue(json, List.class);
    }

    public static Map<String, Object> convertClassToMap(Object obj) throws IOException {
        return (Map) objectMapper.convertValue(obj, Map.class);
    }
    public static List convertClassToList(Object obj) throws IOException {
        return (List) objectMapper.convertValue(obj, List.class);
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    public static <T> T readValue(String json, Class<T> toValueType) throws Exception{
        return objectMapper.readValue(json, toValueType);
    }
}
