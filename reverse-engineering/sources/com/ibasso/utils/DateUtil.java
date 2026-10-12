package com.ibasso.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
public class DateUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static long parseDateToMillis(String str) {
        return simpleDateFormat.parse(str, new ParsePosition(0)).getTime();
    }
}
