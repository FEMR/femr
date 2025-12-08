package femr.business.services.system;

import femr.common.models.DailyReportItem;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Daily Report Service - validates and filters reports with malformed/missing fields.
 * Only exports records that have minimum required fields.
 */
public class DailyReportService {
    
    private List<String> validationErrors;
    
    public DailyReportService() {
        this.validationErrors = new ArrayList<>();
    }
    
    /**
     * Validate a list of daily reports.
     * Returns only reports with required fields + list of validation errors.
     */
    public ExportResult validateRecords(List<DailyReportItem> records) {
        validationErrors.clear();
        
        // Validate each report
        List<DailyReportItem> validReports = records.stream()
            .filter(this::validateReport)
            .collect(Collectors.toList());
        
        // Return results
        ExportResult result = new ExportResult();
        result.setValidRecords(validReports);
        result.setValidationErrors(validationErrors);
        result.setTotalRecords(records.size());
        result.setValidRecordCount(validReports.size());
        
        return result;
    }
    
    /**
     * Validate a single Daily Report against requirements.
     * Returns true if valid, false if invalid (error added to list).
     * 
     * Required fields: organizationName, teamName, dateOfActivity, contactPersonName, 
     *                  totalNumberOfNewConsultations
     */
    private boolean validateReport(DailyReportItem report) {
        boolean isValid = true;
        
        // Check required fields
        if (report.getOrganizationName() == null || report.getOrganizationName().isEmpty()) {
            validationErrors.add("Report missing Organization Name (required field)");
            isValid = false;
        }
        
        if (report.getTeamName() == null || report.getTeamName().isEmpty()) {
            validationErrors.add("Report missing Team Name (required field)");
            isValid = false;
        }
        
        if (report.getDateOfActivity() == null || report.getDateOfActivity().isEmpty()) {
            validationErrors.add("Report missing Date of Activity (required field)");
            isValid = false;
        }
        
        if (report.getContactPersonName() == null || report.getContactPersonName().isEmpty()) {
            validationErrors.add("Report missing Contact Person Name (required field)");
            isValid = false;
        }
        
        if (report.getTotalNumberOfNewConsultations() == null) {
            validationErrors.add("Report missing Total Consultations (required field)");
            isValid = false;
        }
        
        // Validate numeric ranges (if provided)
        if (report.getTotalNumberOfNewConsultations() != null && report.getTotalNumberOfNewConsultations() < 0) {
            validationErrors.add("Report has negative consultation count: " + report.getTotalNumberOfNewConsultations());
            isValid = false;
        }
        
        if (report.getTotalAdmissions() != null && report.getTotalAdmissions() < 0) {
            validationErrors.add("Report has negative admission count: " + report.getTotalAdmissions());
            isValid = false;
        }
        
        // Check for inconsistencies in age categories (gender breakdown shouldn't exceed total)
        if (report.getTotalNumberOfNewConsultations() != null) {
            checkAgeConsistency(report);
        }
        
        return isValid;
    }
    
    /**
     * Validate consistency: age breakdown shouldn't exceed consultation total.
     */
    private void checkAgeConsistency(DailyReportItem report) {
        Integer totalConsultations = report.getTotalNumberOfNewConsultations();
        
        // Count males
        Integer maleTotal = 0;
        if (report.getMaleLessThan1() != null) maleTotal += report.getMaleLessThan1();
        if (report.getMale1To4() != null) maleTotal += report.getMale1To4();
        if (report.getMale5To17() != null) maleTotal += report.getMale5To17();
        if (report.getMale18To64() != null) maleTotal += report.getMale18To64();
        if (report.getMale65Plus() != null) maleTotal += report.getMale65Plus();
        
        // Count females (non-pregnant)
        Integer femaleTotal = 0;
        if (report.getFemaleLessThan1() != null) femaleTotal += report.getFemaleLessThan1();
        if (report.getFemale1To4() != null) femaleTotal += report.getFemale1To4();
        if (report.getFemale5To17() != null) femaleTotal += report.getFemale5To17();
        if (report.getFemale18To64() != null) femaleTotal += report.getFemale18To64();
        if (report.getFemale65Plus() != null) femaleTotal += report.getFemale65Plus();
        
        Integer genderTotal = maleTotal + femaleTotal;
        
        if (genderTotal > totalConsultations) {
            validationErrors.add("Report: Age category breakdown (" + genderTotal + 
                ") exceeds total consultations (" + totalConsultations + ")");
        }
    }
    
    /**
     * Container class for export results.
     */
    public static class ExportResult {
        public List<DailyReportItem> validRecords;
        public List<String> validationErrors;
        private Integer totalRecords;
        private Integer validRecordCount;
        
        public List<DailyReportItem> getValidRecords() { return validRecords; }
        public void setValidRecords(List<DailyReportItem> validRecords) { this.validRecords = validRecords; }
        
        public List<String> getValidationErrors() { return validationErrors; }
        public void setValidationErrors(List<String> validationErrors) { this.validationErrors = validationErrors; }
        
        public Integer getTotalRecords() { return totalRecords; }
        public void setTotalRecords(Integer totalRecords) { this.totalRecords = totalRecords; }
        
        public Integer getValidRecordCount() { return validRecordCount; }
        public void setValidRecordCount(Integer validRecordCount) { this.validRecordCount = validRecordCount; }
    }
}
