package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.common.models.*;
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
         //Get patientEncounter
        CreateEncounterViewModel viewModel = new CreateEncounterViewModel();
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findPatientEncounterById(id);
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();



        // Fetch Vitals
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
        viewModel.setRespiratoryRate(getVitalOrNull(patientEncounterVitals.get(0)));
        viewModel.setHeartRate(getVitalOrNull(patientEncounterVitals.get(1)));
        viewModel.setTemperature(getVitalOrNull(patientEncounterVitals.get(2)));
        viewModel.setOxygenSaturation(getVitalOrNull(patientEncounterVitals.get(3)));
        viewModel.setHeightFeet(getVitalOrNull(patientEncounterVitals.get(4)));
        viewModel.setHeightInches(getVitalOrNull(patientEncounterVitals.get(5)));
        viewModel.setWeight(getVitalOrNull(patientEncounterVitals.get(6)));
        viewModel.setBloodPressureSystolic(getVitalOrNull(patientEncounterVitals.get(7)));
        viewModel.setBloodPressureDiastolic(getVitalOrNull(patientEncounterVitals.get(8)));


         //Get Patient Name and other basic info
        ServiceResponse<IPatient> patientServiceResponseid= null;
        patientServiceResponseid = searchService.findPatientById(patientEncounter.getPatientId());
        if (!patientServiceResponseid.hasErrors()) {
            IPatient patient = patientServiceResponseid.getResponseObject();
            viewModel.setFirstName(patient.getFirstName());
            viewModel.setLastName(patient.getLastName());
            viewModel.setAddress(patient.getAddress());
            viewModel.setCity(patient.getCity());
            viewModel.setAge(dateUtils.calculateYears(patient.getAge()));
            viewModel.setSex(patient.getSex());
        }

        //Get treatment info


        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> patientEncounterProblemsServiceResponse = searchService.findAllTreatmentByEncounterId(id);
        List<? extends IPatientEncounterTreatmentField> patientEncounterProblems = new ArrayList<>();
        List<String> dynamicViewProblems = new ArrayList<>();

        if (patientEncounterProblemsServiceResponse.hasErrors()) {

        } else {
            patientEncounterProblems = patientEncounterProblemsServiceResponse.getResponseObject();
        }

        if (patientEncounterProblems.size() > 0) {
            for (int problem = 0; problem < patientEncounterProblems.size(); problem++) {
                dynamicViewProblems.add(patientEncounterProblems.get(problem).getTreatmentFieldValue());
            }
        }

        if(patientEncounterProblemsServiceResponse.getResponseObject() != null){
            if(patientEncounterProblemsServiceResponse.getResponseObject().get(0).getTreatmentFieldId() == 1){
                viewModel.setAssessment(patientEncounterProblemsServiceResponse.getResponseObject().get(0).getTreatmentFieldValue());
            }
        }

        return ok(showEncounter.render(currentUser, patientEncounter, viewModel));
    }

    private Float getVitalOrNull(IPatientEncounterVital patientEncounterVital) {
        if (patientEncounterVital == null)
            return null;
        else
            return patientEncounterVital.getVitalValue();
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
