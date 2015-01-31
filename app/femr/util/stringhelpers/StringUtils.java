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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    public static boolean isNullOrWhiteSpace(String stringToCheck) {
        return stringToCheck == null || stringToCheck.trim().isEmpty();
    }

    public static boolean isNotNullOrWhiteSpace(String stringToCheck) {
        return !isNullOrWhiteSpace(stringToCheck);
    }

    /**
     * Formats the regular date string into a more humanly readable one
     * @param dateString the Date string in the format yyyy-MM-dd HH:mm:ss.S
     * @return A date String in the format MMMM dd, yyyy, h:mm:ss.s a Or the original if
     * it fails to parse it
     */
    public static String FormatDateTime(String dateString) {
        Date date = null;
        String formattedDate = dateString;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(dateString);
            formattedDate = new SimpleDateFormat("MMMM dd, yyyy, h:mm:ss a").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


    /**
     * Formats the regular date string into a more humanly readable one
     * @param dateString the Date string in the format yyyy-MM-dd HH:mm:ss.S
     * @return A date String in the format MMMM dd, yyyy, h:mm:ss.s a Or the original if
     * it fails to parse it
     */
    public static String FormatTime(String dateString){
        Date date = null;
        String formattedDate = dateString;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(dateString);
            formattedDate = new SimpleDateFormat("h:mm a").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    public static String ToSimpleDate(Date dt)
    {
        try
        {
            if(dt != null)
                return new SimpleDateFormat("yyyy-MM-dd").format(dt);
        } catch(Exception e)
        {
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

}
