package com.lambert.banksimulator.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public LocalDateTime dateFromString(String stringDate) {
        return LocalDateTime.parse(stringDate, formatter);
    }
}
