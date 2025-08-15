package com.zyp.springboot.learn.util;

import java.util.Objects;
import java.util.UUID;

public abstract class StrUtils {
    public static String removeNewLines(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\r?\\n", "");
    }

    public static boolean hasNoLength(String str) {
        return !org.springframework.util.StringUtils.hasLength(str);
    }

    public static boolean hasLength(String str) {
        return org.springframework.util.StringUtils.hasLength(str);
    }
    public static boolean equals(String str1, String str2) {
        return Objects.equals(str2, str1);
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuidWithoutHyphen() {
        return uuid().replaceAll("-", "");
    }
}
