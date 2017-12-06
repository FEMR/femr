package femr.ui.controllers;


import com.google.inject.Inject;
import femr.business.services.core.ISessionService;
import play.mvc.Controller;
import femr.ui.views.html.feedback.feedback;
import play.mvc.Result;

public class FeedbackController extends Controller {

    private final ISessionService sessionService;

    @Inject
    public FeedbackController( ISessionService sessionService ) {

        this.sessionService = sessionService;
    }


    public Result indexGet() {

        return ok(feedback.render(currentUser));
    }


}
