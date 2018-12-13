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
package femr.util.stringhelpers;

import femr.util.calculations.dateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class contains utilities for manipulating strings. If you add something here, please clearly document the
 * format of both the input and output.
 */
public class StringUtils {

    /**
     * Checks if a String is null or doesn't have any characters.
     *
     * @param stringToCheck the string you need to check, may be null.
     * @return true if the string is null or doesn't have any characters, false otherwise
     */
    public static boolean isNullOrWhiteSpace(String stringToCheck) {
        return stringToCheck == null || stringToCheck.trim().isEmpty();
    }

    /**
     * Checks if a String is null or doesn't have any characters.
     *
     * @param stringToCheck the string you need to check, may be null.
     * @return false if the string is null or doesn't have any characters, true otherwise
     */
    public static boolean isNotNullOrWhiteSpace(String stringToCheck) {
        return !isNullOrWhiteSpace(stringToCheck);
    }

    /**
     * Formats the regular date string into a more humanly readable one
     *
     * @param dateString the Date string in the format yyyy-MM-dd HH:mm:ss.S or yyyy-MM-ddTHH:mm:ssZ
     * @return A date String in the format MMMM dd, yyyy, h:mm:ss.s a Or the original if it fails to parse it
     */
    public static String FormatDateTime(String dateString) {

        Date date;
        String formattedDate = dateString;
        String pattern = (dateString.indexOf('T') == -1) ? "yyyy-MM-dd HH:mm:ss.S" : "yyyy-MM-dd'T'HH:mm:ss";
        try {

            date = new SimpleDateFormat(pattern).parse(dateString);
            formattedDate = new SimpleDateFormat("MMMM dd, yyyy, h:mm:ss a").format(date);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return formattedDate;
    }

    /**
     * Formats the regular date string into a more humanly readable one
     *
     * @param dateString the Date string in the format yyyy-MM-dd HH:mm:ss.S
     * @return A date String in the format MMMM dd, yyyy, h:mm:ss.s a Or the original if it fails to parse it
     */
    public static String FormatTime(String dateString) {

        Date date;
        String formattedDate = dateString;
        try {

            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(dateString);
            formattedDate = new SimpleDateFormat("h:mm a").format(date);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return formattedDate;
    }

    /**
     * Convert a Date object to a SimpleDate object
     *
     * @param dt the date
     * @return A SimpleDate in the format yyyy-MM-dd or an empty string if it fails to convert
     */
    public static String ToSimpleDate(Date dt) {
        try {

            if (dt != null)
                return new SimpleDateFormat("yyyy-MM-dd").format(dt);
        } catch (Exception e) {

            e.printStackTrace();
        }

        return "";
    }

    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    /**
     * Checks if a String is empty
     *
     * @param str string to check
     * @return returns the String "N/A" if str is null or empty.
     */
    public static String outputStringOrNA(String str) {
        if (StringUtils.isNullOrWhiteSpace(str)) return "N/A";
        else return str;
    }

    /**
     * Checks if a Integer is null
     *
     * @param value Integer to check
     * @return returns the String "N/A" if value is null or the Integer as a string
     */
    public static String outputIntOrNA(Integer value) {

        if (value == null) return "N/A";
        else return value.toString();
    }

    /**
     * Checks if a valid Height exists, and returns a string with the height info formatted as imperial if it exists.
     * e.g. 1'2" when height exists. N/A when it does not
     *
     * @param feet feet in the height
     * @param inches inches in the height
     * @return returns "N/A" if feet and inches are null or a blank string for either if they are null
     */
    public static String outputHeightImperialOrNA(Integer feet, Integer inches) {
        if (feet == null && inches == null) {
            return "N/A";
        } else {
            String output = "";
            if (feet != null) {
                output += feet + "'";
            }
            if (inches != null) {
                output += inches + "\"";
            }
            return output;
        }
    }

    /**
     * Checks if a valid Height exists, and returns a string with the height info formatted as metric if it exists.
     * e.g. 1m2cm when height exists. N/A when it does not.
     *
     * @param meters meters in the height
     * @param centimeters centimeters in the height
     * @return returns "N/A" if meters and inches are null or a blank string for either if they are null
     */
    public static String outputHeightMetricOrNA(Integer meters, Integer centimeters) {
        if (meters == null && meters == null) {
            return "N/A";
        } else {
            String output = "";
            if (meters != null) {
                output += meters + "m";
            }
            if (centimeters != null) {
                output += centimeters + "cm";
            }
            return output;
        }
    }

    /**
     * Checks if a Float is null
     *
     * @param value Float to check
     * @return returns the String "N/A" if value is null or the Float as a string
     */
    public static String outputFloatOrNA(Float value) {
        if (value == null) return "N/A";
        else return value.toString();
    }

    /**
     * Checks if a valid Blood Pressure exists
     *
     * @param systolic the systolic blood pressure
     * @param diastolic the diastolic blood pressure
     * @return returns "N/A" if systolic and diastolic are null or a blank string for either if they are null
     */
    public static String outputBloodPressureOrNA(String systolic, String diastolic) {
        if (systolic == null && diastolic == null) return "N/A";
        else return systolic + " / " + diastolic;
    }

    /**
     * Generates a user friendly string that displays the title of a mission trip. This title includes
     * the name of the team, country, start date, and end date of the trip. All dates are formatted
     * for international interpretation (e.g. 08-Aug-1989).
     *
     * @param teamName name of the mission team, not null
     * @param country country the team travels to, not null
     * @param startDate approximate start date of the trip, not null
     * @param endDate approximate end date of the trip, not null
     * @return The user friendly trip title or null if parameters were null
     */
    public static String generateMissionTripTitle(String teamName, String country, Date startDate, Date endDate){

        if (StringUtils.isNullOrWhiteSpace(teamName) || StringUtils.isNullOrWhiteSpace(country) || startDate == null || endDate == null){

            return null;
        }

        String tripTitle = teamName + "-" + country + "-(" + dateUtils.getFriendlyInternationalDate(startDate) + "-" + dateUtils.getFriendlyInternationalDate(endDate) + ")";
        return tripTitle;
    }

}
