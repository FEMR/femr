/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.ui.controllers.admin;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.IMissionTripService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.business.services.core.IRoleService;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.common.models.MissionTripItem;
import femr.common.models.UserItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.users.*;
import femr.ui.views.html.admin.users.manage;
import femr.ui.views.html.admin.users.create;
import femr.ui.views.html.admin.users.edit;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import java.util.ArrayList;
import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class UsersController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private IMissionTripService missionTripService;
    private ISessionService sessionService;
    private IUserService userService;
    private IRoleService roleService;

    @Inject
    public UsersController(AssetsFinder assetsFinder,
                           FormFactory formFactory,
                           IMissionTripService missionTripService,
                           ISessionService sessionService,
                           IUserService userService,
                           IRoleService roleService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.missionTripService = missionTripService;
        this.sessionService = sessionService;
        this.userService = userService;
        this.roleService = roleService;
    }

    //Manage all users
    public Result manageGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        ServiceResponse<List<UserItem>> userServiceResponse = userService.retrieveAllUsers();
        if (userServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        ManageViewModelGet viewModelGet = new ManageViewModelGet();
        viewModelGet.setUsers(userServiceResponse.getResponseObject());

        return ok(manage.render(currentUser, viewModelGet, assetsFinder));
    }

    //Create a new User
    public Result createGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);

        ServiceResponse<List<String>> roleServiceResponse = roleService.retrieveAllRoles();
        if (roleServiceResponse.hasErrors()){
            throw new RuntimeException();
        }

        return ok(create.render(currentUser, createViewModelForm, new ArrayList<String>(), roleServiceResponse.getResponseObject(), assetsFinder));
    }

    //Create a new User
    public Result createPost() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        Form<CreateViewModel> form = createViewModelForm.bindFromRequest();
        ServiceResponse<List<String>> roleServiceResponse = roleService.retrieveAllRoles();
        if (roleServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        List<String> messages = new ArrayList<>();


        if (form.hasErrors()) {

            return badRequest(create.render(currentUser, form, messages, roleServiceResponse.getResponseObject(), assetsFinder));
        } else {
            CreateViewModel viewModel = form.bindFromRequest().get();
            UserItem user = createUserItem(viewModel);

            ServiceResponse<UserItem> response = userService.createUser(user, viewModel.getPassword(), currentUser.getId());
            if (response.hasErrors()) {
                messages.add(response.getErrors().get(""));
                return ok(create.render(currentUser, form, messages, roleServiceResponse.getResponseObject(), assetsFinder));
            }
            else
                //added user's last name to be displayed[FEMR-161]
                //Contributed by Harsha Peswani during the CEN5035 course at FSU
                if (StringUtils.isNullOrWhiteSpace(viewModel.getLastName()))
                    messages.add("An account for " + user.getFirstName() + " was created successfully. You may begin creating a new user.");
                else
                    messages.add("An account for " + user.getFirstName() + " "+ user.getLastName() +" was created successfully. You may begin creating a new user.");


            return ok(create.render(currentUser, createViewModelForm, messages, roleServiceResponse.getResponseObject(), assetsFinder));
        }
    }

    //  edit a user dialog
    //  /admin/users/edit
    public Result editGet(Integer id) {
        if (id == null){
            return internalServerError();
        }
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        EditViewModel editUserViewModel = new EditViewModel();

        ServiceResponse<UserItem> userItemServiceResponse = userService.retrieveUser(id);
        if (userItemServiceResponse.hasErrors()) {
            return internalServerError();
        }

        ServiceResponse<List<MissionTripItem>> missionTripItemServiceResponse = missionTripService.retrieveAllTripInformationByUserId(id);
        if (missionTripItemServiceResponse.hasErrors()){
            return internalServerError();
        }


        UserItem userItem = userItemServiceResponse.getResponseObject();
        editUserViewModel.setUserId(userItem.getId());
        editUserViewModel.setFirstName(userItem.getFirstName());
        editUserViewModel.setLastName(userItem.getLastName());
        editUserViewModel.setEmail(userItem.getEmail());
        editUserViewModel.setPasswordReset(Boolean.toString(userItem.isPasswordReset()));
        editUserViewModel.setRoles(userItem.getRoles());
        editUserViewModel.setNotes(userItem.getNotes());
        editUserViewModel.setMissionTripItems(missionTripItemServiceResponse.getResponseObject());

        Form<EditViewModel> editViewModelForm = formFactory.form(EditViewModel.class);
        editViewModelForm = editViewModelForm.fill(editUserViewModel);


        ServiceResponse<List<String>> roleServiceResponse = roleService.retrieveAllRoles();
        if (roleServiceResponse.hasErrors()){
            return internalServerError();
        }

        return ok(edit.render(currentUser, editViewModelForm, roleServiceResponse.getResponseObject(), new ArrayList<String>(), assetsFinder));
    }

    //  edit a user dialog
    //  /admin/users/edit/:id
    public Result editPost(Integer id) {
        if (id == null){
            return internalServerError();
        }
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        Form<EditViewModel> editViewModelForm = formFactory.form(EditViewModel.class);
        Form<EditViewModel> form = editViewModelForm.bindFromRequest();

        ServiceResponse<List<String>> roleServiceResponse = roleService.retrieveAllRoles();
        if (roleServiceResponse.hasErrors()){
            return internalServerError();
        }

        if (form.hasErrors()){

            return badRequest(edit.render(currentUser, form, roleServiceResponse.getResponseObject(), new ArrayList<String>(), assetsFinder));
        }else{
            EditViewModel viewModel = form.bindFromRequest().get();
            ServiceResponse<UserItem> userServiceResponse = userService.retrieveUser(id);

            if (userServiceResponse.hasErrors()){
                return internalServerError();
            }
            UserItem userItem = userServiceResponse.getResponseObject();
            userItem.setId(id);
            userItem.setFirstName(viewModel.getFirstName());
            userItem.setLastName(viewModel.getLastName());
            userItem.setNotes(viewModel.getNotes());

            //mark the password reset flag if necessary
            if (StringUtils.isNotNullOrWhiteSpace(viewModel.getPasswordReset()) && viewModel.getPasswordReset().equals("on")){
                userItem.setPasswordReset(true);
            }else{
                userItem.setPasswordReset(false);
            }
            //change the users password if filled out
            String newPassword = null;
            if (StringUtils.isNotNullOrWhiteSpace(viewModel.getNewPassword()) && viewModel.getNewPassword().equals(viewModel.getNewPasswordVerify())) {
                newPassword = viewModel.getNewPassword();
            }

            if (viewModel.getRoles().size() > 0) {
                ServiceResponse<List<String>> allRolesServiceResponse = roleService.retrieveAllRoles();
                List<String> allRoles = allRolesServiceResponse.getResponseObject();
                List<String> userRoles = new ArrayList<>();
                for (String role : allRoles) {
                    if (viewModel.getRoles().contains(role)) {
                        userRoles.add(role);
                    }
                }
                userItem.setRoles(userRoles);
            }
           
            ServiceResponse<UserItem> updateResponse = userService.updateUser(userItem, newPassword);
            if (updateResponse.hasErrors()) {
                throw new RuntimeException();
            } else {

                //return to manage user homepage
                return redirect(routes.UsersController.manageGet());
            }
        }
    }

    //  AJAX calls this when activating/deactivating a user
    //  /admin/users/toggle/:id
    public Result toggleUser(Integer id) {
        ServiceResponse<UserItem> updateResponse = userService.toggleUser(id);

        if (updateResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            return ok(Boolean.toString(updateResponse.getResponseObject().isDeleted()));
        }
    }


    private UserItem createUserItem(CreateViewModel viewModel) {
        UserItem user = new UserItem();
        user.setFirstName(viewModel.getFirstName());
        user.setLastName(viewModel.getLastName());
        user.setEmail(viewModel.getEmail());
        user.setDeleted(false);
        user.setPasswordReset(false);
        user.setNotes(viewModel.getNotes());
        user.setRoles(viewModel.getRoles());
        user.setDateCreated(viewModel.getDateCreated());
        return user;
    }


}
