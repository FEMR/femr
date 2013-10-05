package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.ServiceResponse;
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
    private Provider<IPatient> patientProvider;
    private Provider<IPatientEncounter> patientEncounterProvider;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private Provider<IVital> vitalProvider;

    @Inject
    public TriageController(ITriageService triageService,
                            Provider<IPatient> patientProvider,
                            Provider<IPatientEncounter> patientEncounterProvider,
                            Provider<IPatientEncounterVital> patientEncounterVitalProvider,
                            Provider<IVital> vitalProvider) {
        this.triageService = triageService;
        this.patientProvider = patientProvider;
        this.patientEncounterProvider = patientEncounterProvider;
        this.patientEncounterVitalProvider = patientEncounterVitalProvider;
        this.vitalProvider = vitalProvider;
    }

    public Result createGet() {
        List<? extends IVital> vitalNames = triageService.findAllVitals();

        return ok(femr.ui.views.html.triage.create.render(vitalNames));
    }

    public Result createPost() {
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();

        IPatient patient = populatePatient(viewModel);
        ServiceResponse<IPatient> patientServiceResponse = triageService.createPatient(patient);

        IPatientEncounter patientEncounter = populatePatientEncounter(viewModel,patientServiceResponse);
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = triageService.createPatientEncounter(patientEncounter);

        List<IPatientEncounterVital> patientEncounterVitals = populatePatientEncounterVitals(viewModel,patientEncounterServiceResponse);
        for (int i = 0; i < patientEncounterVitals.size(); i++){
            triageService.createPatientEncounterVital(patientEncounterVitals.get(i));
        }

        return redirect("/triage/show/" + patientServiceResponse.getResponseObject().getId());
    }

    public Result savedPatient(String id) {
        IPatient patient = triageService.findPatientById(id).getResponseObject();

        List<? extends IPatientEncounter> patientEncounters = triageService.findAllEncounters();

        CreateViewModel viewModel = new CreateViewModel();

        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());
        viewModel.setAddress(patient.getAddress());
        viewModel.setCity(patient.getCity());
        viewModel.setAge(patient.getAge());
        viewModel.setSex(patient.getSex());         //awwww yeahhhh!

        return ok(femr.ui.views.html.triage.show.render(viewModel,patientEncounters));
    }

    private IPatient populatePatient(CreateViewModel viewModel){
        IPatient patient = patientProvider.get();
        patient.setUserId(1);
        patient.setFirstName(viewModel.getFirstName());
        patient.setLastName(viewModel.getLastName());
        patient.setAge(viewModel.getAge());
        patient.setSex(viewModel.getSex()); //gettin' someeee!
        patient.setAddress(viewModel.getAddress());
        patient.setCity(viewModel.getCity());

        return patient;
    }

    private IPatientEncounter populatePatientEncounter(CreateViewModel viewModel,
                                                       ServiceResponse<IPatient> patientServiceResponse){
        IPatientEncounter patientEncounter = patientEncounterProvider.get();
        patientEncounter.setPatientId(patientServiceResponse.getResponseObject().getId());
        patientEncounter.setUserId(1);
        patientEncounter.setDateOfVisit(triageService.getCurrentDateTime());
        patientEncounter.setChiefComplaint(viewModel.getChiefComplaint());

        return patientEncounter;
    }

    private List<IPatientEncounterVital> populatePatientEncounterVitals(CreateViewModel viewModel,
                                                       ServiceResponse<IPatientEncounter> patientEncounterServiceResponse){

        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();
        IPatientEncounterVital[] patientEncounterVital = new IPatientEncounterVital[9];
        for (int j=0;j < patientEncounterVital.length; j++){
            patientEncounterVital[j] = patientEncounterVitalProvider.get();
        }
        patientEncounterVital[0].setDateTaken((triageService.getCurrentDateTime()));
        patientEncounterVital[0].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[0].setUserId(1);
        patientEncounterVital[0].setVitalId(1);
        patientEncounterVital[0].setVitalValue(viewModel.getRespirations());

        patientEncounterVital[1].setDateTaken((triageService.getCurrentDateTime()));
        patientEncounterVital[1].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[1].setUserId(1);
        patientEncounterVital[1].setVitalId(2);
        patientEncounterVital[1].setVitalValue(viewModel.getHeartRate());

        patientEncounterVital[2].setDateTaken((triageService.getCurrentDateTime()));
        patientEncounterVital[2].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[2].setUserId(1);
        patientEncounterVital[2].setVitalId(3);
        patientEncounterVital[2].setVitalValue(viewModel.getTemperature());

        patientEncounterVital[3].setDateTaken((triageService.getCurrentDateTime()));
        patientEncounterVital[3].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[3].setUserId(1);
        patientEncounterVital[3].setVitalId(4);
        patientEncounterVital[3].setVitalValue(viewModel.getOxygen());

        patientEncounterVital[4].setDateTaken((triageService.getCurrentDateTime()));
        patientEncounterVital[4].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[4].setUserId(1);
        patientEncounterVital[4].setVitalId(5);
        patientEncounterVital[4].setVitalValue(viewModel.getHeightFeet());

        patientEncounterVital[5].setDateTaken((triageService.getCurrentDateTime()));
        patientEncounterVital[5].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[5].setUserId(1);
        patientEncounterVital[5].setVitalId(6);
        patientEncounterVital[5].setVitalValue(viewModel.getHeightInches());

        patientEncounterVital[6].setDateTaken((triageService.getCurrentDateTime()));
        patientEncounterVital[6].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[6].setUserId(1);
        patientEncounterVital[6].setVitalId(7);
        patientEncounterVital[6].setVitalValue(viewModel.getWeight());

        patientEncounterVital[7].setDateTaken((triageService.getCurrentDateTime()));
        patientEncounterVital[7].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[7].setUserId(1);
        patientEncounterVital[7].setVitalId(8);
        patientEncounterVital[7].setVitalValue(viewModel.getBloodPressureSystolic());

        patientEncounterVital[8].setDateTaken((triageService.getCurrentDateTime()));
        patientEncounterVital[8].setPatientEncounterId(patientEncounterServiceResponse.getResponseObject().getId());
        patientEncounterVital[8].setUserId(1);
        patientEncounterVital[8].setVitalId(9);
        patientEncounterVital[8].setVitalValue(viewModel.getBloodPressureDiastolic());

        patientEncounterVitals.addAll(Arrays.asList(patientEncounterVital));
        return patientEncounterVitals;
    }


}
