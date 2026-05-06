package femr.ui.helpers.security;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import femr.business.services.core.IUserService;
import femr.data.models.core.IUser;
import org.joda.time.DateTime;
import org.joda.time.Days;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Date;

import static femr.ui.controllers.routes.HomeController;
import static femr.ui.controllers.routes.SessionsController;
import static play.mvc.Controller.session;

public class FEMRAuthenticated extends Security.Authenticator {

    private final Config configuration;
    private final IUserService userService;

    @Inject
    public FEMRAuthenticated(Config configuration, IUserService userService){

        this.configuration = configuration;
        this.userService = userService;
    }

    @Override
    public String getUsername(Http.Context ctx) {
        //check for user logged on
        if (session("currentUser") == null)
            return null;

        String previousTick = session("userTime");
        if (previousTick != null && !previousTick.equals("")) {
            long previousT = Long.valueOf(previousTick);
            long currentT = new Date().getTime();
            long timeout = Long.valueOf(configuration.getString("sessionTimeout")) * 1000 * 60;
            if ((currentT - previousT) > timeout) {
                // session expired
                session().clear();
                return null;
            }
        }

        // update time in session
        String tickString = Long.toString(new Date().getTime());
        session("userTime", tickString);

        String currentUserId = ctx.session().get("currentUser");
        if (mustRedirectToPasswordReset(currentUserId, ctx.request().path())) {
            session("forcePasswordReset", "true");
            return null;
        }
        session().remove("forcePasswordReset");

        return currentUserId;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        if (session("currentUser") != null && "true".equals(session("forcePasswordReset"))) {
            return redirect(SessionsController.editPasswordGet());
        }
        return redirect(HomeController.index());
    }

    private boolean mustRedirectToPasswordReset(String currentUserId, String path) {
        if (currentUserId == null || isAllowedPathWhenPasswordExpired(path)) {
            return false;
        }

        try {
            IUser user = userService.retrieveById(Integer.parseInt(currentUserId));
            if (user == null) {
                return false;
            }

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
        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean isAllowedPathWhenPasswordExpired(String path) {
        if (path == null) {
            return false;
        }

        return path.equals(SessionsController.editPasswordGet().url())
                || path.equals(SessionsController.editPasswordPost().url())
                || path.equals(SessionsController.delete().url())
                || path.startsWith("/assets/");
    }

    private boolean isDefaultSeedUser(IUser user) {
        if (user == null || user.getEmail() == null) {
            return false;
        }

        String email = user.getEmail().trim().toLowerCase();
        return "admin".equals(email) || "superuser".equals(email);
    }
}