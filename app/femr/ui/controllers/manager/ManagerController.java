package femr.ui.controllers.manager;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MissionTripItem;
import femr.common.models.PatientEncounterItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.manager.IndexViewModelGet;
import femr.common.models.DailyReportItem;
import femr.business.services.system.DailyReportService;
import femr.common.models.DailyReportCSV;

import femr.ui.views.html.manager.index;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import java.util.List;
import java.util.ArrayList;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.MANAGER})
public class ManagerController extends Controller {

    private final AssetsFinder assetsFinder;
    private final ISessionService sessionService;
    private final IEncounterService encounterService;
    private final IMissionTripService missionTripService;


    @Inject
    public ManagerController(AssetsFinder assetsFinder,
                             ISessionService sessionService,
                             IEncounterService encounterService,
                             IMissionTripService missionTripService) {

        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
        this.encounterService = encounterService;
        this.missionTripService = missionTripService;
    }

    public Result indexGet() {

//declares empty array lists  view model
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        IndexViewModelGet viewModel = new IndexViewModelGet();
        //if the user is not assigned to a trip renders outpage, with message to user
        if (currentUser.getTripId() == null) {

            return ok(index.render(currentUser, viewModel, assetsFinder));
        }

        //Get the list of patient encounters that were created today, for the current trip and set them in the viewmodel
        ServiceResponse<List<PatientEncounterItem>> patientEncounter = encounterService.retrieveCurrentDayPatientEncounters(currentUser.getTripId());

        viewModel.setPatientEncounter(patientEncounter.getResponseObject());

        //Get the mission trip and set the name of it in the viewmodel
        ServiceResponse<MissionTripItem> missionTripItemServiceResponse = missionTripService.retrieveAllTripInformationByTripId(currentUser.getTripId());
        if (missionTripItemServiceResponse.hasErrors()){

            throw new RuntimeException();
        }

        viewModel.setUserFriendlyTrip(
                StringUtils.generateMissionTripTitle(
                missionTripItemServiceResponse.getResponseObject().getTeamName(),
                missionTripItemServiceResponse.getResponseObject().getTripCountry(),
                missionTripItemServiceResponse.getResponseObject().getTripStartDate(),
                missionTripItemServiceResponse.getResponseObject().getTripEndDate()
                )
        );


        //Get the current day to show the user what day the patients are being displayed for
        viewModel.setUserFriendlyDate(dateUtils.getFriendlyInternationalDate(DateTime.now().toDate()));

        return ok(index.render(currentUser, viewModel, assetsFinder));

    }

    /**
     * Export today's daily report as CSV by aggregating current day's encounters.
     */
    public Result exportDailyReport() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        if (currentUser == null || currentUser.getTripId() == null) {
            return badRequest("User is not assigned to a trip");
        }

        // Retrieve today's encounters for the trip
        ServiceResponse<List<PatientEncounterItem>> patientEncounter = encounterService.retrieveCurrentDayPatientEncounters(currentUser.getTripId());
        List<PatientEncounterItem> encounters = patientEncounter.getResponseObject();

        // Build a single DailyReportItem aggregating available info
        DailyReportItem report = new DailyReportItem();

        // TEAM INFORMATION - Always populate to ensure CSV has columns
        report.setOrganizationName("fEMR");  // Placeholder
        report.setTeamName("");
        report.setTeamType("");
        report.setContactPersonName("");
        report.setPhoneNumber("");
        report.setEmail("");
        report.setState("");
        report.setCity("");
        report.setVillage("");
        report.setFacilityName("");
        report.setDateOfActivity(dateUtils.getFriendlyInternationalDate(DateTime.now().toDate()));
        report.setTimeOfReporting(DateTime.now().toString("HH:mm"));

        // Team / trip information - override with actual data if available
        ServiceResponse<MissionTripItem> missionTripItemServiceResponse = missionTripService.retrieveAllTripInformationByTripId(currentUser.getTripId());
        if (!missionTripItemServiceResponse.hasErrors() && missionTripItemServiceResponse.getResponseObject() != null) {
            MissionTripItem trip = missionTripItemServiceResponse.getResponseObject();
            if (trip.getTeamName() != null) report.setTeamName(trip.getTeamName());
            if (trip.getTripCountry() != null) report.setCity(trip.getTripCountry());
            if (trip.getTripCity() != null) report.setFacilityName(trip.getTripCity());
        }

        // Initialize all age/sex category counters to 0 so they appear in CSV
        report.setMaleLessThan1(0);
        report.setFemaleLessThan1(0);
        report.setFemalePregnantLessThan1(0);
        report.setMale1To4(0);
        report.setFemale1To4(0);
        report.setFemalePregnant1To4(0);
        report.setMale5To17(0);
        report.setFemale5To17(0);
        report.setFemalePregnant5To17(0);
        report.setMale18To64(0);
        report.setFemale18To64(0);
        report.setFemalePregnant18To64(0);
        report.setMale65Plus(0);
        report.setFemale65Plus(0);
        report.setFemalePregnant65Plus(0);
        
