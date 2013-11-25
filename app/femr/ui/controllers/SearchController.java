package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IVital;
import femr.ui.models.search.CreateEncounterViewModel;
import femr.ui.models.search.CreateViewModel;
import femr.util.calculations.dateUtils;
import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.search.show;
import femr.ui.views.html.search.showEncounter;
import femr.ui.views.html.search.showError;
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
    GET - specific encounter details based on encounter id.
    Not yet implemented.
     */
    public Result viewEncounter(int id) {

        CreateEncounterViewModel viewModel = new CreateEncounterViewModel();
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findPatientEncounterById(id);
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();



        //
        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse= null;
        List<IPatientEncounterVital> patientEncounterVitals = new ArrayList<>();

        for (int vital = 1; vital <= 9; vital++) {
            patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(vital, id);
            if (patientEncounterVitalServiceResponse.hasErrors()) {
                patientEncounterVitals.add(null);
            }
            else{
                patientEncounterVitals.add(patientEncounterVitalServiceResponse.getResponseObject());
            }
        }
        //viewModel.setPatientVitalResult(patientEncounterVitals);

        //also grab prescriptions and problems from search service, maybe make new model for all of this


        return ok(showEncounter.render(currentUser, patientEncounter, viewModel));
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

        if (!StringUtils.isNullOrWhiteSpace(s_id)){
            s_id = s_id.trim();
            id = Integer.parseInt(s_id);
            patientServiceResponseid = searchService.findPatientById(id);
        }
       else if (!StringUtils.isNullOrWhiteSpace(firstName) && StringUtils.isNullOrWhiteSpace(lastName) || !StringUtils.isNullOrWhiteSpace(lastName) && StringUtils.isNullOrWhiteSpace(firstName) ||!StringUtils.isNullOrWhiteSpace(firstName) && !StringUtils.isNullOrWhiteSpace(lastName)) {
            firstName = firstName.trim();
            lastName = lastName.trim();
            patientServiceResponse = searchService.findPatientByName(firstName, lastName);
            if(patientServiceResponse.getResponseObject() != null){
                id = patientServiceResponse.getResponseObject().get(0).getId();  //grab 1st index
            }
            else{
                id = 0;
            }
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
