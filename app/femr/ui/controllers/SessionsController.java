package femr.ui.controllers;

import com.google.inject.Inject;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.data.models.core.IUser;
import femr.ui.models.sessions.CreateViewModel;
import femr.ui.views.html.sessions.create;
import femr.ui.views.html.sessions.editPassword;
import femr.util.calculations.dateUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class SessionsController extends Controller {
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);
    private final ISessionService sessionsService;
    private final IUserService userService;

    @Inject
    public SessionsController(ISessionService sessionsService, IUserService userService) {
        this.sessionsService = sessionsService;
        this.userService = userService;
    }

    public Result createGet() {
        CurrentUser currentUser = sessionsService.retrieveCurrentUserSession();

        if (currentUser != null) {
            return redirect(routes.HomeController.index());
        }

        return ok(create.render(createViewModelForm));
    }

    public Result createPost() {
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        ServiceResponse<CurrentUser> response = sessionsService.createSession(viewModel.getEmail(), viewModel.getPassword(), request().remoteAddress());

        if (response.hasErrors()) {
            return ok(create.render(createViewModelForm));
        }else{
            IUser user = userService.retrieveById(response.getResponseObject().getId());
            user.setLastLogin(dateUtils.getCurrentDateTime());
            ServiceResponse<IUser> userResponse = userService.update(user, false);
            if (userResponse.hasErrors()){
                throw new RuntimeException();
            }

            if (user.getPasswordReset() == true){
                return editPasswordGet(user);
            }
        }

        return redirect(routes.HomeController.index());

    }

    public Result editPasswordGet(IUser user){

        return ok(editPassword.render(user.getFirstName(), user.getLastName()));
    }

    public Result editPasswordPost(){
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        CurrentUser currentUser = sessionsService.retrieveCurrentUserSession();
        IUser user = userService.retrieveById(currentUser.getId());
        Boolean isNewPassword = false;

        if (viewModel.getNewPassword().equals(viewModel.getNewPasswordVerify())){
            user.setPassword(viewModel.getNewPassword());
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
