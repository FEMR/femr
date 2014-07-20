package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import femr.business.services.*;
import femr.business.wrappers.sessions.ISessionHelper;
import femr.business.wrappers.sessions.SessionHelper;
import femr.data.models.ISystemSetting;

public class BusinessLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        //Business Service Injection
        bind(IInventoryService.class).to(InventoryService.class);
        bind(IMedicalService.class).to(MedicalService.class);
        bind(IPharmacyService.class).to(PharmacyService.class);
        bind(IPhotoService.class).to(PhotoService.class);
        bind(IResearchService.class).to(ResearchService.class);
        bind(IRoleService.class).to(RoleService.class);
        bind(ISearchService.class).to(SearchService.class);
        bind(ISessionHelper.class).to(SessionHelper.class);
        bind(ISessionService.class).to(SessionService.class);
        bind(IConfigureService.class).to(ConfigureService.class);
        bind(ISuperuserService.class).to(SuperuserService.class);
        bind(ITriageService.class).to(TriageService.class);
        bind(IUserService.class).to(UserService.class);
    }
}
