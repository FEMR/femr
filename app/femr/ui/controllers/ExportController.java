package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.core.IExportService;
import femr.business.services.core.ISessionService;
import femr.common.dtos.ServiceResponse;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.Security;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static play.mvc.Controller.response;
import static play.mvc.Results.ok;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ExportController {

    private final FormFactory formFactory;
    private IExportService exportService;
    private ISessionService sessionService;

    /**
     * @param formFactory
     * @param sessionService {@link ISessionService}
     * @param exportService {@link IExportService}
     */
    @Inject
    public ExportController(FormFactory formFactory, ISessionService sessionService, IExportService exportService) {

        this.formFactory = formFactory;
        this.exportService = exportService;
        this.sessionService = sessionService;
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
