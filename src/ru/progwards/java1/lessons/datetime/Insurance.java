package ru.progwards.java1.lessons.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Insurance {
    public static enum FormatStyle {SHORT, LONG, FULL} // стиль формата даты-времени
    private ZonedDateTime start; // дата-время начала действия страховки.
    private Duration duration; // продолжительность действия.
    public Insurance(ZonedDateTime start) {
        this.start = start;
    }
    public Insurance(String strStart, FormatStyle style) {
        Locale locale = Locale.US;
        Locale.setDefault(locale);
        LocalDate ld;
        LocalTime lt = LocalTime.of(0,0,0,0);
        LocalDateTime ldt;
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
        Long milisec;
        if (style == FormatStyle.SHORT) {
            milisec = Long.valueOf(strDuration);
            this.duration = Duration.ofMillis(milisec);
        } else if (style == FormatStyle.LONG) {
            ldt = LocalDateTime.parse(strDuration, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            this.duration = Duration.ofHours(ldt.getHour())
                    .plusMinutes(ldt.getMinute())
                    .plusSeconds(ldt.getSecond())
                    .plusDays(ldt.getDayOfMonth())
                    .plusDays(ldt.getMonthValue() * 30);
        } else
            this.duration = Duration.parse(strDuration);
    }

    public boolean checkValid(ZonedDateTime dateTime) {
        if (this.start.compareTo(dateTime) > 0) return false;
        if (this.duration == null) return true;
        Duration durationCalculate = Duration.between(this.start, dateTime);
        if (this.duration.compareTo(durationCalculate) > 0) return true;
        return false;
    }

    public String toString() {
        if (checkValid(ZonedDateTime.now()))
            return "Insurance issued on " + this.start.toString() +  " is valid";
        else
            return "Insurance issued on " + this.start.toString() +  " is not valid";
    }

    public static void main(String[] args) {
        Insurance insurance = new Insurance("2020-06-30T12:30:15.264322+03:00[Europe/Moscow]", FormatStyle.FULL);
        insurance.setDuration("0000-01-02T00:00:00", Insurance.FormatStyle.LONG);
        System.out.println(insurance.toString());
    }

}