package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.ui.models.trauma.IndexSheetView;
import femr.ui.models.triage.IndexViewModelGet;
import femr.ui.views.html.trauma.index; // this is what was needed to render the new page
import femr.ui.views.html.sessions.create;
import play.mvc.Controller;
import play.mvc.Result;

public class TraumaController extends Controller {

    private final AssetsFinder assetsFinder;
    private ISessionService sessionService;

    @Inject
    public TraumaController(AssetsFinder assetsFinder, ISessionService sessionService) {

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
