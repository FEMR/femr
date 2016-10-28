package femr.data.daos.core;

import femr.data.models.core.*;

import java.util.List;

public interface IPatientRepository {

    /**
     * Retrieve list of all patient age classificaitons that are not deleted.
     * Performs ascending sort by SortOrder column.
     *
     * @return list of sorted age classifications or NULL if an error occurs
     */
    List<? extends IPatientAgeClassification> retrieveAllPatientAgeClassifications();

    /**
     *  Retrieve list of all patients that have not
     *  been deleted.
     *  @return list containing all patients or NULL if an error occurs
     **/
    List<? extends IPatient> retrieveAllPatients();

    /**
     *  Retrieve list of patients in provided country that
     *  have not been deleted.
     *  @param  country to search by
     *  @return list containing patients in country
     **/
    List<? extends IPatient> retrievePatientsInCountry(String country);

    /**
     *  Retrieve patient that is not deleted
     *  @param  id of the patient
     *  @return the patient with the given ID or NULL if no patients are found
     **/
    IPatient retrievePatientById(Integer id);

    /**
     *  Retrieve list of patients with the given first AND last name. Will also
     *  try to match firstName to all available first names & last names
     *
     *  @param  firstName of the patient or a guess
     *  @param  lastName of the patient, can be null
     *  @return the patient with the given name
     **/
    List<? extends IPatient> retrievePatientsByName(String firstName, String lastName);

    /**
     *  Creates OR Updates a patient. If you send an existing patient, this will update their
     *  record. (existing = available id)
     *
     *  @param  patient data object to save
     *  @return the new or updated patient
     **/
    IPatient savePatient(IPatient patient);
}

