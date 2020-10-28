package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import play.mvc.*;
import femr.ui.views.html.reference.index;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class ReferenceController extends Controller {

    private final AssetsFinder assetsFinder;
    private final ISessionService sessionService;

    @Inject
    public ReferenceController(AssetsFinder assetsFinder, ISessionService sessionService ) {

        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
    }

    public Result indexGet() {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        return ok(index.render(currentUser, assetsFinder));
    }
}
