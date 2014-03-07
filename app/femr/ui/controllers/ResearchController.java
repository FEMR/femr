package femr.ui.controllers;

/**
 * This is the controller for the research page
 */

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.services.IResearchService;
import femr.business.services.ISessionService;

import femr.common.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.views.html.research.index;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ResearchController extends Controller {
    private IResearchService researchService;
    private ISessionService sessionService;

    @Inject
    public ResearchController(ISessionService sessionService) {
       // this.researchService = researchService;
        this.sessionService = sessionService;
    }

    public Result index() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        return ok(index.render(currentUserSession, null)) ;
    }


    //TODO-RESEARCH: Add the code for the Research controller here
}
