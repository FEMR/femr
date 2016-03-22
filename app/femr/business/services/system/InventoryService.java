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
    //Andre Farah - Start Code
    public ServiceResponse<ObjectNode> getPaginatedMedicationInventory(int pageNum, int rowsPerPage, List<DataGridSorting> sorting, List<DataGridFilter> filters) {
        ServiceResponse<ObjectNode> response = new ServiceResponse<>();
        /* Create a data object to store all the information */
        ObjectNode data = Json.newObject();
        ArrayNode page_data = data.putArray("page_data");
        response.setResponseObject(data);

        /* Create ordering string for query based on sorting */
        String orderBy = StringUtils.join(sorting, ", ").trim();

        ExpressionList<Medication> query = QueryProvider.getMedicationQuery()
                .fetch("medicationForm").where();
        query.where().eq("isDeleted", false);
        if (orderBy != "")
            query.orderBy(orderBy);

        if (filters != null && filters.size() > 0) {
            Junction<Medication> subJunction;
            String filterOp = filters.get(0).getLogical_operator();

            // Create a junction AND/OR based on the filter operator
            if (filterOp.equals("AND")) {
                subJunction = query.where().conjunction();
            } else {
                subJunction = query.where().disjunction();
            }

            // Iterate through all filters and add them to the sub junction
            for (DataGridFilter f : filters) {
                if (f.getCondition() == null) continue;
                String operator = f.getCondition().getOperator();
                String field = f.getCondition().getField();
                List<String> values = f.getCondition().getFilterValue();
                String value = (values == null || values.size() == 0) ? "" : values.get(0);

                /* Individual filter types. Definitly need refactoring */
                if (operator.equalsIgnoreCase("equal"))
                    subJunction.add(Expr.eq(field, value));
                else if (operator.equalsIgnoreCase("not_equal"))
                    subJunction.add(Expr.ne(field, value));
                /*else if (f.getCondition().getOperator().equalsIgnoreCase("in"))
                    subJunction.add();
                else if (f.getCondition().getOperator().equalsIgnoreCase("not_in"))
                    subJunction.add();*/
                else if (operator.equalsIgnoreCase("begins_with"))
                    subJunction.add(Expr.startsWith(field, value));
                else if (operator.equalsIgnoreCase("not_begins_with"))
                    subJunction.add(Expr.not(Expr.istartsWith(field, value)));
                else if (operator.equalsIgnoreCase("contains"))
                    subJunction.add(Expr.icontains(field, value));
                else if (operator.equalsIgnoreCase("not_contains"))
                    subJunction.add(Expr.not(Expr.icontains(field, value)));
                else if (operator.equalsIgnoreCase("ends_with"))
                    subJunction.add(Expr.iendsWith(field, value));
                else if (operator.equalsIgnoreCase("not_ends_with"))
                    subJunction.add(Expr.not(Expr.iendsWith(field, value)));
                else if (operator.equalsIgnoreCase("is_empty"))
                    subJunction.add(Expr.eq(field, ""));
                else if (operator.equalsIgnoreCase("is_not_empty"))
                    subJunction.add(Expr.ne(field, ""));
                else if (operator.equalsIgnoreCase("is_null"))
                    subJunction.add(Expr.isNull(field));
                else if (operator.equalsIgnoreCase("is_not_null"))
                    subJunction.add(Expr.isNotNull(field));
                else if (operator.equalsIgnoreCase("greater_than"))
                    subJunction.add(Expr.gt(field, value));
                else if (operator.equalsIgnoreCase("less_than"))
                    subJunction.add(Expr.lt(field, value));


            }
        }

        List<? extends IMedication> medications;
        try {
            medications = medicationRepository.find(query);
        } catch (Exception ex) {
            //response.addError("exception", ex.getMessage());
            data.put("total_rows", 0);
            return response;
        }

        int totalMedication = medications.size();

        /* Store TOTAL number of medications in object */
        data.put("total_rows", totalMedication);

        /* Get start and to index of records we want */
        int fromIndex = (pageNum - 1) * rowsPerPage;
        int toIndex = fromIndex + rowsPerPage;

        /* If toIndex is greater than totalMedication then reduce to last index in result */
        if (toIndex > totalMedication) toIndex = fromIndex + (totalMedication % rowsPerPage);

        medications = medications.subList(fromIndex, toIndex);

        ExpressionList<MedicationInventory> medicationInventoryExpressionList;
        for (IMedication m : medications) {

            /* Create node to customize return values */
            ObjectNode js = page_data.addObject();
            js.put("id", m.getId());
            //js.put("name", m.getName());
            String medicationDisplayName = m.getName();
            //Create list of drug name/unit/values to append to the medication name
            List<String> formattedDrugNames = new ArrayList<String>();
            for (IMedicationActiveDrug drug : m.getMedicationActiveDrugs()) {
                formattedDrugNames.add(String.format("%s%s %s",
                                drug.getValue(),
                                drug.getMedicationMeasurementUnit().getName(),
                                drug.getMedicationActiveDrugName().getName())
                );
            }
            if (formattedDrugNames.size() > 0)
                medicationDisplayName += " " + Joiner.on("/").join(formattedDrugNames);
            js.put("name", medicationDisplayName);

            //js.put("quantity_current", m.getQuantity_current());
            //js.put("quantity_initial", m.getQuantity_total());
            //

            medicationInventoryExpressionList = QueryProvider.getMedicationInventoryQuery()
                    .where()
                    .eq("medication.id", m.getId())
                    .eq("missionTrip.id", 1);
            IMedicationInventory medicationInventory = medicationInventoryRepository.findOne(medicationInventoryExpressionList);

            if (medicationInventory != null) {
                js.put("quantity_current", medicationInventory.getQuantity_current());
                js.put("quantity_initial", medicationInventory.getQuantity_total());
            }



            if (m.getMedicationForm() != null) {
                js.put("form", m.getMedicationForm().getName());
                //Redundant form name... hack for bs_grid to work without changing it's code further
                js.put("medicationForm.name", m.getMedicationForm().getName());
            }

            ArrayNode ingredientsArray = js.putArray("ingredients");
            // Add all the important information about ingredients to the medications object node
            if (m.getMedicationActiveDrugs() != null) {
                List<IMedicationActiveDrug> ingredients = m.getMedicationActiveDrugs();
                for (IMedicationActiveDrug i : ingredients) {
                    ObjectNode ingredientNode = ingredientsArray.addObject();

                    if (i.getMedicationActiveDrugName() != null)
                        ingredientNode.put("name", i.getMedicationActiveDrugName().getName());
                    if (i.getMedicationMeasurementUnit() != null)
                        ingredientNode.put("unit", i.getMedicationMeasurementUnit().getName());
                    ingredientNode.put("value", i.getValue());
                }
            }
        }

        return response;
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


                medicationInventory.setQuantity_total(quantityTotal);
                medicationInventory = medicationInventoryRepository.update(medicationInventory);
            }
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantity_total(), medicationInventory.getQuantity_current(), null);
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
            int medicationTotal = medicationInventory.getQuantity_total();
            int medicationCurrent = medicationInventory.getQuantity_current();

            medicationInventory.setQuantity_total(medicationTotal - (medicationCurrent - newQuantity));

            medicationInventory.setQuantity_current(newQuantity);

            medicationInventory = medicationInventoryRepository.update(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(),  medicationInventory.getQuantity_current(), medicationInventory.getQuantity_total(), null);
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
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(),  medicationInventory.getQuantity_current(), medicationInventory.getQuantity_total(), medicationInventory.getIsDeleted());
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
            Integer currentQuantity = null;
            Integer totalQuantity = null;

            if (medicationInventory != null) {

                medicationInventory.setQuantity_current(medicationInventory.getQuantity_current() - quantityToSubtract);
                medicationInventory = medicationInventoryRepository.update(medicationInventory);
                currentQuantity = medicationInventory.getQuantity_current();
                totalQuantity = medicationInventory.getQuantity_total();
            }

            medicationItem = itemModelMapper.createMedicationItem(medication, currentQuantity, totalQuantity);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }
        response.setResponseObject(medicationItem);

        return response;
    }
}
