package femr.data.daos.core;

import femr.data.models.core.*;

import java.util.List;

public interface IPatientRepository {
    /**
     *  Retrieve list of all patients
     *  @return list containing all patients
     **/
    List<? extends IPatient> findAllPatients();

    /**
     *  Retrieve list of patients in provided country
     *  @param  country to search by
     *  @return list containing patients in country
     **/
    List<? extends IPatient> findPatientsInCountry(String country);

    /**
     *  Patient with the given ID
     *  @param  id of the patient
     *  @return the patient with the given ID
     **/
    IPatient findPatientById(Integer id);

    /**
     *  Retrieve list of patients with the given ID
     *  @param  id of the patient
     *  @return list containing patients with the given ID
     **/
    List<? extends IPatient> findPatientsById(Integer id);

    /**
     *  Retrieve list of patients with the given name
     *  @param  name of the patient
     *  @return the patient with the given name
     **/
    List<? extends IPatient> findPatientsByName(String firstName, String lastName);

    /**
     *  Save patient to database
     *  @param  patient to save
     *  @return the provided patient
     **/
    public IPatient save(IPatient patient);
}

