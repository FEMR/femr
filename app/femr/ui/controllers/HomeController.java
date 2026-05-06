package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.common.dtos.CurrentUser;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.data.models.core.IUser;
import femr.ui.views.html.home.index;
import femr.ui.views.html.sessions.create;
import org.joda.time.DateTime;
import org.joda.time.Days;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;

public class HomeController extends Controller {

    private final AssetsFinder assetsFinder;
    private final ISessionService sessionService;
    private final IUserService userService;

    @Inject
    public HomeController(AssetsFinder assetsFinder, ISessionService sessionService, IUserService userService) {

        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    public Result index() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        if (currentUser != null) {
            IUser user = userService.retrieveById(currentUser.getId());
            if (user != null && mustResetPassword(user)) {
                return redirect(routes.SessionsController.editPasswordGet());
            }

            return ok(index.render(currentUser, assetsFinder));
        }

        return ok(create.render(null, 0, null, assetsFinder, new ArrayList<String>()));
    }

    private boolean mustResetPassword(IUser user) {
        if (isDefaultSeedUser(user)) {
            return false;
        }

        boolean isPasswordExpired = false;
        if (user.getPasswordCreatedDate() != null) {
            DateTime start = new DateTime(user.getPasswordCreatedDate());
            DateTime stop = new DateTime(DateTime.now());
            int daysBetween = Days.daysBetween(start, stop).getDays();
            isPasswordExpired = daysBetween > 90;
        }

        return Boolean.TRUE.equals(user.getPasswordReset()) || isPasswordExpired;
    }

    private boolean isDefaultSeedUser(IUser user) {
        if (user == null || user.getEmail() == null) {
            return false;
        }

        String email = user.getEmail().trim().toLowerCase();
        return "admin".equals(email) || "superuser".equals(email);
    }




}
