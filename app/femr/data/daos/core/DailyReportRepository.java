package femr.data.daos.core;

import femr.common.models.DailyReportItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Daily Report Repository - returns reports with optional/malformed fields.
 * In production, this would query the database for daily reports.
 */
public class DailyReportRepository {
    
    /**
     * Get all daily reports (for testing/demo).
     * Returns reports with potentially missing or malformed fields.
     */
    public List<DailyReportItem> getAllReports() {
        return getMockReports();
    }
    
    /**
     * Get daily reports for a specific team (in production, filtered by team).
     * Returns reports with potentially missing or malformed fields.
     */
    public List<DailyReportItem> getReportsForTeam(String teamName) {
        // In production: return repository.query("SELECT * FROM daily_reports WHERE teamName = ?", teamName)
        // For now: return all mock reports
        return getMockReports();
    }
    
    /**
     * Generate mock Daily Reports for testing.
     * Returns 5 reports with various scenarios: complete, partial, invalid, minimal.
     */
    private static List<DailyReportItem> getMockReports() {
        List<DailyReportItem> reports = new ArrayList<>();
        
        // Report 1: Complete report with all fields populated
        DailyReportItem r1 = new DailyReportItem();
        r1.setOrganizationName("MSF OCA");
        r1.setTeamName("Team Alpha");
        r1.setTeamType("Mobile Clinic");
        r1.setContactPersonName("Dr. Smith");
        r1.setPhoneNumber("+1-555-0001");
        r1.setEmail("smith@msf.org");
        r1.setDateOfActivity("2024-11-10");
        r1.setTimeOfReporting("14:30");
        r1.setState("State A");
        r1.setCity("City A");
        r1.setVillage("Village A");
        r1.setTotalNumberOfNewConsultations(45);
        r1.setTotalAdmissions(3);
        r1.setLiveBirth(1);
        r1.setMale18To64(20);
        r1.setFemale18To64(25);
        r1.setMajorHeadFaceInjury(5);
        r1.setUpperRespiratoryInfection(12);
        r1.setAcuteWateryDiarrhea(8);
        r1.setMajorProcedureExcludingHds31(2);
        r1.setNormalVaginalDeliveryNvd(1);
        r1.setDischargeWithoutMedicalFollowUp(true);
        r1.setDirectlyRelatedToEvent(true);
        r1.setVulnerableChild(true);
        r1.setUnexpectedDeaths(false);
        r1.setDetailedComment1("Good clinic day, high patient load");
        reports.add(r1);
        
        // Report 2: Partial report - missing several optional fields
        DailyReportItem r2 = new DailyReportItem();
        r2.setOrganizationName("MSF OCA");
        r2.setTeamName("Team Beta");
        r2.setTeamType("Fixed Facility");
        r2.setContactPersonName("Nurse Johnson");
        r2.setPhoneNumber("+1-555-0002");
        r2.setDateOfActivity("2024-11-11");
        r2.setState("State B");
        r2.setCity("City B");
        r2.setTotalNumberOfNewConsultations(32);  // Only basic count
        // Missing gender breakdown (optional)
        r2.setUpperRespiratoryInfection(8);
        r2.setAcuteWateryDiarrhea(5);
        r2.setMajorProcedureExcludingHds31(1);
        // Missing most outcome data
        r2.setDetailedComment1("Moderate patient load, limited supplies");
        reports.add(r2);
        
        // Report 3: INVALID - Missing required field (organizationName)
        DailyReportItem r3 = new DailyReportItem();
        r3.setOrganizationName(null);  // REQUIRED - MISSING
        r3.setTeamName("Team Gamma");
        r3.setTeamType("Mobile Clinic");
        r3.setContactPersonName("Dr. Williams");
        r3.setDateOfActivity("2024-11-12");
        r3.setTotalNumberOfNewConsultations(28);
        r3.setUpperRespiratoryInfection(7);
        r3.setDetailedComment1("Data entry incomplete");
        reports.add(r3);
        
        // Report 4: Minimal report - only absolutely required fields
        DailyReportItem r4 = new DailyReportItem();
        r4.setOrganizationName("MSF OCA");
        r4.setTeamName("Team Delta");
        r4.setTeamType("Mobile Clinic");
        r4.setContactPersonName("Health Worker");
        r4.setDateOfActivity("2024-11-13");
        r4.setTotalNumberOfNewConsultations(18);
        // Everything else null/empty
        reports.add(r4);
        
        // Report 5: INVALID - Missing required field (contactPersonName)
        DailyReportItem r5 = new DailyReportItem();
        r5.setOrganizationName("MSF OCA");
        r5.setTeamName("Team Echo");
        r5.setTeamType("Fixed Facility");
        r5.setContactPersonName(null);  // REQUIRED - MISSING
        r5.setDateOfActivity("2024-11-14");
        r5.setTotalNumberOfNewConsultations(25);
        r5.setDetailedComment1("Contact person not provided");
        reports.add(r5);
        
        return reports;
    }
}
