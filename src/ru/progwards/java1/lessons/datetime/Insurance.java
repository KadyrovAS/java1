package ru.progwards.java1.lessons.datetime;

import java.time.*;
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
        LocalDate ld;
        LocalDateTime ldt;
        LocalTime lt = LocalTime.now();
        if (style == FormatStyle.SHORT) {
            ld = LocalDate.parse(strStart, DateTimeFormatter.ISO_LOCAL_DATE);
            this.start = ZonedDateTime.of(ld, lt, ZoneId.systemDefault());
        }
        else if (style == FormatStyle.LONG) {
            ldt = LocalDateTime.parse(strStart, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            this.start = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        }
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
        LocalDateTime ldt;
//        System.out.println("Конструктор setDuration  " + strDuration + "  " + style);
        Long milisec;
//        ZonedDateTime zdt;
        if (style == FormatStyle.SHORT) {
            milisec = Long.valueOf(strDuration);
            this.duration = Duration.ofMillis(milisec);
        } else if (style == FormatStyle.LONG) {
            ldt = LocalDateTime.parse(strDuration, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//            zdt = ZonedDateTime.parse(strDuration, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            this.duration = Duration.ofMillis(ldt.getSecond() * 1000);
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
        Locale.setDefault(Locale.US);
        String str = "2020-07-30T13:09:57.708512100";

        LocalDateTime ldt = LocalDateTime.parse(str, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println(ldt.toString());
        ZoneId zd = ZoneId.systemDefault();
//        LocalTime lt = LocalTime.of(0,0,0);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, zd);
        System.out.println(zdt.toString());
    }
}