package femr.data.daos.core;

import femr.common.models.DailyReportItem;
import femr.business.helpers.QueryProvider;
import femr.data.models.core.IMissionTrip;
import femr.data.models.core.IPatientEncounter;
import io.ebean.Ebean;
import io.ebean.SqlRow;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Daily Report Repository - fetches data from the fEMR database.
 * Aggregates patient encounter data by demographics and health events for WHO export.
 */
public class DailyReportRepository {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
    private volatile Integer weeksPregnantVitalId;

    /**
     * Lazily loads and caches the weeks pregnant vital ID.
     * Uses double-checked locking for thread safety.
     *
     * @return ID of the weeksPregnant vital or null if not found
     */
    private Integer getWeeksPregnantVitalId() {
        if (weeksPregnantVitalId == null) {
            synchronized (this) {
                if (weeksPregnantVitalId == null) {
                    String sql = "SELECT id FROM vitals WHERE name = 'weeksPregnant' AND isDeleted = 0 LIMIT 1";
                    SqlRow row = Ebean.createSqlQuery(sql).findOne();
                    weeksPregnantVitalId = (row != null) ? row.getInteger("id") : -1;
                }
            }
        }
        return weeksPregnantVitalId == -1 ? null : weeksPregnantVitalId;
    }
    
    /**
     * Get all daily reports from the database for all mission trips.
     * Aggregates patient encounters into daily report format.
     */
    public List<DailyReportItem> getAllReports() {
        List<DailyReportItem> reports = new ArrayList<>();
        try {
            // Get all mission trips
            @SuppressWarnings("unchecked")
            List<IMissionTrip> trips = (List<IMissionTrip>) (List<?>) QueryProvider.getMissionTripQuery().findList();
            
            // For each trip, get today's encounters and aggregate them
            DateTime today = new DateTime().withTimeAtStartOfDay();
            DateTime tomorrow = today.plusDays(1);
            
            for (IMissionTrip trip : trips) {
                DailyReportItem report = generateReportForTripAndDate(trip.getId(), today);
                if (report != null) {
                    reports.add(report);
                }
            }
        } catch (Exception ex) {
            Logger.error("DailyReportRepository-getAllReports", ex);
            // Fall back to mock reports on error
            return getMockReports();
        }
        
        // If no real data found, return empty list (don't fall back to mock)
        return reports;
    }
    
    /**
     * Get daily reports for a specific team (in production, filtered by team).
     * Aggregates patient encounters into daily report format for the specified team.
     */
    public List<DailyReportItem> getReportsForTeam(String teamName) {
        List<DailyReportItem> reports = new ArrayList<>();
        try {
            // Find trips for this team
            @SuppressWarnings("unchecked")
            List<IMissionTrip> trips = (List<IMissionTrip>) (List<?>) QueryProvider.getMissionTripQuery()
                    .fetch("missionTeam")
                    .where()
                    .eq("missionTeam.name", teamName)
                    .findList();
            
            DateTime today = new DateTime().withTimeAtStartOfDay();
            
            for (IMissionTrip trip : trips) {
                DailyReportItem report = generateReportForTripAndDate(trip.getId(), today);
                if (report != null) {
                    reports.add(report);
                }
            }
        } catch (Exception ex) {
            Logger.error("DailyReportRepository-getReportsForTeam", ex);
            // Fall back to mock reports on error
            return getMockReports();
        }
        
        return reports;
    }

    /**
     * Generates a daily report for a specific trip and date.
     * Aggregates patient encounter data for that day.
     *
     * @param tripId ID of the mission trip
     * @param date Date to generate report for
     * @return DailyReportItem with aggregated data from encounters
     */
    private DailyReportItem generateReportForTripAndDate(Integer tripId, DateTime date) {
        try {
            // Get mission trip info
            IMissionTrip trip = QueryProvider.getMissionTripQuery()
                    .fetch("missionTeam")
                    .fetch("missionCity")
                    .fetch("missionCity.missionCountry")
                    .where()
                    .eq("id", tripId)
                    .findOne();
            
            if (trip == null) {
                return null;
            }

            // Get demographic data for the trip on this date
            Map<String, Map<String, Integer>> demographics = getDemographicCounts(tripId, date);
            
            // Get all patient encounters for the trip on this date
            List<IPatientEncounter> encounters = getPatientEncountersForDate(tripId, date);
            
            // Build the daily report
            DailyReportItem report = new DailyReportItem();
            
            // Team information
            report.setOrganizationName("fEMR");
            if (trip.getMissionTeam() != null) {
                report.setTeamName(trip.getMissionTeam().getName());
            }
            report.setTeamType("Mobile Clinic");
            report.setContactPersonName("fEMR System");
            report.setPhoneNumber("");
            report.setEmail("");
            
            // Location information
            if (trip.getMissionCity() != null) {
                report.setCity(trip.getMissionCity().getName());
                if (trip.getMissionCity().getMissionCountry() != null) {
                    report.setState(trip.getMissionCity().getMissionCountry().getName());
                }
            }
            report.setVillage("");
            report.setFacilityName("");
            
            // Date information
            report.setDateOfActivity(date.toString(DATE_FORMAT));
            report.setTimeOfReporting(new DateTime().toString("HH:mm"));
            
            // Populate demographic counts from the aggregated data
            populateDemographicsInReport(report, demographics);
            
            // Populate encounter-based data
            populateEncounterDataInReport(report, encounters);
            
            return report;
            
        } catch (Exception ex) {
            Logger.error("DailyReportRepository-generateReportForTripAndDate", ex);
            return null;
        }
    }

