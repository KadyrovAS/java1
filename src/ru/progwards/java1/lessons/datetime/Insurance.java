package ru.progwards.java1.lessons.datetime;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Insurance {
    public static enum FormatStyle {SHORT, LONG, FULL} // стиль формата даты-времени
    private ZonedDateTime start; // дата-время начала действия страховки.
    private Duration duration; // продолжительность действия.
    public Insurance(ZonedDateTime start) {
        this.start = start;
    }
    public Insurance(String strStart, FormatStyle style) {
        System.out.println("Конструктор Insurance  " + strStart + "  " + style);
        if (style == FormatStyle.SHORT)
            this.start = ZonedDateTime.parse(strStart, DateTimeFormatter.ISO_LOCAL_DATE);
        else if (style == FormatStyle.LONG)
            this.start = ZonedDateTime.parse(strStart, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        else if (style == FormatStyle.FULL)
            this.start = ZonedDateTime.parse(strStart, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
    public void setDuration(Duration duration) {this.duration = duration;}
    public void setDuration(ZonedDateTime expiration) {this.duration = Duration.between(this.start, expiration);}
    public void setDuration(int months, int days, int hours) {
        ZonedDateTime zdt = start.plusMonths(months);
        zdt = zdt.plusDays(days);
        zdt = zdt.plusHours(hours);
        this.duration = Duration.between(start, zdt);
    }
    public void setDuration(String strDuration, FormatStyle style) {
        System.out.println("Конструктор setDuration  " + strDuration + "  " + style);
        Long milisec;
        ZonedDateTime zdt;
        if (style == FormatStyle.SHORT) {
            milisec = Long.valueOf(strDuration);
            this.duration = Duration.ofMillis(milisec);
        } else if (style == FormatStyle.LONG) {
            zdt = ZonedDateTime.parse(strDuration, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            this.duration = Duration.ofMillis(zdt.getSecond() * 1000);
        } else
            this.duration = Duration.parse(strDuration);
    }

    public boolean checkValid(ZonedDateTime dateTime) {
        if (this.duration == null) return true;
        Duration durationCalculate = Duration.between(this.start, dateTime);
        if (this.duration.compareTo(durationCalculate) >= 0) return true;
        return false;
    }

    public String toString() {
        if (checkValid(ZonedDateTime.now()))
            return "Insurance issued on " + this.start.toString() +  " is valid";
        else
            return "Insurance issued on " + this.start.toString() +  " is not valid";
    }

    public static void main(String[] args) {
        Locale locale = new Locale("ru", "RU");
        Locale.setDefault(locale);

        Insurance insurance = new Insurance(ZonedDateTime.now().minus(10, ChronoUnit.DAYS));
        insurance.setDuration(0,15,0);
        System.out.println(insurance.toString());
    }
}