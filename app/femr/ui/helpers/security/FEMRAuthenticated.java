package femr.ui.helpers.security;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Date;

import static femr.ui.controllers.routes.HomeController;
import static play.mvc.Controller.session;

public class FEMRAuthenticated extends Security.Authenticator {

    private final Config configuration;

    @Inject
    public FEMRAuthenticated(Config configuration){

        this.configuration = configuration;
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

        return ctx.session().get("currentUser");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect(HomeController.index());
    }
}