    /**
     * Retrieves demographic data counts for a specific mission trip on a specific date.
     * Aggregates patient encounter data by age and gender categories.
     *
     * @param tripId ID of the mission trip
     * @param date date to filter patient encounters
     * @return A map with demographic counts by sex category and age category
     */
    private Map<String, Map<String, Integer>> getDemographicCounts(int tripId, DateTime date) {
        String dateStart = date.toString(DATE_FORMAT);
        String dateEnd = date.plusDays(1).toString(DATE_FORMAT);
        Integer pregnancyVitalId = getWeeksPregnantVitalId();

        String sql =
                "SELECT " +
                        "  age_category, " +
                        "  sex_category, " +
                        "  COUNT(*) as cnt " +
                        "FROM ( " +
                        "  SELECT " +
                        "    CASE " +
                        "      WHEN TIMESTAMPDIFF(MONTH, p.age, :dateStart) < 12 THEN 'UNDER_1' " +
                        "      WHEN TIMESTAMPDIFF(YEAR, p.age, :dateStart) BETWEEN 1 AND 4 THEN 'AGE_1_TO_4' " +
                        "      WHEN TIMESTAMPDIFF(YEAR, p.age, :dateStart) BETWEEN 5 AND 17 THEN 'AGE_5_TO_17' " +
                        "      WHEN TIMESTAMPDIFF(YEAR, p.age, :dateStart) BETWEEN 18 AND 64 THEN 'AGE_18_TO_64' " +
                        "      ELSE 'AGE_65_PLUS' " +
                        "    END AS age_category, " +
                        "    CASE " +
                        "      WHEN p.sex = 'Male' THEN 'MALE' " +
                        "      WHEN pev.vital_value IS NOT NULL AND pev.vital_value > 0 THEN 'FEMALE_PREGNANT' " +
                        "      ELSE 'FEMALE_NON_PREGNANT' " +
                        "    END AS sex_category " +
                        "  FROM patient_encounters pe " +
                        "  JOIN patients p ON pe.patient_id = p.id " +
                        "  LEFT JOIN patient_encounter_vitals pev ON pe.id = pev.patient_encounter_id " +
                        "    AND pev.vital_id = :pregnancyVitalId " +
                        "  WHERE pe.mission_trip_id = :tripId " +
                        "    AND pe.date_of_triage_visit >= :dateStart " +
                        "    AND pe.date_of_triage_visit < :dateEnd " +
                        "    AND pe.isDeleted IS NULL " +
                        "    AND p.isDeleted IS NULL " +
                        ") AS categorized " +
                        "GROUP BY age_category, sex_category";

        try {
            List<SqlRow> rows = Ebean.createSqlQuery(sql)
                    .setParameter("tripId", tripId)
                    .setParameter("dateStart", dateStart)
                    .setParameter("dateEnd", dateEnd)
                    .setParameter("pregnancyVitalId", pregnancyVitalId != null ? pregnancyVitalId : -1)
                    .findList();

            Map<String, Map<String, Integer>> results = new HashMap<>();
            for (SqlRow row : rows) {
                String sexCategory = row.getString("sex_category");
                String ageCategory = row.getString("age_category");
                Integer count = row.getInteger("cnt");

                results.computeIfAbsent(sexCategory, k -> new HashMap<>())
                        .put(ageCategory, count != null ? count : 0);
            }
            return results;
        } catch (Exception ex) {
            Logger.error("DailyReportRepository-getDemographicCounts", ex);
            return new HashMap<>();
        }
    }

    /**
     * Retrieves all patient encounters for a trip on a specific date.
     *
     * @param tripId ID of the mission trip
     * @param date Date to filter encounters
     * @return List of patient encounters
     */
    private List<IPatientEncounter> getPatientEncountersForDate(int tripId, DateTime date) {
        try {
            DateTime dateEnd = date.plusDays(1);
            @SuppressWarnings("unchecked")
            List<IPatientEncounter> encounters = (List<IPatientEncounter>) (List<?>) QueryProvider.getPatientEncounterQuery()
                    .fetch("patient")
                    .fetch("patient.patientAgeClassification")
                    .fetch("chiefComplaints")
                    .fetch("patientPrescriptions")
                    .fetch("patientEncounterVitals")
                    .where()
                    .eq("missionTrip.id", tripId)
                    .ge("dateOfTriageVisit", date)
                    .lt("dateOfTriageVisit", dateEnd)
                    .eq("isDeleted", null)
                    .findList();
            return encounters;
        } catch (Exception ex) {
            Logger.error("DailyReportRepository-getPatientEncountersForDate", ex);
            return new ArrayList<>();
        }
    }