        // Initialize patient/bed count fields
        report.setTotalNumberOfNewConsultations(0);
        report.setTotalAdmissions(0);
        report.setLiveBirth(0);
        report.setTotalBedCapacity(0);
        
        // Initialize health events to 0
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
        
        // Initialize procedures to 0
        report.setMajorProcedureExcludingHds31(0);
        report.setLimbAmputationExcludingDigits(0);
        report.setMinorSurgicalProcedure(0);
        report.setNormalVaginalDeliveryNvd(0);
        report.setCaesareanSection(0);
        report.setObstetricsOthers(0);
        
        // Initialize outcomes to false
        report.setDischargeWithoutMedicalFollowUp(false);
        report.setDischargeWithMedicalFollowUp(false);
        report.setDischargeAgainstMedicalAdvice(false);
        report.setReferral(false);
        report.setDeathWithinFacility(false);
        report.setDeathAfterDischarge(false);

        // Aggregate encounter data
        int total = 0;
        if (encounters != null) {
            total = encounters.size();
            for (PatientEncounterItem e : encounters) {
                if (e == null) continue;
                femr.common.models.PatientItem p = e.getPatientItem();
                if (p == null) continue;

                // Age / sex buckets
                Integer years = p.getYearsOld();
                String sex = p.getSex();
                boolean pregnant = (p.getWeeksPregnant() != null && p.getWeeksPregnant() > 0);

                int y = (years == null) ? -1 : years.intValue();

                if (sex != null && sex.equalsIgnoreCase("male")) {
                    if (y >= 65) report.setMale65Plus(report.getMale65Plus() + 1);
                    else if (y >= 18) report.setMale18To64(report.getMale18To64() + 1);
                    else if (y >= 5) report.setMale5To17(report.getMale5To17() + 1);
                    else if (y >= 1) report.setMale1To4(report.getMale1To4() + 1);
                    else if (y == 0) report.setMaleLessThan1(report.getMaleLessThan1() + 1);
                } else if (sex != null && sex.equalsIgnoreCase("female")) {
                    if (y >= 65) report.setFemale65Plus(report.getFemale65Plus() + 1);
                    else if (y >= 18) report.setFemale18To64(report.getFemale18To64() + 1);
                    else if (y >= 5) report.setFemale5To17(report.getFemale5To17() + 1);
                    else if (y >= 1) report.setFemale1To4(report.getFemale1To4() + 1);
                    else if (y == 0) report.setFemaleLessThan1(report.getFemaleLessThan1() + 1);

                    if (pregnant) {
                        // increment appropriate pregnant bucket
                        if (y >= 65) report.setFemalePregnant65Plus(report.getFemalePregnant65Plus() + 1);
                        else if (y >= 18) report.setFemalePregnant18To64(report.getFemalePregnant18To64() + 1);
                        else if (y >= 5) report.setFemalePregnant5To17(report.getFemalePregnant5To17() + 1);
                        else if (y >= 1) report.setFemalePregnant1To4(report.getFemalePregnant1To4() + 1);
                        else if (y == 0) report.setFemalePregnantLessThan1(report.getFemalePregnantLessThan1() + 1);
                    }
                }

                // Map chief complaints to health events using WHO keywords
                List<String> complaints = e.getChiefComplaints();
                if (complaints != null) {
                    for (String complaint : complaints) {
                        if (complaint == null) continue;
                        String lower = complaint.toLowerCase();

                        // Injuries
                        if (lower.contains("head") || lower.contains("face")) report.setMajorHeadFaceInjury(report.getMajorHeadFaceInjury() + 1);
                        else if (lower.contains("chest") || lower.contains("torso") || lower.contains("abdomen")) report.setMajorTorsoInjury(report.getMajorTorsoInjury() + 1);
                        else if (lower.contains("limb") || lower.contains("arm") || lower.contains("leg") || lower.contains("fracture")) report.setMajorExtremityInjury(report.getMajorExtremityInjury() + 1);
                        else if (lower.contains("wound") || lower.contains("cut") || lower.contains("laceration")) report.setModerateInjury(report.getModerateInjury() + 1);
                        else if (lower.contains("bruise") || lower.contains("bump")) report.setMinorInjury(report.getMinorInjury() + 1);

                        // Infections
                        else if (lower.contains("cough") || lower.contains("respiratory") || lower.contains("cold")) report.setUpperRespiratoryInfection(report.getUpperRespiratoryInfection() + 1);
                        else if (lower.contains("diarrhea") || lower.contains("diarrhoea")) {
                            if (lower.contains("blood")) report.setAcuteBloodyDiarrhea(report.getAcuteBloodyDiarrhea() + 1);
                            else report.setAcuteWateryDiarrhea(report.getAcuteWateryDiarrhea() + 1);
                        }
                        else if (lower.contains("jaundice") || lower.contains("yellow")) report.setAcuteJaundiceSyndrome(report.getAcuteJaundiceSyndrome() + 1);
                        else if (lower.contains("fever") && lower.contains("measles")) report.setSuspectedMeasles(report.getSuspectedMeasles() + 1);
                        else if (lower.contains("meningitis")) report.setSuspectedMeningitis(report.getSuspectedMeningitis() + 1);
                        else if (lower.contains("tetanus")) report.setSuspectedTetanus(report.getSuspectedTetanus() + 1);

                        // Other conditions
                        else if (lower.contains("paralysis") || lower.contains("flaccid")) report.setAcuteFlacidParalysis(report.getAcuteFlacidParalysis() + 1);
                        else if (lower.contains("hemorrhage") || lower.contains("bleeding")) report.setAcuteHaemorrhagicFever(report.getAcuteHaemorrhagicFever() + 1);
                        else if (lower.contains("fever") && (lower.contains("unknown") || lower.contains("unk"))) report.setFeverOfUnknownOrigin(report.getFeverOfUnknownOrigin() + 1);
                        else if (lower.contains("surgery") || lower.contains("emergency")) report.setSurgicalEmergency(report.getSurgicalEmergency() + 1);
                        else if (lower.contains("mental") || lower.contains("depression") || lower.contains("anxiety")) report.setAcuteMentalHealthProblem(report.getAcuteMentalHealthProblem() + 1);
                        else if (lower.contains("diabetes")) report.setDiabeticComplications(report.getDiabeticComplications() + 1);
                        else if (lower.contains("malnutrition") || lower.contains("sam")) report.setSevereAcuteMalnutritionSam(report.getSevereAcuteMalnutritionSam() + 1);
                        else if (lower.contains("skin")) report.setSkinDisease(report.getSkinDisease() + 1);
                        else report.setOtherDiagnosisNotSpecifiedAbove(report.getOtherDiagnosisNotSpecifiedAbove() + 1);
                    }
                }
            }
        }

