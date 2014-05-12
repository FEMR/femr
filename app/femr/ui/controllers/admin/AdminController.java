package femr.ui.controllers.admin;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.services.IPharmacyService;
import femr.business.services.ISessionService;
import femr.common.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.views.html.admin.*;
import play.mvc.*;

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
