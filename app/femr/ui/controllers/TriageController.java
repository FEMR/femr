package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.ui.models.triage.CreateViewModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

public class TriageController extends Controller {
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);
    private ITriageService triageService;
    private Provider<IPatient> patientProvider;
    private Provider<IPatientEncounter> patientEncounterProvider;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;

    @Inject
    public TriageController(ITriageService triageService, Provider<IPatient> patientProvider) {
        this.triageService = triageService;
        this.patientProvider = patientProvider;
    }

    public static Result createGet() {
        boolean isPostBack = false;
        return ok(femr.ui.views.html.triage.create.render());
    }

    public Result createPost() {
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();

        IPatient patient = patientProvider.get();
        IPatientEncounter patientEncounter = patientEncounterProvider.get();
        List<IPatientEncounterVital> patientEncounterVitalList = new ArrayList<IPatientEncounterVital>();   //make a PatientEncounterVitalProvider!
//        IPatientEncounterVital patientEncounterVital = new PatientEncounterVital();

        //Currently using defaults for userID
        patient.setUserId(1);
        patient.setFirstName(viewModel.getFirstName());
        patient.setLastName(viewModel.getLastName());
        patient.setAge(viewModel.getAge());
        patient.setSex(viewModel.getSex()); //gettin' someeee!
        patient.setAddress(viewModel.getAddress());
        patient.setCity(viewModel.getCity());
        ServiceResponse<IPatient> patientServiceResponse = triageService.createPatient(patient);

        patientEncounter.setPatientId(patientServiceResponse.getResponseObject().getId());
        patientEncounter.setUserId(1);
        patientEncounter.setDateOfVisit(triageService.getCurrentDateTime());
        patientEncounter.setChiefComplaint(viewModel.getChiefComplaint());
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = triageService.createPatientEncounter(patientEncounter);


        return redirect("/triage/show/" + patientServiceResponse.getResponseObject().getId());
    }

    public Result savedPatient(String id) {
        IPatient patient = triageService.findPatientById(id).getResponseObject();
        CreateViewModel viewModel = new CreateViewModel();


        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());
        viewModel.setAddress(patient.getAddress());
        viewModel.setCity(patient.getCity());
        viewModel.setAge(patient.getAge());
        viewModel.setSex(patient.getSex());         //awwww yeahhhh!

        return ok(femr.ui.views.html.triage.show.render(viewModel));
    }


}
