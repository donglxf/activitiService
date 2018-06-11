package com.ht.commonactivity.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String getNextId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
