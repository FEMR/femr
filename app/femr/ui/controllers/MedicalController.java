package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IMedicalService;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.*;
import femr.ui.helpers.controller.MedicalHelper;
import femr.ui.models.medical.CreateViewModelGet;
import femr.ui.models.medical.CreateViewModelPost;
import femr.ui.models.medical.UpdateVitalsModel;
import femr.ui.views.html.medical.index;
import femr.ui.views.html.medical.indexPopulated;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicalController extends Controller {

    private final Form<CreateViewModelPost> createViewModelPostForm = Form.form(CreateViewModelPost.class);
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

    public Result createGet() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(index.render(currentUserSession, null));
    }

    public Result createPost() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        CreateViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(viewModelPost.getId());
        if (patientEncounterServiceResponse.hasErrors()) {
            //error
            //goto 500 page
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
                    //error
                    //goto 500 page
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
                    //error
                    //goto 500 page
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
                    //error
                    //goto 500 page
                }
            }
        }
        return createGet();
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
                    //error
                    //goto 500 page
                }
            }
        }
        return ok("true");
    }

    public Result createPopulatedGet() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        String s_id = request().getQueryString("id");
        if (StringUtils.isNullOrWhiteSpace(s_id)) {
            return ok(index.render(currentUserSession, "That patient can not be found."));
        }
        s_id = s_id.trim();
        int i_patientID = Integer.parseInt(s_id);

        //current Patient info for view model
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(i_patientID);
        if (patientServiceResponse.hasErrors()) {
            return ok(index.render(currentUserSession, "That patient can not be found."));
        }
        IPatient patient = patientServiceResponse.getResponseObject();

        //current Encounter info for view model
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(i_patientID);
        if (patientEncounterServiceResponse.hasErrors()) {
            //error
            //should goto 500
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
            }
            else{
                patientEncounterVitals.add(patientEncounterVitalServiceResponse.getResponseObject());
            }
        }

        CreateViewModelGet viewModel = medicalHelper.populateViewModelGet(patient, patientEncounter, patientEncounterVitals);

        //check to make sure a patient hasn't been checked in before
        //if they have, don't goto the populated page
        boolean hasPatientBeenCheckedIn = medicalService.hasPatientBeenCheckedIn(patientEncounter.getId());
        if (hasPatientBeenCheckedIn == true) {
            return ok(index.render(currentUserSession, "That patient has already been seen"));
        } else {
            return ok(indexPopulated.render(currentUserSession, viewModel));
        }
    }
}
