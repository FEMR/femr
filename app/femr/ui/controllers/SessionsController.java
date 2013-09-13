package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.models.CurrentUser;
import femr.business.models.ServiceResponse;
import femr.business.services.ISessionService;
import femr.ui.models.sessions.CreateViewModel;
import femr.ui.views.html.sessions.create;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class SessionsController extends Controller {

    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);
    private final ISessionService sessionsService;

    @Inject
    public SessionsController(ISessionService sessionsService) {
        this.sessionsService = sessionsService;
    }

    public Result createGet() {
        ServiceResponse<CurrentUser> response = sessionsService.getCurrentUserSession();

        if (response.isValid()) {
            return redirect(routes.HomeController.index());
        }

        return ok(create.render(createViewModelForm));
    }

    public Result createPost() {
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        ServiceResponse<CurrentUser> user = sessionsService.createSession(viewModel.email, viewModel.password);

        if (user.isValid()) {
            return redirect(routes.HomeController.index());
        }

        return ok(create.render(createViewModelForm));
    }

    public Result delete() {
        sessionsService.invalidateCurrentUserSession();

        return redirect(routes.HomeController.index());
    }
}
