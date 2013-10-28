package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.medical.index;
import femr.ui.views.html.medical.indexPopulated;
import femr.ui.models.medical.CreateViewModel;

import java.util.ArrayList;
import java.util.List;

public class MedicalController extends Controller {

    private ISessionService sessionService;
    private ISearchService searchService;

    @Inject
    public MedicalController(ISessionService sessionService,
                             ISearchService searchService) {
        this.sessionService = sessionService;
        this.searchService = searchService;

    }

    public Result createGet() {

        boolean error = false;

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        return ok(index.render(currentUserSession,error));
    }

    public Result createPopulatedGet(){
        boolean error = false;

        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        CreateViewModel viewModel = new CreateViewModel();

        String s_patientID = request().getQueryString("id");
        int i_patientID = Integer.parseInt(s_patientID);

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(i_patientID);
        if (patientServiceResponse.hasErrors()){
            error = true;
            return ok(index.render(currentUserSession,error));
        }
        IPatient patient = patientServiceResponse.getResponseObject();
        viewModel.setpID(patient.getId());
        viewModel.setCity(patient.getCity());
        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());
        viewModel.setAge(patient.getAge());
        viewModel.setSex(patient.getSex());

        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse =
                searchService.findCurrentEncounterByPatientId(i_patientID);
        if (patientEncounterServiceResponse.hasErrors()){
            error = true;
            return ok(index.render(currentUserSession,error));
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();
        viewModel.setChiefComplaint(patientEncounter.getChiefComplaint());
        viewModel.setWeeksPregnant(patientEncounter.getWeeksPregnant());

        //populating this viewModel with vitals is
        //(unfortunately and temporarily) dependant
        // on the order of vitals in the database

        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse;

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(1,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setRespiratoryRate(null);
        else
            viewModel.setRespiratoryRate(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(2,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setHeartRate(null);
        else
            viewModel.setHeartRate(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(3,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setTemperature(null);
        else
            viewModel.setTemperature(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(4,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setOxygenSaturation(null);
        else
            viewModel.setOxygenSaturation(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(5,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setHeightFeet(null);
        else
            viewModel.setHeightFeet(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(6,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setHeightInches(null);
        else
            viewModel.setHeightInches(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(7,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setWeight(null);
        else
            viewModel.setWeight(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(8,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setBloodPressureSystolic(null);
        else
            viewModel.setBloodPressureSystolic(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(9,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setBloodPressureDiastolic(null);
        else
            viewModel.setBloodPressureDiastolic(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        return ok(indexPopulated.render(currentUserSession,viewModel));
    }
}
