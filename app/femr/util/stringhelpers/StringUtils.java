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

}
