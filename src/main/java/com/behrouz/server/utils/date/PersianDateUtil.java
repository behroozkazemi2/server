package com.behrouz.server.utils.date;

import com.behrouz.server.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hesam Aldin Ahani on 5/4/2017.
 */

public class PersianDateUtil {
    public static String getPersianMonth(int month) {
        switch (month) {
            case 1:
                return "فروردین ماه";
            case 2:
                return "اردیبهشت ماه";
            case 3:
                return "خرداد ماه";
            case 4:
                return "تیر ماه";
            case 5:
                return "مرداد ماه";
            case 6:
                return "شهریور ماه";
            case 7:
                return "مهر ماه";
            case 8:
                return "آبان ماه";
            case 9:
                return "آذر ماه";
            case 10:
                return "دی ماه";
            case 11:
                return "بهمن ماه";
            case 12:
                return "اسفند ماه";
            default:
                return "فروردین ماه";
        }
    }

    public static String getPersianMonthName(int month) {
        switch (month) {
            case 1:
                return "فروردین";
            case 2:
                return "اردیبهشت";
            case 3:
                return "خرداد";
            case 4:
                return "تیر";
            case 5:
                return "مرداد";
            case 6:
                return "شهریور";
            case 7:
                return "مهر";
            case 8:
                return "آبان";
            case 9:
                return "آذر";
            case 10:
                return "دی";
            case 11:
                return "بهمن";
            case 12:
                return "اسفند";
            default:
                return "فروردین";
        }
    }

    public static String getPersianDayOfWeek(int dayOfWeek) {
        switch (dayOfWeek) {
            case 0:
                return "شنبه";
            case 1:
                return "یکشنبه";
            case 2:
                return "دوشنبه";
            case 3:
                return "سه شنبه";
            case 4:
                return "چهارشنبه";
            case 5:
                return "پنجشنبه";
            case 6:
                return "جمعه";
            default:
                return "شنبه";
        }
    }

    // String timeStamp = strDate.replaceAll("[^0-9]", "");
    // String strPersianDate = PersianDateUtil.getPersianDate("1493926200000");
    public static String getPersianDate(String timeStamp) {
        return getPersianDate(Long.parseLong(timeStamp));
    }

