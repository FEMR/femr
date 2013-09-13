package femr.ui.controllers.admin;

import com.google.inject.Inject;
import femr.business.services.IUserService;
import femr.ui.models.admin.users.CreateViewModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class UsersController extends Controller {
    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);
    private IUserService userService;

    @Inject
    public UsersController(IUserService userService) {
        this.userService = userService;
    }

    public Result createGet() {
        return TODO;
    }

    public Result createPost() {
        return TODO;
    }
}
