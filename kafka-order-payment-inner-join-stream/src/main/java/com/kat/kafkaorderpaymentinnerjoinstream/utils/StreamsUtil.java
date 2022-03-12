package com.kat.kafkaorderpaymentinnerjoinstream.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class StreamsUtil {
    public static long toEpochTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
