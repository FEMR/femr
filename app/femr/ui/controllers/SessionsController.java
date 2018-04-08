package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.data.models.core.IUser;
import femr.ui.models.sessions.CreateViewModel;
import femr.ui.views.html.sessions.create;
import femr.ui.views.html.sessions.editPassword;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SessionsController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private final ISessionService sessionsService;
    private final IUserService userService;

    @Inject
    public SessionsController(AssetsFinder assetsFinder, FormFactory formFactory, ISessionService sessionsService, IUserService userService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.sessionsService = sessionsService;
        this.userService = userService;
    }

    public Result createGet() {
        CurrentUser currentUser = sessionsService.retrieveCurrentUserSession();

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);

        if (currentUser != null) {
            return redirect(routes.HomeController.index());
        }

        return ok(create.render(createViewModelForm, 0, assetsFinder));
    }

    public Result createPost() {

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        ServiceResponse<CurrentUser> response = sessionsService.createSession(viewModel.getEmail(), viewModel.getPassword(), request().remoteAddress());

        if (response.hasErrors()) {
            return ok(create.render(createViewModelForm.bindFromRequest(), 1, assetsFinder));
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
        }

        return redirect(routes.HomeController.index());

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
}
