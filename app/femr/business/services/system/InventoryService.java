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
import com.avaje.ebean.Query;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IInventoryService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationAdministrationItem;
import femr.data.DataModelMapper;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.common.models.MedicationItem;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.ui.models.admin.inventory.DataGridFilter;
import femr.ui.models.admin.inventory.DataGridSorting;
import org.apache.commons.lang3.StringUtils;
import play.libs.Json;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.List;

public class InventoryService implements IInventoryService {

    private final IRepository<IMedication> medicationRepository;
    private final IRepository<IMedicationActiveDrugName> medicationActiveDrugNameRepository;
    private final IRepository<IMedicationForm> medicationFormRepository;
    private final IRepository<IMedicationMeasurementUnit> medicationMeasurementUnitRepository;
    private final IRepository<IMedicationAdministration> medicationAdministrationRepository;
    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public InventoryService(IRepository<IMedication> medicationRepository,
                            IRepository<IMedicationActiveDrugName> medicationActiveDrugNameRepository,
                            IRepository<IMedicationAdministration> medicationAdministrationRepository,
                            IRepository<IMedicationForm> medicationFormRepository,
                            IRepository<IMedicationMeasurementUnit> medicationMeasurementUnitRepository,
                            IRepository<IPatientPrescription> patientPrescriptionRepository,
                            IDataModelMapper dataModelMapper,
                            @Named("identified") IItemModelMapper itemModelMapper) {

        this.medicationRepository = medicationRepository;
        this.medicationActiveDrugNameRepository = medicationActiveDrugNameRepository;
        this.medicationFormRepository = medicationFormRepository;
        this.medicationMeasurementUnitRepository = medicationMeasurementUnitRepository;
        this.medicationAdministrationRepository = medicationAdministrationRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<MedicationItem>> retrieveMedicationInventory() {
        ServiceResponse<List<MedicationItem>> response = new ServiceResponse<>();

        ExpressionList<Medication> query = QueryProvider.getMedicationQuery()
                .where()
                .eq("isDeleted", false);

        List<? extends IMedication> medications;
        try {
            medications = medicationRepository.find(query);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        List<MedicationItem> medicationItems = new ArrayList<>();
        for (IMedication m : medications) {
            medicationItems.add(itemModelMapper.createMedicationItem(m));
        }
        response.setResponseObject(medicationItems);

        return response;
    }

    /**
     *
     *  {@inheritDoc}
     *
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





        /* create array to hold the medication */

        for (IMedication m : medications) {
            /* Create node to customize return values */
            ObjectNode js = page_data.addObject();
            js.put("id", m.getId());
            //js.put("name", m.getName());
            String medicationDisplayName = m.getName();
            //Create list of drug name/unit/values to append to the medication name
            List<String> formattedDrugNames = new ArrayList<String>();
            for(IMedicationActiveDrug drug : m.getMedicationActiveDrugs()) {
                formattedDrugNames.add(String.format("%s%s %s",
                                drug.getValue(),
                                drug.getMedicationMeasurementUnit().getName(),
                                drug.getMedicationActiveDrugName().getName())
                );
            }
            if (formattedDrugNames.size() > 0)
                medicationDisplayName += " " + Joiner.on("/").join(formattedDrugNames);
            js.put("name", medicationDisplayName);

            js.put("quantity_current", m.getQuantity_current());
            js.put("quantity_initial", m.getQuantity_total());

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
    //Andre Farah End Code

    /**
     * {@inheritDoc}
     */
    //Creates the Medication
    public ServiceResponse<MedicationItem> createMedication(MedicationItem medicationItem) {
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        try {

            //set each active drug
            List<IMedicationActiveDrug> medicationActiveDrugs = new ArrayList<>();
            ExpressionList<MedicationMeasurementUnit> medicationMeasurementUnitExpressionList;
            ExpressionList<MedicationActiveDrugName> medicationActiveDrugNameExpressionList;
            if (medicationItem.getActiveIngredients() != null) {

                for (MedicationItem.ActiveIngredient miac : medicationItem.getActiveIngredients()) {
                    medicationMeasurementUnitExpressionList = QueryProvider.getMedicationMeasurementUnitQuery()
                            .where()
                            .eq("name", miac.getUnit());
                    medicationActiveDrugNameExpressionList = QueryProvider.getMedicationActiveDrugNameQuery()
                            .where()
                            .eq("name", miac.getName());

                    //get the measurement unit ID (they are pre recorded)
                    IMedicationMeasurementUnit medicationMeasurementUnit = medicationMeasurementUnitRepository.findOne(medicationMeasurementUnitExpressionList);
                    IMedicationActiveDrugName medicationActiveDrugName = medicationActiveDrugNameRepository.findOne(medicationActiveDrugNameExpressionList);
                    if (medicationActiveDrugName == null) {
                        //it's a new active drug name, were going to cascade(save) the bean
                        medicationActiveDrugName = dataModelMapper.createMedicationActiveDrugName(miac.getName());
                    }
                    if (medicationMeasurementUnit != null) {
                        IMedicationActiveDrug medicationActiveDrug = dataModelMapper.createMedicationActiveDrug(miac.getValue(), false, medicationMeasurementUnit.getId(), medicationActiveDrugName);
                        medicationActiveDrugs.add(medicationActiveDrug);
                    }

                }
            }

            //set the form
            ExpressionList<MedicationForm> medicationFormExpressionList;

            medicationFormExpressionList = QueryProvider.getMedicationFormQuery()
                    .where()
                    .eq("name", medicationItem.getForm());
            IMedicationForm medicationForm = medicationFormRepository.findOne(medicationFormExpressionList);
            if (medicationForm == null) {
                medicationForm = dataModelMapper.createMedicationForm(medicationItem.getForm());
            }

            // Retrieve all medication with the same name
            Query<Medication> query = QueryProvider.getMedicationQuery()
                    .where()
                    .eq("name", medicationItem.getName())
                    .orderBy("isDeleted asc");

            IMedication matchingMedication = null;
            List<? extends IMedication> medications;
            medications = medicationRepository.find(query);

            // Attempt to find a matching medication
            for(IMedication medication : medications) {
                // Check if the medications name match
                if (!medication.getName().equalsIgnoreCase(medicationItem.getName())) continue;

                // Check if the medications form match
                if (medication.getMedicationForm().getId() != medicationForm.getId()) continue;

                // Check if the medication ingredients match
                boolean allDrugsMatch = true;
                for(IMedicationActiveDrug newMedicationDrug : medicationActiveDrugs) {
                    boolean drugMatch = false;
                    for(IMedicationActiveDrug drug : medication.getMedicationActiveDrugs()) {
                        if (newMedicationDrug.getMedicationActiveDrugName().getId() == drug.getMedicationActiveDrugName().getId()
                                && newMedicationDrug.getMedicationMeasurementUnit().getId() == drug.getMedicationMeasurementUnit().getId()) {
                            drugMatch = true;
                        }
                        if (!drugMatch) allDrugsMatch = false;
                    }

                    // No match so break early.
                    if (!allDrugsMatch) break;
                }
                if (!allDrugsMatch) continue;

                // Everything matches so set matchingMedication and break out of loop
                matchingMedication = medication;
                break;
            }

            // There exist a matching medication in the database, so update that one rather then create new one
            if (matchingMedication != null) {
                // Update the quantity
                int quantity = (matchingMedication.getQuantity_current() != null) ? matchingMedication.getQuantity_current() : 0;
                matchingMedication.setQuantity_current(quantity + medicationItem.getQuantity_current());

                // Update isDeleted to false
                matchingMedication.setIsDeleted(false);

                medicationRepository.update(matchingMedication);
                response.setResponseObject(itemModelMapper.createMedicationItem(matchingMedication));
            } else {
                // Create a new medication in the DB
                IMedication medication = dataModelMapper.createMedication(medicationItem.getName(), medicationItem.getQuantity_total(), medicationItem.getQuantity_current(), medicationActiveDrugs, medicationForm);
                medication = medicationRepository.create(medication);
                MedicationItem newMedicationItem = itemModelMapper.createMedicationItem(medication);
                response.setResponseObject(newMedicationItem);
            }

            IMedication medication = dataModelMapper.createMedication(medicationItem.getName(), medicationActiveDrugs, medicationForm);
            medication = medicationRepository.create(medication);
            MedicationItem newMedicationItem = itemModelMapper.createMedicationItem(medication);
            response.setResponseObject(newMedicationItem);

        } catch (Exception ex) {
            response.addError("", "error creating medication");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<List<String>> retrieveAvailableUnits() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();
        try {
            List<? extends IMedicationMeasurementUnit> medicationMeasurementUnits = medicationMeasurementUnitRepository.findAll(MedicationMeasurementUnit.class);
            List<String> availableUnits = new ArrayList<>();
            for (IMedicationMeasurementUnit mmu : medicationMeasurementUnits) {
                availableUnits.add(mmu.getName());
            }
            response.setResponseObject(availableUnits);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<List<String>> retrieveAvailableForms() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();
        try {
            List<? extends IMedicationForm> medicationForms = medicationFormRepository.findAll(MedicationForm.class);
            List<String> availableForms = new ArrayList<>();
            for (IMedicationForm mf : medicationForms) {
                availableForms.add(mf.getName());
            }
            response.setResponseObject(availableForms);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<List<MedicationAdministrationItem>> retrieveAvailableAdministrations() {
        ServiceResponse<List<MedicationAdministrationItem>> response = new ServiceResponse<>();
        try {
            // Retrieve a list of all medicationAdministrations from the database
            List<? extends IMedicationAdministration> medicationAdministrations = medicationAdministrationRepository.findAll(MedicationAdministration.class);

            // Creates a list of MedicationAdministratItems (UI Model) to be passed back to the controller/view
            List<MedicationAdministrationItem> availableAdministrations = new ArrayList<>();
            for (IMedicationAdministration ma : medicationAdministrations) {
                availableAdministrations.add(itemModelMapper.createMedicationAdministrationItem(ma));
            }

            // Set the response object to the list of MedicationAdministrationItem's. The Response is what is sent back to the controller
            response.setResponseObject(availableAdministrations);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    public ServiceResponse<MedicationItem> deleteMedication(int medicationID) {
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        // Get the medication Item by it's ID
        IMedication medication;
        ExpressionList<Medication> medicationQuery = QueryProvider.getMedicationQuery()
                .where()
                .eq("id", medicationID);

        try {
            // Find one medication (should only be 1 with the ID) from the database
            medication = medicationRepository.findOne(medicationQuery);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        // Get patient prescriptions that use this medication
        /*
        ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                .where()
                .eq("medication_id", medicationID);
        List<? extends IPatientPrescription> prescriptions = patientPrescriptionRepository.find(query);

        if (prescriptions.size() == 0) {
            //Can delete medication from table since no prescriptions rely on it
            medicationRepository.delete(medication);
        } else {
        */


        // Set the isDeleted column of the medication to true
        medication.setIsDeleted(true);

        // Update the medication item in the database
        medicationRepository.update(medication);


        return response;
    }
}
