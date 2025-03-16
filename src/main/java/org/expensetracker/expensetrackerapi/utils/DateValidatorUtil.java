package org.expensetracker.expensetrackerapi.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidatorUtil {
    public static boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.from(formatter.parse(date)).toString().equals(date);
    }
}
