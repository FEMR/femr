package femr.util.stringhelpers;

public class StringUtils {
    public static boolean isNullOrWhiteSpace(String stringToCheck) {
        return stringToCheck == null || stringToCheck.trim().isEmpty();
    }

    public static boolean isNotNullOrWhiteSpace(String stringToCheck) {
        return !isNullOrWhiteSpace(stringToCheck);
    }
}
