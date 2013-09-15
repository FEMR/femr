package femr.ui.helpers.security;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import static femr.ui.controllers.routes.HomeController;

public class FEMRAuthenticated extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("currentUser");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect(HomeController.index());
    }
}