package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.common.dtos.CurrentUser;
import femr.business.services.core.ISessionService;
import femr.ui.views.html.home.index;
import femr.ui.views.html.sessions.create;
import play.mvc.Controller;
import play.mvc.Result;

public class HomeController extends Controller {

    private final AssetsFinder assetsFinder;
    private ISessionService sessionService;

    @Inject
    public HomeController(AssetsFinder assetsFinder, ISessionService sessionService) {

        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
    }

    public Result index() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        if (currentUser != null) {
            return ok(index.render(currentUser, assetsFinder));
        }

        return ok(create.render(null, 0, assetsFinder));
    }




}
