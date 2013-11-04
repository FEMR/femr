package femr.util.calculations;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Years;

import java.util.Date;

/**
 * Author: Mrgadgetz
 * Date:   11/3/13
 */
public class ageUtils {
    public static int calculateYears(Date age) {
        DateMidnight birthdate = new DateMidnight(age);
        DateTime now = new DateTime();
        Years years = Years.yearsBetween(birthdate, now);
        return years.getYears();
    }

}

