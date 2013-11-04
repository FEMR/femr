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

    public static String getCurrentDateTime(){
        DateTimeFormatter dateFormat = DateTimeFormat
                .forPattern("yyyy/mm/dd HH:mm:ss");
        LocalDateTime localDateTime = new LocalDateTime();
        dateFormat.print(localDateTime);
        String dt = localDateTime.toString();
        return dt;
    }

}

