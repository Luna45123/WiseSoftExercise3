package com.wisesoft.seminar.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatTime {
    public static String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("hh:mma"));
    }
}
