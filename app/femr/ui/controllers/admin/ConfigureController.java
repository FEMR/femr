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
import femr.ui.models.admin.configure.ConfigureViewModelPost;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import femr.ui.views.html.admin.configure.index;

import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class ConfigureController extends Controller {

    private final Form<ConfigureViewModelPost> ConfigureViewModelForm = Form.form(ConfigureViewModelPost.class);
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
        ServiceResponse<List<? extends ISystemSetting>> systemSettingsResponse = configureService.getCurrentSettings();
        if (systemSettingsResponse.hasErrors()) {
            throw new RuntimeException();
        }
        List<? extends ISystemSetting> systemSettings = systemSettingsResponse.getResponseObject();

        return ok(index.render(currentUser, systemSettings));
    }

    /**
     * Only updates one system setting right now,
     * the service is designed to updated more than one
     * when the time comes
     *
     * @return
     */
    public Result indexPost() {
        ConfigureViewModelPost viewModel = ConfigureViewModelForm.bindFromRequest().get();

        ServiceResponse<ISystemSetting> response = configureService.getSystemSetting("Multiple chief complaints");
        ISystemSetting systemSetting = response.getResponseObject();


        if (StringUtils.isNotNullOrWhiteSpace(viewModel.getSs1()))
            systemSetting.setActive(true);
        else
            systemSetting.setActive(false);

        configureService.updateSystemSetting(systemSetting);

        response = configureService.getSystemSetting("Medical PMH Tab");
        systemSetting = response.getResponseObject();
        if (StringUtils.isNotNullOrWhiteSpace(viewModel.getSs2()))
            systemSetting.setActive(true);
        else
            systemSetting.setActive(false);
        configureService.updateSystemSetting(systemSetting);

        response = configureService.getSystemSetting("Medical Photo Tab");
        systemSetting = response.getResponseObject();
        if (StringUtils.isNotNullOrWhiteSpace(viewModel.getSs3()))
            systemSetting.setActive(true);
        else
            systemSetting.setActive(false);
        configureService.updateSystemSetting(systemSetting);



        return redirect(routes.AdminController.index());
    }
}
