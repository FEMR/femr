package femr.test;

import femr.common.models.DailyReportCSV;
import femr.common.models.DailyReportItem;
import femr.data.daos.core.DailyReportRepository;
import femr.business.services.system.DailyReportService;
import femr.business.services.system.DailyReportService.ExportResult;

import java.util.List;

/**
 * Manual test suite for Daily Report export pipeline.
 * Run each test method individually to verify pipeline steps.
 */
public class DailyReportPipelineTest {
    
    public static void main(String[] args) {
        System.out.println("Running Daily Report Pipeline Tests...\n");
        
        try {
            test1_RepositoryReturnsMockData();
            test2_ServiceValidationFilters();
            test3_ServiceDetectsRequiredFields();
            test4_CSVExportWorks();
            test5_CSVStructure();
            test6_FullPipelineIntegration();
            test7_EdgeCases();
            
            System.out.println("\n" + "=".repeat(80));
            System.out.println("ALL TESTS PASSED ✓");
            System.out.println("=".repeat(80));
            
        } catch (Exception e) {
            System.err.println("TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * TEST 1: Repository should return 5 mock reports
     */
    static void test1_RepositoryReturnsMockData() {
        System.out.println("[TEST 1] Repository returns mock data");
        DailyReportRepository repository = new DailyReportRepository();
        List<DailyReportItem> records = repository.getAllReports();
        
        assert records != null : "Repository should not return null";
        assert records.size() == 5 : "Repository should return 5 records, got " + records.size();
        
        System.out.println("  ✓ Repository returned " + records.size() + " records\n");
    }
    
    /**
     * TEST 2: Service should validate and filter records
     */
    static void test2_ServiceValidationFilters() {
        System.out.println("[TEST 2] Service filters invalid records");
        DailyReportRepository repository = new DailyReportRepository();
        DailyReportService service = new DailyReportService();
        
        List<DailyReportItem> allRecords = repository.getAllReports();
        ExportResult result = service.validateRecords(allRecords);
        
        assert result != null : "ExportResult should not be null";
        assert result.validRecords != null : "Valid records should not be null";
        assert result.validationErrors != null : "Validation errors should not be null";
        assert result.validRecords.size() == 3 : "Should have 3 valid records, got " + result.validRecords.size();
        assert result.validationErrors.size() == 2 : "Should have 2 errors, got " + result.validationErrors.size();
        
        System.out.println("  ✓ Filtered: " + allRecords.size() + " records → " + result.validRecords.size() + " valid");
        System.out.println("  ✓ Found " + result.validationErrors.size() + " invalid records\n");
    }
    
    /**
     * TEST 3: Service should detect required field violations
     */
    static void test3_ServiceDetectsRequiredFields() {
        System.out.println("[TEST 3] Service detects missing required fields");
        DailyReportRepository repository = new DailyReportRepository();
        DailyReportService service = new DailyReportService();
        
        List<DailyReportItem> allRecords = repository.getAllReports();
        ExportResult result = service.validateRecords(allRecords);
        
        String errors = String.join(" | ", result.validationErrors);
        assert errors.contains("Organization Name") : "Should detect missing Organization Name";
        assert errors.contains("Contact Person Name") : "Should detect missing Contact Person Name";
        
        System.out.println("  ✓ Detected required field violations:");
        for (String error : result.validationErrors) {
            System.out.println("    - " + error);
        }
        System.out.println();
    }
    
    /**
     * TEST 4: CSV export should work with valid records
     */
    static void test4_CSVExportWorks() {
        System.out.println("[TEST 4] CSV export generates valid output");
        DailyReportRepository repository = new DailyReportRepository();
        DailyReportService service = new DailyReportService();
        
        List<DailyReportItem> allRecords = repository.getAllReports();
        ExportResult result = service.validateRecords(allRecords);
        String csvContent = DailyReportCSV.exportToCSV(result.validRecords);
        
        assert csvContent != null : "CSV content should not be null";
        assert !csvContent.isEmpty() : "CSV content should not be empty";
        assert csvContent.contains(",") : "CSV should contain commas";
        
        System.out.println("  ✓ CSV generated successfully");
        System.out.println("  ✓ CSV size: " + csvContent.length() + " bytes\n");
    }
    
    /**
     * TEST 5: CSV structure should be valid RFC 4180
     */
    static void test5_CSVStructure() {
        System.out.println("[TEST 5] CSV has valid RFC 4180 structure");
        DailyReportRepository repository = new DailyReportRepository();
        DailyReportService service = new DailyReportService();
        
        List<DailyReportItem> allRecords = repository.getAllReports();
        ExportResult result = service.validateRecords(allRecords);
        String csvContent = DailyReportCSV.exportToCSV(result.validRecords);
        
        assert csvContent.contains("\r\n") : "CSV should use CRLF line endings";
        String[] lines = csvContent.split("\r\n");
        assert lines.length > 1 : "CSV should have multiple lines (header + data)";
        
        String headerLine = lines[0];
        assert !headerLine.isEmpty() : "Header should not be empty";
        
        System.out.println("  ✓ Has header row with fields: " + headerLine.split(",").length + " columns");
        System.out.println("  ✓ Has " + (lines.length - 1) + " data rows");
        System.out.println("  ✓ Uses proper CRLF line endings\n");
    }
    
    /**
     * TEST 6: Full pipeline integration test
     */
    static void test6_FullPipelineIntegration() {
        System.out.println("[TEST 6] Full pipeline: Repository → Service → CSV");
        
        // Step 1: Get records from repository
        DailyReportRepository repository = new DailyReportRepository();
        List<DailyReportItem> allRecords = repository.getAllReports();
        assert !allRecords.isEmpty() : "Repository should have data";
        System.out.println("  Step 1: Retrieved " + allRecords.size() + " records from repository");
        
        // Step 2: Validate through service
        DailyReportService service = new DailyReportService();
        ExportResult result = service.validateRecords(allRecords);
        assert result.validRecords.size() > 0 : "Should have valid records after filtering";
        System.out.println("  Step 2: Service filtered to " + result.validRecords.size() + " valid records");
        System.out.println("           Rejected " + result.validationErrors.size() + " invalid records");
        
        // Step 3: Export to CSV
        String csvContent = DailyReportCSV.exportToCSV(result.validRecords);
        assert csvContent.length() > 0 : "CSV should be generated";
        System.out.println("  Step 3: CSV export generated " + csvContent.length() + " bytes");
        
        System.out.println("  ✓ Full pipeline completed successfully\n");
    }
    
    /**
     * TEST 7: Edge cases
     */
    static void test7_EdgeCases() {
        System.out.println("[TEST 7] Edge cases");
        
        // Empty list
        String emptyCSV = DailyReportCSV.exportToCSV(List.of());
        assert emptyCSV.isEmpty() : "Empty list should produce empty CSV";
        System.out.println("  ✓ Empty record list handled correctly");
        
        // Service with empty list
        DailyReportService service = new DailyReportService();
        ExportResult emptyResult = service.validateRecords(List.of());
        assert emptyResult.validRecords.isEmpty() : "Should have no valid records";
        assert emptyResult.validationErrors.isEmpty() : "Should have no errors";
        System.out.println("  ✓ Service handles empty list correctly");
        
        // Null handling
        DailyReportItem nullFieldsReport = new DailyReportItem();
        nullFieldsReport.setOrganizationName("Test");
        nullFieldsReport.setTeamName("Test Team");
        nullFieldsReport.setDateOfActivity("2024-11-13");
        nullFieldsReport.setContactPersonName("Contact");
        nullFieldsReport.setTotalNumberOfNewConsultations(0);
        
        ExportResult singleResult = service.validateRecords(List.of(nullFieldsReport));
        assert singleResult.validRecords.size() == 1 : "Single valid record should pass";
        String singleCSV = DailyReportCSV.exportToCSV(singleResult.validRecords);
        assert !singleCSV.isEmpty() : "Single record should generate CSV";
        System.out.println("  ✓ Null fields handled correctly\n");
    }
}

