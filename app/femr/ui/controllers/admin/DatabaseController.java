package femr.ui.controllers.admin;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.IInternetStatusService;
import femr.business.services.core.ISessionService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.InternetStatusItem;
import femr.data.models.mysql.Roles;
import femr.ui.controllers.BackEndControllerHelper;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.util.InternetConnnection.InternetCheck;
import play.mvc.*;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class DatabaseController extends Controller {
  private final AssetsFinder assetsFinder;
  private final ISessionService sessionService;
  private final IInternetStatusService internetStatusService;

  @Inject
  public DatabaseController(AssetsFinder assetsFinder, ISessionService sessionService,
                            IInternetStatusService internetStatusService) {

    this.assetsFinder = assetsFinder;
    this.sessionService = sessionService;
    this.internetStatusService = internetStatusService;
  }

  public Result downloadPost() {
    boolean flag = false;

    if (InternetCheck.NetIsAvailable()) {
      flag = true;

      // run python s3 bucket script here
      // BackEndControllerHelper.executePythonScript("");
    }

    ServiceResponse<InternetStatusItem> updateResponse = internetStatusService.updateInternetStatus(flag);
    if (updateResponse.hasErrors())
      throw new RuntimeException();

    return ok();
  }
}
