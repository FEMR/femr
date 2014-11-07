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
package femr.business.services;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.common.dto.ServiceResponse;
import femr.data.models.*;
import femr.data.daos.IRepository;
import femr.common.models.MedicationItem;

import java.util.ArrayList;
import java.util.List;

public class InventoryService implements IInventoryService {
    private final IRepository<IMedication> medicationRepository;
    private final IRepository<IMedicationActiveDrugName> medicationActiveDrugNameRepository;
    private final IRepository<IMedicationForm> medicationFormRepository;
    private final IRepository<IMedicationMeasurementUnit> medicationMeasurementUnitRepository;
    private DomainMapper domainMapper;

    @Inject
    public InventoryService(IRepository<IMedication> medicationRepository,
                            IRepository<IMedicationActiveDrugName> medicationActiveDrugNameRepository,
                            IRepository<IMedicationForm> medicationFormRepository,
                            IRepository<IMedicationMeasurementUnit> medicationMeasurementUnitRepository,
                            DomainMapper domainMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationActiveDrugNameRepository = medicationActiveDrugNameRepository;
        this.medicationFormRepository = medicationFormRepository;
        this.medicationMeasurementUnitRepository = medicationMeasurementUnitRepository;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<MedicationItem>> getMedicationInventory() {
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
            medicationItems.add(DomainMapper.createMedicationItem(m));
        }
        response.setResponseObject(medicationItems);

        return response;
    }

    /**
     * {@inheritDoc}
     */
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
                        medicationActiveDrugName = domainMapper.createMedicationActiveDrugName(miac.getName());
                    }
                    if (medicationMeasurementUnit != null) {
                        IMedicationActiveDrug medicationActiveDrug = domainMapper.createMedicationActiveDrug(miac.getValue(), false, medicationMeasurementUnit.getId(), medicationActiveDrugName);
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
            if (medicationForm == null){
                medicationForm = domainMapper.createMedicationForm(medicationItem.getForm());
            }


            IMedication medication = domainMapper.createMedication(medicationItem, medicationActiveDrugs, medicationForm);
            medication = medicationRepository.create(medication);
            MedicationItem newMedicationItem = DomainMapper.createMedicationItem(medication);
            response.setResponseObject(newMedicationItem);
        } catch (Exception ex) {
            response.addError("", "error creating medication");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<List<String>> getAvailableUnits() {
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
    public ServiceResponse<List<String>> getAvailableForms() {
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

}
