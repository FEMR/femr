package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.ui.models.search.CreateViewModel;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class SearchController extends Controller {
    private ISessionService sessionService;
    private ISearchService searchService;

    @Inject
    public SearchController(ISessionService sessionService,
                            ISearchService searchService) {
        this.sessionService = sessionService;
        this.searchService = searchService;
    }

    public Result createGet(int id) {
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);
        ServiceResponse<CurrentUser> currentUserSession = sessionService.getCurrentUserSession();
        List<? extends IPatientEncounter> patientEncounters = searchService.findAllEncounters();
        CreateViewModel viewModel = new CreateViewModel();


        if (patientServiceResponse.isSuccessful()) {
            IPatient patient = patientServiceResponse.getResponseObject();
            viewModel.setFirstName(patient.getFirstName());
            viewModel.setLastName(patient.getLastName());
            viewModel.setAddress(patient.getAddress());
            viewModel.setCity(patient.getCity());
            viewModel.setAge(patient.getAge());
            viewModel.setSex(patient.getSex());         //awwww yeahhhh!
        }
        else{
            //fail?
        }

        if (currentUserSession.isSuccessful()) {
            return ok(femr.ui.views.html.search.show.render(currentUserSession.getResponseObject(), viewModel, patientEncounters, id));
        } else {
            return ok(femr.ui.views.html.search.show.render(null, viewModel, patientEncounters, id));
        }
    }
}
