package femr.ui.controllers;

import com.google.inject.Inject;
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
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class PharmaciesController extends Controller {

    private final Form<EditViewModelPost> populatedViewModelPostForm = Form.form(EditViewModelPost.class);
    private final IEncounterService encounterService;
    private final IMedicationService medicationService;
    private final ISessionService sessionService;
    private final ISearchService searchService;
    private final IInventoryService inventoryService;

    @Inject
    public PharmaciesController(IEncounterService encounterService,
                                IMedicationService medicationService,
                                ISessionService sessionService,
                                ISearchService searchService,
                                IInventoryService inventoryService) {
        this.encounterService = encounterService;
        this.medicationService = medicationService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.inventoryService = inventoryService;
    }

    public Result indexGet() {
        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();
        return ok(index.render(currentUserSession, null, 0));
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
            return ok(index.render(currentUserSession, idQueryStringResponse.getErrors().get(""), 0));
        }
        Integer patientId = idQueryStringResponse.getResponseObject();

        //get the patient's encounter
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            return ok(index.render(currentUserSession, patientEncounterItemServiceResponse.getErrors().get(""), 0));
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();

        //check for encounter closed
        if (patientEncounterItem.getIsClosed()) {
            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0));
        }

        //ensure prescriptions exist for that patient
        ServiceResponse<List<PrescriptionItem>> prescriptionItemsResponse = searchService.retrieveUnreplacedPrescriptionItems(patientEncounterItemServiceResponse.getResponseObject().getId());
        if (prescriptionItemsResponse.hasErrors()) {
            throw new RuntimeException();

        } else if (prescriptionItemsResponse.getResponseObject().size() < 1) {
            return ok(index.render(currentUserSession, "No prescriptions found for that patient", 0));
        }

        return redirect(routes.PharmaciesController.editGet(patientId));
    }

    public Result editGet(int patientId) {
        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();

        EditViewModelGet viewModelGet = new EditViewModelGet();
        String message;

        //Get Patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByPatientId(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            message = patientItemServiceResponse.getErrors().get("");
            return ok(index.render(currentUserSession, message, 0));
        }
        PatientItem patient = patientItemServiceResponse.getResponseObject();
        viewModelGet.setPatient(patient);

        //get the patient encounter item
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(patient.getId());
        if (patientEncounterItemServiceResponse.hasErrors()) {
            message = patientEncounterItemServiceResponse.getErrors().get("");
            return ok(index.render(currentUserSession, message, 0));
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();
        //check for encounter closed
        if (patientEncounterItem.getIsClosed()) {
            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0));
        }
        viewModelGet.setPatientEncounterItem(patientEncounterItem);


        //find patient prescriptions, they do have to exist
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.retrieveUnreplacedPrescriptionItems(patientEncounterItem.getId());
        if (prescriptionItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else if (prescriptionItemServiceResponse.getResponseObject().size() < 1) {
            return ok(index.render(currentUserSession, "No prescriptions found for that patient", 0));
        }
        viewModelGet.setMedications(prescriptionItemServiceResponse.getResponseObject());

        //get MedicationAdministrationItems
        ServiceResponse<List<MedicationAdministrationItem>> medicationAdministrationItemServiceResponse =
                inventoryService.retrieveAvailableAdministrations();
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

        return ok(edit.render(currentUserSession, viewModelGet, false));
    }

    public Result editPost(int id) {
        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();
        EditViewModelPost createViewModelPost = populatedViewModelPostForm.bindFromRequest().get();

        //get patient encounter
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrieveRecentPatientEncounterItemByPatientId(id);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();

        //get patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByPatientId(patientEncounterItem.getPatientId());
        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();

        boolean isCounseled = StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getDisclaimer());
        //after replacing the prescriptions, this list will contain the non-replaced prescriptions that need
        //to be identified as dispensed.
        List<Integer> prescriptionToMarkAsDispensedOrCounseled = new ArrayList<>();

        for(PrescriptionItem script : createViewModelPost.getPrescriptions()) {
            if (StringUtils.isNotNullOrWhiteSpace(script.getName())) {
                ServiceResponse<PrescriptionItem> response = medicationService.createAndReplacePrescription(
                        script,
                        script.getId(),
                        currentUserSession.getId(),
                        isCounseled
                );
                if (response.hasErrors()) {
                    throw new RuntimeException();
                }
            } else {
                prescriptionToMarkAsDispensedOrCounseled.add(script.getId());
            }
        }


        //update non-replaced prescriptions to dispensed
        ServiceResponse<List<PrescriptionItem>> prescriptionDispensedResponse = medicationService.flagPrescriptionsAsFilled(prescriptionToMarkAsDispensedOrCounseled);
        if (prescriptionDispensedResponse.hasErrors()) {
            throw new RuntimeException();
        }
        //update non-replaced prescriptions that the patient was counseled on
        if (isCounseled){
            ServiceResponse<List<PrescriptionItem>> prescriptionCounseledResponse = medicationService.flagPrescriptionsAsCounseled(prescriptionToMarkAsDispensedOrCounseled);
            if (prescriptionCounseledResponse.hasErrors()){
                throw new RuntimeException();
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

        return ok(index.render(currentUserSession, message, 0));
    }
}
