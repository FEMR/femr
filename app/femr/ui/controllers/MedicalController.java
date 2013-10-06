package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.services.ISessionService;
import play.mvc.Controller;
import play.mvc.Result;
import femr.ui.views.html.medical.index;

public class MedicalController extends Controller {

    private ISessionService sessionService;

    @Inject
    public MedicalController(ISessionService sessionService) {
        this.sessionService = sessionService;
    }

    public Result index() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();
        return ok(index.render(currentUserSession));
    }
}