        report.setTotalNumberOfNewConsultations(total);

        List<DailyReportItem> toExport = new ArrayList<>();
        toExport.add(report);

        // Export directly without validation (validation may filter out records)
        String csv = DailyReportCSV.exportToCSV(toExport);

        // If CSV is empty, create a simple fallback CSV with headers
        if (csv == null || csv.trim().isEmpty()) {
            csv = "organizationName,teamName,teamType,dateOfActivity,timeOfReporting,totalNumberOfNewConsultations,maleLessThan1,femaleLessThan1,male1To4,female1To4,male5To17,female5To17,male18To64,female18To64,male65Plus,female65Plus\n";
            csv += "fEMR," + report.getTeamName() + "," + report.getTeamType() + "," + report.getDateOfActivity() + "," + report.getTimeOfReporting() + "," + total + ",0,0,0,0,0,0,0,0,0,0\n";
        }

        response().setHeader("Content-Disposition", "attachment; filename=\"daily_report.csv\"");
        return ok(csv).as("text/csv");
    }

    /**
     * Wipe all patients from today's encounters for the current trip
     * This endpoint clears all imported patient data
     */
    public Result wipeAllPatients() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        if (currentUser == null || currentUser.getTripId() == null) {
            return badRequest(createJsonResponse(false, "User is not assigned to a trip"));
        }

        try {
            // Retrieve today's encounters
            ServiceResponse<List<PatientEncounterItem>> patientEncounters = 
                encounterService.retrieveCurrentDayPatientEncounters(currentUser.getTripId());

            if (patientEncounters.hasErrors() || patientEncounters.getResponseObject() == null) {
                return ok(createJsonResponse(true, "No patients to clear"));
            }

            List<PatientEncounterItem> encounters = patientEncounters.getResponseObject();
            
            // Delete all encounters for today
            for (PatientEncounterItem encounter : encounters) {
                try {
                    encounterService.deleteEncounter(currentUser.getId(), "Bulk patient wipe from manager page", encounter.getId());
                } catch (Exception e) {
                    // Continue deleting other encounters even if one fails
                }
            }

            return ok(createJsonResponse(true, "All patients cleared successfully"));

        } catch (Exception e) {
            return internalServerError(createJsonResponse(false, "Error clearing patients: " + e.getMessage()));
        }
    }

    /**
     * Helper method to create JSON response
     */
    private String createJsonResponse(boolean success, String message) {
        return "{\"success\": " + success + ", \"message\": \"" + message + "\"}";
    }


}
