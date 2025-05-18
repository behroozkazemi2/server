package com.behrouz.server.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static boolean greaterThan( Date smaller, Date bigger ){

        Calendar cs = createCalendar(smaller);

        Calendar cb = createCalendar(bigger);


        return

                cs.get( Calendar.YEAR ) < cb.get( Calendar.YEAR ) ||

                        (cs.get( Calendar.YEAR ) <= cb.get( Calendar.YEAR ) &&
                                cs.get( Calendar.MONTH ) < cb.get( Calendar.MONTH ) ) ||

                                (cs.get( Calendar.YEAR ) <= cb.get( Calendar.YEAR ) &&
                                cs.get( Calendar.MONTH ) <= cb.get( Calendar.MONTH ) &&
                                cs.get( Calendar.DAY_OF_MONTH ) < cb.get( Calendar.DAY_OF_MONTH) );

    }

    private static Calendar createCalendar ( Date smaller ) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime( smaller );

        return calendar;
    }


    public static int getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        switch (c.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SATURDAY:
                return 0;
            case Calendar.SUNDAY:
                return 1;
            case Calendar.MONDAY:
                return 2;
            case Calendar.TUESDAY:
                return 3;
            case Calendar.WEDNESDAY:
                return 4;
            case Calendar.THURSDAY:
                return 5;
            case Calendar.FRIDAY:
                return 6;
            default:
                return 0;
        }
    }

}
