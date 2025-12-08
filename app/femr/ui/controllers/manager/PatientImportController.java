package femr.ui.controllers.manager;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.business.services.system.PatientImportService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.FileInputStream;

/**
 * Controller for patient import functionality
 */
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.MANAGER})
public class PatientImportController extends Controller {

    private final ISessionService sessionService;
    private final IPatientService patientService;
    private final IEncounterService encounterService;
    private final PatientImportService importService;

    @Inject
    public PatientImportController(ISessionService sessionService,
                                   IPatientService patientService,
                                   IEncounterService encounterService) {
        this.sessionService = sessionService;
        this.patientService = patientService;
        this.encounterService = encounterService;
        this.importService = new PatientImportService();
    }

    /**
     * Handle CSV file upload and patient import
     * Expects multipart form data with file part named "csvFile"
     *
     * @return JSON response with import results
     */
    public Result importPatientsPost() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        // Validate user is assigned to a trip
        if (currentUser == null || currentUser.getTripId() == null) {
            return badRequest(createErrorResponse("User is not assigned to a trip"));
        }

        try {
            // Get multipart form data
            Http.MultipartFormData<java.io.File> body = request().body().asMultipartFormData();
            
            if (body == null) {
                return badRequest(createErrorResponse("No form data received"));
            }

            Http.MultipartFormData.FilePart<java.io.File> csvFile = body.getFile("csvFile");
            
            if (csvFile == null) {
                return badRequest(createErrorResponse("No CSV file uploaded"));
            }

            // Validate file is CSV
            String filename = csvFile.getFilename();
            if (!filename.toLowerCase().endsWith(".csv")) {
                return badRequest(createErrorResponse("File must be a CSV file"));
            }

            // Parse CSV
            ServiceResponse<List<PatientItem>> parseResponse = 
                importService.parsePatientCSV(new java.io.FileInputStream(csvFile.getFile()));

            if (parseResponse.hasErrors()) {
                return badRequest(createImportResponse(null, parseResponse.getErrors()));
            }

            List<PatientItem> patients = parseResponse.getResponseObject();
            
            if (patients == null || patients.isEmpty()) {
                return badRequest(createErrorResponse("No valid patients found in CSV"));
            }

            // Import patients (create and add to encounters for today)
            List<PatientItem> importedPatients = importPatientsToDatabase(patients, currentUser);

            return ok(createImportResponse(importedPatients, parseResponse.getErrors()));

        } catch (Exception e) {
            return internalServerError(createErrorResponse("Import failed: " + e.getMessage()));
        }
    }

    /**
     * Create patients in database and add to today's encounters
     */
    private List<PatientItem> importPatientsToDatabase(List<PatientItem> patients, CurrentUser currentUser) {
        for (PatientItem patient : patients) {
            try {
                // Set user ID for patient creation
                patient.setUserId(currentUser.getId());

                // Create patient in database
                ServiceResponse<PatientItem> createResponse = patientService.createPatient(patient);

                if (!createResponse.hasErrors() && createResponse.getResponseObject() != null) {
                    PatientItem createdPatient = createResponse.getResponseObject();

                    // Ensure any imported chief complaints are carried over from the original patient object
                    if (patient.getImportedChiefComplaints() != null && !patient.getImportedChiefComplaints().isEmpty()) {
                        createdPatient.setImportedChiefComplaints(patient.getImportedChiefComplaints());
                    }

                    // Build chief complaints from health conditions and imported CSV values
                    List<String> chiefComplaints = buildChiefComplaintsFromPatient(createdPatient);
                    
                    // Create encounter for patient in the current trip
                    encounterService.createPatientEncounter(
                        createdPatient.getId(),
                        currentUser.getId(),
                        currentUser.getTripId(),
                        null,
                        chiefComplaints
                    );
                }
            } catch (Exception e) {
                // Log error but continue with other patients - silently continue on import errors
            }
        }
        return patients;
    }

    /**
     * Build chief complaints list from patient health conditions AND imported chief complaints
     */
    private List<String> buildChiefComplaintsFromPatient(PatientItem patient) {
        List<String> complaints = new ArrayList<>();
        
        // First add the imported chief complaints from CSV
        if (patient.getImportedChiefComplaints() != null && !patient.getImportedChiefComplaints().isEmpty()) {
            complaints.addAll(patient.getImportedChiefComplaints());
        }
        
        // Then add health conditions
        if (patient.getDiabetes() != null && patient.getDiabetes() == 1) {
            complaints.add("Diabetes");
        }
        if (patient.getSmoker() != null && patient.getSmoker() == 1) {
            complaints.add("Smoker");
        }
        if (patient.getAlcohol() != null && patient.getAlcohol() == 1) {
            complaints.add("Alcohol use");
        }
        if (patient.getCholesterol() != null && patient.getCholesterol() == 1) {
            complaints.add("High cholesterol");
        }
        if (patient.getHypertension() != null && patient.getHypertension() == 1) {
            complaints.add("Hypertension");
        }
        if (patient.getWeeksPregnant() != null && patient.getWeeksPregnant() > 0) {
            complaints.add("Pregnancy - " + patient.getWeeksPregnant() + " weeks");
        }
        
        return complaints;
    }

    /**
     * Create JSON response for successful import
     */
    private JsonNode createImportResponse(List<PatientItem> patients, Map<String, String> errors) {
        ObjectNode response = Json.newObject();
        response.put("success", true);
        response.put("message", "Import completed");

        if (patients != null) {
            response.put("importedCount", patients.size());
            ArrayNode patientsArray = response.putArray("patients");
            for (PatientItem patient : patients) {
                ObjectNode patientNode = Json.newObject();
                patientNode.put("id", patient.getId());
                patientNode.put("firstName", patient.getFirstName());
                patientNode.put("lastName", patient.getLastName());
                patientNode.put("phone", patient.getPhoneNumber());
                patientNode.put("city", patient.getCity());
                patientsArray.add(patientNode);
            }
        }

        if (errors != null && !errors.isEmpty()) {
            ArrayNode errorsArray = response.putArray("errors");
            for (String error : errors.values()) {
                errorsArray.add(error);
            }
        }

        return response;
    }

    /**
     * Create JSON error response
     */
    private JsonNode createErrorResponse(String message) {
        ObjectNode response = Json.newObject();
        response.put("success", false);
        response.put("message", message);
        return response;
    }
}
