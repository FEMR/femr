package edu.wayne.femr.util.dependencyinjection;

import com.google.inject.AbstractModule;
import edu.wayne.femr.business.services.ISessionService;
import edu.wayne.femr.business.services.IUserService;
import edu.wayne.femr.business.services.SessionService;
import edu.wayne.femr.business.services.UserService;

public class BusinessLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IUserService.class).to(UserService.class);
        bind(ISessionService.class).to(SessionService.class);
    }
}
