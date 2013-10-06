package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IVital;
import femr.ui.models.triage.CreateViewModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriageController extends Controller {
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);
    private ITriageService triageService;
    private ISessionService sessionService;
    private Provider<IPatient> patientProvider;
    private Provider<IPatientEncounter> patientEncounterProvider;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private Provider<IVital> vitalProvider;

    @Inject
    public TriageController(ITriageService triageService,
                            ISessionService sessionService,
                            Provider<IPatient> patientProvider,
                            Provider<IPatientEncounter> patientEncounterProvider,
                            Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                            Provider<IVital> vitalProvider) {
        this.triageService = triageService;
        this.sessionService = sessionService;
        this.patientProvider = patientProvider;
        this.patientEncounterProvider = patientEncounterProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.vitalProvider = vitalProvider;
    }

    public Result createGet() {
        List<? extends IVital> vitalNames = triageService.findAllVitals();
        ServiceResponse<CurrentUser> currentUserSession = sessionService.getCurrentUserSession();
        if(currentUserSession.isSuccessful()){
            return ok(femr.ui.views.html.triage.create.render(vitalNames,currentUserSession.getResponseObject()));
        }
        else{
            return ok(femr.ui.views.html.triage.create.render(vitalNames,null));
        }
    }

    public Result createPost() {
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        ServiceResponse<CurrentUser> currentUserSession = sessionService.getCurrentUserSession();

        IPatient patient = populatePatient(viewModel,currentUserSession.getResponseObject());
        ServiceResponse<IPatient> patientServiceResponse = triageService.createPatient(patient);
        IPatientEncounter patientEncounter = populatePatientEncounter(viewModel,patientServiceResponse,currentUserSession.getResponseObject());
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse =
                triageService.createPatientEncounter(patientEncounter);

        List<IPatientEncounterVital> patientEncounterVitals =
                populatePatientEncounterVitals(viewModel,patientEncounterServiceResponse,currentUserSession.getResponseObject());
        for (int i = 0; i < patientEncounterVitals.size(); i++){
            triageService.createPatientEncounterVital(patientEncounterVitals.get(i));
        }

        return redirect("/show/" + patientServiceResponse.getResponseObject().getId());
    }

    private IPatient populatePatient(CreateViewModel viewModel, CurrentUser currentUser){
        IPatient patient = patientProvider.get();
        patient.setUserId(currentUser.getId());
        patient.setFirstName(viewModel.getFirstName());
        patient.setLastName(viewModel.getLastName());
        patient.setAge(viewModel.getAge());
        patient.setSex(viewModel.getSex());
        patient.setAddress(viewModel.getAddress());
        patient.setCity(viewModel.getCity());

        return patient;
    }

    private IPatientEncounter populatePatientEncounter(CreateViewModel viewModel,
                                                       ServiceResponse<IPatient> patientServiceResponse,
                                                       CurrentUser currentUser){
        IPatientEncounter patientEncounter = patientEncounterProvider.get();
        patientEncounter.setPatientId(patientServiceResponse.getResponseObject().getId());
        patientEncounter.setUserId(currentUser.getId());
        patientEncounter.setDateOfVisit(triageService.getCurrentDateTime());
        patientEncounter.setChiefComplaint(viewModel.getChiefComplaint());

        return patientEncounter;
    }

    private List<IPatientEncounterVital> populatePatientEncounterVitals(CreateViewModel viewModel,
                                                       ServiceResponse<IPatientEncounter> patientEncounterServiceResponse,
                                                       CurrentUser currentUser){

        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();
        IPatientEncounterVital[] patientEncounterVital = new IPatientEncounterVital[9];
        for (int j = 0; j < patientEncounterVital.length; j++) {
            patientEncounterVital[j] = patientEncounterVitalProvider.get();
            patientEncounterVital[j].setDateTaken((triageService.getCurrentDateTime()));
            patientEncounterVital[j].setUserId(currentUser.getId());
        }
        patientEncounterVital[0].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[0].setVitalId(1);
        patientEncounterVital[0].setVitalValue(viewModel.getRespirations());

        patientEncounterVital[1].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[1].setVitalId(2);
        patientEncounterVital[1].setVitalValue(viewModel.getHeartRate());

        patientEncounterVital[2].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[2].setVitalId(3);
        patientEncounterVital[2].setVitalValue(viewModel.getTemperature());

        patientEncounterVital[3].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[3].setVitalId(4);
        patientEncounterVital[3].setVitalValue(viewModel.getOxygen());

        patientEncounterVital[4].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[4].setVitalId(5);
        patientEncounterVital[4].setVitalValue(viewModel.getHeightFeet());

        patientEncounterVital[5].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[5].setVitalId(6);
        patientEncounterVital[5].setVitalValue(viewModel.getHeightInches());

        patientEncounterVital[6].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[6].setVitalId(7);
        patientEncounterVital[6].setVitalValue(viewModel.getWeight());

        patientEncounterVital[7].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[7].setVitalId(8);
        patientEncounterVital[7].setVitalValue(viewModel.getBloodPressureSystolic());

        patientEncounterVital[8].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[8].setVitalId(9);
        patientEncounterVital[8].setVitalValue(viewModel.getBloodPressureDiastolic());

        patientEncounterVitals.addAll(Arrays.asList(patientEncounterVital));
        return patientEncounterVitals;
    }

}
