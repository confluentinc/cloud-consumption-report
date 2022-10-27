package com.confluent.cloud.reporting.consumption.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {
    private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("MM_dd_yyyy_HH_mm");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/YYYY");

    public static String formatFileDate(LocalDateTime localDateTime) {
        return FILE_DATE_FORMAT.format(localDateTime);
    }

    public static String formatDate(LocalDateTime localDateTime) {
        return DATE_FORMAT.format(localDateTime);
    }
}
