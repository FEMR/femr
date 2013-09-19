package femr.ui.controllers.admin;

import femr.common.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.views.html.admin.index;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR})
public class AdminController extends Controller {
    public static Result index() {
        return ok(index.render(null, null));
    }
}
