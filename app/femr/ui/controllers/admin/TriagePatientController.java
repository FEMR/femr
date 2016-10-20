package femr.ui.controllers.admin;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by fq251 on 10/20/2016.
 */
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR})
public class TriagePatientController extends Controller {

    public  Result triagePatient()
    {
        return ok("Hello World");

    }

}
