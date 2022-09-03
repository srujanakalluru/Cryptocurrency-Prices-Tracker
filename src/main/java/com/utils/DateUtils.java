package com.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class to convert date to require format
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Utility method to get current date in the format specified by the formatter
     *
     * @param date
     * @return LocalDateTime
     */
    public static LocalDateTime getDate(String date) {
        LocalDate l = LocalDate.parse(date, formatter);
        return l.atStartOfDay();
    }

    /**
     * Utility method to get next date in the format specified by the formatter
     *
     * @param date
     * @return
     */
    public static LocalDateTime getNextDate(String date) {
        LocalDate l = LocalDate.parse(date, formatter);
        return l.atStartOfDay().plusDays(1);
    }
}
