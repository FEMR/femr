package femr.ui.controllers.admin;
import sun.util.calendar.Gregorian;
import com.google.inject.Inject;
import femr.business.services.core.IPatientService;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientEncounterItem;
import femr.common.models.PatientItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.configure.IndexViewModelGet;
import femr.ui.models.admin.users.ManageViewModelGet;
import femr.ui.models.sessions.CreateViewModel;
import femr.ui.models.triage.IndexViewModelPost;
import femr.ui.models.triage.ManageViewModelPost;
import femr.ui.views.html.admin.triagePatients.triagePatient;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fq251 on 10/20/2016.
 */

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE, Roles.ADMINISTRATOR})
public class TriagePatientController extends Controller {

    private final ISessionService sessionService;
    private final IPatientService patientService;
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);

    @Inject
    public TriagePatientController(ISessionService sessionService, IPatientService patientService) {
        this.sessionService = sessionService;
        this.patientService = patientService;
    }

    public Result triagePatient() {


        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();


        DateTimeFormatter dateFormat = DateTimeFormat
                .forPattern("yyyy/mm/dd HH:mm:ss");
        DateTime date = new DateTime("2015-12-13T00:00:00.618-08:00");
        DateTime date2 = new DateTime("2016-12-13T23:59:59.618-08:00");

        date.minusDays(100);
        String sql = "Select patients.first_Name, patients.last_Name As newlist\n" +
                "From patients\n" +
                "Left Outer JOIN patient_encounters ON (patient_encounters.patient_id= patients.id)\n" +
                "where current_date()+ \"00:00:00\" < patient_encounters.date_of_triage_visit;";
        ServiceResponse<List<PatientItem>> patientServiceResponse = patientService.retrieveCurrentTriagePatients(date, date2);
        ManageViewModelPost viewModel = new ManageViewModelPost();
        viewModel.setTriagePatients(patientServiceResponse.getResponseObject());


        return ok(triagePatient.render(currentUser, viewModel));
    }
}


