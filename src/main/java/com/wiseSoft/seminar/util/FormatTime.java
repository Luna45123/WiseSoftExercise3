package com.wiseSoft.seminar.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatTime {
    public String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("hh:mma"));
    }
}
