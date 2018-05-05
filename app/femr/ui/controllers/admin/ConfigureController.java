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
import femr.business.services.core.IConfigureService;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.data.models.core.ISystemSetting;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.configure.IndexViewModelGet;
import femr.ui.models.admin.configure.IndexViewModelPost;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import femr.ui.views.html.admin.configure.manage;
import java.util.List;
import play.Logger;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class ConfigureController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private ISessionService sessionService;
    private IConfigureService configureService;

    @Inject
    public ConfigureController(AssetsFinder assetsFinder,
                               FormFactory formFactory,
                               ISessionService sessionService,
                               IConfigureService configureService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.sessionService = sessionService;
        this.configureService = configureService;
    }

    public Result manageGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        IndexViewModelGet indexViewModel = new IndexViewModelGet();


        ServiceResponse<List<? extends ISystemSetting>> systemSettingsResponse = configureService.retrieveCurrentSettings();
        if (systemSettingsResponse.hasErrors()) {
            throw new RuntimeException();
        }
        for (ISystemSetting ss : systemSettingsResponse.getResponseObject()) {
            indexViewModel.setSetting(ss.getName(), ss.isActive());
            indexViewModel.setDescription(ss.getName(), ss.getDescription());
        }


        return ok(manage.render(currentUser, indexViewModel, assetsFinder));
    }

    public Result managePost() {

        final Form<IndexViewModelPost> indexViewModelForm = formFactory.form(IndexViewModelPost.class);
        IndexViewModelPost viewModel = indexViewModelForm.bindFromRequest().get();

        ServiceResponse<List<? extends ISystemSetting>> systemSettingsResponse = configureService.updateSystemSettings(viewModel.getSettings());
        if (systemSettingsResponse.hasErrors()) {
            Logger.error("ConfigureController-managePost()","Failed to update System Configuration Settings");
            throw new RuntimeException();
        }

        return redirect(routes.AdminController.index());
    }
}
