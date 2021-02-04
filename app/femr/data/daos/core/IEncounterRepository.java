package femr.data.daos.core;

import femr.data.models.core.IPatientEncounter;
import org.joda.time.DateTime;

import java.util.List;

public interface IEncounterRepository {

    /**
     * Create a new patient encounter
     *
     * @param patientID id of the patient, not null
     * @param date date for triage visit, not null
     * @param userId id of the user, not null
     * @param patientAgeClassificationId id of the patient age classification, may be null
     * @param tripId id of the trip, may be null
     * @return the new patient encounter or null if an error happens
     */
    IPatientEncounter createPatientEncounter(int patientID, DateTime date, int userId, Integer patientAgeClassificationId, Integer tripId);

    /**
     * Deletes a patient's encounter. this is a soft delete
     *
     * @param encounterId id of the encounter to delete, not null
     * @param reason reason that the user is deleting the encounter, may be null
     * @param userId id of the user deleting the encounter, not null
     * @return the deleted patient encounter
     */
    IPatientEncounter deletePatientEncounter(int encounterId, String reason, int userId);

    /**
     * Retrieves all patient encounters with provided constraints
     *
     * @param from patient encounters greater than or equal to this date, may be null
     * @param to patient encounters less than or equal to this date, may be null
     * @param tripId patient encounters on a particular trip, may be null
     * @return a list of patient encounters that were found or NULL if errors occur
     */
    List<? extends IPatientEncounter> retrievePatientEncounters(DateTime from, DateTime to, Integer tripId);

    /**
     * Retrieve a patient encounter from the database by its id
     *
     * @param id id of the patient encounter, not null
     * @return the patient encounter or NULL if errors occur
     */
    IPatientEncounter retrievePatientEncounterById(int id);

    /**
     * Retrieve an ascending list of patient encounters by patient id
     *
     * @param patientId id of the patient, not null
     * @return list of patient encounters
     */
    List<? extends IPatientEncounter> retrievePatientEncountersByPatientIdAsc(int patientId);

    /**
     * Retrieve a descending list of patient encounters by patient id
     *
     * @param patientId id of the patient, not null
     * @return list of patient encounters
     */
    List<? extends IPatientEncounter> retrievePatientEncountersByPatientIdDesc(int patientId);

    /**
     * Checks a patient into Medical at the specified date
     *
     * @param encounterId encounter Id to update, not null
     * @param userId id of the physician checking the patient into medical, not null
     * @param date date and time that the patient was seen in Medical, not null
     * @return updated patient encounter object or NULL if errors occur
     */
    IPatientEncounter savePatientEncounterMedicalCheckin(int encounterId, int userId, DateTime date);

    /**
     * Checks a patient into Pharmacy at the specified date
     *
     * @param encounterId encounter Id to update, not null
     * @param userId id of the physician checking the patient into medical, not null
     * @param date date and time that the patient was seen in Medical, not null
     * @return updated patient encounter object or NULL if errors occur
     */
    IPatientEncounter savePatientEncounterPharmacyCheckin(int encounterId, int userId, DateTime date);

    /**
     * Marks a patient as being screened for diabetes
     *
     * @param encounterId id of the encounter, not null
     * @param userId id of the user screening the patient, not null
     * @param date date of the screening, not null
     * @param isScreened whether or not the patient is screened, not null
     * @return an updated patient encounter object or NULL if errors occur
     */
    IPatientEncounter savePatientEncounterDiabetesScreening(int encounterId, int userId, DateTime date, Boolean isScreened);
}
