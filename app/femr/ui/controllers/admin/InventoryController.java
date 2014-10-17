package femr.ui.controllers.admin;

import com.google.inject.Inject;
import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;
import femr.business.services.IInventoryService;
import femr.business.services.ISessionService;
import femr.data.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.inventory.InventoryViewModelGet;
import femr.common.models.MedicationItem;
import femr.ui.models.admin.inventory.InventoryViewModelPost;
import femr.ui.views.html.admin.inventory.index;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

//Note: Administrative controllers still interface with pure data models
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class InventoryController extends Controller {
    private final Form<InventoryViewModelPost> inventoryViewModelPostForm = Form.form(InventoryViewModelPost.class);
    private final ISessionService sessionService;
    private final IInventoryService inventoryService;

    @Inject
    public InventoryController(ISessionService sessionService,
                               IInventoryService inventoryService) {
        this.sessionService = sessionService;
        this.inventoryService = inventoryService;
    }

    public Result indexGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        InventoryViewModelGet viewModel = new InventoryViewModelGet();
        ServiceResponse<List<MedicationItem>> medicationServiceResponse = inventoryService.getMedicationInventory();
        if (medicationServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModel.setMedications(medicationServiceResponse.getResponseObject());
        }

        return ok(index.render(currentUser, viewModel));
    }

    public Result indexPost(){
        InventoryViewModelPost inventoryViewModelPost = inventoryViewModelPostForm.bindFromRequest().get();
        return redirect("/admin/inventory");
    }

}
