package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IMedicalService;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.*;
import femr.ui.helpers.controller.MedicalHelper;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.medical.CreateViewModelGet;
import femr.ui.models.medical.CreateViewModelPost;
import femr.ui.models.medical.SearchViewModel;
import femr.ui.models.medical.UpdateVitalsModel;
import femr.ui.views.html.medical.edit;
import femr.ui.views.html.medical.index;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class MedicalController extends Controller {

    private final Form<CreateViewModelPost> createViewModelPostForm = Form.form(CreateViewModelPost.class);
    private final Form<SearchViewModel> searchViewModelForm = Form.form(SearchViewModel.class);
    private final Form<UpdateVitalsModel> updateVitalsModelForm = Form.form(UpdateVitalsModel.class);
    private ISessionService sessionService;
    private ISearchService searchService;
    private ITriageService triageService;
    private IMedicalService medicalService;
    private MedicalHelper medicalHelper;

    @Inject
    public MedicalController(ISessionService sessionService, ISearchService searchService, ITriageService triageService, IMedicalService medicalService, MedicalHelper medicalHelper) {

        this.sessionService = sessionService;
        this.searchService = searchService;
        this.triageService = triageService;
        this.medicalService = medicalService;
        this.medicalHelper = medicalHelper;
    }

    public Result indexGet(boolean duplicatePatientError, String message) {

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();


        return ok(index.render(currentUserSession, message));
    }

    public Result searchPost() {

        SearchViewModel searchViewModel = searchViewModelForm.bindFromRequest().get();
        return redirect(routes.MedicalController.editGet(searchViewModel.getId()));
    }

    public Result editGet(int patientId) {

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        //current Patient info for view model
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(patientId);
        if (patientServiceResponse.hasErrors()) {
            return ok(index.render(currentUserSession, "That patient can not be found."));
        }
        IPatient patient = patientServiceResponse.getResponseObject();

        //current Encounter info for view model
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        //current vitals for view model
        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();
        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse;
        int TOTAL_VITALS = 9;
        for (int vital = 1; vital <= TOTAL_VITALS; vital++) {
            patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(vital, patientEncounter.getId());
            if (patientEncounterVitalServiceResponse.hasErrors()) {
                patientEncounterVitals.add(null);
            } else {
                patientEncounterVitals.add(patientEncounterVitalServiceResponse.getResponseObject());
            }
        }



        //check to make sure a patient hasn't been checked in before
        //if they have, don't goto the populated page
        boolean hasPatientBeenCheckedIn = medicalService.hasPatientBeenCheckedIn(patientEncounter.getId());
        if (hasPatientBeenCheckedIn == true) {
            String message = "That patient has already been checked in.";
            ServiceResponse<DateTime> dateResponse = medicalService.getDateOfCheckIn(patientEncounter.getId());
            if (dateResponse.hasErrors()){
                return redirect(routes.MedicalController.indexGet(true,message));
            }

            DateTime dateNow = dateUtils.getCurrentDateTime();
            DateTime dateTaken = dateResponse.getResponseObject();

            if (dateNow.dayOfYear().equals(dateTaken.dayOfYear())){
                message="That patient has already been seen today. Would you like to edit their encounter?";
            }


            return redirect(routes.MedicalController.indexGet(true,message));




        } else {
            CreateViewModelGet viewModel = medicalHelper.populateViewModelGet(patient, patientEncounter, patientEncounterVitals);
            return ok(edit.render(currentUserSession, viewModel));
        }
    }

    public Result editPost(int patientId) {

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        CreateViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            return internalServerError();
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        //HPI Data
        List<IPatientEncounterHpiField> patientEncounterHpiFields = medicalHelper.populateHpiFields(viewModelPost, patientEncounter, currentUserSession);
        ServiceResponse<IPatientEncounterHpiField> patientEncounterHpiFieldServiceResponse;
        for (int j = 0; j < patientEncounterHpiFields.size(); j++) {
            if (StringUtils.isNullOrWhiteSpace(patientEncounterHpiFields.get(j).getHpiFieldValue())) {
                continue;
            } else {
                patientEncounterHpiFieldServiceResponse = medicalService.createPatientEncounterHpiField(patientEncounterHpiFields.get(j));
                if (patientEncounterHpiFieldServiceResponse.hasErrors()) {
                    return internalServerError();
                }
            }
        }
        //Treatment Data
        List<IPatientEncounterTreatmentField> patientEncounterTreatmentFields = medicalHelper.populateTreatmentFields(viewModelPost, patientEncounter, currentUserSession);
        ServiceResponse<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldServiceResponse;
        for (int i = 0; i < patientEncounterTreatmentFields.size(); i++) {
            if (StringUtils.isNullOrWhiteSpace(patientEncounterTreatmentFields.get(i).getTreatmentFieldValue())) {
                continue;
            } else {
                patientEncounterTreatmentFieldServiceResponse = medicalService.createPatientEncounterTreatmentField(patientEncounterTreatmentFields.get(i));
                if (patientEncounterTreatmentFieldServiceResponse.hasErrors()) {
                    return internalServerError();
                }
            }
        }
        //prescriptions
        List<IPatientPrescription> patientPrescriptions = medicalHelper.populatePatientPrescriptions(viewModelPost, patientEncounter, currentUserSession);
        ServiceResponse<IPatientPrescription> patientPrescriptionServiceResponse;
        for (int k = 0; k < patientPrescriptions.size(); k++) {
            if (StringUtils.isNullOrWhiteSpace(patientPrescriptions.get(k).getMedicationName())) {
                continue;
            } else {
                patientPrescriptionServiceResponse = medicalService.createPatientPrescription(patientPrescriptions.get(k));
                if (patientPrescriptionServiceResponse.hasErrors()) {
                    return internalServerError();
                }
            }
        }
        return redirect(routes.MedicalController.indexGet(false,null));
    }

    public Result updateVitalsPost(int id) {

        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<IPatientEncounter> currentEncounterByPatientId = searchService.findCurrentEncounterByPatientId(id);

        UpdateVitalsModel updateVitalsModel = updateVitalsModelForm.bindFromRequest().get();

        List<? extends IPatientEncounterVital> patientEncounterVitals = medicalHelper.populatePatientVitals(updateVitalsModel, currentUser.getId(), currentEncounterByPatientId.getResponseObject().getId());
        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse;
        for (int i = 0; i < patientEncounterVitals.size(); i++) {
            if (patientEncounterVitals.get(i).getVitalValue() > 0) {
                patientEncounterVitalServiceResponse = triageService.createPatientEncounterVital(patientEncounterVitals.get(i));
                if (patientEncounterVitalServiceResponse.hasErrors()) {
                    return internalServerError();
                }
            }
        }
        return ok("true");
    }
}
