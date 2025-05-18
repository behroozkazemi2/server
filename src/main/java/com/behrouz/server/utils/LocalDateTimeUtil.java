package com.behrouz.server.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeUtil {

    public static boolean isShanbeTa4Shanbe(LocalDate dateTime){
        return isShanbeTa4Shanbe(dateTime.atStartOfDay());
    }

    public static boolean isShanbeTa4Shanbe(LocalDateTime dateTime){

        DayOfWeek dayOfWeek = dateTime.toLocalDate().getDayOfWeek();

        return
                dayOfWeek ==  DayOfWeek.SATURDAY ||
                        dayOfWeek ==  DayOfWeek.SUNDAY ||
                        dayOfWeek ==  DayOfWeek.MONDAY ||
                        dayOfWeek ==  DayOfWeek.TUESDAY ||
                        dayOfWeek ==  DayOfWeek.WEDNESDAY;
    }


    public static boolean is5Shanbe(LocalDate dateTime){
        return is5Shanbe(dateTime.atStartOfDay());
    }

    public static boolean is5Shanbe(LocalDateTime dateTime){

        DayOfWeek dayOfWeek = dateTime.toLocalDate().getDayOfWeek();

        return
                dayOfWeek ==  DayOfWeek.THURSDAY;
    }


    public static Date localDateTimeToDate(LocalDateTime dateTime){
        return dateTime == null ? null : new Date(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }


    public static LocalDateTime dateToLocalDateDate(LocalDateTime dateTime){
        return dateTime == null ? null : dateTime.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime dateToLocalDateDate(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
