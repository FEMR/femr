package femr.ui.controllers.manager;

import com.google.inject.Inject;
import femr.business.services.core.IEncounterService;
import femr.business.services.core.IPatientService;
import femr.business.services.core.ISearchService;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.manager.IndexViewModelGet;
import femr.ui.views.html.manager.index;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

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

    @Inject
    public ManagerController(ISessionService sessionService, IPatientService patientService,
                             IEncounterService encounterService, ISearchService searchService) {

        this.sessionService = sessionService;
        this.patientService = patientService;
        this.encounterService=encounterService;
        this.searchService=searchService;
    }

    public Result indexGet() {


        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        List<PatientItem> p=new ArrayList<PatientItem>();
        //returns a list of all triage patients
        ServiceResponse<List<PatientItem>> allPatients= searchService.retrievePatientsForSearch(null);
        //returns list of triage Patients on the current Day
        ServiceResponse<List<PatientEncounterItem>> patientEncounterServices=encounterService.retrieveCurrentDayPatientEncounters();
        //converts patient encounter Items to patient Items
        for(int i=0;i<patientEncounterServices.getResponseObject().size();i++) {
            ServiceResponse<PatientItem> translate = searchService.retrievePatientItemByPatientId(patientEncounterServices.getResponseObject().get(i).getPatientId());
           PatientItem e=translate.getResponseObject();
            p.add(e);
        }

        //sets Patients Items in view model used
        IndexViewModelGet viewModel = new IndexViewModelGet();
        viewModel.setTriagePatients(p);


        return ok(index.render(currentUser, viewModel));
    }


}


