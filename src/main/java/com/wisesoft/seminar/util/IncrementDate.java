package com.wisesoft.seminar.util;

import java.time.LocalDate;

public class IncrementDate {
    public static LocalDate incrementDate(LocalDate date) {
        do {
            date = date.plusDays(1);
        } while (date.getDayOfWeek().toString().equals("SATURDAY") || date.getDayOfWeek().toString().equals("SUNDAY"));
        return date;
    }
}
