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
package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.IExportService;
import femr.business.services.core.IMissionTripService;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MissionItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.export.FilterFormViewModel;
import femr.ui.models.export.ExportViewModel;
import femr.ui.views.html.export.index;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.Security;

import java.io.File;
import java.util.List;

import static play.mvc.Controller.response;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ExportController {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private ISessionService sessionService;
    private IExportService exportService;
    private IMissionTripService missionTripService;

    /**
     * @param assetsFinder
     * @param formFactory
     * @param exportService {@link IExportService}
     * @param missionTripService {@link IMissionTripService}
     */
    @Inject
    public ExportController(AssetsFinder assetsFinder, ISessionService sessionService, FormFactory formFactory, IExportService exportService, IMissionTripService missionTripService) {
        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
        this.formFactory = formFactory;
        this.exportService = exportService;
        this.missionTripService = missionTripService;
    }

    /**
     * Trigger an export of all encounters
     */
    public Result indexGet() {

        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();
        ExportViewModel viewModel = new ExportViewModel();

        final Form<FilterFormViewModel> filterViewModelForm = formFactory.form(FilterFormViewModel.class);

        ServiceResponse<List<MissionItem>> missionTripsResponse = this.missionTripService.retrieveAllTripInformation();
        if (missionTripsResponse.hasErrors())
            throw new RuntimeException();
        viewModel.setAllMissionItems(missionTripsResponse.getResponseObject());

        return ok(index.render(currentUserSession, assetsFinder, viewModel, filterViewModelForm));
    }

    public Result indexPost(){

        final Form<FilterFormViewModel> filterViewModelForm = formFactory.form(FilterFormViewModel.class).bindFromRequest();

        if(filterViewModelForm.hasErrors()){

            CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();
            ExportViewModel viewModel = new ExportViewModel();

            ServiceResponse<List<MissionItem>> missionTripsResponse = this.missionTripService.retrieveAllTripInformation();
            if (missionTripsResponse.hasErrors())
                throw new RuntimeException();
            viewModel.setAllMissionItems(missionTripsResponse.getResponseObject());

            return badRequest(index.render(currentUserSession, assetsFinder, viewModel, filterViewModelForm));
        }

        FilterFormViewModel filterModel = filterViewModelForm.get();
        ServiceResponse<File> filterServiceResponse = exportService.exportAllEncounters(filterModel.getMissionTripIds());
        File csvFile = filterServiceResponse.getResponseObject();
        response().setHeader("Content-disposition", "attachment; filename=" + csvFile.getName());

        return ok(csvFile).as("application/x-download");
    }
}
