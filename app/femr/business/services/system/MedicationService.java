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
import femr.common.models.PrescriptionItem;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.IMedication;
import femr.data.models.core.IMedicationActiveDrug;
import femr.data.models.core.IPatientPrescription;
import femr.data.models.mysql.Medication;
import femr.data.models.mysql.PatientPrescription;
import femr.util.stringhelpers.StringUtils;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;

public class MedicationService implements IMedicationService {

    private final IRepository<IPatientPrescription> patientPrescriptionRepository;
    private final IRepository<IMedication> medicationRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public MedicationService(IRepository<IPatientPrescription> patientPrescriptionRepository,
                             IRepository<IMedication> medicationRepository,
                             IDataModelMapper dataModelMapper,
                             @Named("identified") IItemModelMapper itemModelMapper) {

        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
        this.medicationRepository = medicationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PrescriptionItem> createAndReplacePrescription(PrescriptionItem prescriptionItem, int oldScriptId, int userId, boolean isCounseled) {
        ServiceResponse<PrescriptionItem> response = new ServiceResponse<>();
        if (prescriptionItem == null || prescriptionItem.getMedicationID() == null || oldScriptId < 1 || userId < 1) {
            response.addError("", "bad parameters");
            return response;
        }

        try {
            ExpressionList<PatientPrescription> query = QueryProvider.getPatientPrescriptionQuery()
                    .where()
                    .eq("id", oldScriptId);

            IPatientPrescription oldPatientPrescription = patientPrescriptionRepository.findOne(query);

            //Retrieve the medication item
            ExpressionList<Medication> medicationQuery = QueryProvider.getMedicationQuery()
                    .where()
                    .eq("id", prescriptionItem.getMedicationID());
            IMedication medication = medicationRepository.findOne(medicationQuery);


            //create new prescription
            IPatientPrescription newPatientPrescription = dataModelMapper.createPatientPrescription(
                    prescriptionItem.getAmount(),
                    medication,
                    prescriptionItem.getAdministrationId(),
                    userId,
                    oldPatientPrescription.getPatientEncounter().getId(),
                    null,
                    true,
                    isCounseled
            );
            newPatientPrescription = patientPrescriptionRepository.create(newPatientPrescription);

            // Subtract prescription amount from medication amount in medications (Dispensed)
            int quantityCurrent = (medication.getQuantity_current() == null) ? 0 : medication.getQuantity_current();
            medication.setQuantity_current(quantityCurrent - newPatientPrescription.getAmount());
            medicationRepository.update(medication);

            // Map new prescription item to be returned to UI View
            PrescriptionItem newPrescriptionItem = itemModelMapper.createPrescriptionItem(
                    newPatientPrescription.getId(),
                    newPatientPrescription.getMedication().getName(),
                    newPatientPrescription.getReplacementId(),
                    newPatientPrescription.getPhysician().getFirstName(),
                    newPatientPrescription.getPhysician().getLastName(),
                    newPatientPrescription.getMedicationAdministration(),
                    newPatientPrescription.getAmount(),
                    newPatientPrescription.getMedication()
            );
            response.setResponseObject(newPrescriptionItem);


            //replace the old prescription
            oldPatientPrescription.setReplacementId(newPatientPrescription.getId());
            patientPrescriptionRepository.update(oldPatientPrescription);

            //Retrieve the medication item for old prescription
            medicationQuery = QueryProvider.getMedicationQuery()
                    .where()
                    .eq("id", oldPatientPrescription.getMedication().getId());
            medication = medicationRepository.findOne(medicationQuery);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PrescriptionItem>> createPatientPrescriptions(List<PrescriptionItem> prescriptions, int userId, int encounterId, boolean isDispensed, boolean isCounseled) {
        ServiceResponse<List<PrescriptionItem>> response = new ServiceResponse<>();
        if (prescriptions == null || prescriptions.size() < 1 || encounterId < 1) {
            response.addError("", "invalid parameters");
            return response;
        }

        List<IPatientPrescription> patientPrescriptions = new ArrayList<>();
        for (PrescriptionItem script : prescriptions) {
            if (script.getMedicationID() != null) {
                ExpressionList<Medication> query = QueryProvider.getMedicationQuery()
                        .where()
                        .eq("id", script.getMedicationID());
                IMedication medication = medicationRepository.findOne(query);
                patientPrescriptions.add(dataModelMapper.createPatientPrescription(script.getAmount(), medication, script.getAdministrationId(), userId, encounterId, null, isDispensed, isCounseled));
            }
        }

        try {
            List<? extends IPatientPrescription> newPatientPrescriptions = patientPrescriptionRepository.createAll(patientPrescriptions);
            List<PrescriptionItem> newPrescriptionItems = new ArrayList<>();
            for (IPatientPrescription pp : newPatientPrescriptions) {
                if (pp.getMedication() != null) {
                    newPrescriptionItems.add(itemModelMapper.createPrescriptionItem(
                            pp.getId(),
                            pp.getMedication().getName(),
                            pp.getReplacementId(),
                            pp.getPhysician().getFirstName(),
                            pp.getPhysician().getLastName(),
                            pp.getMedicationAdministration(),
                            pp.getAmount(),
                            pp.getMedication()
                    ));
                }
            }
            response.setResponseObject(newPrescriptionItems);
        } catch (Exception ex) {

            response.addError("exception", ex.getMessage());
        }

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

                    //Retrieve the medication item
                    ExpressionList<Medication> medicationQuery = QueryProvider.getMedicationQuery()
                            .where()
                            .eq("id", patientPrescription.getMedication().getId());
                    IMedication medication = medicationRepository.findOne(medicationQuery);

                    // Subtract prescription amount from the medication (dispensed)
                    int quantityCurrent = (medication.getQuantity_current() == null) ? 0 : medication.getQuantity_current();
                    medication.setQuantity_current(quantityCurrent - patientPrescription.getAmount());
                    medicationRepository.update(medication);
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
                for(IMedicationActiveDrug drug : m.getMedicationActiveDrugs()) {
                    formattedDrugNames.add(String.format("%s%s %s",
                                    drug.getValue(),
                                    drug.getMedicationMeasurementUnit().getName(),
                                    drug.getMedicationActiveDrugName().getName())
                    );
                }
                if (formattedDrugNames.size() > 0)
                    medicationDisplayName += " " + Joiner.on("/").join(formattedDrugNames);
                medication.put("name", medicationDisplayName);

                if (m.getQuantity_current() != null) {
                    medication.put("quantityCurrent", m.getQuantity_current());
                } else {
                    medication.put("quantityCurrent", 0);
                }

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
