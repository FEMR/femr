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
import femr.ui.views.html.search.show;
import femr.ui.views.html.search.showEncounter;
import femr.util.stringhelpers.StringUtils;
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

    /*
    GET - specific encounter details based on encounter id.
    Not yet implemented.
     */
    public Result viewEncounter(int id) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findPatientEncounterById(id);
        IPatientEncounter patientEncounter= patientEncounterServiceResponse.getResponseObject();

        return ok(showEncounter.render(currentUser, patientEncounter));

    }

    /*
    GET - detailed patient information
     */
    public Result createGet(int id) {
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);

        CurrentUser currentUser = sessionService.getCurrentUserSession();

        List<? extends IPatientEncounter> patientEncounters = searchService.findAllEncountersByPatientId(id);

        CreateViewModel viewModel = new CreateViewModel();

        if (!patientServiceResponse.hasErrors()) {
            IPatient patient = patientServiceResponse.getResponseObject();
            viewModel.setFirstName(patient.getFirstName());
            viewModel.setLastName(patient.getLastName());
            viewModel.setAddress(patient.getAddress());
            viewModel.setCity(patient.getCity());
            viewModel.setAge(patient.getAge());
            viewModel.setSex(patient.getSex());
        } else {
            return redirect("/triage");
        }

        return ok(show.render(currentUser, viewModel, patientEncounters, id));
    }

    /*
    GET - Acquire URL query parameters for a patient
      and createGet() if valid
     */
    public Result performSearch() {
        String firstName = request().queryString().get("searchFirstName")[0];
        String lastName = request().queryString().get("searchLastName")[0];

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientByName(firstName,lastName);

        if (patientServiceResponse.hasErrors()){
            return redirect("/triage");
        }
        else{
            return createGet(patientServiceResponse.getResponseObject().getId());
        }
    }
}