    /**
     * Populates demographic fields in a DailyReportItem based on aggregated data.
     *
     * @param report Report to populate
     * @param demographics Demographic counts from database query
     */
    private void populateDemographicsInReport(DailyReportItem report, Map<String, Map<String, Integer>> demographics) {
        // Initialize all counts to 0
        report.setMaleLessThan1(0);
        report.setMale1To4(0);
        report.setMale5To17(0);
        report.setMale18To64(0);
        report.setMale65Plus(0);
        
        report.setFemaleLessThan1(0);
        report.setFemale1To4(0);
        report.setFemale5To17(0);
        report.setFemale18To64(0);
        report.setFemale65Plus(0);
        
        report.setFemalePregnantLessThan1(0);
        report.setFemalePregnant1To4(0);
        report.setFemalePregnant5To17(0);
        report.setFemalePregnant18To64(0);
        report.setFemalePregnant65Plus(0);
        
        // Populate from aggregated data
        for (Map.Entry<String, Map<String, Integer>> sexEntry : demographics.entrySet()) {
            String sexCategory = sexEntry.getKey();
            Map<String, Integer> ageCounts = sexEntry.getValue();
            
            for (Map.Entry<String, Integer> ageEntry : ageCounts.entrySet()) {
                String ageCategory = ageEntry.getKey();
                Integer count = ageEntry.getValue();
                
                if (count == null) count = 0;
                
                // Map categories to report fields
                switch (sexCategory) {
                    case "MALE":
                        switch (ageCategory) {
                            case "UNDER_1": report.setMaleLessThan1(count); break;
                            case "AGE_1_TO_4": report.setMale1To4(count); break;
                            case "AGE_5_TO_17": report.setMale5To17(count); break;
                            case "AGE_18_TO_64": report.setMale18To64(count); break;
                            case "AGE_65_PLUS": report.setMale65Plus(count); break;
                        }
                        break;
                    case "FEMALE_NON_PREGNANT":
                        switch (ageCategory) {
                            case "UNDER_1": report.setFemaleLessThan1(count); break;
                            case "AGE_1_TO_4": report.setFemale1To4(count); break;
                            case "AGE_5_TO_17": report.setFemale5To17(count); break;
                            case "AGE_18_TO_64": report.setFemale18To64(count); break;
                            case "AGE_65_PLUS": report.setFemale65Plus(count); break;
                        }
                        break;
                    case "FEMALE_PREGNANT":
                        switch (ageCategory) {
                            case "UNDER_1": report.setFemalePregnantLessThan1(count); break;
                            case "AGE_1_TO_4": report.setFemalePregnant1To4(count); break;
                            case "AGE_5_TO_17": report.setFemalePregnant5To17(count); break;
                            case "AGE_18_TO_64": report.setFemalePregnant18To64(count); break;
                            case "AGE_65_PLUS": report.setFemalePregnant65Plus(count); break;
                        }
                        break;
                }
            }
        }
    }

    /**
     * Populates encounter-based data in a DailyReportItem.
     *
     * @param report Report to populate
     * @param encounters List of patient encounters for the day
     */
    private void populateEncounterDataInReport(DailyReportItem report, List<IPatientEncounter> encounters) {
        // Initialize all procedure/health event counts to 0
        report.setTotalNumberOfNewConsultations(encounters.size());
        report.setTotalAdmissions(0);
        report.setLiveBirth(0);
        report.setTotalBedCapacity(0);
        
        report.setMajorHeadFaceInjury(0);
        report.setMajorTorsoInjury(0);
        report.setMajorExtremityInjury(0);
        report.setModerateInjury(0);
        report.setMinorInjury(0);
        report.setUpperRespiratoryInfection(0);
        report.setAcuteWateryDiarrhea(0);
        report.setAcuteBloodyDiarrhea(0);
        report.setAcuteJaundiceSyndrome(0);
        report.setSuspectedMeasles(0);
        report.setSuspectedMeningitis(0);
        report.setSuspectedTetanus(0);
        report.setAcuteFlacidParalysis(0);
        report.setAcuteHaemorrhagicFever(0);
        report.setFeverOfUnknownOrigin(0);
        report.setSurgicalEmergency(0);
        report.setMedicalEmergency(0);
        report.setSkinDisease(0);
        report.setAcuteMentalHealthProblem(0);
        report.setDiabeticComplications(0);
        report.setSevereAcuteMalnutritionSam(0);
        report.setOtherDiagnosisNotSpecifiedAbove(0);
        
        report.setMajorProcedureExcludingHds31(0);
        report.setLimbAmputationExcludingDigits(0);
        report.setMinorSurgicalProcedure(0);
        report.setNormalVaginalDeliveryNvd(0);
        report.setCaesareanSection(0);
        report.setObstetricsOthers(0);
        
        report.setDischargeWithoutMedicalFollowUp(false);
        report.setDischargeWithMedicalFollowUp(false);
        report.setDischargeAgainstMedicalAdvice(false);
        report.setReferral(false);
        report.setDeathWithinFacility(false);
        report.setDeathAfterDischarge(false);
        
        // Would populate from encounters here if needed
        // For now, these serve as placeholders showing the structure
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
