package femr.data.daos.core;

import femr.data.models.core.IConceptPrescriptionAdministration;
import femr.data.models.core.IPatientPrescription;

import java.util.List;

public interface IPrescriptionRepository {

    /**
     * Create a new prescription for a patient
     *
     * @param amount                        amount of medication dispensed, not null
     * @param medicationId                  the id of the dispensed medication, not null
     * @param medicationAdministrationId    the id of Administration type of the prescription, may be null
     * @param userId                        id of the user creating the prescription, not null
     * @param encounterId                   encounter id of the prescription, not null
     * @return a new prescription
     */
    IPatientPrescription createPrescription(Integer amount, int medicationId, Integer medicationAdministrationId, int userId, int encounterId);

    /**
     * Returns all available ways concept prescription administration methods. i.e. "BID", "q4h", etc.. and their
     * modifier rules
     * @return List of concept prescription administrations from db
     */
    List<? extends IConceptPrescriptionAdministration> retrieveAllConceptPrescriptionAdministrations();

    /**
     * Retrieve a prescription by its ID
     * @param prescriptionId id of the prescription, not null
     * @return the prescription if it exists, may be null
     */
    IPatientPrescription retrievePrescriptionById(int prescriptionId);

    /**
     * Updates a patient prescription. The patientPrescription object should have an ID.
     * Typically used to dispense a medication or show that the patient was counseled for it.
     *
     * @param patientPrescription patient prescription object with a valid ID, not null
     * @return the updated patient prescription object, may be null
     */
    IPatientPrescription updatePrescription(IPatientPrescription patientPrescription);
}
