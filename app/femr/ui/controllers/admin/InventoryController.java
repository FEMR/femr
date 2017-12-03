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
import femr.ui.models.admin.configure.IndexViewModelPost;
import femr.ui.models.admin.inventory.*;
import femr.common.models.MedicationItem;
import femr.ui.views.html.admin.inventory.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR, Roles.SUPERUSER})
public class InventoryController extends Controller {

    private final FormFactory formFactory;
    private final IConceptService conceptService;
    private final IInventoryService inventoryService;
    private final IMedicationService medicationService;
    private final IMissionTripService missionTripService;
    private final ISessionService sessionService;

    @Inject
    public InventoryController(FormFactory formFactory,
                               IConceptService conceptService,
                               IInventoryService inventoryService,
                               IMedicationService medicationService,
                               IMissionTripService missionTripService,
                               ISessionService sessionService) {

        this.formFactory = formFactory;
        this.conceptService = conceptService;
        this.inventoryService = inventoryService;
        this.medicationService = medicationService;
        this.missionTripService = missionTripService;
        this.sessionService = sessionService;
    }

    /**
     * Serves up the inventory homepage. POST communication for updating quantity
     * is handled by AJAX calls
     */
    public Result manageGet(Integer tripID) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        ManageViewModelGet viewModel = new ManageViewModelGet();

        // If the use does not have a trip ID, we cannot retrieve the list of medications
        // since they are tied to a trip
        Integer retrieveID;

        if(tripID == 0) {
            retrieveID = currentUser.getTripId();
        } else {
            retrieveID = tripID;
        }

        if( retrieveID != null ){

            ServiceResponse<List<MedicationItem>> medicationServiceResponse = inventoryService.retrieveMedicationInventorysByTripId(retrieveID);
            if (medicationServiceResponse.hasErrors()) {
                throw new RuntimeException();
            } else {
                viewModel.setMedications(medicationServiceResponse.getResponseObject());
            }


            ServiceResponse<MissionTripItem> missionTripServiceResponse = missionTripService.retrieveAllTripInformationByTripId(retrieveID);
            if (missionTripServiceResponse.hasErrors()) {

                throw new RuntimeException();
            } else {

                viewModel.setMissionTripItem(missionTripServiceResponse.getResponseObject());
            }

            ServiceResponse<List<MissionTripItem>> missionTripListServiceResponse = missionTripService.retrieveAllTripInformationByUserId(currentUser.getId());
            if (missionTripServiceResponse.hasErrors()) {

                throw new RuntimeException();
            } else {

                viewModel.setMissionTripList(missionTripListServiceResponse.getResponseObject());
            }
        } else {

            viewModel.setMedications(new ArrayList<>());
            viewModel.setMissionTripList(new ArrayList<>());
        }

