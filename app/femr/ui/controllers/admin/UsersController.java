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
import femr.data.models.Role;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.users.CreateViewModelPost;
import femr.ui.models.admin.users.CreateViewModelGet;
import femr.ui.models.admin.users.EditUserViewModel;
import femr.ui.views.html.admin.users.create;
import femr.ui.views.html.admin.users.editUser;
import femr.ui.views.html.admin.users.index;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.joda.time.DateTime;
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
            return internalServerError();
        }
        CreateViewModelGet viewModelGet = new CreateViewModelGet();

        viewModelGet.setUsers(userServiceResponse.getResponseObject());

        return ok(index.render(currentUser, viewModelGet));
    }

    public Result createGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        List<? extends IRole> roles = roleService.getAllRoles();

        return ok(create.render(currentUser, roles, createViewModelForm));
    }

    public Result createPost() {
        CreateViewModelPost viewModel = createViewModelForm.bindFromRequest().get();
        if (viewModel.getUserId() != null && viewModel.getUserId() > 0) {                           //editing a user
            IUser user = userService.findById(viewModel.getUserId());

            if (viewModel.getIsDeleted() != null){                                      //activating/deactivating a user
                user.setDeleted(viewModel.getIsDeleted());
                ServiceResponse<IUser> updateResponse = userService.update(user);

                if (updateResponse.hasErrors()){
                    return internalServerError();
                }else{
                    String buttonText = "Deactivate";
                    if (user.getDeleted() == true){
                        buttonText = "Activate";
                    }
                    //return text for button(used by js)
                    return ok(buttonText);
                }
            }else{                                                                      //editing user info
                if (StringUtils.isNotNullOrWhiteSpace(viewModel.getEmail())){
                    user.setEmail(viewModel.getEmail());
                }
                if (StringUtils.isNotNullOrWhiteSpace(viewModel.getFirstName())){
                    user.setFirstName(viewModel.getFirstName());
                }
                if (StringUtils.isNotNullOrWhiteSpace(viewModel.getLastName())){
                    user.setLastName(viewModel.getLastName());
                }


                ServiceResponse<IUser> updateResponse = userService.update(user);
                if (updateResponse.hasErrors()){
                    return internalServerError();
                }else{
                    //return to manage user homepage
                    return redirect(routes.UsersController.index());

                }
            }

        } else {                                                                                    //creating a new user
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
        }

        return TODO;
    }

    public Result getEditPartial(Integer id){
        EditUserViewModel editUserViewModel = new EditUserViewModel();
        IUser user = userService.findById(id);
        List<? extends IRole> roles = roleService.getAllRoles();
        editUserViewModel.setUser(user);
        editUserViewModel.setAllRoles(roles);

        return ok(editUser.render(editUserViewModel));
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
        return user;
    }
}
