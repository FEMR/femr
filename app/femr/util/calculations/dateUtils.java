package femr.util.calculations;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class dateUtils {
    public static int calculateYears(Date age) {
        DateMidnight birthdate = new DateMidnight(age);
        DateTime now = new DateTime();
        Years years = Years.yearsBetween(birthdate, now);
        return years.getYears();
    }

    public static String getCurrentDateTimeString(){
        DateTimeFormatter dateFormat = DateTimeFormat
                .forPattern("yyyy/mm/dd HH:mm:ss");
        LocalDateTime localDateTime = new LocalDateTime();
        dateFormat.print(localDateTime);
        String dt = localDateTime.toString();
        return dt;
    }

    public static DateTime getCurrentDateTime(){
        return new DateTime();
    }

    public static String getAge(Date born) {
        DateMidnight birthdate = new DateMidnight(born);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(birthdate, now);
        int monthsInt = months.getMonths();
        if (monthsInt < 24)
            return Integer.toString(monthsInt) + " MO";
        else
            return Integer.toString(monthsInt/12) + " YOA";
    }
}

