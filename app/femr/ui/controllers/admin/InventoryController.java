package femr.ui.controllers.admin;

import com.google.inject.Inject;
import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;
import femr.business.services.IInventoryService;
import femr.business.services.ISessionService;
import femr.data.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.InventoryViewModelGet;
import femr.ui.models.admin.InventoryViewModelPost;
import femr.common.models.MedicationItem;
import femr.ui.views.html.admin.inventory.index;
import femr.ui.views.html.admin.inventory.inventory;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

//Note: Administrative controllers still interface with pure data models
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class InventoryController extends Controller {
    private ISessionService sessionService;
    private IInventoryService inventoryService;

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

    public Result indexPost(Integer id){
        ServiceResponse isDeletedServiceResponse =inventoryService.removeMedicationFromInventory(id);
        if (isDeletedServiceResponse.hasErrors()){
            throw new RuntimeException();
        }else{
            return redirect("/admin/inventory");
        }
    }

    public Result inventoryGet() {

        return ok(inventory.render());
    }

    public Result inventoryPost() {
        DynamicForm requestData = Form.form().bindFromRequest();
        if (requestData.data().size() % 2 != 0) {
            throw new RuntimeException();
        }

        //constructor for InventoryViewModelPost
        //takes care of the dynamic form binding.
        InventoryViewModelPost viewModelPost = new InventoryViewModelPost(requestData);

        inventoryService.createMedicationInventory(viewModelPost.getMedications());

        return redirect("/admin/inventory");
    }

}
