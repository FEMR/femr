//for creating users
package femr.ui.controllers.admin;

import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;
import femr.business.services.IRoleService;
import femr.business.services.ISessionService;
import femr.business.services.IUserService;
import femr.data.models.IRole;
import femr.data.models.IUser;
import femr.data.models.Roles;
import femr.data.models.User;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.users.CreateViewModelGet;
import femr.ui.models.admin.users.CreateViewModelPost;
import femr.ui.models.admin.users.IndexViewModelGet;
import femr.ui.models.admin.users.EditUserViewModel;
import femr.ui.views.html.admin.users.*;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static femr.ui.controllers.routes.HomeController;

//Note: Administrative controllers still interface with pure data models
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class UsersController extends Controller {
    private final Form<CreateViewModelPost> createViewModelForm = Form.form(CreateViewModelPost.class);
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

        ServiceResponse<List<? extends IUser>> userServiceResponse = userService.findAllUsers();
        if (userServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        IndexViewModelGet viewModelGet = new IndexViewModelGet();

        viewModelGet.setUsers(userServiceResponse.getResponseObject());

        return ok(index.render(currentUser, viewModelGet));
    }

    //  creating a user page
    //  /admin/users/create
    public Result createGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        List<? extends IRole> roles = roleService.getAllRoles();
        CreateViewModelGet viewModelGet = new CreateViewModelGet();
        viewModelGet.setUser(new User());

        return ok(create.render(currentUser, roles, viewModelGet));
    }

    //  creating a user page
    //  /admin/users/create
    public Result createPost() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        List<? extends IRole> roles = roleService.getAllRoles();
        CreateViewModelGet viewModelGet = new CreateViewModelGet();
        CreateViewModelPost viewModel = createViewModelForm.bindFromRequest().get();
        IUser user = createUser(viewModel);
        String error;

        //make sure user entered an email address (username)
        if (StringUtils.isNullOrWhiteSpace(user.getEmail())) {
            error = "Please enter an email address.";
        } else {
            Map<String, String[]> map = request().body().asFormUrlEncoded();
            String[] checkedValues = map.get("roles");

            //make sure
            if (checkedValues == null || checkedValues.length < 1) {
                error = "Please select one or more roles.";
            } else {
                List<Integer> checkValuesAsIntegers = new ArrayList<Integer>();
                for (String checkedValue : checkedValues) {
                    checkValuesAsIntegers.add(Integer.parseInt(checkedValue));
                }
                user = assignRolesToUser(user, checkValuesAsIntegers);
                ServiceResponse<IUser> response = userService.createUser(user);

                if (!response.hasErrors()) {
                    return redirect(HomeController.index());
                } else {
                    error = response.getErrors().get("");
                }
            }
        }

        viewModelGet.setUser(user);
        viewModelGet.setError(error);

        return ok(create.render(currentUser, roles, viewModelGet));
    }

    //  edit a user dialog
    //  /admin/users/edit
    public Result editGet(Integer id) {
        EditUserViewModel editUserViewModel = new EditUserViewModel();
        IUser user = userService.findById(id);
        List<? extends IRole> roles = roleService.getAllRoles();
        editUserViewModel.setUser(user);
        editUserViewModel.setAllRoles(roles);
        return ok(editUser.render(editUserViewModel));
    }

    //  edit a user dialog
    //  /admin/users/edit/:id
    public Result editPost() {
        CreateViewModelPost viewModel = createViewModelForm.bindFromRequest().get();

        IUser user = userService.findById(viewModel.getUserId());

        Boolean isNewPassword = false;

        if (StringUtils.isNotNullOrWhiteSpace(viewModel.getEmail())) {
            user.setEmail(viewModel.getEmail());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModel.getFirstName())) {
            user.setFirstName(viewModel.getFirstName());
        }
        if (StringUtils.isNotNullOrWhiteSpace(viewModel.getLastName())) {
            user.setLastName(viewModel.getLastName());
        }
        //password reset checkbox.
        if (StringUtils.isNotNullOrWhiteSpace(viewModel.getPasswordReset()) && viewModel.getPasswordReset().equals("on")) {
            user.setPasswordReset(true);
        } else {
            user.setPasswordReset(false);
        }
        //manual password reset
        if (StringUtils.isNotNullOrWhiteSpace(viewModel.getNewPassword()) && viewModel.getNewPassword().equals(viewModel.getNewPasswordVerify())) {
            user.setPassword(viewModel.getNewPassword());
            isNewPassword = true;
        }


        if (viewModel.getRoles().size() > 0) {
            List<? extends IRole> allRoles = roleService.getAllRoles();
            List<IRole> userRoles = new ArrayList<>();
            for (IRole role : allRoles) {
                if (viewModel.getRoles().contains(role.getName())) {
                    userRoles.add(role);
                }
            }
            user.setRoles(userRoles);
        }

        ServiceResponse<IUser> updateResponse = userService.update(user, isNewPassword);
        if (updateResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            //return to manage user homepage
            return redirect(routes.UsersController.index());

        }
    }

    //  jQuery calls this when activating/deactivating a user
    //  /admin/users/toggle/:id
    public Result toggleUser(Integer id) {
        IUser user = userService.findById(id);

        //toggle user
        user.setDeleted(!user.getDeleted());
        ServiceResponse<IUser> updateResponse = userService.update(user, false);
        if (updateResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            return ok(user.getDeleted().toString());
        }
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

    private IUser createUser(CreateViewModelPost viewModel) {
        IUser user = userProvider.get();
        user.setFirstName(viewModel.getFirstName());
        user.setLastName(viewModel.getLastName());
        user.setEmail(viewModel.getEmail());
        user.setPassword(viewModel.getPassword());
        user.setLastLogin(dateUtils.getCurrentDateTime());
        user.setDeleted(false);
        user.setPasswordReset(false);
        return user;
    }
}
