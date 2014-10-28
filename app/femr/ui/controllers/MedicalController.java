package femr.ui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import femr.business.services.*;
import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;
import femr.common.models.*;
import femr.data.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.medical.*;
import femr.ui.models.medical.json.JCustomField;
import femr.ui.views.html.medical.index;
import femr.ui.views.html.medical.edit;
import femr.ui.views.html.medical.newVitals;
import femr.ui.views.html.medical.listVitals;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.MultipartFormData.FilePart;

import java.util.*;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class MedicalController extends Controller {

    private final Form<EditViewModelPost> createViewModelPostForm = Form.form(EditViewModelPost.class);
    private final Form<UpdateVitalsModel> updateVitalsModelForm = Form.form(UpdateVitalsModel.class);
    private final ISessionService sessionService;
    private final ISearchService searchService;
    private final IMedicalService medicalService;
    private final IPhotoService photoService;

    @Inject
    public MedicalController(ISessionService sessionService,
                             ISearchService searchService,
                             IMedicalService medicalService,
                             IPhotoService photoService) {
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.medicalService = medicalService;
        this.photoService = photoService;
    }

    public Result indexGet() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        return ok(index.render(currentUserSession, null, 0));
    }

    public Result indexPost() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        String queryString_id = request().body().asFormUrlEncoded().get("id")[0];
        ServiceResponse<Integer> idQueryStringResponse = searchService.parseIdFromQueryString(queryString_id);
        if (idQueryStringResponse.hasErrors()) {
            return ok(index.render(currentUserSession, idQueryStringResponse.getErrors().get(""), 0));
        }
        Integer patientId = idQueryStringResponse.getResponseObject();

        //get the patient's encounter
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findRecentPatientEncounterItemByPatientId(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            return ok(index.render(currentUserSession, patientEncounterItemServiceResponse.getErrors().get(""), 0));
        }
        PatientEncounterItem patientEncounterItem = patientEncounterItemServiceResponse.getResponseObject();

        //check for encounter closed
        if (patientEncounterItem.getIsClosed()) {
            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0));
        }

        //check if the doc has already seen the patient today
        ServiceResponse<UserItem> userItemServiceResponse = medicalService.getPhysicianThatCheckedInPatient(patientEncounterItem.getId());
        if (userItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            if (userItemServiceResponse.getResponseObject() != null) {
                return ok(index.render(currentUserSession, "That patient has already been seen today. Would you like to edit their encounter?", patientId));
            }
        }

        return redirect(routes.MedicalController.editGet(patientId));
    }

    public Result editGet(int patientId) {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        EditViewModelGet viewModelGet = new EditViewModelGet();

        //Get Patient Encounter
        PatientEncounterItem patientEncounter;
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findRecentPatientEncounterItemByPatientId(patientId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        patientEncounter = patientEncounterItemServiceResponse.getResponseObject();
        viewModelGet.setPatientEncounterItem(patientEncounter);

        //verify encounter is still open
        if (patientEncounter.getIsClosed()) {
            return ok(index.render(currentUserSession, "That patient's encounter has been closed.", 0));
        }

        //get patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemByPatientId(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setPatientItem(patientItemServiceResponse.getResponseObject());

        //get prescriptions
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.findUnreplacedPrescriptionItems(patientEncounter.getId());
        if (prescriptionItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setPrescriptionItems(prescriptionItemServiceResponse.getResponseObject());

        //get vitals
        ServiceResponse<VitalMultiMap> patientEncounterVitalMapResponse = searchService.getVitalMultiMap(patientEncounter.getId());
        if (patientEncounterVitalMapResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setVitalMap(patientEncounterVitalMapResponse.getResponseObject());

        //get non-custom fields
        //Map<String, TabFieldItem>
        // String = tab field name
        // TabFieldItem contains value
        ServiceResponse<Map<String, TabFieldItem>> patientEncounterTabFieldResponse = medicalService.findCurrentTabFieldsByEncounterId(patientEncounter.getId());
        Map<String, TabFieldItem> tabFieldItemMap;
        if (patientEncounterTabFieldResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            tabFieldItemMap = patientEncounterTabFieldResponse.getResponseObject();
            viewModelGet.setStaticFields(tabFieldItemMap);
        }

        //get custom tabs/fields
        ServiceResponse<List<TabItem>> tabItemResponse = medicalService.getCustomTabs();
        if (tabItemResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModelGet.setCustomTabs(tabItemResponse.getResponseObject());
        }
        ServiceResponse<Map<String, List<TabFieldItem>>> tabFieldResponse = medicalService.getCustomFields(patientEncounter.getId());
        if (tabFieldResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModelGet.setCustomFields(tabFieldResponse.getResponseObject());
        }

        ServiceResponse<List<PhotoItem>> photoListResponse = photoService.GetEncounterPhotos(patientEncounter.getId());
        if (photoListResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModelGet.setPhotos(photoListResponse.getResponseObject());
        }

        ServiceResponse<SettingItem> response = searchService.getSystemSettings();
        viewModelGet.setSettings(response.getResponseObject());

        return ok(edit.render(currentUserSession, viewModelGet));
    }

    public Result editPost(int patientId) {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        EditViewModelPost viewModelPost = createViewModelPostForm.bindFromRequest().get();

        //get current encounter
        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = searchService.findRecentPatientEncounterItemByPatientId(patientId);
        if (patientEncounterServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounterItem = patientEncounterServiceResponse.getResponseObject();
        patientEncounterItem = medicalService.checkPatientIn(patientEncounterItem.getId(), currentUserSession.getId()).getResponseObject();
        //update patient encounter

        //Maps the dynamic tab name to the field list
        Gson gson = new Gson();
        Map<String, List<JCustomField>> customFieldInformation = gson.fromJson(viewModelPost.getCustomFieldJSON(), new TypeToken<Map<String, List<JCustomField>>>() {
        }.getType());

        List<TabFieldItem> customFieldItems = new ArrayList<>();
        for (Map.Entry<String, List<JCustomField>> entry : customFieldInformation.entrySet()) {
            List<JCustomField> fields = entry.getValue();
            for (JCustomField jcf : fields) {
                TabFieldItem tabFieldItem = new TabFieldItem();
                tabFieldItem.setName(jcf.getName());
                tabFieldItem.setValue(jcf.getValue());
                tabFieldItem.setIsCustom(true);
                customFieldItems.add(tabFieldItem);
            }
        }

        //save the custom fields, if any
        if (customFieldItems.size() > 0) {
            ServiceResponse<List<TabFieldItem>> customFieldItemResponse =
                    medicalService.createPatientEncounterTabFields(customFieldItems, patientEncounterItem.getId(), currentUserSession.getId());
            if (customFieldItemResponse.hasErrors()) {
                throw new RuntimeException();
            }
        }

        List<TabFieldItem> nonCustomFieldItems = new ArrayList<>();

        //multiple chief complaints
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getMultipleHpiJSON())) {
            //iterate over all values, adding them to the list with the respective chief complaint
            nonCustomFieldItems.addAll(mapHpiFieldItemsFromJSON(viewModelPost.getMultipleHpiJSON()));

        } else {//one or less chief complaints
            nonCustomFieldItems.addAll(mapHpiFieldItems(viewModelPost));
        }

        nonCustomFieldItems.addAll(mapPmhFieldItems(viewModelPost));
        nonCustomFieldItems.addAll(mapTreatmentFieldItems(viewModelPost));

        if (nonCustomFieldItems.size() > 0) {
            ServiceResponse<List<TabFieldItem>> nonCustomFieldItemResponse =
                    medicalService.createPatientEncounterTabFields(nonCustomFieldItems, patientEncounterItem.getId(), currentUserSession.getId());
            if (nonCustomFieldItemResponse.hasErrors()) {
                throw new RuntimeException();
            }
        }


        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemByPatientId(patientId);
        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();


        List<FilePart> fps = request().body().asMultipartFormData().getFiles();

        //wtf is this
        photoService.HandleEncounterPhotos(fps, patientEncounterItem, viewModelPost);


        //save prescriptions
        List<PrescriptionItem> prescriptionItems = new ArrayList<>();
        if (viewModelPost.getPrescription1() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription1()))
            prescriptionItems.add(new PrescriptionItem(viewModelPost.getPrescription1()));
        if (viewModelPost.getPrescription2() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription2()))
            prescriptionItems.add(new PrescriptionItem(viewModelPost.getPrescription2()));
        if (viewModelPost.getPrescription3() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription3()))
            prescriptionItems.add(new PrescriptionItem(viewModelPost.getPrescription3()));
        if (viewModelPost.getPrescription4() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription4()))
            prescriptionItems.add(new PrescriptionItem(viewModelPost.getPrescription4()));
        if (viewModelPost.getPrescription5() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPrescription5()))
            prescriptionItems.add(new PrescriptionItem(viewModelPost.getPrescription5()));
        if (prescriptionItems.size() > 0) {
            ServiceResponse<List<PrescriptionItem>> prescriptionResponse =
                    medicalService.createPatientPrescriptions(prescriptionItems, currentUserSession.getId(), patientEncounterItem.getId());
            if (prescriptionResponse.hasErrors()) {
                throw new RuntimeException();
            }
        }

        String message = "Patient information for " + patientItem.getFirstName() + " " + patientItem.getLastName() + " (id: " + patientItem.getId() + ") was saved successfully.";

        return ok(index.render(currentUserSession, message, 0));
    }

    public Result updateVitalsPost(int id) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<PatientEncounterItem> currentEncounterByPatientId = searchService.findRecentPatientEncounterItemByPatientId(id);
        if (currentEncounterByPatientId.hasErrors()) {
            throw new RuntimeException();
        }
        //update date_of_medical_visit when a vital is updated
        medicalService.checkPatientIn(currentEncounterByPatientId.getResponseObject().getId(), currentUser.getId());

        PatientEncounterItem patientEncounter = currentEncounterByPatientId.getResponseObject();

        UpdateVitalsModel updateVitalsModel = updateVitalsModelForm.bindFromRequest().get();

        Map<String, Float> patientEncounterVitals = getPatientEncounterVitals(updateVitalsModel);
        ServiceResponse<List<VitalItem>> patientEncounterVitalsServiceResponse =
                medicalService.createPatientEncounterVitals(patientEncounterVitals, currentUser.getId(), patientEncounter.getId());
        if (patientEncounterVitalsServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        return ok("true");
    }

    //partials
    public Result newVitalsGet() {
        return ok(newVitals.render());
    }

    public Result listVitalsGet(Integer id) {


        ServiceResponse<PatientEncounterItem> patientEncounterServiceResponse = searchService.findRecentPatientEncounterItemByPatientId(id);
        if (patientEncounterServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        ServiceResponse<VitalMultiMap> vitalMultiMapServiceResponse = searchService.getVitalMultiMap(patientEncounterServiceResponse.getResponseObject().getId());
        if (vitalMultiMapServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        return ok(listVitals.render(vitalMultiMapServiceResponse.getResponseObject()));
    }

    /**
     * Maps vitals from view model to a Map structure
     *
     * @param viewModel the view model with POST data
     * @return Mapped vital value to vital name
     */
    private Map<String, Float> getPatientEncounterVitals(UpdateVitalsModel viewModel) {
        Map<String, Float> newVitals = new HashMap<>();
        if (viewModel.getRespiratoryRate() != null) {
            newVitals.put("respiratoryRate", viewModel.getRespiratoryRate());
        }
        if (viewModel.getHeartRate() != null) {
            newVitals.put("heartRate", viewModel.getHeartRate());
        }
        if (viewModel.getTemperature() != null) {
            newVitals.put("temperature", viewModel.getTemperature());
        }
        if (viewModel.getOxygenSaturation() != null) {
            newVitals.put("oxygenSaturation", viewModel.getOxygenSaturation());
        }
        if (viewModel.getHeightFeet() != null) {
            newVitals.put("heightFeet", viewModel.getHeightFeet());
        }
        if (viewModel.getHeightInches() != null) {
            newVitals.put("heightInches", viewModel.getHeightInches());
        }
        if (viewModel.getWeight() != null) {
            newVitals.put("weight", viewModel.getWeight());
        }
        if (viewModel.getBloodPressureSystolic() != null) {
            newVitals.put("bloodPressureSystolic", viewModel.getBloodPressureSystolic());
        }
        if (viewModel.getBloodPressureDiastolic() != null) {
            newVitals.put("bloodPressureDiastolic", viewModel.getBloodPressureDiastolic());
        }
        if (viewModel.getGlucose() != null) {
            newVitals.put("glucose", viewModel.getGlucose());
        }
        return newVitals;
    }

    /**
     * Creates a list of available pmh fields in the viewmodel
     *
     * @param viewModelPost view model POST from edit.scala.html
     * @return a list of values the user entered
     */
    private List<TabFieldItem> mapPmhFieldItems(EditViewModelPost viewModelPost) {
        List<TabFieldItem> tabFieldItems = new ArrayList<>();
        //Pmh_fields
        if (viewModelPost.getMedicalSurgicalHistory() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getMedicalSurgicalHistory()))
            tabFieldItems.add(createTabFieldItem("medicalSurgicalHistory", viewModelPost.getMedicalSurgicalHistory()));
        if (viewModelPost.getSocialHistory() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getSocialHistory()))
            tabFieldItems.add(createTabFieldItem("socialHistory", viewModelPost.getSocialHistory()));
        if (viewModelPost.getCurrentMedication() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getCurrentMedication()))
            tabFieldItems.add(createTabFieldItem("currentMedication", viewModelPost.getCurrentMedication()));
        if (viewModelPost.getFamilyHistory() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getFamilyHistory()))
            tabFieldItems.add(createTabFieldItem("familyHistory", viewModelPost.getFamilyHistory()));
        return tabFieldItems;
    }

    /**
     * Creates a list of available treatment fields in the viewmodel
     *
     * @param viewModelPost view model POST from edit.scala.html
     * @return a list of values the user entered
     */
    private List<TabFieldItem> mapTreatmentFieldItems(EditViewModelPost viewModelPost) {
        List<TabFieldItem> tabFieldItems = new ArrayList<>();
        //treatment fields
        if (viewModelPost.getAssessment() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAssessment()))
            tabFieldItems.add(createTabFieldItem("assessment", viewModelPost.getAssessment()));
        if (viewModelPost.getProblem1() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem1()))
            tabFieldItems.add(createTabFieldItem("problem", viewModelPost.getProblem1()));
        if (viewModelPost.getProblem2() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem2()))
            tabFieldItems.add(createTabFieldItem("problem", viewModelPost.getProblem2()));
        if (viewModelPost.getProblem3() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem3()))
            tabFieldItems.add(createTabFieldItem("problem", viewModelPost.getProblem3()));
        if (viewModelPost.getProblem4() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem4()))
            tabFieldItems.add(createTabFieldItem("problem", viewModelPost.getProblem4()));
        if (viewModelPost.getProblem5() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProblem5()))
            tabFieldItems.add(createTabFieldItem("problem", viewModelPost.getProblem5()));
        if (viewModelPost.getTreatment() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getTreatment()))
            tabFieldItems.add(createTabFieldItem("treatment", viewModelPost.getTreatment()));
        return tabFieldItems;
    }

    /**
     * Creates a list of available hpi fields in the viewmodel
     *
     * @param viewModelPost view model POST from edit.scala.html
     * @return a list of values the user entered
     */
    private List<TabFieldItem> mapHpiFieldItems(EditViewModelPost viewModelPost) {
        List<TabFieldItem> tabFieldItems = new ArrayList<>();
        //hpi fields
        if (viewModelPost.getOnset() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getOnset()))
            tabFieldItems.add(createTabFieldItem("onset", viewModelPost.getOnset()));
        if (viewModelPost.getOnsetTime() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getOnsetTime()))
            tabFieldItems.add(createTabFieldItem("onsetTime", viewModelPost.getOnsetTime()));
        if (viewModelPost.getSeverity() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getSeverity()))
            tabFieldItems.add(createTabFieldItem("severity", viewModelPost.getSeverity()));
        if (viewModelPost.getRadiation() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getRadiation()))
            tabFieldItems.add(createTabFieldItem("radiation", viewModelPost.getRadiation()));
        if (viewModelPost.getQuality() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getQuality()))
            tabFieldItems.add(createTabFieldItem("quality", viewModelPost.getQuality()));
        if (viewModelPost.getProvokes() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getProvokes()))
            tabFieldItems.add(createTabFieldItem("provokes", viewModelPost.getProvokes()));
        if (viewModelPost.getPalliates() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPalliates()))
            tabFieldItems.add(createTabFieldItem("palliates", viewModelPost.getPalliates()));
        if (viewModelPost.getTimeOfDay() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getTimeOfDay()))
            tabFieldItems.add(createTabFieldItem("timeOfDay", viewModelPost.getTimeOfDay()));
        if (viewModelPost.getPhysicalExamination() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getPhysicalExamination()))
            tabFieldItems.add(createTabFieldItem("physicalExamination", viewModelPost.getPhysicalExamination()));
        if (viewModelPost.getNarrative() != null && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getNarrative()))
            tabFieldItems.add(createTabFieldItem("narrative", viewModelPost.getNarrative()));
        return tabFieldItems;
    }

    private List<TabFieldItem> mapHpiFieldItemsFromJSON(String JSON) {
        List<TabFieldItem> tabFieldItems = new ArrayList<>();
        Gson gson = new Gson();
        //get values from JSON, assign list of values to chief complaint
        Map<String, List<JCustomField>> hpiTabInformation = gson.fromJson(JSON, new TypeToken<Map<String, List<JCustomField>>>() {
        }.getType());

        for (Map.Entry<String, List<JCustomField>> entry : hpiTabInformation.entrySet()) {
            List<JCustomField> fields = entry.getValue();

            for (JCustomField jcf : fields) {
                TabFieldItem tabFieldItem = new TabFieldItem();
                tabFieldItem.setName(jcf.getName());
                tabFieldItem.setChiefComplaint(entry.getKey().trim());
                tabFieldItem.setIsCustom(false);
                tabFieldItem.setValue(jcf.getValue());
                tabFieldItems.add(tabFieldItem);
            }
        }
        return tabFieldItems;
    }

    /**
     * Creates a non-custom tabfielditem
     *
     * @param name  name of the field
     * @param value value of the field
     * @return a non custom tab field item
     */
    private TabFieldItem createTabFieldItem(String name, String value) {
        TabFieldItem tabFieldItem = new TabFieldItem();
        tabFieldItem.setIsCustom(false);
        tabFieldItem.setName(name);
        tabFieldItem.setOrder(null);
        tabFieldItem.setPlaceholder(null);
        tabFieldItem.setSize(null);
        tabFieldItem.setType(null);
        tabFieldItem.setValue(value.trim());
        return tabFieldItem;
    }
}
