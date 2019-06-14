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
import femr.business.services.core.IExportService;
import femr.common.dtos.ServiceResponse;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import play.mvc.Result;
import play.mvc.Security;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static play.mvc.Controller.response;
import static play.mvc.Results.ok;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ExportController {

    private IExportService exportService;

    /**
     * @param exportService {@link IExportService}
     */
    @Inject
    public ExportController(IExportService exportService) {
        this.exportService = exportService;
    }


    /**
     * Trigger an export of all encounters
     */
    public Result exportAllGet() {

        // TODO -- get form request
        List<Integer> tripIds = new ArrayList<>();
        tripIds.add(3);
        tripIds.add(5);
        tripIds.add(6);

        ServiceResponse<File> filterServiceResponse = exportService.exportAllEncounters(tripIds);
        File csvFile = filterServiceResponse.getResponseObject();
        response().setHeader("Content-disposition", "attachment; filename=" + csvFile.getName());

        return ok(csvFile).as("application/x-download");
    }
}
