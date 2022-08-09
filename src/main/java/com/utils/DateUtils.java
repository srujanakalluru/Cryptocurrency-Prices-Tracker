package com.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDateTime getDate(String date) {
        LocalDate l = LocalDate.parse(date, formatter);
        return l.atStartOfDay();
    }

    public static LocalDateTime getNextDate(String date) {
        LocalDate l = LocalDate.parse(date, formatter);
        return l.atStartOfDay().plusDays(1);
    }
}
