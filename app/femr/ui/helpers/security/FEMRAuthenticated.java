package femr.ui.helpers.security;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Date;
import java.util.Optional;

import static femr.ui.controllers.routes.HomeController;
import static play.mvc.Controller.session;

public class FEMRAuthenticated extends Security.Authenticator {

    private final Config configuration;

    @Inject
    public FEMRAuthenticated(Config configuration){

        this.configuration = configuration;
    }
    /*
    @Override
    public String getUsername(Http.Context ctx) {
        //check for user logged on
        if (ctx.session().get("currentUser") == null)
            return null;

        String previousTick = session("userTime");
        if (previousTick != null && !previousTick.equals("")) {
            long previousT = Long.valueOf(previousTick);
            long currentT = new Date().getTime();
            long timeout = Long.valueOf(configuration.getString("sessionTimeout")) * 1000 * 60;
            if ((currentT - previousT) > timeout) {
                // session expired
                ctx.session().clear();
                return null;
            }
        }
        // update time in session
        String tickString = Long.toString(new Date().getTime());
        ctx.session().put("userTime", tickString);

        return ctx.session().get("currentUser");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        System.out.println("onUnauthorized");
        return redirect(HomeController.index());
    }

    Old, deprecated methods not being called. New ones ahead use Http.Request instead...  */

    @Override
    public Optional<String> getUsername(Http.Request req) {
        // Check for user logged on
        Optional<String> currentUser = req.session().getOptional("currentUser");
        if (!currentUser.isPresent()) {
            return Optional.empty();
        }

        Optional<String> previousTickOpt = req.session().getOptional("userTime");
        if (previousTickOpt.isPresent()) {
            long previousT = Long.parseLong(previousTickOpt.get());
            long currentT = new Date().getTime();
            long timeout = Long.parseLong(configuration.getString("sessionTimeout")) * 1000 * 60;

            if ((currentT - previousT) > timeout) {
                // Session expired
                return Optional.empty();
            }
        }

        // Update "userTime" in the session
        String updatedTick = Long.toString(new Date().getTime());
        req.session().adding("userTime", updatedTick);

        // Return the current user
        return currentUser;
    }

    @Override
    public Result onUnauthorized(Http.Request req) {
        return redirect(HomeController.index());
    }

}