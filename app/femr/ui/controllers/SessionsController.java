package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.business.services.system.UpdatesService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.InternetStatusItem;
import femr.common.models.UserItem;
import femr.data.models.core.INetworkStatus;
import femr.data.models.core.IUser;
import femr.data.models.mysql.NetworkStatus;
import femr.data.models.mysql.SystemSetting;
import femr.ui.models.sessions.CreateViewModel;
import femr.ui.views.html.sessions.create;
import femr.ui.views.html.sessions.editPassword;
import femr.util.ThreadHelper;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import femr.util.InternetConnnection.InternetCheck;

public class SessionsController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private final ISessionService sessionsService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final IUpdatesService internetStatusService;

    @Inject
    public SessionsController(AssetsFinder assetsFinder, FormFactory formFactory, ISessionService sessionsService, IUserService userService,
                              IRoleService roleService, IUpdatesService internetStatusService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.sessionsService = sessionsService;
        this.userService = userService;
        this.roleService = roleService;
        this.internetStatusService = internetStatusService;
    }

    public Result createGet() {
        CurrentUser currentUser = sessionsService.retrieveCurrentUserSession();

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);


        if (currentUser != null) {
            return redirect(routes.HomeController.index());
        }

        return ok(create.render(createViewModelForm, 0,null, assetsFinder, new ArrayList<String>()));
    }

    public Result createPost() {

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        ServiceResponse<CurrentUser> response = sessionsService.createSession(viewModel.getEmail(), viewModel.getPassword(), request().remoteAddress());

        if (response.hasErrors()) {
            if (response.getErrors().containsValue("Not Activated.")) {
                return ok(create.render(createViewModelForm.bindFromRequest(), 3, null, assetsFinder, new ArrayList<String>()));
            } else {
                return ok(create.render(createViewModelForm.bindFromRequest(), 1, null, assetsFinder, new ArrayList<String>()));
            }
        }else{
            IUser user = userService.retrieveById(response.getResponseObject().getId());
            user.setLastLogin(dateUtils.getCurrentDateTime());
            ServiceResponse<IUser> userResponse = userService.update(user, false);
            if (userResponse.hasErrors()){
                throw new RuntimeException();
            }

            DateTime start = new DateTime(user.getPasswordCreatedDate());
            DateTime stop = new DateTime(DateTime.now());
            int daysBetween = Days.daysBetween(start, stop).getDays();

            if(daysBetween > 90){
                user.setPasswordReset(true);
            }

            if (user.getPasswordReset() == true){
                return editPasswordGet(user);
            }

            ThreadHelper threadHelper = new ThreadHelper(internetStatusService);
            Thread t = new Thread(threadHelper);
            t.start();
        }

        return redirect(routes.HomeController.index());

    }

    public Result editRegisterPost() {
        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        Form<CreateViewModel> form = createViewModelForm.bindFromRequest();

        ServiceResponse<List<String>> roleServiceResponse = roleService.retrieveAllRoles();
        if (roleServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        List<String> messages = new ArrayList<>();

        return ok(create.render(form, 2, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
    }

    public Result createAccountPost() {
        CurrentUser currentUser = sessionsService.retrieveCurrentUserSession();

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        Form<CreateViewModel> form = createViewModelForm.bindFromRequest();

        ServiceResponse<List<String>> roleServiceResponse = roleService.retrieveAllRoles();
        if (roleServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        List<String> messages = new ArrayList<>();

        if (!form.field("email").getValue().isPresent()) {
            return badRequest(create.render(form, 2, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
        }

        if (form.field("email").getValue().isPresent() &&
                !form.field("email").getValue().get().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$")) {
            messages.add("Invalid Email");
            return badRequest(create.render(form, 2, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
        }

        if (form.hasErrors()) {
            return badRequest(create.render(form, 2, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
        } else {
            CreateViewModel viewModel = form.bindFromRequest().get();
            UserItem user = createUserItem(viewModel);

            ServiceResponse<UserItem> response = userService.createUser(user, viewModel.getPassword(), 0);
            if (response.hasErrors()) {
                messages.add(response.getErrors().get(""));
                return ok(create.render(form, 2, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
            }
            else
                //added user's last name to be displayed[FEMR-161]
                //Contributed by Harsha Peswani during the CEN5035 course at FSU
                if (StringUtils.isNullOrWhiteSpace(viewModel.getLastName()))
                    messages.add("An account for " + user.getFirstName() + " was created successfully. You may begin creating a new user.");
                else
                    messages.add("An account for " + user.getFirstName() + " "+ user.getLastName() +" was created successfully. You may begin creating a new user.");


            return ok(create.render(form, 0, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
        }
    }


    public Result editPasswordGet(IUser user){

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);

        return ok(editPassword.render(user.getFirstName(), user.getLastName(), createViewModelForm, new ArrayList<String>(), assetsFinder));
    }

    public Result editPasswordPost(){

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        CurrentUser currentUser = sessionsService.retrieveCurrentUserSession();
        IUser user = userService.retrieveById(currentUser.getId());
        Boolean isNewPassword = false;

        Pattern hasLowercase = Pattern.compile("[a-z]");

        Pattern hasUppercase = Pattern.compile("[A-Z]");
        Pattern hasNumber = Pattern.compile("\\d");
        ArrayList<String> messages = new ArrayList<>();
        if (StringUtils.isNullOrWhiteSpace(viewModel.getNewPassword()))
            messages.add("password is a required field");
        else
        {
            if(viewModel.getNewPassword().length() < 8)        //AJ Saclayan Password Constraints
                messages.add("password is less than 8 characters");
            if(!hasLowercase.matcher(viewModel.getNewPassword()).find())
                messages.add("password must have a lowercase character");
            if (!hasUppercase.matcher(viewModel.getNewPassword()).find())
                    messages.add("password must have an uppercase character");

            if (!hasNumber.matcher(viewModel.getNewPassword()).find())
                    messages.add("password must have a number");

            if(!viewModel.getNewPassword().equals(viewModel.getNewPasswordVerify()))
                messages.add("passwords do not match");
            //check if new password is equal to the old password
            if(userService.checkOldPassword(viewModel.getNewPassword(),userService.retrieveById(currentUser.getId()).getPassword()))
                messages.add("password must not be the same one used before reset");


        }

        if(!messages.isEmpty())
            return ok(editPassword.render(user.getFirstName(), user.getLastName(), createViewModelForm, messages, assetsFinder));
        else
        {
            user.setPassword(viewModel.getNewPassword());
            user.setPasswordCreatedDate(DateTime.now());
            user.setPasswordReset(false);
            isNewPassword = true;
        }

        ServiceResponse<IUser> userResponse = userService.update(user, isNewPassword);
        if (userResponse.hasErrors()){
            throw new RuntimeException();
        }
        return redirect(routes.HomeController.index());
    }

    public Result delete() {
        sessionsService.invalidateCurrentUserSession();

        return redirect(routes.HomeController.index());
    }

    private UserItem createUserItem(CreateViewModel viewModel) {
        UserItem user = new UserItem();
        user.setFirstName(viewModel.getFirstName());
        user.setLastName(viewModel.getLastName());
        user.setEmail(viewModel.getEmail());
        user.setDeleted(true);
        user.setPasswordReset(false);
        user.setNotes(viewModel.getNotes());
        user.setRoles(viewModel.getRoles());
        user.setDateCreated(viewModel.getDateCreated());
        user.setLanguageCode(viewModel.getLanguage());
        return user;
    }
}
