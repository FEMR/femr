package edu.wayne.femr.ui.controllers;

import com.google.inject.Inject;
import edu.wayne.femr.business.models.CurrentUser;
import edu.wayne.femr.business.models.ServiceResponse;
import edu.wayne.femr.business.services.ISessionService;
import edu.wayne.femr.ui.views.html.home.index;
import edu.wayne.femr.ui.views.html.sessions.create;
import play.mvc.Controller;
import play.mvc.Result;

public class HomeController extends Controller {

    private ISessionService sessionService;

    @Inject
    public HomeController(ISessionService sessionService) {
        this.sessionService = sessionService;
    }

    public Result index() {
        ServiceResponse<CurrentUser> currentUserSession = sessionService.getCurrentUserSession();

        if (currentUserSession.isValid()) {
            return ok(index.render(currentUserSession.getResponseObject()));
        }

        return ok(create.render(null));
    }
}