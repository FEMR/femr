package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.UserItem;
import femr.data.models.mysql.User;
import femr.ui.models.settings.EditViewModel;
import femr.ui.models.settings.IndexViewModelGet;
import femr.ui.views.html.settings.index;


import femr.ui.views.html.sessions.create;
import femr.util.stringhelpers.StringUtils;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;

import javax.xml.ws.Service;
import java.util.ArrayList;

import java.util.ArrayList;

public class SettingsController extends Controller {
    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private final ISessionService sessionService;
    private final IUserService userService;

    @Inject
    public SettingsController(AssetsFinder assetsFinder,
                              FormFactory formFactory,
                              ISessionService sessionService,
                              IUserService userService) {
        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    public Result index() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        return ok(femr.ui.views.html.settings.index.render(currentUser, assetsFinder));
    }

    public Result update(String languageCode) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        if (currentUser == null) {
            return unauthorized();
        }

        ServiceResponse<UserItem> userServiceResponse = userService.retrieveUser(currentUser.getId());
        if (userServiceResponse.hasErrors()) {
            return internalServerError();
        }

        UserItem userItem = userServiceResponse.getResponseObject();
        userItem.setLanguageCode(languageCode);


        ServiceResponse<UserItem> updateResponse = userService.updateUser(userItem, null);
        if (updateResponse.hasErrors()) {
            return internalServerError();
        }


        return redirect("/settings");
    }



    private EditViewModel convertToEditViewModel(UserItem userItem) {
        EditViewModel editViewModel = new EditViewModel();
        editViewModel.setUserId(userItem.getId());
        editViewModel.setFirstName(userItem.getFirstName());
        editViewModel.setLastName(userItem.getLastName());
        editViewModel.setEmail(userItem.getEmail());
        editViewModel.setPasswordReset(Boolean.toString(userItem.isPasswordReset()));
        editViewModel.setLanguageCode(userItem.getLanguageCode());
        return editViewModel;
    }

}