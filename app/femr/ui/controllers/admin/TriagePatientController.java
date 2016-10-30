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
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

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


        DateTimeFormatter dateFormat = DateTimeFormat
                .forPattern("yyyy/mm/dd HH:mm:ss");
        LocalDateTime dat= LocalDateTime.now();
        String date;


//        DateTime date = new DateTime(T00:00:00");
  //      DateTime date2 = new DateTime("2016-12-13 23:59:59");
    //    date.minusDays(100);
        String sql = "Select patients.first_Name, patients.last_Name As newlist\n" +
                "From patients\n" +
                "JOIN patient_encounters ON (patient_encounters.patient_id= patients.id)\n" +
                "where current_date()+ \"00:00:00\" < patient_encounters.date_of_triage_visit;";
     //   ServiceResponse<List<PatientEncounterItem>> patientEnounterServiceResponse=encounterService.
      // ServiceResponse<List<PatientItem>> SearchServiceResponse = searchService.retrievePatientsFromQueryString(sql);
      //  ServiceResponse<List<PatientItem>> SearchServiceResponse2 = searchService.retrievePatientsForSearch(null);
        List<PatientItem> p=null;
        ServiceResponse<List<PatientEncounterItem>> patientServices=encounterService.returnTriagePatients(null,null,p);
        //List<IPatient> t=null;
        //for (PatientEncounterItem patient1 : patientEncounterService.getResponseObject()) {
          // t.add(patient1.getPa
        //}
        ManageViewModelPost viewModel = new ManageViewModelPost();
        viewModel.setEncounterItems(patientServices.getResponseObject());

       // ManageViewModelPost viewModel2 = new ManageViewModelPost();
        //viewModel.setTriagePatients(SearchServiceResponse2.getResponseObject());


        return ok(triagePatient.render(currentUser, viewModel));
    }
}


