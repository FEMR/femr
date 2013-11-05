package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IPharmacyService;
import femr.business.services.ISearchService;
import femr.business.services.ISessionService;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.ui.views.html.pharmacies.index;
import femr.ui.views.html.pharmacies.populated;
import femr.util.calculations.dateUtils;
import femr.common.models.IPatientPrescription;
import femr.ui.models.pharmacy.CreateViewModelGet;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class PharmaciesController extends Controller {
    private ISessionService sessionService;
    private ISearchService searchService;
    private ITriageService triageService;
    private IPharmacyService pharmacyService;

    private final Form<CreateViewModelGet> createViewModelForm = Form.form(CreateViewModelGet.class);


    @Inject
    public PharmaciesController(IPharmacyService pharmacyService,
                                ITriageService triageService,
                                ISessionService sessionService,
                                ISearchService searchService) {
        this.pharmacyService = pharmacyService;
        this.triageService = triageService;
        this.sessionService = sessionService;
        this.searchService = searchService;
    }

    public Result index() {

        boolean error = false;
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(index.render(currentUserSession, error));
    }

    public Result createGet(){

        String s_id = request().getQueryString("id");
        Integer id = Integer.parseInt(s_id);

        Boolean error = false;

        CreateViewModelGet viewModel = new CreateViewModelGet();
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);
        if (patientServiceResponse.hasErrors()){
            error = true;
            return ok(index.render(currentUserSession,error));
        }
        IPatient patient = patientServiceResponse.getResponseObject();
        //getting the general patient information needed to display
        viewModel.setpID(patient.getId());
        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());
        viewModel.setAge(dateUtils.calculateYears(patient.getAge()));
        viewModel.setSex(patient.getSex());


        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(id);
        if (patientEncounterServiceResponse.hasErrors()){
            error = true;
            return ok(index.render(currentUserSession,error));
        }
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();
        //getting the patient encounter information needed to display
        viewModel.setWeeksPregnant(patientEncounter.getWeeksPregnant());


        List<? extends IPatientPrescription> patientPrescriptions = searchService.findPrescriptionsByEncounterId(patientEncounter.getId());

        int numberOfPossiblePrescriptions = 5;
        int numberOfFilledPrescriptions = patientPrescriptions.size();
        String[] viewMedications = new String[numberOfPossiblePrescriptions];
        for (int filledPrescription = 0; filledPrescription < numberOfFilledPrescriptions; filledPrescription++){
            viewMedications[filledPrescription] = patientPrescriptions.get(filledPrescription).getMedicationName();
        }
        for (int unfilledPrescription = numberOfFilledPrescriptions; unfilledPrescription < numberOfPossiblePrescriptions; unfilledPrescription++){
            viewMedications[unfilledPrescription] = null;
        }
        viewModel.setMedications(viewMedications);

        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse;
        //getting the patient vital information needed to display
        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(5,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setHeightFeet(null);
        else
            //height in feet
            viewModel.setHeightFeet(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(6,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setHeightinches(null);
        else
            //height for inches
            viewModel.setHeightinches(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(7,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setWeight(null);
        else
            viewModel.setWeight(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

            return ok(populated.render(currentUserSession, viewModel, error));

    }

}
