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
package femr.ui.controllers.admin;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.common.dtos.CurrentUser;
import femr.business.services.core.ISessionService;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.views.html.admin.index;
import play.mvc.*;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class AdminController extends Controller {

    private final AssetsFinder assetsFinder;
    private ISessionService sessionService;

    @Inject
    public AdminController(AssetsFinder assetsFinder, ISessionService sessionService) {

        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
    }

    public Result index() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        return ok(index.render(currentUser, assetsFinder));
    }
}
