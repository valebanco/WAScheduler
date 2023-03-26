package it.bancon.wascheduler.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateTimeUtils {

    public static boolean isTodayDateEqualsToSelectedDate(String dateSelected) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime todayDate = LocalDateTime.now();
        return dateSelected.equals(dtf.format(todayDate));

    }

    public static boolean isTimeSelectedAfterNowTime (int hour,int minute) {

        Calendar datetimeSelected = Calendar.getInstance();
        Calendar dateTimeActual = Calendar.getInstance();
        datetimeSelected.set(Calendar.HOUR_OF_DAY, hour);
        datetimeSelected.set(Calendar.MINUTE, minute);
        if (datetimeSelected.getTimeInMillis() > dateTimeActual.getTimeInMillis()) {
            return true;
        } else {
            return false;
        }

    }
}
