package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import play.mvc.Controller;
import play.mvc.Result;

public class SettingsController extends Controller {
    private final AssetsFinder assetsFinder;
    private ISessionService sessionService;

    @Inject
    public SettingsController(AssetsFinder assetsFinder, ISessionService sessionService) {

        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
    }

    public Result index() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        return ok(femr.ui.views.html.settings.index.render(currentUser, assetsFinder));
    }
}
