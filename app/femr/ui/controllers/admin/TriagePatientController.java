package femr.ui.controllers.admin;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.views.html.admin.triagePatient;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR})

/**
 * Created by fq251 on 10/20/2016.
 */

public class TriagePatientController extends Controller {
   // public static Result triagePatientName()
       // {
        //return ok("Triage Patients");
     //   return ok(views.html.);
    //}
    public  Result triagePatient()
    {
        return ok(triagePatient.render());
        //return ok("Hello World!");
    }


}
