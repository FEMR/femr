/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.ui.helpers.security.AllowedRoles;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.history.*;
import femr.ui.models.triage.DeleteViewModelPost;
import femr.ui.views.html.history.indexEncounter;
import femr.ui.views.html.history.indexPatient;
import femr.ui.views.html.partials.history.listTabFieldHistory;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import java.util.ArrayList;
import java.util.HashMap;

import play.data.FormFactory;
import play.mvc.Security;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import java.util.Map;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class HistoryController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private final IEncounterService encounterService;
    private final ISessionService sessionService;
    private final ISearchService searchService;
    private final ITabService tabService;
    private final IPhotoService photoService;
    private final IVitalService vitalService;

    @Inject
    public HistoryController(AssetsFinder assetsFinder,
                             FormFactory formFactory,
                             IEncounterService encounterService,
                             ISessionService sessionService,
                             ISearchService searchService,
                             ITabService tabService,
                             IPhotoService photoService,
                             IVitalService vitalService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.encounterService = encounterService;
        this.sessionService = sessionService;
        this.searchService = searchService;
        this.tabService = tabService;
        this.photoService = photoService;
        this.vitalService = vitalService;
    }

    /**
     * Render the page for viewing a patient (a patient can have multiple encounters)
     *
     * @param query this is a query string representing what the user searched (patient first/last name).
     */
    public Result indexPatientGet(String query) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        boolean error = false;


        IndexPatientViewModelGet viewModel = new IndexPatientViewModelGet();

        //how do we show more than one?
        query = query.replace("-", " ");
        ServiceResponse<List<PatientItem>> patientResponse = searchService.retrievePatientsFromQueryString(query);
        if (patientResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<PatientItem> patientItems = patientResponse.getResponseObject();


        if (patientItems == null || patientItems.size() < 1) {
//            return ok(showError.render(currentUser));
            //return an error near the search box
        }

        //too much logic - move patient photo finding up to the service layer
        for (PatientItem patientItem : patientItems)
            patientItem.setPathToPhoto(routes.PhotoController.GetPatientPhoto(patientItem.getId(), true).toString());
        viewModel.setPatientItems(patientItems);
        viewModel.setRankedPatientItems(new ArrayList<>());
        viewModel.setPatientItem(patientItems.get(0));

        ServiceResponse<List<PatientEncounterItem>> patientEncountersServiceResponse = searchService.retrievePatientEncounterItemsByPatientId(patientItems.get(0).getId());
        if (patientEncountersServiceResponse.hasErrors()) {


            throw new RuntimeException();
        }
        List<PatientEncounterItem> patientEncounterItems = patientEncountersServiceResponse.getResponseObject();
        if (patientEncounterItems == null || patientEncounterItems.size() < 1) {
            //return ok(showError.render(currentUser));
            //return an error near the search box
        }
        viewModel.setPatientEncounterItems(patientEncounterItems);


        return ok(indexPatient.render(currentUser, error, viewModel, patientEncounterItems, assetsFinder));
    }

    /**
     * Render the page for viewing a patient with potential ranked patient matches from triage search (a patient can have multiple encounters)
     *
     */
    public Result indexPatientGetWithRankedMatches(String first, String last, String phone, String addr, Long age, String gender, String city) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        boolean error = false;


        IndexPatientViewModelGet viewModel = new IndexPatientViewModelGet();

        //how do we show more than one?
//        query = query.replace("-", " ");
        ServiceResponse<List<RankedPatientItem>> patientResponse = searchService.retrievePatientsFromTriageSearch(first, last, phone, addr, gender, age, city);
