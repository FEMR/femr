package femr.ui.controllers;


import com.google.inject.Inject;
import femr.common.dto.CurrentUser;
import femr.business.services.IResearchService;
import femr.business.services.ISessionService;

import femr.data.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.views.html.research.index;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


/**
 * This is the controller for the research page, it is currently not supported.
 * Research was designed before combining of some tables in the database
 */
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ResearchController extends Controller {
    private IResearchService researchService;
    private ISessionService sessionService;

    /**
     * Research Controller constructer that Injects the services indicated by the parameters
     *
     * @param sessionService  {@link ISessionService}
     * @param researchService {@link IResearchService}
     */
    @Inject
    public ResearchController(ISessionService sessionService, IResearchService researchService) {
        this.researchService = researchService;
        this.sessionService = sessionService;
    }

    public Result indexGet() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();


        return ok(index.render(currentUserSession));
    }
}