    public static String getPersianDate(long timeStamp) {
        Date date = new Date(timeStamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        CivilDate civilDate = new CivilDate(calendar);
        PersianDate persianDate = DateConverter.civilToPersian(civilDate);

        int dayOfMonth = persianDate.getDayOfMonth();
        int month = persianDate.getMonth();
        int year = persianDate.getYear();

        return year + "/" + month + "/" + dayOfMonth;
    }

    public static String getPersianDate(LocalDateTime dateTime) {
        return getPersianDate(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

    }

    public static String getPersianDateAndHour(long timeStamp) {
        Date date = new Date(timeStamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        calendar.setTimeZone(TimeZone.getTimeZone());

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return String.format("%d:%02d - %s", hour, minute, getPersianDate(timeStamp));
    }

    public static PersianDate getPersianDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        CivilDate civilDate = new CivilDate(calendar);
        return DateConverter.civilToPersian(civilDate);
    }

//    public static String getPersianDate(String strDate) {
//        return getPersianDate(DateUtil.getDateFromDateString(strDate, DateUtil.DATETIME_FORMAT));
//    }

    // return persian date like this "شنبه 13 مرداد ماه 1396"
    public static String getPersianDateString(Date date) {
        String strPersianDate = getPersianDate(date.getTime());
        String[] split = strPersianDate.split("/");
        String dayName= getPersianDayOfWeek(DateUtil.getDayOfWeek(date));
        String monthName= getPersianMonth(Integer.parseInt(split[1]));

        return dayName + " " + split[2] + " " + monthName + " " + split[0];
    }

    // return persian date like this "شنبه 13 مرداد ماه 1396"
    public static String getPersianDateStringWithoutYear(Date date) {
        String strPersianDate = getPersianDate(date.getTime());
        String[] split = strPersianDate.split("/");
        String dayName= getPersianDayOfWeek(DateUtil.getDayOfWeek(date));
        String monthName= getPersianMonthName(Integer.parseInt(split[1]));

        return dayName + " " + split[2] + " " + monthName;
    }

    public static Date getDateFromPersianDateString(String strPersianDate) {
        JalaliCalendar calendar = new JalaliCalendar();
        return calendar.getGregorianDate(strPersianDate);
    }

    public static Date getDateFromPersianDate(PersianDate presianDate) {
        return getDateFromPersianDateString(presianDate.toString());
    }

    public static CivilDate getCivilDateAsCivilDate(String strPersianDate) {
        String[] splitDateFromDateTime;
        String[] splitYearMonthDayFromDate;

        if (strPersianDate.split(" ").length > 1) {
            splitDateFromDateTime = strPersianDate.split(" "); // getting persian date from persian datetime
            splitYearMonthDayFromDate = splitDateFromDateTime[0].split("/"); // getting year, month, and day from persian date
        } else
            splitYearMonthDayFromDate = strPersianDate.split("/"); // getting year, month, and day from persian date

        int year = Integer.parseInt(splitYearMonthDayFromDate[0]);
        int month = Integer.parseInt(splitYearMonthDayFromDate[1]);
        int day = Integer.parseInt(splitYearMonthDayFromDate[2]);

        PersianDate persianDate = new PersianDate(year, month, day);
        return DateConverter.persianToCivil(persianDate);
    }

    public static CivilDate getCivilDateAsDate(String strPersianDate) {
        String[] splitDateFromDateTime;
        String[] splitYearMonthDayFromDate;

        if (strPersianDate.split(" ").length > 1) {
            splitDateFromDateTime = strPersianDate.split(" "); // getting persian date from persian datetime
            splitYearMonthDayFromDate = splitDateFromDateTime[0].split("/"); // getting year, month, and day from persian date
        } else
            splitYearMonthDayFromDate = strPersianDate.split("/"); // getting year, month, and day from persian date

        int year = Integer.parseInt(splitYearMonthDayFromDate[0]);
        int month = Integer.parseInt(splitYearMonthDayFromDate[1]);
        int day = Integer.parseInt(splitYearMonthDayFromDate[2]);

        PersianDate persianDate = new PersianDate(year, month, day);
        return DateConverter.persianToCivil(persianDate);
    }

    public static String getCivilDateAsString(String strPersianDate) {
        String[] splitDateFromDateTime;
        String[] splitYearMonthDayFromDate;

        if (strPersianDate.split(" ").length > 1) {
            splitDateFromDateTime = strPersianDate.split(" "); // getting persian date from persian datetime
            splitYearMonthDayFromDate = splitDateFromDateTime[0].split("/"); // getting year, month, and day from persian date
        } else
            splitYearMonthDayFromDate = strPersianDate.split("/"); // getting year, month, and day from persian date

        int year = Integer.parseInt(splitYearMonthDayFromDate[0]);
        int month = Integer.parseInt(splitYearMonthDayFromDate[1]);
        int day = Integer.parseInt(splitYearMonthDayFromDate[2]);

        PersianDate persianDate = new PersianDate(year, month, day);
        return DateConverter.persianToCivil(persianDate).toString();
    }

    public static String getPersianDateTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(" hh:mm:ss");
        return getPersianDate(date.getTime()) + sdf.format(date.getTime());
    }
    public static String getPersianDateTimeHourAndMinute(long date){
        SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm");
        return sdf.format(date);
    }



    public static String getPersianDateWithoutDay(long timeStamp) {
        Date date = new Date(timeStamp);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        CivilDate civilDate = new CivilDate(calendar);
        PersianDate persianDate = DateConverter.civilToPersian(civilDate);

        int dayOfMonth = persianDate.getDayOfMonth();
        int month = persianDate.getMonth();
        int year = persianDate.getYear();

        return year + "/" + month ;
    }

}
