package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.business.services.system.DbDumpService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;

import femr.ui.views.html.backup.backup;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.io.IOException;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE, Roles.MANAGER, Roles.RESEARCHER, Roles.ADMINISTRATOR})
public class DatabaseDumpController extends Controller {
    private final AssetsFinder assetsFinder;
    private final ISessionService sessionService;

    @Inject
    public DatabaseDumpController(AssetsFinder assetsFinder, ISessionService sessionService){
        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
    }
    public Result indexGet(){
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        return ok(backup.render(currentUser, assetsFinder, getCommand(), ""));
    }

    public Result indexPost(){
        String successMessage = "Data Backup Successful!";
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        DbDumpService dbService = new DbDumpService();
        ServiceResponse<Boolean> response = dbService.getAllData();
        if (!response.getResponseObject()){
            successMessage = "Data Backup Failed..";
        }
        return ok(backup.render(currentUser, assetsFinder, getCommand(), successMessage));
    }

    private boolean getCommand(){
        try{
            Process process = new ProcessBuilder("which", "mysqldump").start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        }
        catch (IOException | InterruptedException e){
            return false;
        }

    }

}
