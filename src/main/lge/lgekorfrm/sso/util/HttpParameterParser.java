package lgekorfrm.sso.util;

import java.util.HashMap;
import java.util.Map;

public class HttpParameterParser {

    public static Map<String, Object> mapToParameterMapWithPrefix(Map<String, Object> map, String prefix) {
        Map<String, Object> params = new HashMap<>();
        if (!map.isEmpty()) {
            for (String key : map.keySet()) {
                params.put(prefix + key, map.get(key));
            }
        }
        return params;
    }
}
