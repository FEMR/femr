package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import femr.business.services.*;
import femr.business.wrappers.sessions.ISessionHelper;
import femr.business.wrappers.sessions.SessionHelper;

public class BusinessLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IUserService.class).to(UserService.class);
        bind(IRoleService.class).to(RoleService.class);
        bind(ISessionService.class).to(SessionService.class);
        bind(ITriageService.class).to(TriageService.class);
        bind(ISessionHelper.class).to(SessionHelper.class);
        bind(ISearchService.class).to(SearchService.class);
        bind(IPharmacyService.class).to(PharmacyService.class);
        bind(IMedicalService.class).to(MedicalService.class);
    }
}
