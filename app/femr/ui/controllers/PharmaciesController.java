package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.pharmacy.*;
import femr.ui.views.html.pharmacies.index;
import femr.ui.views.html.pharmacies.edit;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class PharmaciesController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private final IEncounterService encounterService;
    private final IMedicationService medicationService;
    private final ISessionService sessionService;
    private final ISearchService searchService;
    private final IInventoryService inventoryService;

    @Inject
    public PharmaciesController(AssetsFinder assetsFinder,
                                FormFactory formFactory,
                                IEncounterService encounterService,
                                IMedicationService medicationService,
                                ISessionService sessionService,
                                ISearchService searchService,
                                IInventoryService inventoryService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.encounterService = encounterService;
        this.medicationService = medicationService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.inventoryService = inventoryService;
    }

    public Result indexGet() {
        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();
        return ok(index.render(currentUserSession, null, 0, assetsFinder));
    }

    /**
     * Validates the patient ID that the user entered
     *
     * @return redirect to editGet
     */
    public Result indexPost() {
        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();

        String queryString_id = request().body().asFormUrlEncoded().get("id")[0];
        ServiceResponse<Integer> idQueryStringResponse = searchService.parseIdFromQueryString(queryString_id);
        if (idQueryStringResponse.hasErrors()) {
            return ok(index.render(currentUserSession, idQueryStringResponse.getErrors().get(""), 0, assetsFinder));
        }
        Integer patientId = idQueryStringResponse.getResponseObject();

        //get the patient's encounter
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            return ok(index.render(currentUserSession, patientEncounterItemServiceResponse.getErrors().get(""), 0, assetsFinder));
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();

        //check for encounter closed
        if (patientEncounterItem.getIsClosed()) {
            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0, assetsFinder));
        }

        //ensure prescriptions exist for that patient
        ServiceResponse<List<PrescriptionItem>> prescriptionItemsResponse = searchService.retrieveUnreplacedPrescriptionItems(patientEncounterItem.getId(), currentUserSession.getTripId());
        if (prescriptionItemsResponse.hasErrors()) {
            throw new RuntimeException();

        } else if (prescriptionItemsResponse.getResponseObject().size() < 1) {
            return ok(index.render(currentUserSession, "No prescriptions found for that patient", 0, assetsFinder));
        }

        return redirect(routes.PharmaciesController.editGet(patientId));
    }

    public Result editGet(int patientId) {
        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();

        EditViewModelGet viewModelGet = new EditViewModelGet();
        String message;

        // Get settings
        ServiceResponse<SettingItem> response = searchService.retrieveSystemSettings();
        if (response.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setSettings(response.getResponseObject());

        //Get Patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByPatientId(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            message = patientItemServiceResponse.getErrors().get("");
            return ok(index.render(currentUserSession, message, 0, assetsFinder));
        }
        PatientItem patient = patientItemServiceResponse.getResponseObject();
        viewModelGet.setPatient(patient);

        //get the patient encounter item
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(patient.getId());
        if (patientEncounterItemServiceResponse.hasErrors()) {
            message = patientEncounterItemServiceResponse.getErrors().get("");
            return ok(index.render(currentUserSession, message, 0, assetsFinder));
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();
        //check for encounter closed
        if (patientEncounterItem.getIsClosed()) {
            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0, assetsFinder));
        }
        viewModelGet.setPatientEncounterItem(patientEncounterItem);


        //find patient prescriptions, they do have to exist
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.retrieveUnreplacedPrescriptionItems(patientEncounterItem.getId(), currentUserSession.getTripId());
        if (prescriptionItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else if (prescriptionItemServiceResponse.getResponseObject().size() < 1) {
            return ok(index.render(currentUserSession, "No prescriptions found for that patient", 0, assetsFinder));
        }
        viewModelGet.setPrescriptions(prescriptionItemServiceResponse.getResponseObject());

        // get inventory for prescriptions

        //get MedicationAdministrationItems
        ServiceResponse<List<MedicationAdministrationItem>> medicationAdministrationItemServiceResponse =
                medicationService.retrieveAvailableMedicationAdministrations();
        if (medicationAdministrationItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setMedicationAdministrationItems(medicationAdministrationItemServiceResponse.getResponseObject());


        //find patient problems, they do not have to exist.
        ServiceResponse<List<ProblemItem>> problemItemServiceResponse = encounterService.retrieveProblemItems(patientEncounterItem.getId());
        if (problemItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            if (problemItemServiceResponse.getResponseObject().size() > 0) {
                viewModelGet.setProblems(problemItemServiceResponse.getResponseObject());
            }
        }

        //find patient notes, they do not have to exist.
        ServiceResponse<List<NoteItem>> noteItemServiceResponse = encounterService.retrieveNoteItems(patientEncounterItem.getId());
        if (noteItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            if (noteItemServiceResponse.getResponseObject().size() > 0) {
                viewModelGet.setNotes(noteItemServiceResponse.getResponseObject());
            }
        }

        return ok(edit.render(currentUserSession, viewModelGet, false, assetsFinder));
    }

    public Result editPost(int id) {

        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();

        // If form errors exist
        final Form<EditViewModelPost> populatedViewModelPostForm = formFactory.form(EditViewModelPost.class);
        EditViewModelPost createViewModelPost = populatedViewModelPostForm.bindFromRequest().get();

        // @TODO -- Do validation on  the counseled flag

        //get patient encounter
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(id);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();

        //get patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByPatientId(patientEncounterItem.getPatientItem().getId());
        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();

        //assume the patient was not counseled, set to true if they were.
        boolean isCounseled = false;
        if (createViewModelPost.getDisclaimer() == 1){
            isCounseled = true;
        }


        // Map<id of the new prescription, id of the old prescription>
        Map<Integer, Integer> prescriptionsToReplace = new HashMap<>();
        // Map<id, isCounseled>
        Map<Integer, Boolean> prescriptionsToDispense = new HashMap<>();

        for(PrescriptionItem script : createViewModelPost.getPrescriptions()) {

            if (StringUtils.isNotNullOrWhiteSpace(script.getMedicationName())) {
                //The POST data sends -1 if an administration ID is not set. Null is more appropriate for the
                //service layer
                if (script.getAdministrationID() == -1)
                    script.setAdministrationID(null);

                if (script.getMedicationID() != null){
                    //the medication has already been entered into the medications table (through admin inventory?)

                    ServiceResponse<PrescriptionItem> createPrescriptionResponse = medicationService.createPrescription(
                            script.getMedicationID(),
                            script.getAdministrationID(),
                            patientEncounterItem.getId(),
                            currentUserSession.getId(),
                            script.getAmountWithNull(),
                            null);
                    PrescriptionItem newPrescriptionItem = createPrescriptionResponse.getResponseObject();
                    //mark the prescription for replacing
                    prescriptionsToReplace.put(newPrescriptionItem.getId(), script.getId());

                }else{
                    //the medication has not already been entered into the medications table
                    ServiceResponse<PrescriptionItem> createPrescriptionResponse = medicationService.createPrescriptionWithNewMedication(
                            script.getMedicationName(),
                            script.getAdministrationID(),
                            patientEncounterItem.getId(),
                            currentUserSession.getId(),
                            script.getAmountWithNull(),
                            null);
                    PrescriptionItem newPrescriptionItem = createPrescriptionResponse.getResponseObject();
                    //mark the prescription for replacing
                    prescriptionsToReplace.put(newPrescriptionItem.getId(), script.getId());
                }

            } else {
                // mark the prescription for dispensing
                prescriptionsToDispense.put(script.getId(), isCounseled);
            }
        }

        // replace the prescriptions! (but do not dispense them)
        if (prescriptionsToReplace.size() > 0){
            ServiceResponse<List<PrescriptionItem>> replacePrescriptionsServiceResponse = medicationService.replacePrescriptions(prescriptionsToReplace);
            if (replacePrescriptionsServiceResponse.hasErrors()) {

                throw new RuntimeException();
            } else {

                for (PrescriptionItem prescriptionItem : replacePrescriptionsServiceResponse.getResponseObject()) {

                    prescriptionsToDispense.put(prescriptionItem.getId(), prescriptionItem.getCounseled());
                }
            }
        }

        // dispense the prescriptions! then inventory them!
        if (prescriptionsToDispense.size() > 0) {
            //dispense!
            ServiceResponse<List<PrescriptionItem>> dispensePrescriptionsServiceResponse = medicationService.dispensePrescriptions(prescriptionsToDispense);
            if (dispensePrescriptionsServiceResponse.hasErrors()) {

                throw new RuntimeException();
            } else if( currentUserSession.getTripId() != null ) {
                //inventory -- user must be assigned to a trip
                for (PrescriptionItem prescriptionItem : dispensePrescriptionsServiceResponse.getResponseObject()) {

                    ServiceResponse<MedicationItem> inventoryServiceResponse = inventoryService.subtractFromQuantityCurrent(prescriptionItem.getMedicationID(), currentUserSession.getTripId(), prescriptionItem.getAmount());
                    if (inventoryServiceResponse.hasErrors()){

                        throw new RuntimeException();
                    }
                }
            }

        }

        //check the patient in!
        encounterService.checkPatientInToPharmacy(patientEncounterItem.getId(), currentUserSession.getId());
        String message = "Patient information for " +
                patientItem.getFirstName() +
                " " +
                patientItem.getLastName() +
                " (id: " +
                patientItem.getId() +
                ") was saved successfully.";

        return ok(index.render(currentUserSession, message, 0, assetsFinder));
    }
}
