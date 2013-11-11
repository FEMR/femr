package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.ui.models.search.CreateViewModel;
import femr.util.calculations.dateUtils;
import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.search.show;
import femr.ui.views.html.search.showEncounter;
import femr.ui.views.html.search.showError;
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
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        return ok(showEncounter.render(currentUser, patientEncounter));
    }

    /*
    GET - detailed patient information
        based on ID
     */
    public Result createGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        boolean error = false;

        String firstName = request().getQueryString("searchFirstName");
        String lastName = request().getQueryString("searchLastName");
        String s_id = request().getQueryString("id");
        ServiceResponse  <List<? extends IPatient>> patientServiceResponse= null;
        ServiceResponse<IPatient> patientServiceResponseid= null;
        Integer id;

        if (!StringUtils.isNullOrWhiteSpace(firstName) && !StringUtils.isNullOrWhiteSpace(lastName)) {
            firstName = firstName.trim();
            lastName = lastName.trim();
            patientServiceResponse = searchService.findPatientByName(firstName, lastName);


            if(patientServiceResponse.getResponseObject() != null){
                id = patientServiceResponse.getResponseObject().get(0).getId();  //grab 1st index
            }
            else{
                id = 2;
            }


        }
        else if (!StringUtils.isNullOrWhiteSpace(s_id)){
            s_id = s_id.trim();
            id = Integer.parseInt(s_id);
            patientServiceResponseid = searchService.findPatientById(id);
        }
        else{

            return ok(showError.render(currentUser));
        }
        if(patientServiceResponseid != null){
            if (patientServiceResponseid.hasErrors()) {
                return ok(showError.render(currentUser));
            }
        }
        ServiceResponse<List<? extends IPatientEncounter>> patientEncountersServiceResponse = searchService.findAllEncountersByPatientId(id);
        if (patientEncountersServiceResponse.hasErrors()) {

            return ok(showError.render(currentUser));
        }

        List<? extends IPatientEncounter> patientEncounters = patientEncountersServiceResponse.getResponseObject();

        CreateViewModel viewModel = new CreateViewModel();


        if(patientServiceResponse != null){
            if (!patientServiceResponse.hasErrors()) {
                IPatient patient = patientServiceResponse.getResponseObject().get(0);
                viewModel.setPatientNameResult(patientServiceResponse.getResponseObject());

                viewModel.setFirstName(patient.getFirstName());
                viewModel.setLastName(patient.getLastName());
                viewModel.setAddress(patient.getAddress());
                viewModel.setCity(patient.getCity());
                viewModel.setAge(dateUtils.calculateYears(patient.getAge()));
                viewModel.setSex(patient.getSex());
            } else {
                return ok(showError.render(currentUser));
            }

        }
        else{
            if (!patientServiceResponseid.hasErrors()) {
                IPatient patient = patientServiceResponseid.getResponseObject();
                viewModel.setFirstName(patient.getFirstName());
                viewModel.setLastName(patient.getLastName());
                viewModel.setAddress(patient.getAddress());
                viewModel.setCity(patient.getCity());
                viewModel.setAge(dateUtils.calculateYears(patient.getAge()));
                viewModel.setSex(patient.getSex());
                viewModel.setUserID(patient.getId());
            } else {
                return ok(showError.render(currentUser));
            }
        }

        return ok(show.render(currentUser, error, viewModel, patientEncounters, id));
    }
}
