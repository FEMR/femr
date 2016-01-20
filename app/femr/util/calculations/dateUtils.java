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

/**
 * This class contains utilities for manipulating dates. If you add something here, please clearly document the
 * format of both the input and output.
 */
public class dateUtils {

    public static int calculateYears(Date age) {

        LocalDate birthdate = new LocalDate(age);
        LocalDate now = new LocalDate();
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

    public static float getAgeAsOfDateFloat(Date born, DateTime asOfDate) {

        LocalDate birthdate = new LocalDate(born);
        LocalDate currDate = new LocalDate(asOfDate);
        Months months = Months.monthsBetween(birthdate, currDate);
        int monthsInt = months.getMonths();
        float result = (float) monthsInt;
        return result/12;
    }

    /**
     * Converts a DateTime object to a string
     *
     * @param dateTime the DateTime object to convert, not null
     * @return A string in the format "mm yyyy" or null if dateTime is null
     */
    public static String getFriendlyDateMonthYear(DateTime dateTime){

        if (dateTime == null)
            return null;

        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/yyyy");
        String dateString = dateTime.toString(formatter);

        return dateString;
    }

    public static String getFriendlyDate(DateTime dateTime){
        if (dateTime == null)
            return null;
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMMM d, yyyy - HH:mm:ss");
        String dtStr = dateTime.toString(fmt);
        return dtStr;
    }

    public static String getFriendlyDate(Date date){
        if (date == null)
            return null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String dStr = df.format(date);
        return dStr;
    }
}

