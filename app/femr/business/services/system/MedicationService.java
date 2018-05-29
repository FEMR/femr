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

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.services.core.IMedicationService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationAdministrationItem;
import femr.common.models.MedicationItem;
import femr.common.models.PrescriptionItem;
import femr.data.IDataModelMapper;
import femr.data.daos.core.*;
import femr.data.models.core.*;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MedicationService implements IMedicationService {

    private final IMedicationRepository medicationRepository;
    private final IPrescriptionRepository prescriptionRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public MedicationService(IMedicationRepository medicationRepository,
                             IPrescriptionRepository prescriptionRepository,
                             IDataModelMapper dataModelMapper,
                             @Named("identified") IItemModelMapper itemModelMapper) {

        this.medicationRepository = medicationRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<MedicationItem> createMedication(String name, String form, List<MedicationItem.ActiveIngredient> activeIngredients) {

        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        try {

            //set each generic drug
            List<IMedicationGenericStrength> medicationGenericStrengths = new ArrayList<>();

            if (activeIngredients != null) {

                for (MedicationItem.ActiveIngredient miac : activeIngredients) {
                    //get the measurement unit ID (they are concepts)
                    IConceptMedicationUnit conceptMedicationUnit = medicationRepository.retrieveMedicationUnitByUnitName(miac.getUnit());
                    IMedicationGeneric medicationGeneric = medicationRepository.retrieveMedicationGenericByName(miac.getName());

                    if (medicationGeneric == null) {
                        //it's a new active drug name, were going to cascade(save) the bean
                        medicationGeneric = dataModelMapper.createMedicationActiveDrugName(miac.getName());
                    }
                    if (conceptMedicationUnit != null) {
                        IMedicationGenericStrength medicationGenericStrength = dataModelMapper.createMedicationGenericStrength(miac.getValue(), false, conceptMedicationUnit.getId(), medicationGeneric);
                        medicationGenericStrengths.add(medicationGenericStrength);
                    }

                }
            }

            IConceptMedicationForm conceptMedicationForm = medicationRepository.retrieveConceptMedicationFormByFormName(form);
            if (conceptMedicationForm == null) {
                conceptMedicationForm = dataModelMapper.createConceptMedicationForm(form);
            }

            IMedication matchingMedication = null;
            List<? extends IMedication> medications;
            medications = medicationRepository.retrieveAllPreInventoryMedications();

            // Attempt to find a matching medication
            for (IMedication medication : medications) {
                // Check if the medications name match
                if (!medication.getName().equalsIgnoreCase(name)) continue;

                // Check if the medications form match
                if (medication.getConceptMedicationForm().getId() != conceptMedicationForm.getId()) continue;

                // Check if the medication ingredients match
                boolean allDrugsMatch = true;
                for (IMedicationGenericStrength newMedicationGenericDrug : medicationGenericStrengths) {
                    boolean drugMatch = false;
                    for (IMedicationGenericStrength drug : medication.getMedicationGenericStrengths()) {
                        if (newMedicationGenericDrug.getMedicationGeneric().getId() == drug.getMedicationGeneric().getId() //generic name match
                                && newMedicationGenericDrug.getConceptMedicationUnit().getId() == drug.getConceptMedicationUnit().getId() //unit match
                                && newMedicationGenericDrug.getValue().equals(drug.getValue())) { //value match
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
                medicationRepository.deleteMedication(matchingMedication.getId(), false);
                response.setResponseObject(itemModelMapper.createMedicationItem(matchingMedication, null, null, null, null, null));

            } else {
                IMedication medication = medicationRepository.createNewMedication(name, medicationGenericStrengths, conceptMedicationForm);
                //creates the medication item - quantities are null because the medication was just created.
                MedicationItem newMedicationItem = itemModelMapper.createMedicationItem(medication, null, null, null, null, null);

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
        List<IPatientPrescriptionReplacement> patientPrescriptionReplacements = new ArrayList<>();

        //get the reason for replacing
        IPatientPrescriptionReplacementReason patientPrescriptionReplacementReason = prescriptionRepository.retrieveReplacementReasonByName("pharmacist replacement");

        //iterate over each prescription and its replacement
        prescriptionPairs.forEach((newId, oldId) -> {

            try {
                IPatientPrescription newPrescription = prescriptionRepository.retrievePrescriptionById(newId);
                IPatientPrescription replacedPrescription = prescriptionRepository.retrievePrescriptionById(oldId);

                if (newPrescription == null) {

                    response.addError("not found", "new prescription with id: " + newId + " not found.");
                } else if (replacedPrescription == null) {

                    response.addError("not found", "old prescription with id: " + oldId + " not found.");
                } else {
                    patientPrescriptionReplacements.add(
                            dataModelMapper.createPatientPrescriptionReplacement(
                                    replacedPrescription.getId(),
                                    newPrescription.getId(),
                                    patientPrescriptionReplacementReason.getId()
                            )
                    );


                }

            } catch (Exception ex) {

                response.addError("", ex.getMessage());
            }
        });

        try {

            List<? extends IPatientPrescriptionReplacement> replacements = prescriptionRepository.createPrescriptionReplacements(patientPrescriptionReplacements);
            for (IPatientPrescriptionReplacement prescriptionReplacement : replacements) {

                prescriptionItems.add(itemModelMapper.createPrescriptionItem(
                        prescriptionReplacement.getReplacementPrescription().getId(),
                        prescriptionReplacement.getReplacementPrescription().getMedication().getName(),
                        prescriptionReplacement.getReplacementPrescription().getPhysician().getFirstName(),
                        prescriptionReplacement.getReplacementPrescription().getPhysician().getLastName(),
                        prescriptionReplacement.getReplacementPrescription().getConceptPrescriptionAdministration(),
                        prescriptionReplacement.getReplacementPrescription().getAmount(),
                        prescriptionReplacement.getReplacementPrescription().isCounseled(),
                        itemModelMapper.createMedicationItem(prescriptionReplacement.getReplacementPrescription().getMedication(), null, null, null, null, null))
                );
            }
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }


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

        DateTime dateTime = dateUtils.getCurrentDateTime();

        prescriptionsToDispense.forEach((prescriptionId, isCounseled) -> {

            try {

                IPatientPrescription prescription = prescriptionRepository.retrievePrescriptionById(prescriptionId);
                prescription.setDateDispensed(dateTime);
                prescription.setCounseled(isCounseled);
                prescription = prescriptionRepository.updatePrescription(prescription);



                MedicationItem medicationItem = itemModelMapper.createMedicationItem(prescription.getMedication(), null, null, null, null, null);
                prescriptionItems.add(itemModelMapper.createPrescriptionItem(prescription.getId(),
                        prescription.getMedication().getName(),
                        prescription.getPhysician().getFirstName(),
                        prescription.getPhysician().getLastName(),
                        prescription.getConceptPrescriptionAdministration(),
                        prescription.getAmount(),
                        prescription.isCounseled(),
                        medicationItem)
                );

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
    public ServiceResponse<PrescriptionItem> createPrescription(int medicationId, Integer administrationId, int encounterId, int userId, Integer amount, String specialInstructions) {

        ServiceResponse<PrescriptionItem> response = new ServiceResponse<>();
        if (administrationId !=null && administrationId <= 0)
            administrationId = null;

        try {
            IPatientPrescription patientPrescription = prescriptionRepository.createPrescription(amount, medicationId, administrationId, userId, encounterId);


            MedicationItem medicationItem = itemModelMapper.createMedicationItem(patientPrescription.getMedication(), null, null, null, null, null);
            PrescriptionItem prescriptionItem = itemModelMapper.createPrescriptionItem(
                    patientPrescription.getId(),
                    patientPrescription.getMedication().getName(),
                    patientPrescription.getPhysician().getFirstName(),
                    patientPrescription.getPhysician().getLastName(),
                    patientPrescription.getConceptPrescriptionAdministration(),
                    patientPrescription.getAmount(),
                    patientPrescription.isCounseled(),
                    medicationItem);
            response.setResponseObject(prescriptionItem);
        } catch (Exception ex) {

            response.addError("", "there was an issue creating the prescription");
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<PrescriptionItem> createPrescriptionWithNewMedication(String medicationName, Integer administrationId, int encounterId, int userId, Integer amount, String specialInstructions) {

        ServiceResponse<PrescriptionItem> response = new ServiceResponse<>();

        if (StringUtils.isNullOrWhiteSpace(medicationName)) {

            response.addError("", "medicationName can't be null or empty");
            return response;
        }

        if (administrationId !=null && administrationId <= 0)
            administrationId = null;

        try {

            IMedication medication = medicationRepository.createNewMedication(medicationName);

            IPatientPrescription patientPrescription = prescriptionRepository.createPrescription(amount, medication.getId(), administrationId, userId, encounterId);

            MedicationItem medicationItem = itemModelMapper.createMedicationItem(patientPrescription.getMedication(), null, null, null, null, null);
            PrescriptionItem prescriptionItem = itemModelMapper.createPrescriptionItem(
                    patientPrescription.getId(),
                    patientPrescription.getMedication().getName(),
                    patientPrescription.getPhysician().getFirstName(),
                    patientPrescription.getPhysician().getLastName(),
                    patientPrescription.getConceptPrescriptionAdministration(),
                    patientPrescription.getAmount(),
                    patientPrescription.isCounseled(),
                    medicationItem);
            response.setResponseObject(prescriptionItem);

        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<List<String>> retrieveAvailableMedicationForms() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();
        try {
            List<? extends IConceptMedicationForm> conceptMedicationForms = medicationRepository.retrieveAllConceptMedicationForms();
            List<String> availableForms = new ArrayList<>();
            for (IConceptMedicationForm mf : conceptMedicationForms) {
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
            List<? extends IConceptPrescriptionAdministration> medicationAdministrations = prescriptionRepository.retrieveAllConceptPrescriptionAdministrations();

            // Creates a list of MedicationAdministratItems (UI Model) to be passed back to the controller/view
            List<MedicationAdministrationItem> availableAdministrations = new ArrayList<>();
            for (IConceptPrescriptionAdministration ma : medicationAdministrations) {
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
            List<? extends IConceptMedicationUnit> conceptMedicationUnits = medicationRepository.retrieveAllConceptMedicationUnits();
            List<String> availableUnits = new ArrayList<>();
            for (IConceptMedicationUnit mmu : conceptMedicationUnits) {
                availableUnits.add(mmu.getName());
            }
            response.setResponseObject(availableUnits);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    public ServiceResponse<ObjectNode> retrieveAllMedicationsWithID(Integer tripId) {
        ServiceResponse<ObjectNode> response = new ServiceResponse<>();
        ObjectNode returnObject = Json.newObject();
        ArrayNode allMedications = returnObject.putArray("medication");

        try {
            List<? extends IMedication> medications = medicationRepository.retrieveAllMedicationByTripId(tripId);
            MedicationItem medicationItem;

            for (IMedication m : medications) {
                //use the item model mapper to generate the MedicationItem which contains
                //the standard full name for a medication everywhere
                medicationItem = itemModelMapper.createMedicationItem(m, null, null, null, null, null);
                ObjectNode medication = Json.newObject();

                medication.put("id", m.getId());
                medication.put("name", medicationItem.getFullName());

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
