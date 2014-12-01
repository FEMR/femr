/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.util.calculations;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dateUtils {
//    public static int calculateYears(Date age) {
//        DateMidnight birthdate = new DateMidnight(age);
//        DateTime now = new DateTime();
//        Years years = Years.yearsBetween(birthdate, now);
//        return years.getYears();
//    }

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
        LocalDate birthdate = new LocalDate(born);
        LocalDate now = new LocalDate();
        Months months = Months.monthsBetween(birthdate, now);
        int monthsInt = months.getMonths();
        if (monthsInt < 24)
            return Integer.toString(monthsInt) + " MO";
        else
            return Integer.toString(monthsInt/12) + " YO";
    }

    public static float getAgeFloat(Date born) {
        LocalDate birthdate = new LocalDate(born);
        LocalDate now = new LocalDate();
        Months months = Months.monthsBetween(birthdate, now);
        int monthsInt = months.getMonths();
        float result = (float) monthsInt;
        return result/12;
    }


    public static String getFriendlyDate(DateTime dateTime){
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMMM d, yyyy - HH:mm:ss");
        String dtStr = dateTime.toString(fmt);
        return dtStr;
    }
    public static String getFriendlyDate(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String dStr = df.format(date);
        return dStr;
    }
}

