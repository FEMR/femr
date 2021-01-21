package femr.data.daos.core;

import femr.data.models.core.IConceptPrescriptionAdministration;
import femr.data.models.core.IPatientPrescription;
import femr.data.models.core.IPatientPrescriptionReplacement;
import femr.data.models.core.IPatientPrescriptionReplacementReason;

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
     * Identify replaced prescriptions and their reason
     * @param prescriptionReplacements a list of prescription replacements, not null
     *
     * This method could also be simplified to do replacements individually
     *
     * @return List of prescription replacements
     */
    List<? extends IPatientPrescriptionReplacement> createPrescriptionReplacements(List<? extends IPatientPrescriptionReplacement> prescriptionReplacements);

    /**
     * Returns all available ways concept prescription administration methods. i.e. "BID", "q4h", etc.. and their
     * modifier rules
     * @return List of concept prescription administrations from db
     */
    List<? extends IConceptPrescriptionAdministration> retrieveAllConceptPrescriptionAdministrations();

    /**
     * Retrieves all dispensed prescriptions for a particular encounter ID. This is achieved by eliminating results
     * that have the user_id_pharmacy column not null
     *
     * This method can probably be reduced to "retrieve all prescriptions by encounter ID" and let the service
     * layer take care of the rest, but that's for another time
     *
     * @param encounterId id of the encounter
     * @return a list of dispensed prescriptions
     */
    List<? extends IPatientPrescription> retrieveAllDispensedPrescriptionsByEncounterId(int encounterId);

    /**
     * Retrieve the reason for replacing a prescription - there are only 3 reasons someone can do this:
     * 1) "physician edit" : editing a prescription as it's being prescribed by a physician
     * 2) "pharmacist replacement": replacing a prescription by a pharmacist
     * 3) "encounter edit": editing a prescription after the encounter has been closed
     *
     * @param name the reason you need, not null
     * @return the reason
     */
    IPatientPrescriptionReplacementReason retrieveReplacementReasonByName(String name);

    /**
     * Retrieves all unreplaced prescriptions by encounter ID.
     *
     * This method can likely also be simplified as a more generic "retrieve all prescriptions by encounter ID"
     * @param encounterId id of the encounter, not null
     * @return a list of unreplaced prescriptions, not null
     */
    List<? extends IPatientPrescription> retrieveUnreplacedPrescriptionsByEncounterId(int encounterId);

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
