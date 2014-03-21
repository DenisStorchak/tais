package ua.org.tees.yarosh.tais.homework;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtils {
    public static LocalDate toLocalDate(Date date) {
        return LocalDate.from(Instant.ofEpochMilli(date.getTime()));
    }

    public static Date toDate(LocalDate localDate) {
        return Date.from(Instant.from(localDate));
    }

    public static Date minus(Date date, int amountToSubtract, ChronoUnit unit) {
        return toDate(toLocalDate(date).minus(amountToSubtract, unit));
    }

    public static Date minusDays(Date date, int amountToSubtract) {
        return minus(date, amountToSubtract, ChronoUnit.DAYS);
    }
}
