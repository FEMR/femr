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

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.inject.Inject;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.business.services.core.IInventoryService;
import femr.business.services.core.ISessionService;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.inventory.*;
import femr.common.models.MedicationItem;
import femr.ui.views.html.admin.inventory.index;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

/**
 * Right now, the inventory feature supports the adding of medications, but
 * when a physician submits a prescription it simply creates a new medication
 * with a name equal to the prescription. The feature is not complete, yet.
 */
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class InventoryController extends Controller {
    private final Form<InventoryViewModelPost> inventoryViewModelPostForm = Form.form(InventoryViewModelPost.class);
    private final Form<InventoryViewModelDataQuery> inventoryViewModelDataQueryForm = Form.form(InventoryViewModelDataQuery.class);
    private final ISessionService sessionService;
    private final IInventoryService inventoryService;

    @Inject
    public InventoryController(ISessionService sessionService,
                               IInventoryService inventoryService) {
        this.sessionService = sessionService;
        this.inventoryService = inventoryService;
    }

    public Result indexGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        InventoryViewModelGet viewModel = new InventoryViewModelGet();
        ServiceResponse<List<MedicationItem>> medicationServiceResponse = inventoryService.retrieveMedicationInventory();
        if (medicationServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModel.setMedications(medicationServiceResponse.getResponseObject());
        }

        ServiceResponse<List<String>> availableMedicationUnitsResponse = inventoryService.retrieveAvailableUnits();
        if (availableMedicationUnitsResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModel.setAvailableUnits(availableMedicationUnitsResponse.getResponseObject());
        }

        ServiceResponse<List<String>> availableMedicationFormsResponse = inventoryService.retrieveAvailableForms();
        if (availableMedicationFormsResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModel.setAvailableForms(availableMedicationFormsResponse.getResponseObject());
        }

        return ok(index.render(currentUser, viewModel));
    }

    public Result indexPost() {
        Form<InventoryViewModelPost> form = inventoryViewModelPostForm.bindFromRequest();
        if (form.hasErrors()) {
            System.out.println(form.errors().toString());
            //if the request gets past the javascript validation and fails validation in the viewmodel, then
            //don't proceed to save anything. In the future, this should alert the user as to what they did
            //wrong.
            /* Should be validated client side and server-side throws error */
            throw new RuntimeException();
            //return redirect("/admin/inventory");
        }

        InventoryViewModelPost inventoryViewModelPost = form.bindFromRequest().get();

        MedicationItem medicationItem = new MedicationItem();
        medicationItem.setName(inventoryViewModelPost.getMedicationName());
        medicationItem.setQuantity_total(inventoryViewModelPost.getMedicationQuantity());
        medicationItem.setQuantity_current(inventoryViewModelPost.getMedicationQuantity());
        medicationItem.setForm(inventoryViewModelPost.getMedicationForm());

        for (int activeIngredientIndex = 0; activeIngredientIndex < inventoryViewModelPost.getMedicationStrength().size(); activeIngredientIndex++) {
            medicationItem.addActiveIngredient(
                    inventoryViewModelPost.getMedicationIngredient().get(activeIngredientIndex),
                    inventoryViewModelPost.getMedicationUnit().get(activeIngredientIndex),
                    inventoryViewModelPost.getMedicationStrength().get(activeIngredientIndex),
                    false
            );
        }

        ServiceResponse<MedicationItem> medicationItemServiceResponse = inventoryService.createMedication(medicationItem);
        if (medicationItemServiceResponse.hasErrors()) {
            return internalServerError();
        }

        return redirect("/admin/inventory");
    }

      /* Andre Farah - Updated  */
    public Result ajaxGet() {

        //Andre Farah - Changed from bindFromRequest() to bind(reqqest().body().asJson()) to properly bind
        //              the json objects passed from bs_grid
        Form<InventoryViewModelDataQuery> form = inventoryViewModelDataQueryForm.bind(request().body().asJson());
        if (form.hasErrors()){
            throw new RuntimeException();
        }
        InventoryViewModelDataQuery dataQuery = form.get();

        // Get paginated rows
        ServiceResponse<ObjectNode> medicationServiceResponse = inventoryService.getPaginatedMedicationInventory(
                dataQuery.getPage_num(),
                dataQuery.getRows_per_page(),
                dataQuery.getSorting(), //Andre Farah - Added for Sorting
                dataQuery.getFilter_rules()// Andre Farah - Added for Filtering
        );
        if (medicationServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        ObjectNode result = medicationServiceResponse.getResponseObject();

        return ok(result);

    }
}
