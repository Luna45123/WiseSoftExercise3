package com.wiseSoft.seminar.util;

import java.time.LocalDate;

public class IncrementDate {
    public LocalDate incrementDate(LocalDate date) {
        do {
            date = date.plusDays(1);
        } while (date.getDayOfWeek().toString().equals("SATURDAY") || date.getDayOfWeek().toString().equals("SUNDAY"));
        return date;
    }
}
