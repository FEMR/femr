package femr.test;

import femr.common.models.DailyReportCSV;
import femr.common.models.DailyReportItem;
import femr.data.daos.core.DailyReportRepository;
import femr.business.services.system.DailyReportService;
import femr.business.services.system.DailyReportService.ExportResult;

import java.io.IOException;
import java.util.List;

/**
 * Test harness for Daily Report export pipeline.
 * Demonstrates: Repository → Service (validation) → CSV export
 */
public class DailyReportTest {

    public static void main(String[] args) {
        try {
            String separator = "================================================================================";
            System.out.println(separator);
            System.out.println("DAILY REPORT EXPORT PIPELINE TEST");
            System.out.println(separator);

            // Step 1: Fetch records from repository (mock data)
            System.out.println("\n[STEP 1] Fetching records from DailyReportRepository...");
            DailyReportRepository repository = new DailyReportRepository();
            List<DailyReportItem> allRecords = repository.getAllReports();
            System.out.println("[OK] Retrieved " + allRecords.size() + " total records");

            // Step 2: Validate records through service
            System.out.println("\n[STEP 2] Validating records through DailyReportService...");
            DailyReportService service = new DailyReportService();
            ExportResult result = service.validateRecords(allRecords);
            
            System.out.println("[OK] Valid records: " + result.validRecords.size());
            System.out.println("[OK] Invalid records: " + result.validationErrors.size());

            // Display validation errors
            if (!result.validationErrors.isEmpty()) {
                System.out.println("\n[VALIDATION ERRORS]");
                for (String error : result.validationErrors) {
                    System.out.println("  [ERROR] " + error);
                }
            }

            // Step 3: Export valid records to CSV
            System.out.println("\n[STEP 3] Exporting valid records to CSV...");
            String csvContent = DailyReportCSV.exportToCSV(result.validRecords);
            System.out.println("[OK] CSV generated successfully");

            // Display CSV content
            System.out.println("\n[CSV CONTENT]");
            System.out.println(csvContent);

            // Summary
            System.out.println("\n" + separator);
            System.out.println("SUMMARY");
            System.out.println(separator);
            System.out.println("Total records from repository: " + allRecords.size());
            System.out.println("Records that passed validation: " + result.validRecords.size() + " [OK]");
            System.out.println("Records that failed validation: " + result.validationErrors.size());
            System.out.println("CSV rows (including header): " + (result.validRecords.size() + 1));
            System.out.println("\nExport pipeline: SUCCESSFUL [OK]");

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
