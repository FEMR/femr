package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.data.models.Patient;
import femr.data.models.PatientEncounter;
import femr.data.models.PatientEncounterVital;
import femr.ui.models.triage.CreateViewModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class TriageController extends Controller {
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);
    private ITriageService triageService;

    @Inject
    public TriageController(ITriageService triageService){
        this.triageService = triageService;
    }

    public static Result createGet(){
        return ok(femr.ui.views.html.triage.create.render());
    }

    public Result createPost(){
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();

        IPatient patient = new Patient();
        IPatientEncounter patientEncounter = new PatientEncounter();
        IPatientEncounterVital patientEncounterVital = new PatientEncounterVital();
        //Currently using defaults for userID and patientID

        patient.setUserId(1);
        patient.setFirstName(viewModel.getFirstName());
        patient.setLastName(viewModel.getLastName());
        patient.setAge(viewModel.getAge());
        patient.setSex(viewModel.getSex()); //gettin' someeee!
        patient.setAddress(viewModel.getAddress());
        patient.setCity(viewModel.getCity());

        patientEncounter.setPatientId(1);
        patientEncounter.setUserId(1);
        patientEncounter.setDateOfVisit(triageService.getCurrentDateTime());
        patientEncounter.setChiefComplaint(viewModel.getChiefComplaint());

        //loop through vitals



        ServiceResponse<IPatient> patientServiceResponse = triageService.createPatient(patient);
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = triageService.createPatientEncounter(patientEncounter);


//        if (response.isSuccessful()) {
//
//        }
        //return ok(femr.ui.views.html.triage.create.render());
        return redirect("/triage/save/" + patientServiceResponse.getResponseObject().getId());
    }

    public Result savedPatient(Long id){

        return ok(femr.ui.views.html.triage.save.render());
    }



}
