package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import femr.business.services.ISessionService;
import femr.business.services.IUserService;
import femr.business.services.SessionService;
import femr.business.services.UserService;

public class BusinessLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IUserService.class).to(UserService.class);
        bind(ISessionService.class).to(SessionService.class);
    }
}
