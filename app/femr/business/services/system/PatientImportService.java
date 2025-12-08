package femr.business.services.system;

import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service for importing patient data from CSV files
 * Expected CSV format: firstName,lastName,phone,address,city,sex,birth,encounterDate,weight,height,weeksPregnant,chiefComplaints,smoker,diabetic,alcohol,cholesterol,hypertension
 * Optional fields: weight (kg), height (cm), weeksPregnant (weeks), chiefComplaints (comma-separated list), smoker (0/1), diabetic (0/1), alcohol (0/1), cholesterol (0/1), hypertension (0/1)
 */
public class PatientImportService {

    private static final String CSV_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Parse CSV input stream and return list of PatientItems
     *
     * @param inputStream the input stream from CSV file
     * @return ServiceResponse containing list of PatientItems or errors
     */
    public ServiceResponse<List<PatientItem>> parsePatientCSV(InputStream inputStream) {
        ServiceResponse<List<PatientItem>> response = new ServiceResponse<>();
        List<PatientItem> patients = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            String[] headers = null;
            int rowNumber = 0;

            while ((line = reader.readLine()) != null) {
                rowNumber++;
                processCSVRow(line, rowNumber, headers, patients, response, rowNumber == 1);
                if (rowNumber == 1 && headers == null) {
                    headers = parseCSVLine(line);
                    validateHeaders(headers);
                }
            }

            response.setResponseObject(patients);

        } catch (IOException e) {
            response.addError("CSV Parse", "Error reading CSV file: " + e.getMessage());
        }

