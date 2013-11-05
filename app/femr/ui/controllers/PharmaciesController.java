package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.*;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.ui.views.html.pharmacies.index;
import femr.ui.views.html.pharmacies.populated;
import femr.util.calculations.dateUtils;
import femr.common.models.IPatientPrescription;
import femr.ui.models.pharmacy.CreateViewModelGet;
import femr.ui.models.pharmacy.CreateViewModelPost;
import femr.util.dependencyinjection.providers.PatientPrescriptionProvider;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class PharmaciesController extends Controller {
    private final Form<CreateViewModelPost> createViewModelPostForm = Form.form(CreateViewModelPost.class);
    private Provider<IPatientPrescription> patientPrescriptionProvider;
    private ISessionService sessionService;
    private ISearchService searchService;
    private ITriageService triageService;
    private IPharmacyService pharmacyService;
    private IMedicalService medicalService;

    private final Form<CreateViewModelGet> createViewModelForm = Form.form(CreateViewModelGet.class);


    @Inject
    public PharmaciesController(IPharmacyService pharmacyService,
                                ITriageService triageService,
                                ISessionService sessionService,
                                ISearchService searchService,
                                IMedicalService medicalService,
                                Provider<IPatientPrescription> patientPrescriptionProvider) {
        this.pharmacyService = pharmacyService;
        this.triageService = triageService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.medicalService = medicalService;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
    }

    public Result index() {

        boolean error = false;
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(index.render(currentUserSession, error));
    }

    public Result createGet(){
        //get from query parameters
        String s_id = request().getQueryString("id");
        Integer id = Integer.parseInt(s_id);
        CreateViewModelGet viewModel = new CreateViewModelGet();
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        Boolean error = false;

        //return to index if error finding a patient
        ServiceResponse<IPatient> patientServiceResponse = searchService.findPatientById(id);
        if (patientServiceResponse.hasErrors()){
            error = true;
            return ok(index.render(currentUserSession,error));
        }

        IPatient patient = patientServiceResponse.getResponseObject();
        viewModel.setpID(patient.getId());
        viewModel.setFirstName(patient.getFirstName());
        viewModel.setLastName(patient.getLastName());
        viewModel.setAge(dateUtils.calculateYears(patient.getAge()));
        viewModel.setSex(patient.getSex());

        //return to index if error finding a patient encounter
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(id);
        if (patientEncounterServiceResponse.hasErrors()){
            error = true;
            return ok(index.render(currentUserSession,error));
        }

        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();
        viewModel.setWeeksPregnant(patientEncounter.getWeeksPregnant());

        //set viewModel field to null if patient vital does not exist
        ServiceResponse<IPatientEncounterVital> patientEncounterVitalServiceResponse;
        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(5,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setHeightFeet(null);
        else
            viewModel.setHeightFeet(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(6,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setHeightinches(null);
        else
            viewModel.setHeightinches(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());

        patientEncounterVitalServiceResponse = searchService.findPatientEncounterVitalByVitalIdAndEncounterId(7,patientEncounter.getId());
        if (patientEncounterVitalServiceResponse.hasErrors())
            viewModel.setWeight(null);
        else
            viewModel.setWeight(patientEncounterVitalServiceResponse.getResponseObject().getVitalValue());


        //find patient prescriptions
        List<? extends IPatientPrescription> patientPrescriptions = searchService.findPrescriptionsByEncounterId(patientEncounter.getId());





        int numberOfPrescriptions = patientPrescriptions.size();
        int POSSIBLE_PRESCRIPTIONS = 5;
        String[] viewMedications = new String[POSSIBLE_PRESCRIPTIONS];
        int numberOfFilledPrescriptions = 0;



        for (int filledPrescription = 0; filledPrescription < numberOfPrescriptions; filledPrescription++){
            if (patientPrescriptions.get(filledPrescription).getReplaced() != true){
                viewMedications[numberOfFilledPrescriptions] = patientPrescriptions.get(filledPrescription).getMedicationName();
                numberOfFilledPrescriptions++;
            }
        }

        viewModel.setMedications(viewMedications);

        return ok(populated.render(currentUserSession, viewModel, error));

    }

    public Result createPost(int id){
        CreateViewModelPost createViewModelPost = createViewModelPostForm.bindFromRequest().get();
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findCurrentEncounterByPatientId(id);
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();



        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication1())){
            IPatientPrescription newPatientPrescription = patientPrescriptionProvider.get();
            newPatientPrescription.setEncounterId(patientEncounter.getId());
            newPatientPrescription.setUserId(currentUserSession.getId());
            newPatientPrescription.setReplaced(false);
            newPatientPrescription.setReplacementId(null);
            newPatientPrescription.setMedicationName(createViewModelPost.getReplacementMedication1());
            ServiceResponse<IPatientPrescription> newPatientPrescriptionServiceResponse = medicalService.createPatientPrescription(newPatientPrescription);

            ServiceResponse<IPatientPrescription> oldPatientPrescriptionServiceResponse =
                    pharmacyService.findPatientPrescriptionByEncounterIdAndPrescriptionName(patientEncounter.getId(),createViewModelPost.getPrescription1());
            IPatientPrescription oldPatientPrescription = oldPatientPrescriptionServiceResponse.getResponseObject();
            oldPatientPrescription.setReplaced(true);
            oldPatientPrescription.setReplacementId(newPatientPrescriptionServiceResponse.getResponseObject().getId());
            ServiceResponse<IPatientPrescription> updatedOldPatientPrescription = pharmacyService.updatePatientPrescription(oldPatientPrescription);
        }
        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication2())){
            IPatientPrescription newPatientPrescription = patientPrescriptionProvider.get();
            newPatientPrescription.setEncounterId(patientEncounter.getId());
            newPatientPrescription.setUserId(currentUserSession.getId());
            newPatientPrescription.setReplaced(false);
            newPatientPrescription.setReplacementId(null);
            newPatientPrescription.setMedicationName(createViewModelPost.getReplacementMedication2());
            ServiceResponse<IPatientPrescription> newPatientPrescriptionServiceResponse = medicalService.createPatientPrescription(newPatientPrescription);

            ServiceResponse<IPatientPrescription> oldPatientPrescriptionServiceResponse =
                    pharmacyService.findPatientPrescriptionByEncounterIdAndPrescriptionName(patientEncounter.getId(),createViewModelPost.getPrescription2());
            IPatientPrescription oldPatientPrescription = oldPatientPrescriptionServiceResponse.getResponseObject();
            oldPatientPrescription.setReplaced(true);
            oldPatientPrescription.setReplacementId(newPatientPrescriptionServiceResponse.getResponseObject().getId());
            ServiceResponse<IPatientPrescription> updatedOldPatientPrescription = pharmacyService.updatePatientPrescription(oldPatientPrescription);
        }

        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication3())){
            IPatientPrescription newPatientPrescription = patientPrescriptionProvider.get();
            newPatientPrescription.setEncounterId(patientEncounter.getId());
            newPatientPrescription.setUserId(currentUserSession.getId());
            newPatientPrescription.setReplaced(false);
            newPatientPrescription.setReplacementId(null);
            newPatientPrescription.setMedicationName(createViewModelPost.getReplacementMedication3());
            ServiceResponse<IPatientPrescription> newPatientPrescriptionServiceResponse = medicalService.createPatientPrescription(newPatientPrescription);

            ServiceResponse<IPatientPrescription> oldPatientPrescriptionServiceResponse =
                    pharmacyService.findPatientPrescriptionByEncounterIdAndPrescriptionName(patientEncounter.getId(),createViewModelPost.getPrescription3());
            IPatientPrescription oldPatientPrescription = oldPatientPrescriptionServiceResponse.getResponseObject();
            oldPatientPrescription.setReplaced(true);
            oldPatientPrescription.setReplacementId(newPatientPrescriptionServiceResponse.getResponseObject().getId());
            ServiceResponse<IPatientPrescription> updatedOldPatientPrescription = pharmacyService.updatePatientPrescription(oldPatientPrescription);
        }

        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication4())){
            IPatientPrescription newPatientPrescription = patientPrescriptionProvider.get();
            newPatientPrescription.setEncounterId(patientEncounter.getId());
            newPatientPrescription.setUserId(currentUserSession.getId());
            newPatientPrescription.setReplaced(false);
            newPatientPrescription.setReplacementId(null);
            newPatientPrescription.setMedicationName(createViewModelPost.getReplacementMedication4());
            ServiceResponse<IPatientPrescription> newPatientPrescriptionServiceResponse = medicalService.createPatientPrescription(newPatientPrescription);

            ServiceResponse<IPatientPrescription> oldPatientPrescriptionServiceResponse =
                    pharmacyService.findPatientPrescriptionByEncounterIdAndPrescriptionName(patientEncounter.getId(),createViewModelPost.getPrescription4());
            IPatientPrescription oldPatientPrescription = oldPatientPrescriptionServiceResponse.getResponseObject();
            oldPatientPrescription.setReplaced(true);
            oldPatientPrescription.setReplacementId(newPatientPrescriptionServiceResponse.getResponseObject().getId());
            ServiceResponse<IPatientPrescription> updatedOldPatientPrescription = pharmacyService.updatePatientPrescription(oldPatientPrescription);
        }

        if (StringUtils.isNotNullOrWhiteSpace(createViewModelPost.getReplacementMedication5())){
            IPatientPrescription newPatientPrescription = patientPrescriptionProvider.get();
            newPatientPrescription.setEncounterId(patientEncounter.getId());
            newPatientPrescription.setUserId(currentUserSession.getId());
            newPatientPrescription.setReplaced(false);
            newPatientPrescription.setReplacementId(null);
            newPatientPrescription.setMedicationName(createViewModelPost.getReplacementMedication5());
            ServiceResponse<IPatientPrescription> newPatientPrescriptionServiceResponse = medicalService.createPatientPrescription(newPatientPrescription);

            ServiceResponse<IPatientPrescription> oldPatientPrescriptionServiceResponse =
                    pharmacyService.findPatientPrescriptionByEncounterIdAndPrescriptionName(patientEncounter.getId(),createViewModelPost.getPrescription5());
            IPatientPrescription oldPatientPrescription = oldPatientPrescriptionServiceResponse.getResponseObject();
            oldPatientPrescription.setReplaced(true);
            oldPatientPrescription.setReplacementId(newPatientPrescriptionServiceResponse.getResponseObject().getId());
            ServiceResponse<IPatientPrescription> updatedOldPatientPrescription = pharmacyService.updatePatientPrescription(oldPatientPrescription);
        }




        return createGet();

    }

}
