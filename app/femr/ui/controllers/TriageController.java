package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.data.models.Patient;
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
        patient.setFirstName(viewModel.getFirstName());
        patient.setLastName(viewModel.getLastName());
        triageService.createPatient(patient);
        return ok(femr.ui.views.html.triage.create.render());
    }


}
