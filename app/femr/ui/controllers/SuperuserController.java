package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.services.ISessionService;
import femr.common.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import femr.ui.views.html.superuser.index;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.SUPERUSER})
public class SuperuserController extends Controller {
    private ISessionService sessionService;

    @Inject
    public SuperuserController(ISessionService sessionService){
        this.sessionService = sessionService;
    }

    public Result indexGet(){

        CurrentUser currentUser = sessionService.getCurrentUserSession();
        return ok(index.render(currentUser));
    }

}
