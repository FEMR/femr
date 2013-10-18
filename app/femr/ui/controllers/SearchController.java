package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.ui.models.search.CreateViewModel;
import femr.ui.models.search.CreateViewModelPost;
import femr.util.dependencyinjection.providers.PatientEncounterProvider;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.search.show;
import femr.ui.views.html.search.showEncounter;

import java.util.ArrayList;
import java.util.List;

public class SearchController extends Controller {
    private final Form<CreateViewModelPost> createViewModelPostForm = Form.form(CreateViewModelPost.class);
    private ISessionService sessionService;
    private ISearchService searchService;

    @Inject
    public SearchController(ISessionService sessionService,
                            ISearchService searchService) {
        this.sessionService = sessionService;
        this.searchService = searchService;
    }


    /*
    Create POST for going back to triage to create a new encounter
    Should be done in Triage controller?
     */
    public Result createNewEncounterPost(int id) {
        return redirect("/triage/" + id);
    }

    /*
    Post from the search page should be finding and displaying an encounter
     */

    public Result viewEncounter(int id) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findPatientEncounterById(id);
        IPatientEncounter patientEncounter= patientEncounterServiceResponse.getResponseObject();


        return ok(showEncounter.render(currentUser, patientEncounter));

    }

    /*
    Handles search POST requests from anywhere
    Currently cannot handle more than one person
    with the same name.
     */
    public Result createPost() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        CreateViewModelPost createViewModelPost = createViewModelPostForm.bindFromRequest().get();

        ServiceResponse<IPatient> patientServiceResponseId = new ServiceResponse<>();
        if (createViewModelPost.getSearchId() == null) {
            patientServiceResponseId.addError("ID","ID not entered");
        } else {
            patientServiceResponseId =
                    searchService.findPatientById(createViewModelPost.getSearchId());
        }

        ServiceResponse<IPatient> patientServiceResponseName =
                searchService.findPatientByName(createViewModelPost.getSearchFirstName(), createViewModelPost.getSearchLastName());

        if (!patientServiceResponseId.hasErrors()) {
            return redirect("show/" + patientServiceResponseId.getResponseObject().getId());
        } else if (!patientServiceResponseName.hasErrors()) {
            return redirect("show/" + patientServiceResponseName.getResponseObject().getId());
        } else
            return redirect("triage");
    }

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
            viewModel.setSex(patient.getSex());         //awwww yeahhhh!
        } else {
            //fail?
        }

        return ok(show.render(currentUser, viewModel, patientEncounters, id));
    }

}
