package femr.ui.controllers.admin;

import com.google.inject.Inject;
import femr.common.dto.CurrentUser;
import femr.business.services.ISessionService;
import femr.data.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.views.html.admin.index;
import play.mvc.*;

//Note: Administrative controllers still interface with pure data models
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class AdminController extends Controller {
    private ISessionService sessionService;

    @Inject
    public AdminController(ISessionService sessionService) {
        this.sessionService = sessionService;
    }

    public Result index() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        return ok(index.render(currentUser));
    }
}
