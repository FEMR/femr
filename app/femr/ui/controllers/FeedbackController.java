package femr.ui.controllers;


import com.google.inject.Inject;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import play.mvc.Controller;
import femr.ui.views.html.feedback.feedback;
import play.mvc.Result;
import play.mvc.Security;


@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})
public class FeedbackController extends Controller {

    private final ISessionService sessionService;

    @Inject
    public FeedbackController( ISessionService sessionService ) {

        this.sessionService = sessionService;
    }

    public Result indexGet() {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        return ok(feedback.render());
    }
}