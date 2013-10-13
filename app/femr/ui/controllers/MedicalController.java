package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.common.models.IPatient;
import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.medical.index;
import femr.ui.views.html.medical.find;
import femr.ui.models.medical.CreateViewModel;

public class MedicalController extends Controller {

    private ISessionService sessionService;
    private ISearchService searchService;

    @Inject
    public MedicalController(ISessionService sessionService,
                             ISearchService searchService) {
        this.sessionService = sessionService;
        this.searchService = searchService;
    }

    public Result find() {
        return ok(find.render());
    }

    public Result index() {
        String s_patientID = request().getQueryString("searchId");
        int i_patientID = Integer.parseInt(s_patientID);
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(i_patientID);
        IPatient patient = patientServiceResponse.getResponseObject();
        CreateViewModel viewModel = new CreateViewModel();

        viewModel.setAge(patient.getAge());
        viewModel.setCity(patient.getCity());
        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());
        viewModel.setpID(patient.getId());
        viewModel.setSex(patient.getSex());


        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        return ok(index.render(currentUserSession,viewModel));
    }
}
