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

import com.avaje.ebean.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IMedicationService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationAdministrationItem;
import femr.common.models.MedicationItem;
import femr.common.models.PrescriptionItem;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.stringhelpers.StringUtils;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MedicationService implements IMedicationService {

    private final IRepository<IMedication> medicationRepository;
    private final IRepository<IMedicationActiveDrugName> medicationActiveDrugNameRepository;
    private final IRepository<IMedicationForm> medicationFormRepository;
    private final IRepository<IMedicationMeasurementUnit> medicationMeasurementUnitRepository;
    private final IRepository<IMedicationAdministration> medicationAdministrationRepository;
    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public MedicationService(IRepository<IMedication> medicationRepository,
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
    public ServiceResponse<MedicationItem> createMedication(String name, String form, List<MedicationItem.ActiveIngredient> activeIngredients) {

        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        if (StringUtils.isNullOrWhiteSpace(name)) {
            response.addError("name", "name field was empty");
        }

        try {

            //set each active drug
            List<IMedicationActiveDrug> medicationActiveDrugs = new ArrayList<>();
            ExpressionList<MedicationMeasurementUnit> medicationMeasurementUnitExpressionList;
            ExpressionList<MedicationActiveDrugName> medicationActiveDrugNameExpressionList;
            if (activeIngredients != null) {

                for (MedicationItem.ActiveIngredient miac : activeIngredients) {
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
                    .eq("name", form);
            IMedicationForm medicationForm = medicationFormRepository.findOne(medicationFormExpressionList);
            if (medicationForm == null) {
                medicationForm = dataModelMapper.createMedicationForm(form);
            }

            // Retrieve all medication with the same name
            Query<Medication> query = QueryProvider.getMedicationQuery()
                    .where()
                    .eq("name", name)
                    .orderBy("isDeleted asc");

            IMedication matchingMedication = null;
            List<? extends IMedication> medications;
            medications = medicationRepository.find(query);

            // Attempt to find a matching medication
            for (IMedication medication : medications) {
                // Check if the medications name match
                if (!medication.getName().equalsIgnoreCase(name)) continue;

                // Check if the medications form match
                if (medication.getMedicationForm().getId() != medicationForm.getId()) continue;

                // Check if the medication ingredients match
                boolean allDrugsMatch = true;
                for (IMedicationActiveDrug newMedicationDrug : medicationActiveDrugs) {
                    boolean drugMatch = false;
                    for (IMedicationActiveDrug drug : medication.getMedicationActiveDrugs()) {
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

                // Update isDeleted to false
                matchingMedication.setIsDeleted(false);

                medicationRepository.update(matchingMedication);
                response.setResponseObject(itemModelMapper.createMedicationItem(matchingMedication, null, null));
            } else {
                // Create a new medication in the DB
                IMedication medication = dataModelMapper.createMedication(name, medicationActiveDrugs, medicationForm);
                medication = medicationRepository.create(medication);
                //creates the medication item - quantities are null because the medication was just created.
                MedicationItem newMedicationItem = itemModelMapper.createMedicationItem(medication, null, null);
                response.setResponseObject(newMedicationItem);
            }

        } catch (Exception ex) {

            response.addError("", "error creating medication");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> replacePrescriptions(Map<Integer, Integer> prescriptionPairs) {

        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();
        List<PrescriptionItem> prescriptionItems = new ArrayList<>();

        prescriptionPairs.forEach((newId, oldId) -> {

            ExpressionList<PatientPrescription> newPrescriptionExpressionList = QueryProvider.getPatientPrescriptionQuery()
                    .where()
                    .eq("id", newId);

            ExpressionList<PatientPrescription> replacedPrescriptionExpressionList = QueryProvider.getPatientPrescriptionQuery()
                    .where()
                    .eq("id", oldId);

            try {
                IPatientPrescription newPrescription = patientPrescriptionRepository.findOne(newPrescriptionExpressionList);
                IPatientPrescription replacedPrescription = patientPrescriptionRepository.findOne(replacedPrescriptionExpressionList);

                if (newPrescription == null || replacedPrescription == null) {

                    response.addError("id", "did not find the prescription");
                } else {

                    replacedPrescription.setReplacementId(newPrescription.getId());
                    patientPrescriptionRepository.update(replacedPrescription);
                    prescriptionItems.add(itemModelMapper.createPrescriptionItem(newPrescription.getId(),
                            newPrescription.getMedication().getName(),
                            null,
                            newPrescription.getPhysician().getFirstName(),
                            newPrescription.getPhysician().getLastName(),
                            newPrescription.getMedicationAdministration(),
                            newPrescription.getAmount(),
                            newPrescription.getMedication()));
                }

            } catch (Exception ex) {

                response.addError("", ex.getMessage());
            }
        });
        response.setResponseObject(prescriptionItems);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> dispensePrescriptions(Map<Integer, Boolean> prescriptionsToDispense) {

        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();

        List<PrescriptionItem> prescriptionItems = new ArrayList<>();

        prescriptionsToDispense.forEach((prescriptionId, isCounseled) -> {

            ExpressionList<PatientPrescription> prescriptionExpressionList = QueryProvider.getPatientPrescriptionQuery()
                    .where()
                    .eq("id", prescriptionId);

            try {

                IPatientPrescription prescription = patientPrescriptionRepository.findOne(prescriptionExpressionList);
                prescription.setDispensed(true);
                prescription.setCounseled(isCounseled);
                prescription = patientPrescriptionRepository.update(prescription);

                prescriptionItems.add(itemModelMapper.createPrescriptionItem(prescription.getId(),
                        prescription.getMedication().getName(),
                        null,
                        prescription.getPhysician().getFirstName(),
                        prescription.getPhysician().getLastName(),
                        prescription.getMedicationAdministration(),
                        prescription.getAmount(),
                        prescription.getMedication()));

            } catch (Exception ex) {

                response.addError("", ex.getMessage());
            }
        });


        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PrescriptionItem> createPrescription(int medicationId, Integer administrationId, int encounterId, int userId, int amount, String specialInstructions) {

        ServiceResponse<PrescriptionItem> response = new ServiceResponse<>();

        try {
            IPatientPrescription patientPrescription = dataModelMapper.createPatientPrescription(
                    amount,
                    medicationId,
                    administrationId,
                    userId,
                    encounterId,
                    null,
                    false,
                    false);

            patientPrescription = patientPrescriptionRepository.create(patientPrescription);

            PrescriptionItem prescriptionItem = itemModelMapper.createPrescriptionItem(
                    patientPrescription.getId(),
                    patientPrescription.getMedication().getName(),
                    patientPrescription.getReplacementId(),
                    patientPrescription.getPhysician().getFirstName(),
                    patientPrescription.getPhysician().getLastName(),
                    patientPrescription.getMedicationAdministration(),
                    patientPrescription.getAmount(),
                    patientPrescription.getMedication());
            response.setResponseObject(prescriptionItem);
        } catch (Exception ex) {

            response.addError("", "there was an issue creating the prescription");
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

        //V kevin takes no responsibility for this comment V
        // Set the isDeleted column of the medication to true
        medication.setIsDeleted(true);

        // Update the medication item in the database
        medicationRepository.update(medication);


        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> flagPrescriptionsAsFilled(List<Integer> prescriptionIds) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();

        List<PrescriptionItem> updatedPrescriptions = new ArrayList<>();
        try {

            for (Integer i : prescriptionIds) {
                if (i != null && i > 0) {
                    ExpressionList<PatientPrescription> patientPrescriptionExpressionList = QueryProvider.getPatientPrescriptionQuery()
                            .where()
                            .eq("id", i);
                    IPatientPrescription patientPrescription = patientPrescriptionRepository.findOne(patientPrescriptionExpressionList);
                    patientPrescription.setDispensed(true);
                    patientPrescription = patientPrescriptionRepository.update(patientPrescription);

                    updatedPrescriptions.add(itemModelMapper.createPrescriptionItem(
                            patientPrescription.getId(),
                            patientPrescription.getMedication().getName(),
                            patientPrescription.getReplacementId(),
                            patientPrescription.getPhysician().getFirstName(),
                            patientPrescription.getPhysician().getLastName(),
                            patientPrescription.getMedicationAdministration(),
                            patientPrescription.getAmount(),
                            patientPrescription.getMedication()
                    ));
                }
            }
            response.setResponseObject(updatedPrescriptions);
        } catch (Exception ex) {

            response.addError("", "prescriptions were not updated");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> flagPrescriptionsAsCounseled(List<Integer> prescriptionIds) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();

        List<PrescriptionItem> updatedPrescriptions = new ArrayList<>();
        try {

            for (Integer i : prescriptionIds) {
                if (i != null && i > 0) {
                    ExpressionList<PatientPrescription> patientPrescriptionExpressionList = QueryProvider.getPatientPrescriptionQuery()
                            .where()
                            .eq("id", i);
                    IPatientPrescription patientPrescription = patientPrescriptionRepository.findOne(patientPrescriptionExpressionList);
                    patientPrescription.setCounseled(true);
                    patientPrescription = patientPrescriptionRepository.update(patientPrescription);
                    updatedPrescriptions.add(itemModelMapper.createPrescriptionItem(
                            patientPrescription.getId(),
                            patientPrescription.getMedication().getName(),
                            patientPrescription.getReplacementId(),
                            patientPrescription.getPhysician().getFirstName(),
                            patientPrescription.getPhysician().getLastName(),
                            patientPrescription.getMedicationAdministration(),
                            patientPrescription.getAmount(),
                            patientPrescription.getMedication()
                    ));
                }
            }
            response.setResponseObject(updatedPrescriptions);
        } catch (Exception ex) {

            response.addError("", "prescriptions were not updated");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> retrieveAllMedications() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();

        try {
            List<String> medicationNames = new ArrayList<>();

            Query<Medication> medicationQuery = QueryProvider.getMedicationQuery()
                    .where()
                    .eq("isDeleted", false).orderBy("name");
            List<? extends IMedication> medications = medicationRepository.find(medicationQuery);

            for (IMedication m : medications) {
                medicationNames.add(m.getName());
            }
            response.setResponseObject(medicationNames);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<List<String>> retrieveAvailableMedicationForms() {
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
    public ServiceResponse<List<MedicationAdministrationItem>> retrieveAvailableMedicationAdministrations() {
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

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<List<String>> retrieveAvailableMedicationUnits() {
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
            medicationItems.add(itemModelMapper.createMedicationItem(m, null, null));
        }
        response.setResponseObject(medicationItems);

        return response;
    }

    public ServiceResponse<ObjectNode> retrieveAllMedicationsWithID() {
        ServiceResponse<ObjectNode> response = new ServiceResponse<>();
        ObjectNode returnObject = Json.newObject();
        ArrayNode allMedications = returnObject.putArray("medication");

        try {
            Query<Medication> medicationQuery = QueryProvider.getMedicationQuery()
                    .where()
                    .eq("isDeleted", false).orderBy("name");
            List<? extends IMedication> medications = medicationRepository.find(medicationQuery);

            for (IMedication m : medications) {
                ObjectNode medication = Json.newObject();

                medication.put("id", m.getId());
                //medication.put("name", m.getName());
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
                medication.put("name", medicationDisplayName);

                /*  //not including medication quantities right now
                if (m.getQuantity_current() != null) {
                    medication.put("quantityCurrent", m.getQuantity_current());
                } else {
                    medication.put("quantityCurrent", 0);
                } */

                if (m.getMedicationForm() != null)
                    medication.put("form", m.getMedicationForm().getName());
                else
                    medication.put("form", "N/A");

                ArrayNode ingredientsArray = medication.putArray("ingredients");
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

                allMedications.add(medication);
            }
            response.setResponseObject(returnObject);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            response.addError("exception", ex.getMessage());
        }

        return response;
    }
}
