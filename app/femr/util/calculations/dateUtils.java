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

import femr.common.models.PatientEncounterItem;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.Logger;

import java.text.DateFormat;
import java.text.ParseException;
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

    /**
     * Returns a string in the format "yyyy/MM/dd HH:mm:ss"
     * *
     * @param timeStamp an unformated DateTime
     * @return the time as a string
     */
    public static String convertTimeToString(DateTime timeStamp){
        DateTimeFormatter dateFormat = DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm:ss");
        String dt = dateFormat.print(timeStamp).toString();
        return dt;
    }

    public static String getCurrentDateTimeString(){
        DateTimeFormatter dateFormat = DateTimeFormat
                .forPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = new LocalDateTime();
        dateFormat.print(localDateTime);
        String dt = dateFormat.print(localDateTime).toString();
        return dt;
    }

    public static DateTime getCurrentDateTime(){
        return new DateTime();
    }

    /**
     * Gets the integer age of a patient, then appends "YO" or "MO"
     * depending on if the patient age is in years(adult) or months(baby)
     *
     * @param born the birthdate of the patient
     * @return a string with the patient's age
     */
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

    /**
     * Gets the patient's age in years. If the patient is an infant with less than 12 years of life,
     * 0 will be returned.
     *
     * @param born the birthdate of the patient
     * @return an Integer that represents the number of years the patient has been alive. Returns null
     * if an error occured OR if the patient does not have an age (just an age classification).
     */
    public static Integer getYearsInteger(Date born) {

        if (born == null){
            return null;
        }

        LocalDate birthdate = new LocalDate(born);
        LocalDate now = new LocalDate();
        Integer age = 0;
        Months months = Months.monthsBetween(birthdate, now);
        int monthsInt = months.getMonths();
        if (monthsInt >= 12) {
            double temp = Math.floor(monthsInt / 12);
            try {
                age = (int) Math.round(temp);
            } catch (Exception ex) {
                age = null;
                Logger.error("a patient's age could not be handled as an int");
            }

        }

        return age;
    }

    /**
     * Gets the patient's age in months.
     *
     * @param born the birthdate of the patient
     * @return an Integer that represents the number of months the patient has been alive. Returns null
     * if an error occured OR if the patient does not have an age (just an age classification).
     */
    public static Integer getMonthsInteger(Date born) {

        if (born == null){
            return null;
        }

        LocalDate birthdate = new LocalDate(born);
        LocalDate now = new LocalDate();
        Months months = Months.monthsBetween(birthdate, now);

        return months.getMonths();
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

    /**
     * Converts a DateTime object to an international date string
     *
     * Format: (dd/MMMM/yyyy)
     * Format: 02-Aug-1989
     *
     * @param dateTime
     * @return
     */
    public static String getFriendlyInternationalDateTime(DateTime dateTime){

        if (dateTime == null)
            return null;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MMMM/yyyy");
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

    /**
     * Takes a date object and formats it into a user friendly string
     *
     * Format: (MM/dd/yyyy)
     * Format: (08/02/1989)
     *
     * @param date the date you would like to format, not null
     * @return the sexy looking date string or null if errors
     */
    public static String getFriendlyDate(Date date){

        if (date == null)
            return null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String dStr = df.format(date);
        return dStr;
    }

    /**
     * Takes a date object and formats it into a user friendly string of which
     * can be recognized internationally
     *
     * Format: (dd/MMMM/yyyy)
     * Format: 02-Aug-1989
     *
     * @param date the date you would like to format, not null
     * @return the sexy looking date string or null if errors
     */
    public static String getFriendlyInternationalDate(Date date){

        if (date == null)
            return null;
        DateFormat df = new SimpleDateFormat("dd/MMMM/yyyy");
        String dStr = df.format(date);
        return dStr;
    }

    /**
     * Takes a String and composed of the date and time and converts
     * to a string of just the time
     * Return Format: (HH:MM:SS)
     *
     *
     * @param String of the complete date and time
     * @return String of just the time or " " if string is null
     */

    public static String getDisplayTime(String completeTime)
    {
        String time;
        if(completeTime!=null) {
            String[] parts = completeTime.split("-");
            time = parts[1];
            return time;
        }
        else
            return " ";

    }

    /**
     * Takes a PatientEncounter Time and returns
     * a string of just the turn around time (time elasped between the Triage Check in
     * and Pharmacy Check out
     * Return Format: (HH:MM:SS)
     *
     *
     * @param item the patient encounter item
     * @return String of just the time or " " if string is null
     */
    public static String getTurnAroundTime(PatientEncounterItem item){

        String time=" ";
        if(item.getPharmacyDateOfVisit()!= null &&item.getTriageDateOfVisit()!=null) {
            SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
            try {
                Date td,pd;
                td = ft.parse(getDisplayTime(item.getTriageDateOfVisit()));
                pd= ft.parse(getDisplayTime(item.getPharmacyDateOfVisit()));
                long diff = pd.getTime() - td.getTime();
                String ss = String.valueOf(diff / 1000 % 60);
                String sm = String.valueOf(diff / (60 * 1000) % 60);
                String sh = String.valueOf(diff / (60 * 60 * 1000));
                time="Hours: "+sh+" Minutes: " +sm+"  Seconds: "+ss;
            }catch (ParseException e) {
                System.out.println("Unparseable using " + ft);
            }
            return time;
        }
        else {
            return time;
        }
    }

}

