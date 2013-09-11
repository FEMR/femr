package edu.wayne.femr.controllers;

import com.google.inject.Inject;
import edu.wayne.femr.business.services.ISessionService;
import edu.wayne.femr.data.models.User;
import edu.wayne.femr.models.sessions.CreateViewModel;
import edu.wayne.femr.views.html.sessions.create;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

public class SessionsController extends Controller {

    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);
    private ISessionService sessionsService;

    @Inject
    public SessionsController(ISessionService sessionsService) {
        this.sessionsService = sessionsService;
    }

    public Result createGet() {
        return ok(create.render(createViewModelForm));
    }

    public Result createPost() {
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        User user = sessionsService.createSession(viewModel.email, viewModel.password);

        return Results.TODO;
    }
}
