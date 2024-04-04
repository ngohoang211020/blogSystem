package com.blogsystem.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static Map<String, String> createErrorMap(Throwable e) {
        Map<String, String> errorMsg = new HashMap<>();
        errorMsg.put("message", e.getMessage());

        return errorMsg;
    }

    public static Map<String, String> createErrorMap(String message) {
        Map<String, String> errorMsg = new HashMap<>();
        errorMsg.put("message", message);

        return errorMsg;
    }
}
