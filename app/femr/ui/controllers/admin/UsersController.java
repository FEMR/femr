package femr.ui.controllers.admin;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISessionService;
import femr.business.services.IUserService;
import femr.common.models.IUser;
import femr.common.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.users.CreateViewModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR})
public class UsersController extends Controller {
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);
    private ISessionService sessionService;
    private IUserService userService;
    private Provider<IUser> userProvider;

    @Inject
    public UsersController(ISessionService sessionService, IUserService userService, Provider<IUser> userProvider) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.userProvider = userProvider;
    }

    public Result createGet() {
        ServiceResponse<CurrentUser> currentUserSession = sessionService.getCurrentUserSession();
        CurrentUser currentUser = currentUserSession.getResponseObject();

        return ok(femr.ui.views.html.admin.users.create.render(currentUser, createViewModelForm));
    }

    public Result createPost() {
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();

        IUser user = createUser(viewModel);

        ServiceResponse<IUser> response = userService.createUser(user);

        if (response.isSuccessful()) {
            return redirect(femr.ui.controllers.routes.HomeController.index());
        }

        return TODO;
    }

    private IUser createUser(CreateViewModel viewModel) {
        IUser user = userProvider.get();
        user.setFirstName(viewModel.getFirstName());
        user.setLastName(viewModel.getLastName());
        user.setEmail(viewModel.getEmail());
        user.setPassword(viewModel.getPassword());

        return user;
    }
}
