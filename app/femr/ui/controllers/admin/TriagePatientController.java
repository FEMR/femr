package femr.ui.controllers.admin;
import com.google.inject.Inject;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import femr.ui.views.html.admin.triagePatients.triagePatient;

/**
 * Created by fq251 on 10/20/2016.
 */

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE, Roles.ADMINISTRATOR})
public class TriagePatientController extends Controller {

    private final ISessionService sessionService;

    @Inject
    public TriagePatientController(ISessionService sessionService){
        this.sessionService = sessionService;
    }

   // public static Result triagePatientName()
       // {
        //return ok("Triage Patients");
     //   return ok(views.html.);
    //}
    public  Result triagePatient()
    {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        return ok(triagePatient.render(currentUser));
        //return ok("Hello World!");
    }


}
