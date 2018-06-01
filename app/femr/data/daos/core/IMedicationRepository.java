package femr.data.daos.core;

import femr.data.models.core.*;

import java.util.List;

/**
 * Created by ajsaclayan on 6/27/16.
 */
public interface IMedicationRepository {
    /**
      *  Retrieve medication units by unit name
      *  @param  unitName of the medication, not null
      *  @return the unit name and description
     **/
    IConceptMedicationUnit retrieveMedicationUnitByUnitName(String unitName);

    /**
     *  Retrieve generic name if it exists in the database by generic name
     *  @param  genericName of the medication, not null
     *  @return the generic name if it exists, may be null
     **/
    IMedicationGeneric retrieveMedicationGenericByName(String genericName);

    /**
     * Retrieve a medication's inventory based on the id of the medication and the trip id
     * @param medicationId id of the medication, not null
     * @param tripId trip id that the user is on, not null. This is required because each trip has
     *               a different formulary
     * @return the medication inventory or null if none exists
     */
    IMedicationInventory retrieveMedicationInventoryByMedicationIdAndTripId(int medicationId, int tripId);

    /**
     * Retrieves all medication inventories for a particular trip that are or aren't deleted
     * @param tripId id of the trip to retrieve medication inventories for, not null
     * @param isDeleted set to false if you ONLY want inventories that have not been deleted, may be null
     * @return a list of all medication inventories for a particular trip or null if none
     */
    List<? extends IMedicationInventory> retrieveMedicationInventoriesByTripId(int tripId, Boolean isDeleted);

    /**
     *  Retrieve form name if it exists in the database by form name
     *  @param  formName of the medication, not null
     *  @return the form name if it exists, may be null
     **/
    IConceptMedicationForm retrieveConceptMedicationFormByFormName(String formName);

    /**
     *  Retrieve all medication from previous trips that require a medication form
     *  @return all medications preinventory in ascending order
     **/
    List<? extends IMedication> retrieveAllPreInventoryMedications();

    /**
     *  Delete or undelete medication that exists in the database
     * @param medicationId of the medication, not null
     * @param isDeleted to delete or un-delete, may be true or not true
     */
    IMedication deleteMedication (Integer medicationId, boolean isDeleted);

    /**
     * Creates new medication in the database given only the name of the medicine.
     *
     * @param medicationName name of the medicine
     * @return the new Medication
     */
    IMedication createNewMedication(String medicationName);

    /**
     *  Create new medication in the database
     * @param medicationName of the medication, not null
     * @param medicationGenericStrengths of list of generic strengths, may be null
     * @param conceptMedicationForm of form of concept medication, may be null
     */
    IMedication createNewMedication(String medicationName, List<IMedicationGenericStrength> medicationGenericStrengths, IConceptMedicationForm conceptMedicationForm);

    /**
     * Returns all available concept medication forms. i.e. "B/S", "inj", "caps", etc..
     * @return a list of concept medication fros from db
     */
    List<? extends IConceptMedicationForm> retrieveAllConceptMedicationForms();

    /**
     * Returns all available concept medications that aren't deleted
     * @return List of concept medications from db
     */
    List<? extends IMedication> retrieveAllConceptMedications();

    /**
     * Retrieves a concept medication by its id if it hasn't been deleted
     *
     * @param id id of the concept medication, not null
     * @return the concept medication
     */
    IMedication retrieveConceptMedicationById(int id);

    /**
     * Returns all available concept medication units. i.e. "g/dL", "milligram", "ounces", etc..
     * @return List of concept medication units from db
     */
    List<? extends IConceptMedicationUnit> retrieveAllConceptMedicationUnits();

    /**
     *  Create new medication in the database
     * @param tripId
     * @return
     */
    List<? extends IMedication> retrieveAllMedicationByTripId(Integer tripId);

    /**
     *  Creates OR Updates a medication inventory. If you send an existing medication inventory, this will update the
     *  record. (existing = available id)
     *
     *  @param  medicationInventory data object to save
     *  @return the new or updated medicationInventory
     **/
    IMedicationInventory saveMedicationInventory(IMedicationInventory medicationInventory);
}

