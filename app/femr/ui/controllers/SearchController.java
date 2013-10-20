package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IVital;
import femr.ui.models.search.CreateViewModel;
import femr.util.dependencyinjection.providers.PatientEncounterProvider;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.search.show;
import femr.ui.views.html.search.showEncounter;
import femr.util.stringhelpers.StringUtils;

import java.util.ArrayList;
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
    GET - specific encounter details based on encounter id
     */
    public Result viewEncounter(int id) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findPatientEncounterById(id);
        IPatientEncounter patientEncounter= patientEncounterServiceResponse.getResponseObject();

        return ok(showEncounter.render(currentUser, patientEncounter));

    }

    public Result createGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        String firstName = request().queryString().get("searchFirstName")[0];
        String lastName = request().queryString().get("searchLastName")[0];
        String s_id = request().queryString().get("searchId")[0];

        Integer id = getIdFromSearch(s_id,firstName,lastName);
        if (id == null)
            return redirect("triage");

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);

        IPatient patient = patientServiceResponse.getResponseObject();

        List<? extends IPatientEncounter> patientEncounters = searchService.findAllEncountersByPatientId(patient.getId());

        CreateViewModel viewModel = new CreateViewModel();
        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());
        viewModel.setAddress(patient.getAddress());
        viewModel.setCity(patient.getCity());
        viewModel.setAge(patient.getAge());
        viewModel.setSex(patient.getSex());

        return ok(show.render(currentUser, viewModel, patientEncounters, patient.getId()));
    }

    public Result createGetId(int id) {
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
            //fail?
        }

        return ok(show.render(currentUser, viewModel, patientEncounters, id));
    }



    private Integer getIdFromSearch(String s_id, String firstName, String lastName){
        Integer id;
        if (StringUtils.isNullOrWhiteSpace(s_id)){
            ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientByName(firstName,lastName);
            if (patientServiceResponse.hasErrors()){
                id = null;
            }
            else{
                id = patientServiceResponse.getResponseObject().getId();
            }
        }
        else{
            id = Integer.parseInt(s_id);
        }

        return id;
    }

}
