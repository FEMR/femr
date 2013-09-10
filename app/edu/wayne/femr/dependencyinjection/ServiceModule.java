package edu.wayne.femr.dependencyinjection;

import com.google.inject.AbstractModule;
import edu.wayne.femr.business.services.ISessionService;
import edu.wayne.femr.business.services.SessionService;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ISessionService.class).to(SessionService.class);
    }
}
