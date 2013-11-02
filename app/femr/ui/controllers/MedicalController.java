package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IMedicalService;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterTreatmentField;
import femr.common.models.IPatientEncounterVital;
import femr.data.models.PatientEncounterTreatmentField;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.medical.index;
import femr.ui.views.html.medical.indexPopulated;
import femr.ui.models.medical.CreateViewModelGet;
import femr.ui.models.medical.CreateViewModelPost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicalController extends Controller {
    private final Form<CreateViewModelPost> createViewModelPostForm = Form.form(CreateViewModelPost.class);
    private Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider;
    private ISessionService sessionService;
    private ISearchService searchService;
    private IMedicalService medicalService;

    @Inject
    public MedicalController(ISessionService sessionService,
                             ISearchService searchService,
                             IMedicalService medicalService,
                             Provider<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldProvider) {
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.medicalService = medicalService;
        this.patientEncounterTreatmentFieldProvider = patientEncounterTreatmentFieldProvider;

    }

    public Result createGet() {
        boolean error = false;

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(index.render(currentUserSession,error));
    }

    public Result createPopulatedPost(){
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();


        CreateViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(viewModelPost.getId());
        if (patientServiceResponse.hasErrors()){
            return createGet();
        }
        IPatient patient = patientServiceResponse.getResponseObject();

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(viewModelPost.getId());
        if (patientEncounterServiceResponse.hasErrors()){
            return createGet();
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        List<IPatientEncounterTreatmentField> patientEncounterTreatmentFields =
                populatePatientEncounterTreatmentFields(viewModelPost,patientEncounter,currentUserSession);

        for (int i = 0; i < patientEncounterTreatmentFields.size(); i++){
            if (StringUtils.isNullOrWhiteSpace(patientEncounterTreatmentFields.get(i).getTreatmentFieldValue())){
                continue;
            }
            else{
                medicalService.createPatientEncounterTreatmentField(patientEncounterTreatmentFields.get(i));
            }
        }

        return createGet();
    }

    public Result createPopulatedGet(){
        boolean error = false;

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        CreateViewModelGet viewModel = new CreateViewModelGet();

        String s_patientID = request().getQueryString("id");
        int i_patientID = Integer.parseInt(s_patientID);

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(i_patientID);
        if (patientServiceResponse.hasErrors()){
            error = true;
            return ok(index.render(currentUserSession,error));
        }
        IPatient patient = patientServiceResponse.getResponseObject();
        viewModel.setpID(patient.getId());
        viewModel.setCity(patient.getCity());
        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());
        viewModel.setAge(patient.getAge());
        viewModel.setSex(patient.getSex());

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse =
                searchService.findCurrentEncounterByPatientId(i_patientID);
        if (patientEncounterServiceResponse.hasErrors()){
            error = true;
            return ok(index.render(currentUserSession,error));
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();
        viewModel.setChiefComplaint(patientEncounter.getChiefComplaint());
        viewModel.setWeeksPregnant(patientEncounter.getWeeksPregnant());

        //populating this viewModel with vitals is
        //(unfortunately and temporarily) dependant
        // on the order of vitals in the database

        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse;

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(1,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setRespiratoryRate(null);
        else
            viewModel.setRespiratoryRate(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(2,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setHeartRate(null);
        else
            viewModel.setHeartRate(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(3,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setTemperature(null);
        else
            viewModel.setTemperature(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(4,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setOxygenSaturation(null);
        else
            viewModel.setOxygenSaturation(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(5,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setHeightFeet(null);
        else
            viewModel.setHeightFeet(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(6,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setHeightInches(null);
        else
            viewModel.setHeightInches(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(7,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setWeight(null);
        else
            viewModel.setWeight(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(8,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setBloodPressureSystolic(null);
        else
            viewModel.setBloodPressureSystolic(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(9,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setBloodPressureDiastolic(null);
        else
            viewModel.setBloodPressureDiastolic(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        return ok(indexPopulated.render(currentUserSession,viewModel));
    }

    //helper functions

    private List<IPatientEncounterTreatmentField> populatePatientEncounterTreatmentFields(CreateViewModelPost viewModelPost,
                                                                                          IPatientEncounter patientEncounter,
                                                                                          CurrentUser currentUserSession){
        List<IPatientEncounterTreatmentField> patientEncounterTreatmentFields = new ArrayList<>();
        IPatientEncounterTreatmentField[] patientEncounterTreatmentField = new IPatientEncounterTreatmentField[9];
        for (int i = 0; i < 9; i++){
            patientEncounterTreatmentField[i] = patientEncounterTreatmentFieldProvider.get();
            patientEncounterTreatmentField[i].setDateTaken(medicalService.getCurrentDateTime());
            patientEncounterTreatmentField[i].setPatientEncounterId(patientEncounter.getId());
            patientEncounterTreatmentField[i].setUserId(currentUserSession.getId());
            //if statements take care of the 5 prescriptions which all have
            //a treatment fieldId of 5
            if (i < 4){
                patientEncounterTreatmentField[i].setTreatmentFieldId(i+1);
            }
            else if (i > 3){

                patientEncounterTreatmentField[i].setTreatmentFieldId(5);
            }

        }

        patientEncounterTreatmentField[0].setTreatmentFieldValue(viewModelPost.getAssessment());
        patientEncounterTreatmentField[1].setTreatmentFieldValue(viewModelPost.getProblem());
        patientEncounterTreatmentField[2].setTreatmentFieldValue(viewModelPost.getTreatment());
        patientEncounterTreatmentField[3].setTreatmentFieldValue(viewModelPost.getFamilyHistory());
        patientEncounterTreatmentField[4].setTreatmentFieldValue(viewModelPost.getPrescription1());
        patientEncounterTreatmentField[5].setTreatmentFieldValue(viewModelPost.getPrescription2());
        patientEncounterTreatmentField[6].setTreatmentFieldValue(viewModelPost.getPrescription3());
        patientEncounterTreatmentField[7].setTreatmentFieldValue(viewModelPost.getPrescription4());
        patientEncounterTreatmentField[8].setTreatmentFieldValue(viewModelPost.getPrescription5());

        patientEncounterTreatmentFields.addAll(Arrays.asList(patientEncounterTreatmentField));
        return patientEncounterTreatmentFields;
    }
}
