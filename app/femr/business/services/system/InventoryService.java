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
package femr.business.services.system;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.services.core.IInventoryService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationItem;
import femr.common.models.InventoryExportItem;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IMedicationRepository;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.*;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;
import femr.util.stringhelpers.CSVWriterGson;
import femr.util.stringhelpers.GsonFlattener;
import com.google.gson.Gson;
import play.Logger;

import java.util.*;

public class InventoryService implements IInventoryService {

    private final IMedicationRepository medicationRepository;
    private final IUserRepository userRepository;
    private IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public InventoryService(IMedicationRepository medicationRepository,
                            IUserRepository userRepository,
                            IDataModelMapper dataModelMapper,
                            @Named("identified") IItemModelMapper itemModelMapper) {

        this.medicationRepository = medicationRepository;
        this.userRepository = userRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<MedicationItem>> retrieveMedicationInventorysByTripId(int tripId) {
        ServiceResponse<List<MedicationItem>> response = new ServiceResponse<>();

        List<? extends IMedicationInventory> medicationsInventory;
        try {
            medicationsInventory = medicationRepository.retrieveMedicationInventoriesByTripId(tripId, true);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        List<MedicationItem> medicationItems = new ArrayList<>();

        for (IMedicationInventory m : medicationsInventory) {

            String name;
            if(m.getCreatedBy() == null) {
                name = "";
            } else {
                IUser user = userRepository.retrieveUserById(m.getCreatedBy());
                name = user.getLastName() + ", " + user.getFirstName();
            }

            String timeStamp;
            if(m.getTimeAdded() == null) {
                timeStamp = "";
            } else {
                timeStamp = dateUtils.convertTimeToString(m.getTimeAdded());
            }
            medicationItems.add(itemModelMapper.createMedicationItem(m.getMedication(), m.getQuantityCurrent(),
                    m.getQuantityInitial(), m.getIsDeleted(), timeStamp, name));
        }
        response.setResponseObject(medicationItems);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<MedicationItem> retrieveMedicationInventoryByMedicationIdAndTripId(int medicationId, int tripId){
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        try {

            IMedicationInventory medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);
            MedicationItem medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), medicationInventory.getIsDeleted(), null, null);
            response.setResponseObject(medicationItem);
        } catch (Exception ex) {

            Logger.error("Attempted and failed to execute retrieveMedicationInventoryByMedicationIdAndTripId(" + medicationId + "," + tripId + ") in InventoryService. Stack trace to follow.");
            ex.printStackTrace();
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<MedicationItem> setQuantityTotal(int medicationId, int tripId, int quantityTotal) {

        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {

            medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);
            medicationInventory.setQuantityInitial(quantityTotal);
            medicationInventory = medicationRepository.saveMedicationInventory(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantityInitial(), medicationInventory.getQuantityCurrent(), null, null, null);

            response.setResponseObject(medicationItem);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> existsInventoryMedicationInTrip(int medicationId, int tripId){
        ServiceResponse<Boolean> response = new ServiceResponse<>();
        IMedicationInventory medicationInventory;

        boolean existsInTrip = false;
        try{
            medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);
            if(medicationInventory != null){
                response.setResponseObject(new Boolean(true));
            } else {
                response.setResponseObject(new Boolean(false));
            }
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;

    }


    /**
    *{@inheritDoc}
    **/
    @Override
    public ServiceResponse<MedicationItem> setQuantityCurrent(int medicationId, int tripId, int newQuantity) {

        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {
            //This should exist already, so no need to query for unique.
            medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);
            medicationInventory.setQuantityCurrent(newQuantity);
            medicationInventory = medicationRepository.saveMedicationInventory(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(),  medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), null, null, null);

            response.setResponseObject(medicationItem);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;

    }

    /**
     *{@inheritDoc}
     **/
    @Override
    public ServiceResponse<MedicationItem> createMedicationInventory(int medicationId, int tripId){
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {

            medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);

            if (medicationInventory == null){
                //If the medication is not in the inventory, then create it.
                //Assume new inventory medications have 0 current quantity and 0 total quantity.
                medicationInventory = dataModelMapper.createMedicationInventory(0, 0, medicationId, tripId);
                medicationInventory = medicationRepository.saveMedicationInventory(medicationInventory);
            }

            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantityInitial(), medicationInventory.getQuantityCurrent(), null, null, null);
            response.setResponseObject(medicationItem);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }


    /**
     *{@inheritDoc}
     **/
    @Override
    public ServiceResponse<MedicationItem> reAddInventoryMedication(int medicationId, int tripId){
        return setDeletionStateOfInventoryMedication(medicationId, tripId, false);
    }
    
    /**
     *{@inheritDoc}
     **/
    @Override
    public ServiceResponse<MedicationItem> deleteInventoryMedication(int medicationId, int tripId){
        return setDeletionStateOfInventoryMedication(medicationId, tripId, true);
    }

    private ServiceResponse<MedicationItem> setDeletionStateOfInventoryMedication(int medicationId, int tripId, boolean stateToSetTo){
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {
            //This should exist already, so no need to query for unique.
            medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);
            //Soft-delete of the medication from the formulary, then update the backend to reflect the change.
            if (stateToSetTo == true) {
                medicationInventory.setIsDeleted(DateTime.now());
            } else {
                medicationInventory.setIsDeleted(null);
            }
            medicationInventory = medicationRepository.saveMedicationInventory(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(),  medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), medicationInventory.getIsDeleted(), null, null);
            response.setResponseObject(medicationItem);

        } catch (Exception ex) {

            Logger.error("InventoryService-setDeletionStateOfInventoryMedication error while delete state of inventory medication", ex.getStackTrace(), ex);
            response.addError("", ex.getMessage());
        }