        return ok(manage.render(currentUser, viewModel));
    }

    public Result managePost() {

        final Form<ManageViewModelPost> manageViewModelForm = formFactory.form(ManageViewModelPost.class);
        ManageViewModelPost viewModel = manageViewModelForm.bindFromRequest().get();

        //if(viewModel.getSelectedTrip()==null) {viewModel.setSelectedTrip(0);}

        return redirect("/admin/inventory/" + viewModel.getSelectedTrip());
    }
        /**
         * Page for adding a new custom medication
         */
    public Result customGet(Integer tripId) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        CustomViewModelGet viewModel = new CustomViewModelGet();

        ServiceResponse<List<String>> availableMedicationFormsResponse = medicationService.retrieveAvailableMedicationForms();
        if (availableMedicationFormsResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModel.setAvailableForms(availableMedicationFormsResponse.getResponseObject());
        }

        ServiceResponse<List<String>> availableMedicationUnitsResponse = medicationService.retrieveAvailableMedicationUnits();
        if (availableMedicationUnitsResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModel.setAvailableUnits(availableMedicationUnitsResponse.getResponseObject());
        }

        ServiceResponse<MissionTripItem> missionTripServiceResponse = missionTripService.retrieveAllTripInformationByTripId(tripId);
        if (missionTripServiceResponse.hasErrors()) {

            throw new RuntimeException();
        } else {

            viewModel.setMissionTripItem(missionTripServiceResponse.getResponseObject());
        }

        return ok(custom.render(currentUser, viewModel));
    }

    /**
     * Handles the submission of a new custom medication from an Admin
     */
    public Result customPost(Integer tripId){
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        final Form<CustomViewModelPost> inventoryViewModelPostForm = formFactory.form(CustomViewModelPost.class);

        Form<CustomViewModelPost> form = inventoryViewModelPostForm.bindFromRequest();

        if (form.hasErrors()) {
            return redirect("/admin/inventory/0");

            //System.out.println(form.errors().toString());
            //if the request gets past the javascript validation and fails validation in the viewmodel, then
            //don't proceed to save anything. In the future, this should alert the user as to what they did
            //wrong.
            /* Should be validated client side and server-side throws error
            //throw new RuntimeException();    */

        }

        CustomViewModelPost inventoryViewModelPost = form.bindFromRequest().get();



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
                    tripId,
                    inventoryViewModelPost.getMedicationQuantity());

            if (setQuantityServiceResponse.hasErrors()){

                return internalServerError();
            }
        }


        return redirect("/admin/inventory/"+tripId);
    }

    /**
     * Page for adding a new medication from the concept dictionary
     */
    public Result existingGet(Integer tripId) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        ExistingViewModelGet viewModel = new ExistingViewModelGet();

        ServiceResponse<List<MedicationItem>> conceptMedicationServiceResponse = conceptService.retrieveAllMedicationConcepts();
        if (conceptMedicationServiceResponse.hasErrors()) {
            throw new RuntimeException();
        } else {
            viewModel.setConceptMedications(conceptMedicationServiceResponse.getResponseObject());
        }

        ServiceResponse<MissionTripItem> missionTripServiceResponse = missionTripService.retrieveAllTripInformationByTripId(tripId);
        if (missionTripServiceResponse.hasErrors()) {

            throw new RuntimeException();
        } else {

            viewModel.setMissionTripItem(missionTripServiceResponse.getResponseObject());
        }

        return ok(existing.render(currentUser, viewModel));
    }

    /**
     * Handles the submission of an existing medication from an Admin
     */
    public Result existingPost(Integer tripId) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        final Form<ExistingViewModelPost> existingViewModelPostForm = formFactory.form(ExistingViewModelPost.class);
        Form<ExistingViewModelPost> existingForm = existingViewModelPostForm.bindFromRequest();
        ExistingViewModelPost existingViewModelPost = existingForm.bindFromRequest().get();

        //if just adding medications from the concept dictionary, there won't be any amount involved
        //and knowledge of the ingredients already exists.
        if (existingViewModelPost.getNewConceptMedicationsForInventory() != null) {

            ServiceResponse<MedicationItem> conceptMedicationServiceResponse;
            ServiceResponse<MedicationItem> medicationItemServiceResponse;
            MedicationItem conceptMedicationItem;

            //for each concept medication id that was sent from the select2 form
            for (Integer conceptMedicationId : existingViewModelPost.getNewConceptMedicationsForInventory()) {

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

                        ServiceResponse<MedicationItem> setQuantityServiceResponse = inventoryService.setQuantityTotal(medicationItemServiceResponse.getResponseObject().getId(), tripId, 0);
                        if (setQuantityServiceResponse.hasErrors()){

                            return internalServerError();
                        }
                    }


                }
            }
        }

        return redirect("/admin/inventory/"+tripId);
    }

    /**
     * Called when a user wants to export the data to a CSV file.
     */
    public Result exportGet(int tripId) {
      CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

      ServiceResponse<String> exportServiceResponse = inventoryService.exportCSV(tripId);

      SimpleDateFormat format = new SimpleDateFormat("MMddyy-HHmmss");
      String timestamp = format.format(new Date());
      String csvFileName = "inventory-"+timestamp+".csv";
      response().setHeader("Content-disposition", "attachment; filename=" + csvFileName);

      return ok(exportServiceResponse.getResponseObject()).as("application/x-download");
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
        DynamicForm df = formFactory.form().bindFromRequest();
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
        DynamicForm df = formFactory.form().bindFromRequest();
        int quantity = Integer.parseInt(df.get("quantity"));

        ServiceResponse<MedicationItem> inventoryServiceResponse = inventoryService.setQuantityTotal(medicationID, tripId, quantity);
        if (inventoryServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }
        return ok("true");
    }


}
