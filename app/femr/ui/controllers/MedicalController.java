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
    private Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider;
    private Provider<IPatientEncounterHpiField> patientEncounterHpiFieldProvider;
    private Provider<IPatientPrescription> patientPrescriptionProvider;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private ISessionService sessionService;
    private ISearchService searchService;
    private ITriageService triageService;
    private IMedicalService medicalService;
    private MedicalHelper medicalHelper;

    @Inject
    public MedicalController(ISessionService sessionService, ISearchService searchService, ITriageService triageService, IMedicalService medicalService, Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider, Provider<IPatientEncounterHpiField> patientEncounterHpiFieldProvider, Provider<IPatientPrescription> patientPrescriptionProvider, Provider<IPatientEncounterVital> patientEncounterVitalProvider, MedicalHelper medicalHelper) {

        this.sessionService = sessionService;
        this.searchService = searchService;
        this.triageService = triageService;
        this.medicalService = medicalService;
        this.patientEncounterTreatmentFieldProvider = patientEncounterTreatmentFieldProvider;
        this.patientEncounterHpiFieldProvider = patientEncounterHpiFieldProvider;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
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
        updatePatientVitals(updateVitalsModel, currentUser.getId(), currentEncounterByPatientId.getResponseObject().getId());

        return ok("true");
    }

    private void updatePatientVitals(UpdateVitalsModel updateVitalsModel, int currentUserId, int patientEncounterId) {

        List<Double> vitals = new ArrayList<>();
        vitals.add(updateVitalsModel.getRespRate());
        vitals.add(updateVitalsModel.getHeartRate());
        vitals.add(updateVitalsModel.getTemperature());
        vitals.add(updateVitalsModel.getOxygen());
        vitals.add(updateVitalsModel.getHeightFt());
        vitals.add(updateVitalsModel.getHeightIn());
        vitals.add(updateVitalsModel.getWeight());
        vitals.add(updateVitalsModel.getBpSystolic());
        vitals.add(updateVitalsModel.getBpDiastolic());

        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            IPatientEncounterVital patientEncounterVital = patientEncounterVitalProvider.get();
            patientEncounterVital.setDateTaken((dateUtils.getCurrentDateTime()));
            patientEncounterVital.setUserId(currentUserId);
            patientEncounterVital.setPatientEncounterId(patientEncounterId);
            patientEncounterVital.setVitalId(i + 1);
            patientEncounterVital.setVitalValue(vitals.get(i).floatValue());
            patientEncounterVitals.add(patientEncounterVital);
        }

        for (int i = 0; i < patientEncounterVitals.size(); i++) {
            if (patientEncounterVitals.get(i).getVitalValue() > 0) {
                triageService.createPatientEncounterVital(patientEncounterVitals.get(i));
            }
        }
    }

    public Result createPopulatedGet() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        CreateViewModelGet viewModel = new CreateViewModelGet();

        String s_patientID = request().getQueryString("id");
        s_patientID = s_patientID.trim();
        int i_patientID = Integer.parseInt(s_patientID);

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(i_patientID);
        if (patientServiceResponse.hasErrors()) {
            return ok(index.render(currentUserSession, "That patient can not be found."));
        }
        IPatient patient = patientServiceResponse.getResponseObject();
        viewModel.setpID(patient.getId());
        viewModel.setCity(patient.getCity());
        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());
        viewModel.setAge(dateUtils.calculateYears(patient.getAge()));
        viewModel.setSex(patient.getSex());

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(i_patientID);
        if (patientEncounterServiceResponse.hasErrors()) {
            return ok(index.render(currentUserSession, "An error occurred"));
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();
        viewModel.setChiefComplaint(patientEncounter.getChiefComplaint());
        viewModel.setWeeksPregnant(patientEncounter.getWeeksPregnant());

        //populating this viewModel with vitals is
        //(unfortunately and temporarily) dependant
        // on the order of vitals in the database

        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse;

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(1, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setRespiratoryRate(null);
        } else {
            viewModel.setRespiratoryRate(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(2, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setHeartRate(null);
        } else {
            viewModel.setHeartRate(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(3, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setTemperature(null);
        } else {
            viewModel.setTemperature(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(4, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setOxygenSaturation(null);
        } else {
            viewModel.setOxygenSaturation(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(5, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setHeightFeet(null);
        } else {
            viewModel.setHeightFeet(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(6, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setHeightInches(null);
        } else {
            viewModel.setHeightInches(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(7, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setWeight(null);
        } else {
            viewModel.setWeight(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(8, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setBloodPressureSystolic(null);
        } else {
            viewModel.setBloodPressureSystolic(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(9, patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors()) {
            viewModel.setBloodPressureDiastolic(null);
        } else {
            viewModel.setBloodPressureDiastolic(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());
        }

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
