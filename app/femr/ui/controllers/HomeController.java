package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISessionService;
import femr.ui.views.html.home.index;
import femr.ui.views.html.sessions.create;
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

        if (currentUserSession.isSuccessful()) {
            return ok(index.render(currentUserSession.getResponseObject()));
        }

        return ok(create.render(null));
    }
}