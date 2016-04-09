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
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MissionTripItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.inventory.*;
import femr.common.models.MedicationItem;
import femr.ui.views.html.admin.inventory.manage;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class InventoryController extends Controller {

    private final Form<InventoryViewModelPost> inventoryViewModelPostForm = Form.form(InventoryViewModelPost.class);
    private final IConceptService conceptService;
    private final IInventoryService inventoryService;
    private final IMedicationService medicationService;
    private final IMissionTripService missionTripService;
    private final ISessionService sessionService;

    @Inject
    public InventoryController(IConceptService conceptService,
                               IInventoryService inventoryService,
                               IMedicationService medicationService,
                               IMissionTripService missionTripService,
                               ISessionService sessionService) {

        this.conceptService = conceptService;
        this.inventoryService = inventoryService;
        this.medicationService = medicationService;
        this.missionTripService = missionTripService;
        this.sessionService = sessionService;
    }

    public Result manageGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();


        InventoryViewModelGet viewModel = new InventoryViewModelGet();

        // If the use does not have a trip ID, we cannot retrieve the list of medications
        // since they are tied to a trip
        if( currentUser.getTripId() != null ){

            ServiceResponse<List<MedicationItem>> medicationServiceResponse = medicationService.retrieveMedicationInventory(currentUser.getTripId());
            if (medicationServiceResponse.hasErrors()) {
                throw new RuntimeException();
            } else {
                viewModel.setMedications(medicationServiceResponse.getResponseObject());
            }

            ServiceResponse<MissionTripItem> missionTripServiceResponse = missionTripService.retrieveAllTripInformationByTripId(currentUser.getTripId());
            if (missionTripServiceResponse.hasErrors()) {

                throw new RuntimeException();
            } else {

                viewModel.setMissionTripItem(missionTripServiceResponse.getResponseObject());
            }

        }
        else{

            viewModel.setMedications( new ArrayList<>() );
        }

        ServiceResponse<List<MedicationItem>> conceptMedicationServiceResponse = conceptService.retrieveAllMedicationConcepts();
        if (conceptMedicationServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModel.setConceptMedications(conceptMedicationServiceResponse.getResponseObject());
        }

        ServiceResponse<List<String>> availableMedicationUnitsResponse = medicationService.retrieveAvailableMedicationUnits();
        if (availableMedicationUnitsResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModel.setAvailableUnits(availableMedicationUnitsResponse.getResponseObject());
        }

        ServiceResponse<List<String>> availableMedicationFormsResponse = medicationService.retrieveAvailableMedicationForms();
        if (availableMedicationFormsResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModel.setAvailableForms(availableMedicationFormsResponse.getResponseObject());
        }

        return ok(manage.render(currentUser, viewModel));
    }

    /**
     * Handles the submission of a new medication from the Admin Inventory Tracking screen.
     */
    public Result managePost() {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        Form<InventoryViewModelPost> form = inventoryViewModelPostForm.bindFromRequest();
        if (form.hasErrors()) {
            return redirect("/admin/inventory");

            //System.out.println(form.errors().toString());
            //if the request gets past the javascript validation and fails validation in the viewmodel, then
            //don't proceed to save anything. In the future, this should alert the user as to what they did
            //wrong.
            /* Should be validated client side and server-side throws error
            //throw new RuntimeException();    */

        }

        InventoryViewModelPost inventoryViewModelPost = form.bindFromRequest().get();

        // create a new medicationItem for managing the compilation of active ingredients
        // (this could potentially be moved into the service layer)
        MedicationItem medicationItem = new MedicationItem();

        // assumes medication strength is a good indicator of how many
        // strength/unit/ingredients (active ingredietns) are involved
        // *denominator field not taken into consideration - always false
        if (inventoryViewModelPost.getMedicationStrength().get(0) != null) {
            for (int genericIndex = 0; genericIndex < inventoryViewModelPost.getMedicationStrength().size(); genericIndex++) {

                if (inventoryViewModelPost.getMedicationIngredient().get(genericIndex) != null &&
                        inventoryViewModelPost.getMedicationUnit().get(genericIndex) != null &&
                        inventoryViewModelPost.getMedicationStrength().get(genericIndex) != null) {

                    medicationItem.addActiveIngredient(
                            inventoryViewModelPost.getMedicationIngredient().get(genericIndex),
                            inventoryViewModelPost.getMedicationUnit().get(genericIndex),
                            inventoryViewModelPost.getMedicationStrength().get(genericIndex),
                            false
                    );
                }
            }
            ServiceResponse<MedicationItem> createMedicationServiceResponse = medicationService.createMedication(
                    inventoryViewModelPost.getMedicationName(),
                    inventoryViewModelPost.getMedicationForm(),
                    medicationItem.getActiveIngredients());
            // check for errors before updating the quantity to ensure a medication ID has been returned in the response
            // object.
            if (createMedicationServiceResponse.hasErrors()) {

                return internalServerError();
            }
            ServiceResponse<MedicationItem> setQuantityServiceResponse = inventoryService.setQuantityTotal(
                    createMedicationServiceResponse.getResponseObject().getId(),
                    currentUser.getTripId(),
                    inventoryViewModelPost.getMedicationQuantity());

            if (setQuantityServiceResponse.hasErrors()){

                return internalServerError();
            }
        }




        //if just adding medications from the concept dictionary, there won't be any amount involved
        //and knowledge of the ingredients already exists.
        if (inventoryViewModelPost.getNewConceptMedicationsForInventory() != null) {

            ServiceResponse<MedicationItem> conceptMedicationServiceResponse;
            ServiceResponse<MedicationItem> medicationItemServiceResponse;
            MedicationItem conceptMedicationItem;

            //for each concept medication id that was sent from the select2 form
            for (Integer conceptMedicationId : inventoryViewModelPost.getNewConceptMedicationsForInventory()) {

                //get the actual concept MedicationItem from the id that was sent in
                conceptMedicationServiceResponse = conceptService.retrieveConceptMedication(conceptMedicationId);
                if (conceptMedicationServiceResponse.hasErrors()) {

                    return internalServerError();
                } else {

                    //create a non-concept MedicationItem from the concept MedicationItem
                    conceptMedicationItem = conceptMedicationServiceResponse.getResponseObject();
                    medicationItemServiceResponse = medicationService.createMedication(conceptMedicationItem.getName(), conceptMedicationItem.getForm(), conceptMedicationItem.getActiveIngredients());

                    if (medicationItemServiceResponse.hasErrors()) {

                        return internalServerError();
                    }else{

                        ServiceResponse<MedicationItem> setQuantityServiceResponse = inventoryService.setQuantityTotal(medicationItemServiceResponse.getResponseObject().getId(), currentUser.getTripId(), 0);
                        if (setQuantityServiceResponse.hasErrors()){

                            return internalServerError();
                        }
                    }


                }
            }
        }

        return redirect("/admin/inventory");
    }


    public Result ajaxDelete(int medicationID, int tripId) {
        ServiceResponse<MedicationItem> inventoryServiceResponse = inventoryService.deleteInventoryMedication(medicationID, tripId);

        if (inventoryServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        return ok("true");
    }


    /**
     * Alters medication based on submit.
     */
    public Result ajaxEditCurrent(int medicationID, int tripId) {
        // Get POST data
        DynamicForm df = play.data.Form.form().bindFromRequest();
        int quantity = Integer.parseInt(df.get("quantity"));

        ServiceResponse<MedicationItem> inventoryServiceResponse = inventoryService.setQuantityCurrent(medicationID, tripId, quantity);
        if (inventoryServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        return ok("true");
    }

    /**
     * Alters medication based on submit.
     */
    public Result ajaxEditTotal(int medicationID, int tripId) {
        // Get POST data
        DynamicForm df = play.data.Form.form().bindFromRequest();
        int quantity = Integer.parseInt(df.get("quantity"));

        ServiceResponse<MedicationItem> inventoryServiceResponse = inventoryService.setQuantityTotal(medicationID, tripId, quantity);
        if (inventoryServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        return ok("true");
    }


}
