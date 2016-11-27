package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.common.models.TabFieldItem;
import femr.common.models.TabItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.sessions.CreateViewModel;
import femr.ui.models.manager.EditViewModelGet;
import femr.ui.models.history.IndexEncounterMedicalViewModel;
import femr.ui.views.html.manager.index;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fq251 on 10/20/2016.
 */

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
        this.encounterService=encounterService;
        this.searchService=searchService;
        this.tabService = tabService;
        this.vitalService = vitalService;

    }

    public Result indexGet() {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        List<PatientItem> p = new ArrayList<PatientItem>();
        List<Map<String, TabFieldItem>> tab = new ArrayList<Map<String, TabFieldItem>>();
        List<VitalMultiMap> vital = new ArrayList<VitalMultiMap>();
        List<PatientEncounterItem> encounter = new ArrayList<PatientEncounterItem>();
        EditViewModelGet viewModel = new EditViewModelGet();
        IndexEncounterMedicalViewModel hpimodel = new IndexEncounterMedicalViewModel();


        if (currentUser.getTripId() == null) {
            return ok(index.render(currentUser, viewModel, hpimodel));
        }
        ServiceResponse<List<PatientEncounterItem>> patientEncounter = encounterService.returnCurrentDayPatientEncounters(currentUser.getTripId());

        //converts patient encounter Items to patient Items
        for (int i = 0; i < patientEncounter.getResponseObject().size(); i++) {
            ServiceResponse<PatientItem> translate = searchService.retrievePatientItemByPatientId(patientEncounter.getResponseObject()
                    .get(i).getPatientId());
            PatientItem e = translate.getResponseObject();
            ServiceResponse<VitalMultiMap> patientEncounterVitalMapResponse = vitalService.retrieveVitalMultiMap
                    (patientEncounter.getResponseObject().get(i).getId());
            VitalMultiMap v = patientEncounterVitalMapResponse.getResponseObject();
            //   hpimodel.getHpiFieldsWithMultipleChiefComplaints();
            ServiceResponse<TabFieldMultiMap> patientEncounterTabFieldResponse = tabService.retrieveTabFieldMultiMap
                    (patientEncounter.getResponseObject().get(i).getId());
            TabFieldMultiMap tabFieldMultiMap = patientEncounterTabFieldResponse.getResponseObject();
            Map<String, TabFieldItem> hpiFields = new HashMap<>();
            if (patientEncounter.getResponseObject().get(i).getChiefComplaints().size() >= 1) {

                for (String cc : patientEncounter.getResponseObject().get(i).getChiefComplaints()) {
                    Map<String, TabFieldItem> hpiFieldsUnderChiefComplaint = new HashMap<>();
                    hpiFieldsUnderChiefComplaint.put("narrative", tabFieldMultiMap.getMostRecentOrEmpty("narrative", cc.trim()));
                    hpimodel.setHpiFieldsWithoutMultipleChiefComplaints(hpiFieldsUnderChiefComplaint);
                    hpiFields.putAll(hpiFieldsUnderChiefComplaint);
                }

                tab.add(hpiFields);
                vital.add(v);
                p.add(e);
            }
        }
            viewModel.setTriagePatients(p);
            viewModel.setPatientEncounter(patientEncounter.getResponseObject());
            viewModel.setVitals(vital);
            viewModel.setHPI(tab);
            return ok(index.render(currentUser, viewModel, hpimodel));


    }

    public Result editPost( )
    {


        return redirect(routes.ManagerController.indexGet());

    }


}


