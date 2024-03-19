package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.common.dtos.CurrentUser;
import femr.business.services.core.ISessionService;
import femr.ui.views.html.home.index;
import femr.ui.views.html.sessions.create;
import play.mvc.Controller;
import play.mvc.Result;
import play.Logger; // Ensure this import is present

public class HomeController extends Controller {

    private final AssetsFinder assetsFinder;
    private final ISessionService sessionService; // Changed to 'final' to ensure it's initialized

    @Inject
    public HomeController(AssetsFinder assetsFinder, ISessionService sessionService) {
        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
    }

    public Result index() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        // Logging the currentUser object and its language code if not null
        Logger.debug("CurrentUser: " + (currentUser != null ? currentUser.toString() : "null"));
        if (currentUser != null) {
            Logger.debug("Language Code: " + currentUser.getLanguageCode());
            return ok(index.render(currentUser, assetsFinder));
        } else {
            Logger.debug("CurrentUser is null");
            return ok(create.render(null, 0, assetsFinder));
        }
    }
}
