package femr.ui.controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.*;
import femr.common.models.*;
import femr.ui.helpers.controller.EncounterHelper;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.search.CreateEncounterViewModel;
import femr.ui.models.search.CreateViewModel;
import femr.ui.views.html.pharmacies.index;
import femr.util.DataStructure.VitalMultiMap;
import femr.util.calculations.dateUtils;

import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.search.show;
import femr.ui.views.html.search.showEncounter;
import femr.ui.views.html.search.showError;
import femr.util.stringhelpers.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// include the view model for medical
import femr.ui.models.medical.CreateViewModelGet;
import play.mvc.Security;
import views.html.defaultpages.error;

// include the view model for pharmacy
//import femr.ui.models.pharmacy.CreateViewModelGet;
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class SearchController extends Controller {
    private ISessionService sessionService;
    private ISearchService searchService;
    private IMedicalService medicalService;
    private IPharmacyService pharmacyService;
    private Provider<IPatientPrescription> patientPrescriptionProvider;
    private EncounterHelper encounterHelper;
    private IPhotoService photoService;


    @Inject
    public SearchController(ISessionService sessionService,
                            ISearchService searchService,
                            IMedicalService medicalService,
                            IPharmacyService pharmacyService,
                            Provider<IPatientPrescription> patientPrescriptionProvider,
                            EncounterHelper encounterHelper,
                            IPhotoService photoService) {
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.medicalService = medicalService;
        this.encounterHelper = encounterHelper;
        this.pharmacyService = pharmacyService;
        this.patientPrescriptionProvider = patientPrescriptionProvider;
        this.photoService = photoService;

    }

    /*
    GET - specific encounter details based on encounter id.
    Not yet implemented.
     */
    public Result viewEncounter(int id) {
        //Get patientEncounter
        //CreateEncounterViewModel viewModel = new CreateEncounterViewModel();
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ServiceResponse<IPatientEncounter> patientEncounterServiceResponse = searchService.findPatientEncounterById(id);
        IPatientEncounter patientEncounter = patientEncounterServiceResponse.getResponseObject();

        //Get Patient Name and other basic info
        ServiceResponse<IPatient> patientServiceResponseid = null;
        patientServiceResponseid = searchService.findPatientById(patientEncounter.getPatientId());
        IPatient patient = patientServiceResponseid.getResponseObject();

        // Add the Medical data
        //get a list of prescriptions
        ServiceResponse<List<? extends IPatientPrescription>> patientPrescriptionsServiceResponse = searchService.findPrescriptionsByEncounterId(patientEncounter.getId());
        List<? extends IPatientPrescription> patientPrescriptions = new ArrayList<>();
        if (patientPrescriptionsServiceResponse.hasErrors()) {
            //do nothing, there might not always be available prescriptions
        } else {
            patientPrescriptions = patientPrescriptionsServiceResponse.getResponseObject();
        }

        //region **Mapping of treatment fields, HPI, PMH, and vitals**

        //Create linked hash map of treatment fields
        //get a list of available treatment fields
        ServiceResponse<List<? extends ITreatmentField>> treatmentFieldsServiceResponse = searchService.findAllTreatmentFields();
        List<? extends ITreatmentField> treatmentFields = treatmentFieldsServiceResponse.getResponseObject();

        ServiceResponse<Map<String, List<? extends IPatientEncounterTreatmentField>>> patientEncounterTreatmentMapResponse = medicalService.findTreatmentFieldsByEncounterId(patientEncounter.getId());
        Map<String, List<? extends IPatientEncounterTreatmentField>> patientEncounterTreatmentMap = patientEncounterTreatmentMapResponse.getResponseObject();

        ServiceResponse<Map<String, List<? extends IPatientEncounterHpiField>>> patientEncounterHpiMapResponse = medicalService.findHpiFieldsByEncounterId(patientEncounter.getId());
        Map<String, List<? extends IPatientEncounterHpiField>> patientEncounterHpiMap = patientEncounterHpiMapResponse.getResponseObject();

        ServiceResponse<Map<String, List<? extends IPatientEncounterPmhField>>> patientEncounterPmhMapResponse = medicalService.findPmhFieldsByEncounterId(patientEncounter.getId());
        Map<String, List<? extends IPatientEncounterPmhField>> patientEncounterPmhMap = patientEncounterPmhMapResponse.getResponseObject();


        ServiceResponse<VitalMultiMap> vitalMapServiceResponse = searchService.getVitalMultiMap(patientEncounter.getId());
        VitalMultiMap patientEncounterVitalMap = vitalMapServiceResponse.getResponseObject();


        //endregion

        ServiceResponse<List<IPhoto>> photoListSr =  photoService.GetEncounterPhotos(id);
        List<IPhoto> photoLst = null;
        if(!photoListSr.hasErrors())
            photoLst = photoListSr.getResponseObject();

        CreateEncounterViewModel viewModel = encounterHelper.populateViewModelGet(patient, patientEncounter,
                                patientPrescriptions, patientEncounterVitalMap, patientEncounterTreatmentMap,
                                patientEncounterHpiMap, patientEncounterPmhMap, photoLst);







        return ok(showEncounter.render(currentUser, patientEncounter, viewModel));   // this is where the responce is returned to the encounter page
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
        ServiceResponse<List<? extends IPatient>> patientServiceResponse = null;
        ServiceResponse<IPatient> patientServiceResponseid = null;
        List<String> photoURIs; //List to store all generated photo URIs.  Will pass into viewmodel object

        Integer id;

        if (!StringUtils.isNullOrWhiteSpace(s_id)) {
            s_id = s_id.trim();
            id = Integer.parseInt(s_id);
            patientServiceResponseid = searchService.findPatientById(id);
        } else if (!StringUtils.isNullOrWhiteSpace(firstName) && StringUtils.isNullOrWhiteSpace(lastName) || !StringUtils.isNullOrWhiteSpace(lastName) && StringUtils.isNullOrWhiteSpace(firstName) || !StringUtils.isNullOrWhiteSpace(firstName) && !StringUtils.isNullOrWhiteSpace(lastName)) {
            firstName = firstName.trim();
            lastName = lastName.trim();
            patientServiceResponse = searchService.findPatientByName(firstName, lastName);
            if (patientServiceResponse.getResponseObject() != null) {
                id = patientServiceResponse.getResponseObject().get(0).getId();  //grab 1st index
            } else {
                id = 0;
            }
        } else {

            return ok(showError.render(currentUser));
        }
        if (patientServiceResponseid != null) {
            if (patientServiceResponseid.hasErrors()) {
                return ok(showError.render(currentUser));
            }
        }
        ServiceResponse<List<? extends IPatientEncounter>> patientEncountersServiceResponse = searchService.findAllEncountersByPatientId(id);
        if (patientEncountersServiceResponse.hasErrors()) {

            return ok(showError.render(currentUser));
        }

        photoURIs = new ArrayList<String>();
        if(patientServiceResponse != null)
            if(patientServiceResponse.getResponseObject() != null)
                for(int i = 0; i < patientServiceResponse.getResponseObject().size(); i++)
                    photoURIs.add(routes.PhotoController.GetPatientPhoto(patientServiceResponse.getResponseObject().get(i).getId(), true).toString());


        List<? extends IPatientEncounter> patientEncounters = patientEncountersServiceResponse.getResponseObject();
        CreateViewModel viewModel = new CreateViewModel();
        if (patientServiceResponse != null) {
            if (!patientServiceResponse.hasErrors()) {
                IPatient patient = patientServiceResponse.getResponseObject().get(0);
                viewModel.setPatientNameResult(patientServiceResponse.getResponseObject());
                viewModel.setFirstName(patient.getFirstName());
                viewModel.setLastName(patient.getLastName());
                viewModel.setAddress(patient.getAddress());
                viewModel.setCity(patient.getCity());
                viewModel.setAge(dateUtils.getAge(patient.getAge()));
                viewModel.setSex(patient.getSex());
                viewModel.setPhotoURIList(photoURIs);
            } else {
                return ok(showError.render(currentUser));
            }

        } else {
            if (!patientServiceResponseid.hasErrors()) {
                IPatient patient = patientServiceResponseid.getResponseObject();
                viewModel.setFirstName(patient.getFirstName());
                viewModel.setLastName(patient.getLastName());
                viewModel.setAddress(patient.getAddress());
                viewModel.setCity(patient.getCity());
                viewModel.setAge(dateUtils.getAge(patient.getAge()));
                viewModel.setSex(patient.getSex());
                viewModel.setPatientID(patient.getId());
                viewModel.setPhotoURIList(photoURIs);
            } else {
                return ok(showError.render(currentUser));
            }
        }

        return ok(show.render(currentUser, error, viewModel, patientEncounters, id));
    }
}
