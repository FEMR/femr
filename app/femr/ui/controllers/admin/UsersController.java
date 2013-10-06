//for creating users
package femr.ui.controllers.admin;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IRoleService;
import femr.business.services.ISessionService;
import femr.business.services.IUserService;
import femr.common.models.IRole;
import femr.common.models.IUser;
import femr.common.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.users.CreateViewModel;
import femr.ui.views.html.admin.users.create;
import femr.ui.views.html.admin.users.index;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static femr.ui.controllers.routes.HomeController;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR})
public class UsersController extends Controller {
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);
    private ISessionService sessionService;
    private IUserService userService;
    private IRoleService roleService;
    private Provider<IUser> userProvider;

    @Inject
    public UsersController(ISessionService sessionService, IUserService userService, IRoleService roleService,
                           Provider<IUser> userProvider) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.roleService = roleService;
        this.userProvider = userProvider;
    }

    public Result index() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        List<? extends IRole> roles = roleService.getAllRoles();

        return ok(index.render(currentUser, roles));
    }

    public Result createGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        List<? extends IRole> roles = roleService.getAllRoles();

        return ok(create.render(currentUser, roles, createViewModelForm));
    }

    public Result createPost() {
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();

        IUser user = createUser(viewModel);

        Map<String, String[]> map = request().body().asFormUrlEncoded();
        String[] checkedValues = map.get("roles");
        List<Integer> checkValuesAsIntegers = new ArrayList<Integer>();
        for (String checkedValue : checkedValues) {
            checkValuesAsIntegers.add(Integer.parseInt(checkedValue));
        }

        user = assignRolesToUser(user, checkValuesAsIntegers);

        ServiceResponse<IUser> response = userService.createUser(user);

        if (!response.hasErrors()) {
            return redirect(HomeController.index());
        }

        return TODO;
    }

    private IUser assignRolesToUser(IUser user, List<Integer> checkValuesAsIntegers) {
        List<? extends IRole> roles = roleService.getRolesFromIds(checkValuesAsIntegers);
        List<IRole> roleList = new ArrayList<IRole>();

        for (IRole role : roles) {
            roleList.add(role);
        }
        user.setRoles(roleList);

        return user;
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
