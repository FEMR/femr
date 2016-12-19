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
     * @param medicationGenericStrengths of list of generic strengths, not null
     * @param conceptMedicationForm of form of concept medication, not null
     */
    IMedication createNewMedication(String medicationName, List<IMedicationGenericStrength> medicationGenericStrengths, IConceptMedicationForm conceptMedicationForm);

    /**
     *  Create new medication in the database
     * @param tripId
     * @return
     */
    List<? extends IMedication> retrieveAllMedicationByTripId(Integer tripId);
}