        return response;
    }

    /**
     * Process a single CSV row
     */
    private void processCSVRow(String line, int rowNumber, String[] headers, List<PatientItem> patients,
                               ServiceResponse<List<PatientItem>> response, boolean isHeaderRow) {
        if (line.trim().isEmpty() || isHeaderRow) {
            return;
        }

        if (headers == null) {
            response.addError("CSV Parse", "No headers found in CSV");
            return;
        }

        String[] values = parseCSVLine(line);
        if (values.length < 4) {
            response.addError("Row " + rowNumber, "Insufficient columns (expected at least 4)");
            return;
        }

        PatientItem patient = parsePatientFromRow(values, rowNumber, response);
        if (patient != null) {
            patients.add(patient);
        }
    }

    /**
     * Parse a single patient from CSV values
     */
    private PatientItem parsePatientFromRow(String[] values, int rowNumber, ServiceResponse<List<PatientItem>> response) {
        PatientItem patient = new PatientItem();
        SimpleDateFormat dateFormat = new SimpleDateFormat(CSV_DATE_FORMAT);

        // Set required fields
        patient.setFirstName(values[0].trim());
        patient.setLastName(values[1].trim());

        // Set optional phone
        if (values.length > 2 && !values[2].trim().isEmpty()) {
            patient.setPhoneNumber(values[2].trim());
        }

        // Set optional address
        if (values.length > 3 && !values[3].trim().isEmpty()) {
            patient.setAddress(values[3].trim());
        }

        // Set city (required, default to Unknown)
        if (values.length > 4 && !values[4].trim().isEmpty()) {
            patient.setCity(values[4].trim());
        } else {
            patient.setCity("Unknown");
        }

        // Set optional sex
        if (values.length > 5 && !values[5].trim().isEmpty()) {
            patient.setSex(values[5].trim());
        }

        // Set optional birth date
        if (values.length > 6 && !values[6].trim().isEmpty()) {
            try {
                Date birthDate = dateFormat.parse(values[6].trim());
                patient.setBirth(birthDate);
            } catch (ParseException e) {
                response.addError("Row " + rowNumber, "Invalid birth date format (expected yyyy-MM-dd): " + values[6]);
                return null;
            }
        }

        // Set optional weight (kg)
        if (values.length > 8 && !values[8].trim().isEmpty()) {
            try {
                patient.setWeight(Float.parseFloat(values[8].trim()));
            } catch (NumberFormatException e) {
                response.addError("Row " + rowNumber, "Invalid weight format: " + values[8]);
            }
        }

        // Set optional height (cm) - convert to feet and inches
        if (values.length > 9 && !values[9].trim().isEmpty()) {
            try {
                int heightCm = Integer.parseInt(values[9].trim());
                int feet = heightCm / 30; // 1 foot ≈ 30.48 cm
                int inches = (heightCm % 30) / 2; // 1 inch ≈ 2.54 cm
                patient.setHeightFeet(feet);
                patient.setHeightInches(inches);
            } catch (NumberFormatException e) {
                response.addError("Row " + rowNumber, "Invalid height format: " + values[9]);
            }
        }

        // Set optional weeks pregnant
        if (values.length > 10 && !values[10].trim().isEmpty()) {
            try {
                patient.setWeeksPregnant(Integer.parseInt(values[10].trim()));
            } catch (NumberFormatException e) {
                response.addError("Row " + rowNumber, "Invalid weeks pregnant format: " + values[10]);
            }
        }

        // Set chief complaints (column 11) - split by semicolon if multiple
        if (values.length > 11 && !values[11].trim().isEmpty()) {
            String complaintsStr = values[11].trim();
            List<String> complaints = new ArrayList<>();
            for (String complaint : complaintsStr.split(";")) {
                String trimmed = complaint.trim();
                if (!trimmed.isEmpty()) {
                    complaints.add(trimmed);
                }
            }
            if (!complaints.isEmpty()) {
                patient.setImportedChiefComplaints(complaints);
            }
        }

        // Set optional smoker status (0 or 1)
        if (values.length > 12 && !values[12].trim().isEmpty()) {
            try {
                patient.setSmoker(Integer.parseInt(values[12].trim()));
            } catch (NumberFormatException e) {
                // Skip on error
            }
        }

        // Set optional diabetic status (0 or 1)
        if (values.length > 13 && !values[13].trim().isEmpty()) {
            try {
                patient.setDiabetic(Integer.parseInt(values[13].trim()));
            } catch (NumberFormatException e) {
                // Skip on error
            }
        }

        // Set optional alcohol status (0 or 1)
        if (values.length > 14 && !values[14].trim().isEmpty()) {
            try {
                patient.setAlcohol(Integer.parseInt(values[14].trim()));
            } catch (NumberFormatException e) {
                // Skip on error
            }
        }

        // Set optional cholesterol status (0 or 1)
        if (values.length > 15 && !values[15].trim().isEmpty()) {
            try {
                patient.setCholesterol(Integer.parseInt(values[15].trim()));
            } catch (NumberFormatException e) {
                // Skip on error
            }
        }

        // Set optional hypertension status (0 or 1)
        if (values.length > 16 && !values[16].trim().isEmpty()) {
            try {
                patient.setHypertension(Integer.parseInt(values[16].trim()));
            } catch (NumberFormatException e) {
                // Skip on error
            }
        }

        // Validate required fields
        if (!isValidPatient(patient, rowNumber, response)) {
            return null;
        }

        return patient;
    }

    /**
     * Validate patient has required fields
     */
    private boolean isValidPatient(PatientItem patient, int rowNumber, ServiceResponse<List<PatientItem>> response) {
        if (patient.getFirstName() == null || patient.getFirstName().trim().isEmpty()) {
            response.addError("Row " + rowNumber, "firstName is required");
            return false;
        }

        if (patient.getLastName() == null || patient.getLastName().trim().isEmpty()) {
            response.addError("Row " + rowNumber, "lastName is required");
            return false;
        }

        return true;
    }

    /**
     * Parse a CSV line, handling quoted fields
     *
     * @param line the CSV line to parse
     * @return array of field values
     */
    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ',' && !insideQuotes) {
                result.add(currentField.toString().trim());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }

        result.add(currentField.toString().trim());
        return result.toArray(new String[0]);
    }

    /**
     * Validate that CSV has expected headers
     *
     * @param headers the header row
     */
    private void validateHeaders(String[] headers) {
        if (headers.length < 4) {
            throw new IllegalArgumentException("CSV must have at least 4 columns");
        }
    }
}
