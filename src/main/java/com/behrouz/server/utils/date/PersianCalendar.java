package com.behrouz.server.utils.date;

/**
 * Created by Hesam Aldin Ahani on 5/5/2017.
 */

import com.behrouz.server.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

//PersianCalendar sc = new PersianCalendar();
//String persianDate = sc.strWeekDay  + " " + sc.day  + " " + sc.strMonth + " " + sc.year;

public class PersianCalendar {

    String[] weekDayNames = {
            "شنبه", "یکشنبه", "دوشنبه",
            "سه شنبه", "چهارشنبه",
            "پنج شنبه", "جمعه"
    };
    String[] monthNames = {
            "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
            "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    };
    public String strWeekDay = "";
    public String strMonth = "";
    public int day;
    int month;
    public int year;
    int ld;
    Calendar calendar = Calendar.getInstance();
    int gregorianYear = calendar.get(Calendar.YEAR);
    int gregorianMonth = calendar.get(Calendar.MONTH) + 1;
    int gregorianDate = calendar.get(Calendar.DATE);
    int WeekDay = calendar.get(Calendar.DAY_OF_WEEK);

    int[] buf1 = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
    int[] buf2 = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};

    public PersianCalendar() {
        calendar.setFirstDayOfWeek(Calendar.SATURDAY);

        Date gregorianDate = new Date();
        calendar.setTime(gregorianDate);
        toPersian(gregorianDate);
    }

    public PersianCalendar(Date gregorianDate) {
        calendar.setFirstDayOfWeek(Calendar.SATURDAY);
        calendar.setTime(gregorianDate);
        toPersian(gregorianDate);
    }

    private void toPersian(Date gregorianDate) {
        if ((gregorianYear % 4) != 0)
            func1();
        else
            func2();

        strMonth = monthNames[month - 1];
        strWeekDay = weekDayNames[DateUtil.getDayOfWeek(new Date())];
    }

    private void func1() {
        day = buf1[gregorianMonth - 1] + gregorianDate;
        if (day > 79) {
            day = day - 79;
            if (day <= 186) {
                int day2 = day;
                month = (day2 / 31) + 1;
                day = (day2 % 31);
                if (day2 % 31 == 0) {
                    month--;
                    day = 31;
                }
                year = gregorianYear - 621;
            } else {
                int day2 = day - 186;
                month = (day2 / 30) + 7;
                day = (day2 % 30);
                if (day2 % 30 == 0) {
                    month = (day2 / 30) + 6;
                    day = 30;
                }
                year = gregorianYear - 621;
            }
        } else {
            ld = gregorianYear > 1996 && gregorianYear % 4 == 1 ? 11 : 10;
            int day2 = day + ld;
            month = (day2 / 30) + 10;
            day = (day2 % 30);
            if (day2 % 30 == 0) {
                month--;
                day = 30;
            }
            year = gregorianYear - 622;
        }
    }

    private void func2() {
        day = buf2[gregorianMonth - 1] + gregorianDate;
        ld = gregorianYear >= 1996 ? 79 : 80;
        if (day > ld) {
            day = day - ld;
            if (day <= 186) {
                int day2 = day;
                month = (day2 / 31) + 1;
                day = (day2 % 31);
                if (day2 % 31 == 0) {
                    month--;
                    day = 31;
                }
                year = gregorianYear - 621;
            } else {
                int day2 = day - 186;
                month = (day2 / 30) + 7;
                day = (day2 % 30);
                if (day2 % 30 == 0) {
                    month--;
                    day = 30;
                }
                year = gregorianYear - 621;
            }
        } else {
            int day2 = day + 10;
            month = (day2 / 30) + 10;
            day = (day2 % 30);
            if (day2 % 30 == 0) {
                month--;
                day = 30;
            }
            year = gregorianYear - 622;
        }
    }
}
