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

import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IInventoryService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationItem;
import femr.common.models.InventoryExportItem;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.MedicationInventory;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;
import femr.data.models.mysql.Medication;
import femr.util.stringhelpers.CSVWriterGson;
import femr.util.stringhelpers.GsonFlattener;
import com.google.gson.Gson;
import play.Logger;

import java.util.*;

public class InventoryService implements IInventoryService {

    private final IRepository<IMedication> medicationRepository;
    private final IRepository<IMedicationInventory> medicationInventoryRepository;
    private final IUserRepository userRepository;
    private IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public InventoryService(IRepository<IMedication> medicationRepository,
                            IRepository<IMedicationInventory> medicationInventoryRepository,
                            IUserRepository userRepository,
                            IDataModelMapper dataModelMapper,
                            @Named("identified") IItemModelMapper itemModelMapper) {

        this.medicationRepository = medicationRepository;
        this.medicationInventoryRepository = medicationInventoryRepository;
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

        //Querying based on the trip id.  Each trip will have its own inventory.
        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                .where()
                .eq("missionTrip.id", tripId);

        List<? extends IMedicationInventory> medicationsInventory;
        try {
            medicationsInventory = medicationInventoryRepository.find(medicationInventoryExpressionList);
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

        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                .where()
                .eq("missionTrip.id", tripId)
                .eq("medication.id", medicationId);

        try{

            IMedicationInventory medicationInventory = medicationInventoryRepository.findOne(medicationInventoryExpressionList);
            MedicationItem medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), medicationInventory.getIsDeleted(), null, null);
            response.setResponseObject(medicationItem);
        } catch (Exception ex){

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

        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                .where()
                .eq("medication.id", medicationId)
                .eq("missionTrip.id", tripId);

        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {

            medicationInventory = medicationInventoryExpressionList.findUnique();
            if (medicationInventory == null) {
                //it doesn't yet exist, create a new one
                medicationInventory = dataModelMapper.createMedicationInventory(quantityTotal, quantityTotal, medicationId, tripId);
                medicationInventory = medicationInventoryRepository.create(medicationInventory);
            } else if (medicationInventory.getIsDeleted() != null){
                //If it exists, but was deleted at some point, re-add (un-delete) it
                reAddInventoryMedication(medicationId, tripId);
            } else {


                medicationInventory.setQuantityInitial(quantityTotal);
                medicationInventory = medicationInventoryRepository.update(medicationInventory);
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
    public ServiceResponse<MedicationItem> setQuantityCurrent(int medicationId, int tripId, int newQuantity) {

        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery().where() .eq("medication.id", medicationId)
                .eq("missionTrip.id", tripId);
        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {
            //This should exist already, so no need to query for unique.
            medicationInventory = medicationInventoryRepository.findOne(medicationInventoryExpressionList);

            medicationInventory.setQuantityCurrent(newQuantity);

            medicationInventory = medicationInventoryRepository.update(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(),  medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), null, null, null);
            response.setResponseObject(medicationItem);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;

    }

    /*
    *{@inheritDoc}
    * This method assumes that the medication has been soft-deleted before it is called.
    * The medication therefore had to have existed in teh formulary table before this method updates it.
    **/
    public ServiceResponse <MedicationItem> reAddInventoryMedication(int medicationId, int tripId){
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                .where()
                .eq("medication.id", medicationId)
                .eq("missionTrip.id", tripId);

        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try{
            //Find the medication of interest from the formulary.
            medicationInventory = medicationInventoryRepository.findOne(medicationInventoryExpressionList);
            //Undo the soft-delete of  the medication from the formulary, then update the backend to reflect the change.
            medicationInventory.setIsDeleted(null);
            medicationInventory = medicationInventoryRepository.update(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(),  medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), medicationInventory.getIsDeleted(), null, null);
            response.setResponseObject(medicationItem);

        } catch (Exception ex) {

        response.addError("", ex.getMessage());
        }

    }


    /**
     *{@inheritDoc}
     * This method soft-deletes a trip formulary medication that is assumed to be added but not yet soft-deleted.
     * If it has been soft deleted, the only thing that will update is the time of deletion.
     **/
    @Override
    public ServiceResponse<MedicationItem> deleteInventoryMedication(int medicationId, int tripId){
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();
        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery().where() .eq("medication.id", medicationId)
                .eq("missionTrip.id", tripId);
        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {
            //This should exist already, so no need to query for unique.
            //Find the medication of interest from the formulary.
            medicationInventory = medicationInventoryRepository.findOne(medicationInventoryExpressionList);
            //Soft-delete of the medication from the formulary, then update the backend to reflect the change.
            medicationInventory.setIsDeleted(DateTime.now());
            medicationInventory = medicationInventoryRepository.update(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(),  medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), medicationInventory.getIsDeleted(), null, null);
            response.setResponseObject(medicationItem);
        } catch (Exception ex) {
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

        ExpressionList<Medication> medicationExpressionList = QueryProvider.getMedicationQuery()
                .where()
                .eq("id", medicationId);
        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                .where()
                .eq("medication_id", medicationId)
                .eq("mission_trip_id", tripId);

        try {


            IMedication medication = medicationRepository.findOne(medicationExpressionList);
            IMedicationInventory medicationInventory = medicationInventoryRepository.findOne(medicationInventoryExpressionList);
            int currentQuantity = 0;
            int totalQuantity = 0;

            if (medicationInventory != null) {

                medicationInventory.setQuantityCurrent(medicationInventory.getQuantityCurrent() - quantityToSubtract);
                medicationInventory = medicationInventoryRepository.update(medicationInventory);
                currentQuantity = medicationInventory.getQuantityCurrent();
                totalQuantity = medicationInventory.getQuantityInitial();
            }

            medicationItem = itemModelMapper.createMedicationItem(medication, currentQuantity, totalQuantity, null, null, null);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }
        response.setResponseObject(medicationItem);

        return response;
    }

    @Override
    public ServiceResponse<String> exportCSV(int tripId) {

      // We want the inventory from the current trip that has not been deleted
      // Which is the same as from the table on the inventory page
      ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery().where()
              .eq("missionTrip.id", tripId).eq("isDeleted", null);

      List<? extends IMedicationInventory> medicationInventory = medicationInventoryRepository.find(medicationInventoryExpressionList);

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
