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

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Junction;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IInventoryService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationItem;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.MedicationInventory;
import femr.ui.models.admin.inventory.DataGridFilter;
import femr.ui.models.admin.inventory.DataGridSorting;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import play.libs.Json;
import femr.data.models.mysql.Medication;

import java.util.ArrayList;
import java.util.List;

public class InventoryService implements IInventoryService {

    private final IRepository<IMedication> medicationRepository;
    private final IRepository<IMedicationInventory> medicationInventoryRepository;
    private IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public InventoryService(IRepository<IMedication> medicationRepository,
                            IRepository<IMedicationInventory> medicationInventoryRepository,
                            IDataModelMapper dataModelMapper,
                            @Named("identified") IItemModelMapper itemModelMapper) {

        this.medicationRepository = medicationRepository;
        this.medicationInventoryRepository = medicationInventoryRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }





    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<MedicationItem> setQuantityTotal(int medicationId, int tripId, int quantityTotal) {

        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        //does this work without fetching? it should.
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
            } else {


                medicationInventory.setQuantityInitial(quantityTotal);
                medicationInventory = medicationInventoryRepository.update(medicationInventory);
            }
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantityInitial(), medicationInventory.getQuantityCurrent(), null);

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
            int medicationTotal = medicationInventory.getQuantityInitial();
            int medicationCurrent = medicationInventory.getQuantityCurrent();

            //Currently left out to leave out editing Initial Quantity
            // medicationInventory.setQuantity_total(medicationTotal - (medicationCurrent - newQuantity));

            medicationInventory.setQuantityCurrent(newQuantity);

            medicationInventory = medicationInventoryRepository.update(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(),  medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), null);
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
    public ServiceResponse<MedicationItem> deleteInventoryMedication(int medicationId, int tripId){
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();
        ExpressionList<MedicationInventory> medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery().where() .eq("medication.id", medicationId)
                .eq("missionTrip.id", tripId);
        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {
            //This should exist already, so no need to query for unique.
            medicationInventory = medicationInventoryRepository.findOne(medicationInventoryExpressionList);
            //Checks to see if medication was already deleted, then user wanted to undo delete
            if(medicationInventory.getIsDeleted() != null)
                medicationInventory.setIsDeleted(null);
            else
                medicationInventory.setIsDeleted(DateTime.now());
            medicationInventory = medicationInventoryRepository.update(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(),  medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), medicationInventory.getIsDeleted());
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

            medicationItem = itemModelMapper.createMedicationItem(medication, currentQuantity, totalQuantity, null);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }
        response.setResponseObject(medicationItem);

        return response;
    }
}
