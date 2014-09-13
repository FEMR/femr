package femr.business.services;

import femr.common.models.PrescriptionItem;
import femr.common.dto.ServiceResponse;
import femr.common.models.ProblemItem;
import femr.common.models.VitalItem;
import femr.data.models.IPatientEncounter;

import java.util.List;

public interface IPharmacyService {

    /**
     * Checks a patient into pharmacy (updates date_of_pharmacy_visit and identifies the user)
     *
     * @param encounterId current encounter
     * @param userId      id of the pharmacist
     * @return updated patient encounter
     */
    ServiceResponse<IPatientEncounter> checkPatientIn(int encounterId, int userId);

    /**
     * Create a new prescription and replace an old one with it
     *
     * @param prescriptionItem new prescription to replace the old one
     * @param oldScriptId      id of the old prescription that is being replaced
     * @return updated new prescription
     */
    ServiceResponse<PrescriptionItem> createAndReplacePrescription(PrescriptionItem prescriptionItem, int oldScriptId, int userId);

    /**
     * Find all problems
     *
     * @param encounterId id of the encounter
     * @return list of problems
     */
    ServiceResponse<List<ProblemItem>> findProblemItems(int encounterId);

    /**
     * Retrieve all medication names for typeahead
     *
     * @return
     */
    ServiceResponse<List<String>> findAllMedications();
}
