package femr.common.models;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV exporter for Daily Report records.
 * Generates RFC 4180 compliant CSV with dynamic headers (only exports non-null fields).
 * Properly escapes commas, quotes, and newlines in field values.
 */
public final class DailyReportCSV {

    private DailyReportCSV() {
        // Utility class, no instantiation
    }

    /**
     * Export a list of DailyReportItem records to CSV format.
     * Only includes fields that have at least one non-null value across all records.
     *
     * @param records List of DailyReportItem records to export
     * @return RFC 4180 compliant CSV string
     */
    public static String exportToCSV(List<DailyReportItem> records) {
        if (records == null || records.isEmpty()) {
            return "";
        }

        // Get all getter methods and identify which fields have values
        List<String> fieldNames = new ArrayList<>();
        List<Method> getters = new ArrayList<>();

        Method[] methods = DailyReportItem.class.getDeclaredMethods();
        for (Method method : methods) {
            if (isGetter(method)) {
                // Extract field name from getter method
                String fieldName = method.getName().substring(3); // Remove "get" prefix
                fieldName = decapitalize(fieldName);

                // Check if any record has a non-null value for this field
                if (hasNonNullValue(records, method)) {
                    fieldNames.add(fieldName);
                    getters.add(method);
                }
            }
        }

        StringBuilder writer = new StringBuilder();

        // Write header row
        if (!fieldNames.isEmpty()) {
            writer.append(escapeCSVField(fieldNames.get(0)));
            for (int i = 1; i < fieldNames.size(); i++) {
                writer.append(",");
                writer.append(escapeCSVField(fieldNames.get(i)));
            }
            writer.append("\r\n");

            // Write data rows
            for (DailyReportItem dailyReport : records) {
                for (int i = 0; i < getters.size(); i++) {
                    if (i > 0) {
                        writer.append(",");
                    }
                    Method getter = getters.get(i);
                    try {
                        Object value = getter.invoke(dailyReport);
                        if (value != null) {
                            writer.append(escapeCSVField(value.toString()));
                        }
                    } catch (Exception e) {
                        // If reflection fails, write empty field
                        writer.append("");
                    }
                }
                writer.append("\r\n");
            }
        }

        return writer.toString();
    }

    /**
     * Check if a method is a getter (starts with "get" and takes no parameters).
     */
    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get")
                && !method.getName().equals("getClass")
                && method.getParameterCount() == 0
                && method.getReturnType() != void.class;
    }

    /**
     * Check if any record has a non-null value for the given getter method.
     */
    private static boolean hasNonNullValue(List<DailyReportItem> records, Method getter) {
        for (DailyReportItem record : records) {
            try {
                Object value = getter.invoke(record);
                if (value != null) {
                    return true;
                }
            } catch (Exception e) {
                // Continue checking other records
            }
        }
        return false;
    }

    /**
     * Escape a field value for CSV according to RFC 4180.
     * Fields containing quotes, commas, or newlines are wrapped in quotes,
     * and internal quotes are doubled.
     */
    private static String escapeCSVField(String field) {
        if (field == null || field.isEmpty()) {
            return "";
        }

        // Check if field needs escaping
        if (field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r")) {
            // Escape internal quotes by doubling them
            field = field.replace("\"", "\"\"");
            // Wrap field in quotes
            return "\"" + field + "\"";
        }

        return field;
    }

    /**
     * Decapitalize the first letter of a string.
     */
    private static String decapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
