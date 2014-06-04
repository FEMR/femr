package femr.ui.controllers;

import com.google.gson.JsonObject;
import com.google.inject.Inject;
import femr.business.services.*;
import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;
import femr.common.models.*;
import femr.data.models.Roles;
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

import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class PharmaciesController extends Controller {
    private final Form<EditViewModelPost> populatedViewModelPostForm = Form.form(EditViewModelPost.class);
    private ISessionService sessionService;
    private ISearchService searchService;
    private IPharmacyService pharmacyService;

    @Inject
    public PharmaciesController(IPharmacyService pharmacyService,
                                ISessionService sessionService,
                                ISearchService searchService) {
        this.pharmacyService = pharmacyService;
        this.sessionService = sessionService;
        this.searchService = searchService;
    }

    public Result indexGet() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        return ok(index.render(currentUserSession, null, 0));
    }

    /**
     * Validates the patient ID that the user entered
     *
     * @return redirect to editGet
     */
    public Result indexPost() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        String queryString_id = request().body().asFormUrlEncoded().get("id")[0];
        ServiceResponse<Integer> idQueryStringResponse = searchService.parseIdFromQueryString(queryString_id);
        if (idQueryStringResponse.hasErrors()) {
            return ok(index.render(currentUserSession, idQueryStringResponse.getErrors().get(""), 0));
        }
        Integer patientId = idQueryStringResponse.getResponseObject();

        //get the patient's encounter
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findPatientEncounterItemById(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            return ok(index.render(currentUserSession, patientEncounterItemServiceResponse.getErrors().get(""), 0));
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();

        //check for encounter closed
        if (patientEncounterItem.getIsClosed()) {
            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0));
        }

        //ensure prescriptions exist for that patient
        ServiceResponse<List<PrescriptionItem>> prescriptionItemsResponse = searchService.findUnreplacedPrescriptionItems(patientEncounterItemServiceResponse.getResponseObject().getId());
        if (prescriptionItemsResponse.hasErrors()) {
            throw new RuntimeException();

        } else if (prescriptionItemsResponse.getResponseObject().size() < 1) {
            return ok(index.render(currentUserSession, "No prescriptions found for that patient", 0));
        }

        return redirect(routes.PharmaciesController.editGet(patientId));
    }

    public Result editGet(int patientId) {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        EditViewModelGet viewModelGet = new EditViewModelGet();
        String message;

        //get the patient item
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemById(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            message = patientItemServiceResponse.getErrors().get("");
            return ok(index.render(currentUserSession, message, 0));
        }
        PatientItem patient = patientItemServiceResponse.getResponseObject();

        //get the patient encounter item
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findPatientEncounterItemById(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            message = patientEncounterItemServiceResponse.getErrors().get("");
            return ok(index.render(currentUserSession, message, 0));
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();
        viewModelGet.setPatientEncounterItem(patientEncounterItem);

        //get vital items
        ServiceResponse<List<VitalItem>> vitalItemsServiceResponse = pharmacyService.findPharmacyVitalItems(patientEncounterItem.getId());
        if (vitalItemsServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            for (VitalItem vi : vitalItemsServiceResponse.getResponseObject()) {
                //consider using a command pattern?
                switch (vi.getName()) {
                    case "heightFeet":
                        patient.setHeightFeet(vi.getValue().intValue());
                        break;
                    case "heightInches":
                        patient.setHeightInches(vi.getValue().intValue());
                        break;
                    case "weight":
                        patient.setWeight(vi.getValue());
                        break;
                }
            }
        }
        viewModelGet.setPatient(patientItemServiceResponse.getResponseObject());

        //find patient prescriptions, they do have to exist
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.findUnreplacedPrescriptionItems(patientEncounterItem.getId());
        if (prescriptionItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setMedications(prescriptionItemServiceResponse.getResponseObject());

        //find patient problems, they do not have to exist.
        ServiceResponse<List<ProblemItem>> problemItemServiceResponse = searchService.findProblemItems(patientEncounterItem.getId());
        if (problemItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            if (problemItemServiceResponse.getResponseObject().size() > 0){
                viewModelGet.setProblems(problemItemServiceResponse.getResponseObject());
            }
        }

        return ok(edit.render(currentUserSession, viewModelGet, false));
    }

    public Result editPost(int id) {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        EditViewModelPost createViewModelPost = populatedViewModelPostForm.bindFromRequest().get();

        //get patient encounter
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findPatientEncounterItemById(id);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();

        //get patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemById(patientEncounterItem.getPatientId());
        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();

        //replace prescription 1
        PrescriptionItem prescriptionItem = new PrescriptionItem();
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication1())) {
            prescriptionItem.setName(createViewModelPost.getReplacementMedication1());
            ServiceResponse<PrescriptionItem> response = pharmacyService.createAndReplacePrescription(
                    prescriptionItem,
                    createViewModelPost.getId_prescription1(),
                    currentUserSession.getId()
            );
            if (response.hasErrors()) {
                throw new RuntimeException();
            }
        }
        //replace prescription 2
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication2())) {
            prescriptionItem.setName(createViewModelPost.getReplacementMedication2());
            ServiceResponse<PrescriptionItem> response = pharmacyService.createAndReplacePrescription(
                    prescriptionItem,
                    createViewModelPost.getId_prescription2(),
                    currentUserSession.getId()
            );
            if (response.hasErrors()) {
                throw new RuntimeException();
            }
        }
        //replace prescription 3
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication3())) {
            prescriptionItem.setName(createViewModelPost.getReplacementMedication3());
            ServiceResponse<PrescriptionItem> response = pharmacyService.createAndReplacePrescription(
                    prescriptionItem,
                    createViewModelPost.getId_prescription3(),
                    currentUserSession.getId()
            );
            if (response.hasErrors()) {
                throw new RuntimeException();
            }
        }
        //replace prescription 4
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication4())) {
            prescriptionItem.setName(createViewModelPost.getReplacementMedication4());
            ServiceResponse<PrescriptionItem> response = pharmacyService.createAndReplacePrescription(
                    prescriptionItem,
                    createViewModelPost.getId_prescription4(),
                    currentUserSession.getId()
            );
            if (response.hasErrors()) {
                throw new RuntimeException();
            }
        }
        //replace prescription 5
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication5())) {
            prescriptionItem.setName(createViewModelPost.getReplacementMedication5());
            ServiceResponse<PrescriptionItem> response = pharmacyService.createAndReplacePrescription(
                    prescriptionItem,
                    createViewModelPost.getId_prescription5(),
                    currentUserSession.getId()
            );
            if (response.hasErrors()) {
                throw new RuntimeException();
            }
        }

        //check the patient in!
        pharmacyService.checkPatientIn(patientEncounterItem.getId(), currentUserSession.getId());
        String message = "Patient information for " +
                patientItem.getFirstName() +
                " " +
                patientItem.getLastName() +
                " (id: " +
                patientItem.getId() +
                ") was saved successfully.";

        return ok(index.render(currentUserSession, message, 0));
    }

    /**
     * Used for typeahead in replacement prescription boxes
     * Called via ajax
     *
     * @return JSON object of medications that exist in the medications table
     */
    public Result typeaheadJSONGet() {
        JsonObject jsonObject = new JsonObject();

        //get a list of medications in the medication table
        //these medications are added by an administrator in the admin section
        ServiceResponse<List<String>> medicationServiceResponse = pharmacyService.findAllMedications();
        if (medicationServiceResponse.hasErrors()) {
            return ok(jsonObject.toString());
        }

        List<String> medications = medicationServiceResponse.getResponseObject();

        //create a JsonObject to send back via AJAX
        for (int medication = 0; medication < medications.size(); medication++) {
            jsonObject.addProperty("medicine" + medication, medications.get(medication));
        }
        return ok(jsonObject.toString());
    }
}
