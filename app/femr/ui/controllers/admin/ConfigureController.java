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
import femr.business.services.IConfigureService;
import femr.business.services.ISessionService;
import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;
import femr.data.models.ISystemSetting;
import femr.data.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.configure.IndexViewModelGet;
import femr.ui.models.admin.configure.IndexViewModelPost;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import femr.ui.views.html.admin.configure.index;
import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class ConfigureController extends Controller {

    private final Form<IndexViewModelPost> indexViewModelForm = Form.form(IndexViewModelPost.class);
    private ISessionService sessionService;
    private IConfigureService configureService;

    @Inject
    public ConfigureController(ISessionService sessionService,
                               IConfigureService configureService) {
        this.sessionService = sessionService;
        this.configureService = configureService;
    }

    public Result indexGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        IndexViewModelGet indexViewModel = new IndexViewModelGet();


        ServiceResponse<List<? extends ISystemSetting>> systemSettingsResponse = configureService.getCurrentSettings();
        if (systemSettingsResponse.hasErrors()) {
            throw new RuntimeException();
        }
        for (ISystemSetting ss : systemSettingsResponse.getResponseObject()) {
            indexViewModel.setSetting(ss.getName(), ss.isActive());
        }


        return ok(index.render(currentUser, indexViewModel));
    }

    public Result indexPost() {
        IndexViewModelPost viewModel = indexViewModelForm.bindFromRequest().get();

        ServiceResponse<List<? extends ISystemSetting>> systemSettingsResponse = configureService.updateSystemSettings(viewModel.getSettings());
        if (systemSettingsResponse.hasErrors()) {
            throw new RuntimeException();
        }

        return redirect(routes.AdminController.index());
    }
}
