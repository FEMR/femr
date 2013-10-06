package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import femr.business.services.*;

public class BusinessLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IUserService.class).to(UserService.class);
        bind(IRoleService.class).to(RoleService.class);
        bind(ISessionService.class).to(SessionService.class);
        bind(ITriageService.class).to(TriageService.class);
        bind(ISearchService.class).to(SearchService.class);
    }
}
