package util;

import java.sql.Timestamp;
import java.util.Calendar;

public class TimestampWorker {
    private static Calendar calendar = Calendar.getInstance();
    private TimestampWorker() { }
    public static Timestamp resetTime(Timestamp dateTime) {
        calendar.setTime(dateTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }
    public static Timestamp addHours(Timestamp dateTime, int hours) {
        calendar.setTime(dateTime);
        calendar.add(Calendar.HOUR, hours);
        return new Timestamp(calendar.getTimeInMillis());
    }
    public static Timestamp addDays(Timestamp dateTime, int days) {
        calendar.setTime(dateTime);
        calendar.add(Calendar.DATE, days);
        return new Timestamp(calendar.getTimeInMillis());
    }
}
