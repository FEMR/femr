/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
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
