package femr.ui.helpers.security;

import play.Play;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Date;

import static femr.ui.controllers.routes.HomeController;
import static play.mvc.Controller.session;

public class FEMRAuthenticated extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        //check for user logged on
        if (session("currentUser") == null)
            return null;

        String previousTick = session("userTime");
        if (previousTick != null && !previousTick.equals("")) {
            long previousT = Long.valueOf(previousTick);
            long currentT = new Date().getTime();
            long timeout = Long.valueOf(Play.application().configuration().getString("sessionTimeout")) * 1000 * 60;
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