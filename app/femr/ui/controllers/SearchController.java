package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.*;
import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;
import femr.common.models.*;
import femr.data.models.*;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.search.EncounterViewModel;
import femr.ui.models.search.CreateViewModel;
import femr.util.DataStructure.Pair;
import femr.ui.views.html.search.indexEncounter;
import femr.ui.views.html.search.indexPatient;
import femr.util.DataStructure.VitalMultiMap;
import femr.util.calculations.dateUtils;
import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.search.showError;
import femr.util.stringhelpers.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import play.mvc.Security;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class SearchController extends Controller {
    private ISessionService sessionService;
    private ISearchService searchService;
    private IMedicalService medicalService;
    private IPhotoService photoService;

    @Inject
    public SearchController(ISessionService sessionService,
                            ISearchService searchService,
                            IMedicalService medicalService,
                            IPhotoService photoService) {
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.medicalService = medicalService;
        this.photoService = photoService;
    }

    public Result indexPatientGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        boolean error = false;
        String firstName = request().getQueryString("searchFirstName");
        String lastName = request().getQueryString("searchLastName");
        String s_id = request().getQueryString("id");

        Integer patientId = null;
        if (StringUtils.isNotNullOrWhiteSpace(s_id)) {
            patientId = Integer.parseInt(s_id);
        }

        ServiceResponse<List<PatientItem>> patientResponse = searchService.getPatientsFromQueryString(firstName, lastName, patientId);
        if (patientResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<PatientItem> patientItems = patientResponse.getResponseObject();
        if (patientItems == null || patientItems.size() < 1) {
            return ok(showError.render(currentUser));
        }

        ServiceResponse<List<PatientEncounterItem>> patientEncountersServiceResponse = searchService.findPatientEncounterItemsById(patientItems.get(0).getId());
        if (patientEncountersServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<PatientEncounterItem> patientEncounterItems = patientEncountersServiceResponse.getResponseObject();
        if (patientEncounterItems == null || patientEncounterItems.size() < 1) {
            return ok(showError.render(currentUser));
        }

        CreateViewModel viewModel = new CreateViewModel();

        //list of patients
        if (patientItems.size() > 1) {
            List<String> photoURIs; //List to store all generated photo URIs.  Will pass into viewmodel object
            photoURIs = new ArrayList<>();
            for (int i = 0; i < patientItems.size(); i++)
                photoURIs.add(routes.PhotoController.GetPatientPhoto(patientItems.get(i).getId(), true).toString());
            PatientItem patient = patientItems.get(0);
            viewModel.setPatientNameResult(patientItems);
            viewModel.setFirstName(patient.getFirstName());
            viewModel.setLastName(patient.getLastName());
            viewModel.setAddress(patient.getAddress());
            viewModel.setCity(patient.getCity());
            viewModel.setAge(dateUtils.getAge(patient.getBirth()));
            viewModel.setSex(patient.getSex());
            viewModel.setPhotoURIList(photoURIs);
        } else { //one patient
            PatientItem patient = patientItems.get(0);
            viewModel.setFirstName(patient.getFirstName());
            viewModel.setLastName(patient.getLastName());
            viewModel.setAddress(patient.getAddress());
            viewModel.setCity(patient.getCity());
            viewModel.setAge(dateUtils.getAge(patient.getBirth()));
            viewModel.setSex(patient.getSex());
            viewModel.setPatientID(patient.getId());
        }

        return ok(indexPatient.render(currentUser, error, viewModel, patientEncounterItems, patientItems.get(0).getId()));
    }

    public Result indexEncounterGet(int id) {

        EncounterViewModel viewModel = new EncounterViewModel();
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = searchService.findPatientEncounterItemById(id);
        if (patientEncounterServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounter = patientEncounterServiceResponse.getResponseObject();
        viewModel.setPatientEncounterItem(patientEncounter);

        //Get Patient Name and other basic info

        ServiceResponse<PatientItem> patientServiceResponse = searchService.findPatientItemById(patientEncounter.getPatientId());
        if (patientServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModel.setPatientItem(patientServiceResponse.getResponseObject());

        //prescriptions
        ServiceResponse<List<PrescriptionItem>> patientPrescriptionsServiceResponse = searchService.findAllPrescriptionItemsByEncounterId(patientEncounter.getId());
        if (patientPrescriptionsServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<PrescriptionItem> patientPrescriptions = patientPrescriptionsServiceResponse.getResponseObject();
        List<Pair<String, String>> medicationAndReplacement = getPrescriptions(patientPrescriptions);
        viewModel.setMedicationAndReplacement(medicationAndReplacement);
        viewModel.setPrescription1(getOriginalPrescriptionOrNull(1, medicationAndReplacement));
        viewModel.setPrescription2(getOriginalPrescriptionOrNull(2, medicationAndReplacement));
        viewModel.setPrescription3(getOriginalPrescriptionOrNull(3, medicationAndReplacement));
        viewModel.setPrescription4(getOriginalPrescriptionOrNull(4, medicationAndReplacement));
        viewModel.setPrescription5(getOriginalPrescriptionOrNull(5, medicationAndReplacement));


        //get vital map
        ServiceResponse<VitalMultiMap> vitalMapServiceResponse = searchService.getVitalMultiMap(patientEncounter.getId());
        if (vitalMapServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModel.setVitalList(vitalMapServiceResponse.getResponseObject());

        //get custom tabs/fields
        ServiceResponse<List<TabItem>> tabItemServiceResponse = medicalService.getCustomTabs();
        if (tabItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModel.setCustomTabs(tabItemServiceResponse.getResponseObject());
        ServiceResponse<Map<String, List<TabFieldItem>>> tabFieldItemServiceResponse = medicalService.getCustomFields(patientEncounter.getId());
        if (tabFieldItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModel.setCustomFields(tabFieldItemServiceResponse.getResponseObject());

        /*photos
        ServiceResponse<List<IPhoto>> photoListSr = photoService.GetEncounterPhotos(id);
        List<IPhoto> photoLst = null;
        if (!photoListSr.hasErrors())
            photoLst = photoListSr.getResponseObject()
                    ;
        viewModel.setPhotos(getPhotoModel(photoLst));
        */

        return ok(indexEncounter.render(currentUser, viewModel));
    }

    /**
     * Gets the photo list and adds them to the Photo Model or sets it to null if it is empty
     *
     * @param photos the list of IPhoto to iterate over
     * @return A list of PhotoModel or null
     */
    private List<PhotoItem> getPhotoModel(List<IPhoto> photos) {
        List<PhotoItem> tempPhotoList = new ArrayList<>();
        if (photos != null) {
            for (IPhoto p : photos) {
                PhotoItem pm = new PhotoItem();
                pm.setId(p.getId()); //set photo Id
                pm.setImageDesc(p.getDescription()); //set description
                pm.setImageUrl(routes.PhotoController.GetPhoto(p.getId()).toString()); //set image URL
                pm.setImageDate(StringUtils.ToSimpleDate(p.getInsertTS()));
                tempPhotoList.add(pm);
            }
        }
        return tempPhotoList;
    }

    private List<Pair<String, String>> getPrescriptions(List<PrescriptionItem> prescriptionItems) {

        List<Pair<String, String>> medicationAndReplacement = new LinkedList<>();
        List<Integer> ignoreList = new ArrayList<>(); // list used to make sure we ignore duplicate entries
        for (PrescriptionItem patientPrescription : prescriptionItems) {
            // check if the medication was replaced if so save it and the replacement medications name
            if (patientPrescription.getReplacementId() != null) {
                medicationAndReplacement.add(new Pair<String, String>(patientPrescription.getName(),
                        getPrescriptionNameById(patientPrescription.getReplacementId())));
                // add the replaced prescription id to the ignore list so we don't added it twice
                ignoreList.add(patientPrescription.getReplacementId());
            } else if (!ignoreList.contains(patientPrescription.getId())) {
                // if the medication is not in the ignore list
                medicationAndReplacement.add(new Pair<String, String>(patientPrescription.getName(), ""));
            }
        }
        return medicationAndReplacement;
    }

    /**
     * given the id for a prescription the function will return its name.
     * used for getting the name of the replacement prescription
     *
     * @param id the id of the prescription
     * @return The name of the Prescription as a string
     */
    private String getPrescriptionNameById(int id) {
        ServiceResponse<IPatientPrescription> patientPrescriptionServiceResponse = searchService.findPatientPrescriptionById(id);
        IPatientPrescription patientPrescription = patientPrescriptionServiceResponse.getResponseObject();
        return patientPrescription.getMedicationName();
    }

    private String getOriginalPrescriptionOrNull(int number, List<Pair<String, String>> patientPrescriptions) {
        if (patientPrescriptions.size() >= number) {
            return patientPrescriptions.get(number - 1).getKey();
        } else {
            return null;
        }
    }


}
