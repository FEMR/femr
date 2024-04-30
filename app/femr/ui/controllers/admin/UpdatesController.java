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
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUpdatesService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.data.models.core.IDatabaseStatus;
import femr.data.models.core.IKitStatus;
import femr.data.models.core.ILanguageCode;
import femr.data.models.core.INetworkStatus;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.updates.IndexViewModelGet;
import femr.ui.views.html.admin.updates.manage;
import play.Logger;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class UpdatesController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private ISessionService sessionService;
    private IUpdatesService updatesService;

    private List<String> messages;

    @Inject
    public UpdatesController(AssetsFinder assetsFinder,
                             FormFactory formFactory,
                             ISessionService sessionService,
                             IUpdatesService updatesService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.sessionService = sessionService;
        this.updatesService = updatesService;
        this.messages = new ArrayList<>();
    }

    public Result manageGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        IndexViewModelGet indexViewModel = new IndexViewModelGet();

        ServiceResponse<List<? extends INetworkStatus>> networkStatusesResponse = updatesService.retrieveNetworkStatuses();
        if (networkStatusesResponse.hasErrors()) {
            throw new RuntimeException();
        }
        for (INetworkStatus ns : networkStatusesResponse.getResponseObject()) {
            indexViewModel.setNetworkStatus(ns.getName(), ns.getValue());
        }

        ServiceResponse<List<? extends IKitStatus>> kitStatusesResponse = updatesService.retrieveKitStatuses();
        if (kitStatusesResponse.hasErrors()) {
            throw new RuntimeException();
        }
        for (IKitStatus ks : kitStatusesResponse.getResponseObject()) {
            indexViewModel.setKitStatus(ks.getName(), ks.getValue());
        }

        ServiceResponse<List<? extends IDatabaseStatus>> databaseStatusResponse = updatesService.retrieveDatabaseStatuses();
        if (databaseStatusResponse.hasErrors()) {
            throw new RuntimeException();
        }
        for (IDatabaseStatus ds : databaseStatusResponse.getResponseObject()) {
            indexViewModel.setDatabaseStatus(ds.getName(), ds.getValue());
        }

        //TODO setIsUpdateAvailable according to something --- Team Lemur

        ServiceResponse<List<? extends ILanguageCode>> languagesResponse = updatesService.retrieveLanguages();
        if(languagesResponse.hasErrors()){
            throw new RuntimeException();
        }
        for(ILanguageCode lang : languagesResponse.getResponseObject()) {
            indexViewModel.setLanguages(lang);
        }

        return ok(manage.render(currentUser, indexViewModel, assetsFinder, messages));
    }
    public Result databasePost() {
        ServiceResponse<List<? extends IDatabaseStatus>> databaseStatusesResponse = updatesService.updateDatabaseStatuses();
        this.messages = new ArrayList<>();

        if (databaseStatusesResponse.hasErrors()) {
            Logger.error("UpdatesController-databasePost()","Failed to update statuses");
            messages.add("Failed to back up database");
        }
        else
        {
            messages.add("The database was successfully backed up.");
        }

        return manageGet();
    }
    public Result kitUpdatePost() {
        this.messages = new ArrayList<>();

        // TODO just run the script here
        // TODO need to check errors
        ServiceResponse<List<? extends IKitStatus>> kitStatusesResponse = updatesService.updateKitStatuses();

        if (kitStatusesResponse.hasErrors()) {
            Logger.error("UpdatesController-kitUpdatePost()","Failed to update statuses");
            throw new RuntimeException();
        }

        else {
            messages.add("The kit was successfully backed up.");
        }

        return manageGet();
    }
    public Result refreshInternetStatus() {
        ServiceResponse<List<? extends INetworkStatus>> updateResponse = updatesService.updateNetworkStatuses();
        if (updateResponse.hasErrors()) {
            System.out.println("Update Response Error: " + updateResponse.getErrors());
            throw new RuntimeException();
        }

        return manageGet();
    }

    public Result updateLanguage(){
        String langCode = request().getQueryString("code");
        boolean updateScheduled = request().getQueryString("update").equals("true");

        ServiceResponse<List<? extends ILanguageCode>> updateResponse = updatesService.updateLanguage(langCode,updateScheduled);
        if(updateResponse.hasErrors()){
            throw new RuntimeException();
        }

        return manageGet();
    }

    public Result downloadPackages(){
        String langCode = request().getQueryString("code");

        ServiceResponse<List<? extends ILanguageCode>> downloadPackagesResponse = updatesService.downloadPackages(langCode);
        if(downloadPackagesResponse.hasErrors()) {
            throw new RuntimeException();
        }
        return manageGet();
    }

    public Result initializeLanguages(){
        ServiceResponse<List<? extends ILanguageCode>> initializeLanguagesResponse = updatesService.initializeLanguages();
        if (initializeLanguagesResponse.hasErrors()) {
            throw new RuntimeException();
        }
        return manageGet();
    }


}
