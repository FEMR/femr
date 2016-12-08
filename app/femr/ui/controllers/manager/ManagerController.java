package femr.ui.controllers.manager;

import com.google.inject.Inject;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
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
                             IEncounterService encounterService, ISearchService searchService,ITabService tabService, IVitalService vitalService) {
        this.sessionService = sessionService;
        this.patientService = patientService;
        this.encounterService=encounterService;
        this.searchService=searchService;
        this.tabService = tabService;
        this.vitalService = vitalService;
    }

    public Result indexGet() {

//declares empty array lists and view models
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        List<PatientItem> patientList=new ArrayList<PatientItem>();
        List<Map<String, TabFieldItem>> tab = new ArrayList<Map<String, TabFieldItem>>();
        List<VitalMultiMap> vitalList = new ArrayList<VitalMultiMap>();
        List<PatientEncounterItem> encounter = new ArrayList<PatientEncounterItem>();
        IndexViewModelGet viewModel = new  IndexViewModelGet();
        IndexEncounterMedicalViewModel hpimodel = new IndexEncounterMedicalViewModel();

//if the user is not assigned to a trip
        if (currentUser.getTripId() == null) {
            return ok(index.render(currentUser, viewModel, hpimodel));
        }
        //returns a list of Patient encounter items of patients checked in Triage on the current day
        ServiceResponse<List<PatientEncounterItem>> patientEncounter = encounterService.retrieveCurrentDayPatientEncounters(currentUser.getTripId());
        //sets items for display
        for (int i = 0; i < patientEncounter.getResponseObject().size(); i++) {
            ServiceResponse<PatientItem> translate = searchService.retrievePatientItemByPatientId(patientEncounter.getResponseObject().get(i).getPatientId());
            PatientItem e = translate.getResponseObject();
            //gets patients vitals
            ServiceResponse<VitalMultiMap> patientEncounterVitalMapResponse = vitalService.retrieveVitalMultiMap
                    (patientEncounter.getResponseObject().get(i).getId());
            VitalMultiMap vitals = patientEncounterVitalMapResponse.getResponseObject();
            ServiceResponse<TabFieldMultiMap> patientEncounterTabFieldResponse = tabService.retrieveTabFieldMultiMap(patientEncounter.getResponseObject().get(i).getId());
            TabFieldMultiMap tabFieldMultiMap = patientEncounterTabFieldResponse.getResponseObject();
            //gets hpi fields
            Map<String, TabFieldItem> hpiFields = new HashMap<>();
            if (patientEncounter.getResponseObject().get(i).getChiefComplaints().size() >= 0) {

                for (String cc : patientEncounter.getResponseObject().get(i).getChiefComplaints()) {
                    Map<String, TabFieldItem> hpiFieldsUnderChiefComplaint = new HashMap<>();
                    hpiFieldsUnderChiefComplaint.put("narrative", tabFieldMultiMap.getMostRecentOrEmpty("narrative", cc.trim()));
                    hpimodel.setHpiFieldsWithoutMultipleChiefComplaints(hpiFieldsUnderChiefComplaint);
                    hpiFields.putAll(hpiFieldsUnderChiefComplaint);
                }

                tab.add(hpiFields);
                vitalList.add(vitals);
                patientList.add(e);
            }
        }


        //set view model values
        viewModel.setTriagePatients(patientList);
        viewModel.setPatientEncounter(patientEncounter.getResponseObject());
        viewModel.setVitals(vitalList);
        viewModel.setHPI(tab);


        return ok(index.render(currentUser, viewModel,hpimodel));

    }


}


