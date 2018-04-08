package femr.ui.controllers;


import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.common.dtos.CurrentUser;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import play.mvc.Controller;
import femr.ui.views.html.feedback.feedback;
import femr.ui.models.feedback.IndexViewModelPost;
import play.mvc.Result;
import play.mvc.Security;

import play.data.Form;
import play.data.FormFactory;


@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE, Roles.MANAGER, Roles.RESEARCHER, Roles.ADMINISTRATOR})

public class FeedbackController extends Controller {

    private final AssetsFinder assetsFinder;
    private final ISessionService sessionService;
    private final FormFactory formFactory;
    private final IUserService userService;


    @Inject
    public FeedbackController( AssetsFinder assetsFinder,
                               ISessionService sessionService,
                               FormFactory formFactory,
                               IUserService userService) {

        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
        this.formFactory = formFactory;
        this.userService = userService;
    }



    // GET
    public Result indexGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        return ok(feedback.render(currentUser, assetsFinder));
    }

    // POST
    public Result indexPost() {
        final Form<IndexViewModelPost> IndexViewModelForm = formFactory.form(IndexViewModelPost.class);
        IndexViewModelPost viewModel = IndexViewModelForm.bindFromRequest().get();
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        userService.createFeedback(viewModel.getFeedbackMsg());


        return redirect("/?feedback=received");

    }


}