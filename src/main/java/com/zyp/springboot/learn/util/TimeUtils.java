package com.zyp.springboot.learn.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtils {
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
