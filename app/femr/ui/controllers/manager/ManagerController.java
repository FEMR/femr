package femr.ui.controllers.manager;

import com.google.inject.Inject;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.util.calculations.dateUtils;
import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.manager.IndexViewModelGet;
import femr.ui.models.sessions.CreateViewModel;
import femr.common.models.TabFieldItem;
import femr.ui.models.history.IndexEncounterMedicalViewModel;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;

import java.util.HashMap;
import java.util.Map;

import femr.ui.views.html.manager.index;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

import static femr.util.calculations.dateUtils.getDisplayTime;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.MANAGER})
public class ManagerController extends Controller {

    private final ISessionService sessionService;
    private final IPatientService patientService;
    private final IEncounterService encounterService;
    private final ISearchService searchService;
    private final ITabService tabService;
    private final IVitalService vitalService;
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);


    @Inject
    public ManagerController(ISessionService sessionService, IPatientService patientService,
                             IEncounterService encounterService, ISearchService searchService, ITabService tabService, IVitalService vitalService) {
        this.sessionService = sessionService;
        this.patientService = patientService;
        this.encounterService = encounterService;
        this.searchService = searchService;
        this.tabService = tabService;
        this.vitalService = vitalService;
    }

    public Result indexGet() {

//declares empty array lists  view model
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        IndexViewModelGet viewModel = new IndexViewModelGet();
        IndexEncounterMedicalViewModel hpimodel = new IndexEncounterMedicalViewModel();
        List<PatientItem> patientList = new ArrayList<PatientItem>();
        List<Map<String, TabFieldItem>> tab = new ArrayList<Map<String, TabFieldItem>>();
        List<VitalMultiMap> vitalList = new ArrayList<VitalMultiMap>();
        List<PatientEncounterItem> encounter = new ArrayList<PatientEncounterItem>();
        List<String> triageCheckInTime = new ArrayList<String>();
        List<String> medicalCheckInTime = new ArrayList<String>();
        List<String> pharmCheckInTime = new ArrayList<String>();
//if the user is not assigned to a trip renders outpage, with message to user
        if (currentUser.getTripId() == null) {

            return ok(index.render(currentUser, viewModel, hpimodel));
        }
        //returns a list of Patient encounter items of patients checked in Triage on the current day
        ServiceResponse<List<PatientEncounterItem>> patientEncounter = encounterService.retrieveCurrentDayPatientEncounters(currentUser.getTripId());

        //loops through patient encounters found in patientEncounter above and consolidates data for those patient encounters in arrays declared above
        for (int i = 0; i < patientEncounter.getResponseObject().size(); i++) {
            //gets a PatientItem from the Patient Encounter Item
            ServiceResponse<PatientItem> translate = searchService.retrievePatientItemByPatientId(patientEncounter.getResponseObject().get(i).getPatientId());
            PatientItem patientItem = translate.getResponseObject();
            //gets patients vitalsmultimap from the Patient Encounter Item
            ServiceResponse<VitalMultiMap> patientEncounterVitalMapResponse = vitalService.retrieveVitalMultiMap(patientEncounter.getResponseObject().get(i).getId());
            VitalMultiMap vitals = patientEncounterVitalMapResponse.getResponseObject();
            //gets the a TabFeildMultimap from the Patient Encounter Item
            ServiceResponse<TabFieldMultiMap> patientEncounterTabFieldResponse = tabService.retrieveTabFieldMultiMap(patientEncounter.getResponseObject().get(i).getId());
            TabFieldMultiMap tabFieldMultiMap = patientEncounterTabFieldResponse.getResponseObject();
            //gets the HPI feilds for the patients
            Map<String, TabFieldItem> hpiFields = new HashMap<>();
            if (patientEncounter.getResponseObject().get(i).getChiefComplaints().size() >= 0) {

                for (String cc : patientEncounter.getResponseObject().get(i).getChiefComplaints()) {
                    Map<String, TabFieldItem> hpiFieldsUnderChiefComplaint = new HashMap<>();
                    hpiFieldsUnderChiefComplaint.put("narrative", tabFieldMultiMap.getMostRecentOrEmpty("narrative", cc.trim()));
                    hpimodel.setHpiFieldsWithoutMultipleChiefComplaints(hpiFieldsUnderChiefComplaint);
                    hpiFields.putAll(hpiFieldsUnderChiefComplaint);
                }
                //adds the time the patient in that patient encounter was checked into triage, medical, and pharmacy respectively
                triageCheckInTime.add(getDisplayTime(patientEncounter.getResponseObject().get(i).getTriageDateOfVisit()));
                medicalCheckInTime.add(getDisplayTime(patientEncounter.getResponseObject().get(i).getMedicalDateOfVisit()));
                pharmCheckInTime.add(getDisplayTime(patientEncounter.getResponseObject().get(i).getPharmacyDateOfVisit()));
                //adds the tabFeildMultimap, the vital map, and the patientItem to their respective arrays
                tab.add(hpiFields);
                vitalList.add(vitals);
                patientList.add(patientItem);
            }
        }


        //set viewmodel values for manager tab
        //sets list of triagePatients
        viewModel.setTriagePatients(patientList);
        //sets list of Patient Encounters
        viewModel.setPatientEncounter(patientEncounter.getResponseObject());
        //sets vitals (if found) for each patient
        viewModel.setVitals(vitalList);
        //sets HPI items found
        viewModel.setHPI(tab);
        //sets the time the patients were checked into Triage
        viewModel.setTimeOfTriageVisit(triageCheckInTime);
        //sets the time the patients were checked into Medical
        viewModel.setTimeOfMedicalVisit(medicalCheckInTime);
        //sets the time the patients were checked out of Pharmacy
        viewModel.setTimeOfPharmVisit(pharmCheckInTime);
        //gets hpi fields
        return ok(index.render(currentUser, viewModel, hpimodel));

    }


}


