package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISessionService;
import femr.business.services.IUserService;
import femr.common.models.IUser;
import femr.ui.models.sessions.CreateViewModel;
import femr.ui.views.html.sessions.*;
import femr.util.calculations.dateUtils;
import org.h2.command.ddl.CreateView;
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
        CurrentUser currentUser = sessionsService.getCurrentUserSession();

        if (currentUser != null) {
            return redirect(routes.HomeController.index());
        }

        return ok(create.render(createViewModelForm));
    }

    public Result createPost() {
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        ServiceResponse<CurrentUser> response = sessionsService.createSession(viewModel.getEmail(), viewModel.getPassword());

        if (response.hasErrors()) {
            return ok(create.render(createViewModelForm));
        }else{
            IUser user = userService.findById(response.getResponseObject().getId());
            user.setLastLogin(dateUtils.getCurrentDateTime());
            ServiceResponse<IUser> userResponse = userService.update(user, false);
            if (userResponse.hasErrors()){
                return internalServerError();
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
        CurrentUser currentUser = sessionsService.getCurrentUserSession();
        IUser user = userService.findById(currentUser.getId());
        Boolean isNewPassword = false;

        if (viewModel.getNewPassword().equals(viewModel.getNewPasswordVerify())){
            user.setPassword(viewModel.getNewPassword());
            user.setPasswordReset(false);
            isNewPassword = true;
        }

        ServiceResponse<IUser> userResponse = userService.update(user, isNewPassword);
        if (userResponse.hasErrors()){
            return internalServerError();
        }
        return redirect(routes.HomeController.index());
    }

    public Result delete() {
        sessionsService.invalidateCurrentUserSession();

        return redirect(routes.HomeController.index());
    }
}