        return response;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<MedicationItem> subtractFromQuantityCurrent(int medicationId, int tripId, int quantityToSubtract) {

        ServiceResponse<MedicationItem> response = new ServiceResponse<>();
        MedicationItem medicationItem = null;

        try {

            IMedicationInventory medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);

            if (medicationInventory != null && medicationInventory.getMedication() != null) {

                int currentQuantity = 0;
                int totalQuantity = 0;

                medicationInventory.setQuantityCurrent(medicationInventory.getQuantityCurrent() - quantityToSubtract);
                medicationInventory = medicationRepository.saveMedicationInventory(medicationInventory);
                currentQuantity = medicationInventory.getQuantityCurrent();
                totalQuantity = medicationInventory.getQuantityInitial();

                medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), currentQuantity, totalQuantity, null, null, null);
            } else {
                Logger.error("InventoryService-subtractFromQuantityCurrent", "tried to search for medication inventory but got null", "medicationId: " + medicationId + "tripId: " + tripId);
            }
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }
        response.setResponseObject(medicationItem);

        return response;
    }

    @Override
    public ServiceResponse<String> exportCSV(int tripId) {

      List<? extends IMedicationInventory> medicationInventory = medicationRepository.retrieveMedicationInventoriesByTripId(tripId, false);

      // Convert result of query to a list to export
      List<InventoryExportItem> inventoryExport = new ArrayList<>();
      for (IMedicationInventory med : medicationInventory) {

          String name;
          if(med.getCreatedBy() == null) {
              name = "";
          } else {
              IUser user = userRepository.retrieveUserById(med.getCreatedBy());
              name = user.getLastName() + ", " + user.getFirstName();
          }

          String timeStamp;
          if(med.getTimeAdded() == null) {
              timeStamp = "";
          } else {
              timeStamp = dateUtils.convertTimeToString(med.getTimeAdded());
          }
          inventoryExport.add(new InventoryExportItem(itemModelMapper.createMedicationItem(
                med.getMedication(), med.getQuantityCurrent(), med.getQuantityInitial(), med.getIsDeleted(),
                timeStamp, name)));
      }

      // Convert export list to json
      Gson gson = new Gson();
      GsonFlattener parser = new GsonFlattener();
      List<Map<String, String>> flatJson = parser.parse(gson.toJsonTree(inventoryExport).getAsJsonArray());

      // Convert json to CSV
      CSVWriterGson writer = new CSVWriterGson();
      String csvString = writer.getAsCSV(flatJson, InventoryExportItem.getFieldOrder());

      ServiceResponse<String> response = new ServiceResponse<>();
      response.setResponseObject(csvString.toString());

      return response;
    }
}
