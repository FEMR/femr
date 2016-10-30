package femr.ui.controllers.admin;

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
import femr.ui.models.sessions.CreateViewModel;
import femr.ui.models.triage.ManageViewModelPost;
import femr.ui.views.html.admin.triagePatients.triagePatient;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fq251 on 10/20/2016.
 */

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE, Roles.ADMINISTRATOR})
public class TriagePatientController extends Controller {

    private final ISessionService sessionService;
    private final IPatientService patientService;
    private final IEncounterService encounterService;
    private final ISearchService searchService;
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);

    @Inject
    public TriagePatientController(ISessionService sessionService, IPatientService patientService,
                                   IEncounterService encounterService,ISearchService searchService) {
        this.sessionService = sessionService;
        this.patientService = patientService;
        this.encounterService=encounterService;
        this.searchService=searchService;
    }

    public Result triagePatient() {


        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        String sql = "Select patients.first_Name, patients.last_Name As newlist\n" +
                "From patients\n" +
                "JOIN patient_encounters ON (patient_encounters.patient_id= patients.id)\n" +
                "where current_date()+ \"00:00:00\" < patient_encounters.date_of_triage_visit;";

        List<PatientItem> p=new ArrayList<PatientItem>();

        ServiceResponse<List<PatientItem>> allPatients= searchService.retrievePatientsForSearch(null);
        ServiceResponse<List<PatientEncounterItem>> patientEncounterServices=encounterService.returnTriagePatients(null,null,p);
        for(int i=0;i<patientEncounterServices.getResponseObject().size();i++) {
            ServiceResponse<PatientItem> translate = searchService.retrievePatientItemByPatientId(patientEncounterServices.getResponseObject().get(i).getPatientId());
           PatientItem e=translate.getResponseObject();
            p.add(e);
        }


        ManageViewModelPost viewModel = new ManageViewModelPost();
        viewModel.setTriagePatients(p);


        return ok(triagePatient.render(currentUser, viewModel));
    }


}