//        ServiceResponse<List<PatientItem>> patientResponse = searchService.retrievePatientsFromQueryString(query);
        if (patientResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<RankedPatientItem> rankedPatientItems = patientResponse.getResponseObject();


        if (rankedPatientItems == null || rankedPatientItems.size() < 1) {
//            return ok(showError.render(currentUser));
            //return an error near the search box
        }

        //too much logic - move patient photo finding up to the service layer
        for (RankedPatientItem r : rankedPatientItems)
            r.getPatientItem().setPathToPhoto(routes.PhotoController.GetPatientPhoto(r.getPatientItem().getId(), true).toString());
        viewModel.setRankedPatientItems(rankedPatientItems);
        viewModel.setPatientItems(new ArrayList<>());
        viewModel.setPatientItem(rankedPatientItems.get(0).getPatientItem());

        ServiceResponse<List<PatientEncounterItem>> patientEncountersServiceResponse = searchService.retrievePatientEncounterItemsByPatientId(rankedPatientItems.get(0).getPatientItem().getId());
        if (patientEncountersServiceResponse.hasErrors()) {


            throw new RuntimeException();
        }
        List<PatientEncounterItem> patientEncounterItems = patientEncountersServiceResponse.getResponseObject();
        if (patientEncounterItems == null || patientEncounterItems.size() < 1) {
            //return ok(showError.render(currentUser));
            //return an error near the search box
        }
        viewModel.setPatientEncounterItems(patientEncounterItems);


        return ok(indexPatient.render(currentUser, error, viewModel, patientEncounterItems, assetsFinder));
    }

    /**
     * Render the page for viewing an encounter.
     *
     * @param encounterId the id of the encounter
     */
    public Result indexEncounterGet(int encounterId) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        IndexEncounterViewModel indexEncounterViewModel = new IndexEncounterViewModel();
        IndexEncounterMedicalViewModel indexEncounterMedicalViewModel = new IndexEncounterMedicalViewModel();
        IndexEncounterPharmacyViewModel indexEncounterPharmacyViewModel = new IndexEncounterPharmacyViewModel();

        /* Alaa Serhan */
        ServiceResponse<SettingItem> response = searchService.retrieveSystemSettings();

        indexEncounterMedicalViewModel.setSettings(response.getResponseObject());

        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByEncounterId(encounterId);

        if (patientItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();
        patientItem.setPathToPhoto(routes.PhotoController.GetPatientPhoto(patientItem.getId(), true).toString());
        indexEncounterViewModel.setPatientItem(patientItem);

        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrievePatientEncounterItemByEncounterId(encounterId);
        if (patientEncounterItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        indexEncounterViewModel.setPatientEncounterItem(patientEncounterItemServiceResponse.getResponseObject());

        //get vitals
        ServiceResponse<VitalMultiMap> patientEncounterVitalMapResponse = vitalService.retrieveVitalMultiMap(encounterId);
        if (patientEncounterVitalMapResponse.hasErrors()) {
            throw new RuntimeException();
        }

        /* Alaa Serhan */
        // Get patient vitals
        VitalMultiMap vitalMultiMap = patientEncounterVitalMapResponse.getResponseObject();
        indexEncounterMedicalViewModel.setVitalList(vitalMultiMap);

        //get photos
        ServiceResponse<List<PhotoItem>> photoListResponse = photoService.retrieveEncounterPhotos(encounterId);
        if (photoListResponse.hasErrors()) {
            throw new RuntimeException();
        }
        indexEncounterMedicalViewModel.setPhotos(photoListResponse.getResponseObject());


        // Get patient encounter tab field multimap
        ServiceResponse<TabFieldMultiMap> patientEncounterTabFieldResponse = tabService.retrieveTabFieldMultiMap(encounterId);
        if (patientEncounterTabFieldResponse.hasErrors()) {
            throw new RuntimeException();
        }
        TabFieldMultiMap tabFieldMultiMap = patientEncounterTabFieldResponse.getResponseObject();

        //Get a mapping of tabs to tab fields in string form
        ServiceResponse<Map<String, List<String>>> tabFieldToTabMappingServiceResponse = tabService.retrieveTabFieldToTabMapping(false, false);
        if (tabFieldToTabMappingServiceResponse.hasErrors()){

            throw new RuntimeException();
        }
        Map<String, List<String>> tabFieldToTabMapping = tabFieldToTabMappingServiceResponse.getResponseObject();



        //extract the most recent treatment fields
        Map<String, TabFieldItem> treatmentFields = new HashMap<>();
        for (String field : tabFieldToTabMapping.get("treatment")){

            treatmentFields.put(field, tabFieldMultiMap.getMostRecentOrEmpty(field, null));
        }
        indexEncounterMedicalViewModel.setTreatmentFields(treatmentFields);

        //extract the most recent pmh fields
        Map<String, TabFieldItem> pmhFields = new HashMap<>();
        for (String field : tabFieldToTabMapping.get("pmh")){

            pmhFields.put(field, tabFieldMultiMap.getMostRecentOrEmpty(field, null));
        }
        indexEncounterMedicalViewModel.setPmhFields(pmhFields);

        //extract the most recent hpi fields
        if (patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints().size() > 1) {
            //extract the HPI fields while the user has entered multiple chief complaints
            indexEncounterMedicalViewModel.setHpiFieldsWithMultipleChiefComplaints(extractHpiFieldsWithMultipleChiefComplaints(tabFieldMultiMap, patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints()));
            indexEncounterMedicalViewModel.setIsMultipleChiefComplaints(true);
        } else {
            String chiefComplaint = null;
            if (patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints().size() == 1)
                chiefComplaint = patientEncounterItemServiceResponse.getResponseObject().getChiefComplaints().get(0);
            Map<String, TabFieldItem> hpiFields = new HashMap<>();
            for (String field : tabFieldToTabMapping.get("hpi")){

                hpiFields.put(field, tabFieldMultiMap.getMostRecentOrEmpty(field, chiefComplaint));
            }
            indexEncounterMedicalViewModel.setHpiFieldsWithoutMultipleChiefComplaints(hpiFields);
            indexEncounterMedicalViewModel.setIsMultipleChiefComplaints(false);
        }

        //extract the most recent custom fields
        indexEncounterMedicalViewModel.setCustomFields(extractCustomFields(tabFieldMultiMap));

        //get problems
        List<String> problems = new ArrayList<>();
        ServiceResponse<List<ProblemItem>> problemItemServiceResponse = encounterService.retrieveProblemItems(encounterId);
        if (problemItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        for (ProblemItem pi : problemItemServiceResponse.getResponseObject()) {
            problems.add(pi.getName());
        }
        indexEncounterPharmacyViewModel.setProblems(problems);

        //get prescriptions
        List<String> prescriptions = new ArrayList<>();
        ServiceResponse<List<PrescriptionItem>>  prescriptionItemServiceResponse = searchService.retrieveDispensedPrescriptionItems(encounterId);
        if (prescriptionItemServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        indexEncounterPharmacyViewModel.setPrescriptions(prescriptionItemServiceResponse.getResponseObject());

        return ok(indexEncounter.render(currentUser, indexEncounterViewModel, indexEncounterMedicalViewModel, indexEncounterPharmacyViewModel, assetsFinder));
    }

    /**
     * Updates a field from an encounter. Called from AJAX.
     */
    public Result updateEncounterPost(int encounterId) {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        //get POST data
        final Form<fieldValueViewModel> fieldValueViewModelForm = formFactory.form(fieldValueViewModel.class);
        fieldValueViewModel fields = fieldValueViewModelForm.bindFromRequest().get();

        //get the patient encounter from the service layer
        ServiceResponse<PatientEncounterItem> patientEncounterByEncounterId = searchService.retrievePatientEncounterItemByEncounterId(encounterId);
        if (patientEncounterByEncounterId.hasErrors()) {
            throw new RuntimeException();
        }

        //extract the patient encounter item from the service response
        PatientEncounterItem patientEncounter = patientEncounterByEncounterId.getResponseObject();

        //Create a mapping of the only tabfield from its name to its value for saving
        Map<String, String> tabFieldNameValues = new HashMap<>();
        tabFieldNameValues.put(fields.getFieldName(), fields.getFieldValue());

        //save the MFer
        ServiceResponse<List<TabFieldItem>> patientEncounterTabFieldsServiceResponse = new ServiceResponse<>();
        if (StringUtils.isNullOrWhiteSpace(fields.getChiefComplaintName()))
            encounterService.createPatientEncounterTabFields(tabFieldNameValues, patientEncounter.getId(), currentUser.getId());
        else
            encounterService.createPatientEncounterTabFields(tabFieldNameValues, patientEncounter.getId(), currentUser.getId(), fields.getChiefComplaintName());

        if (patientEncounterTabFieldsServiceResponse.hasErrors())
            throw new RuntimeException();

        return ok("true");
    }

    /**
     * Gets the partial view that shows the history of tab field items. Called from AJAX.
     */
    public Result listTabFieldHistoryGet(int encounterID) {

        //Populate model with request data that was changed
        final Form<fieldValueViewModel> fieldValueViewModelForm = formFactory.form(fieldValueViewModel.class);
        fieldValueViewModel fields = fieldValueViewModelForm.bindFromRequest().get();

        //get the recorded tab field values using the id from the previous
        ServiceResponse<TabFieldMultiMap> tabFieldsResponseObject = tabService.findTabFieldMultiMap(
                encounterID,
                fields.getFieldName(),
                fields.getChiefComplaintName()
        );
        if (tabFieldsResponseObject.hasErrors()) {

            throw new RuntimeException();
        }

        return ok(listTabFieldHistory.render(fields.getFieldName(), tabFieldsResponseObject.getResponseObject()));
    }

    /**
     * A helper that extracts the most recent custom fields from the tabfieldmultimap. This method
     * is not aware of which tab the fields are under, nor does it care.
     */
    private Map<String, TabFieldItem> extractCustomFields(TabFieldMultiMap tabFieldMultiMap) {

        Map<String, TabFieldItem> customFields = new HashMap<>();
        List<String> customFieldNames = tabFieldMultiMap.getCustomFieldNameList();
        for (String customField : customFieldNames) {

            customFields.put(customField, tabFieldMultiMap.getMostRecentOrEmpty(customField, null));
        }

        return customFields;
    }

    /**
     * A helper that extracts the most recent HPI fields from the tabfieldmultimap. Takes into consideration all chief complaints.
     */
    private Map<String, Map<String, TabFieldItem>> extractHpiFieldsWithMultipleChiefComplaints(TabFieldMultiMap tabFieldMultiMap, List<String> chiefComplaints) {

        Map<String, Map<String, TabFieldItem>> hpiFields = new HashMap<>();

        for (String cc : chiefComplaints) {
            Map<String, TabFieldItem> hpiFieldsUnderChiefComplaint = new HashMap<>();
            hpiFieldsUnderChiefComplaint.put("onset", tabFieldMultiMap.getMostRecentOrEmpty("onset", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("quality", tabFieldMultiMap.getMostRecentOrEmpty("quality", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("radiation", tabFieldMultiMap.getMostRecentOrEmpty("radiation", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("severity", tabFieldMultiMap.getMostRecentOrEmpty("severity", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("provokes", tabFieldMultiMap.getMostRecentOrEmpty("provokes", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("palliates", tabFieldMultiMap.getMostRecentOrEmpty("palliates", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("timeOfDay", tabFieldMultiMap.getMostRecentOrEmpty("timeOfDay", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("narrative", tabFieldMultiMap.getMostRecentOrEmpty("narrative", cc.trim()));
            hpiFieldsUnderChiefComplaint.put("physicalExamination", tabFieldMultiMap.getMostRecentOrEmpty("physicalExamination", cc.trim()));
            hpiFields.put(cc.trim(), hpiFieldsUnderChiefComplaint);
        }

        return hpiFields;
    }
}
