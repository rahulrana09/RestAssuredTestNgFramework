package framework.common;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAndTime {
    public static Long getTimeStamp(int minutes) {
        return DateUtils.addMinutes(new Date(), minutes).getTime();
    }

    public static String getDateYYYYMMDD(int value) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, value);
        Date date = cal.getTime();
        String newDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return newDate;
    }

    public static String getDateYYYYDDMM(int value) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, value);
        Date date = cal.getTime();
        String newDate = new SimpleDateFormat("yyyy-dd-MM").format(date);
        return newDate;
    }

    public static String getTimeDifference(Timestamp t1, Timestamp t2) {
        Long diff = t2.getTime() - t1.getTime();
        int seconds = (int) (diff / 1000);

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = (seconds % 3600) % 60;

        return hours + ":" + minutes + ":" + seconds;
    }

}
