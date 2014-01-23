package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ISearchService {
    ServiceResponse<IPatient> findPatientById(int id);

    public ServiceResponse<List<? extends IPatient>> findPatientByName(String firstName, String lastName);

    ServiceResponse<IPatientEncounter> findPatientEncounterById(int id);

    ServiceResponse<IPatientEncounter> findCurrentEncounterByPatientId(int id);

    //turn the service response object into a List<? extends IPatientEncounterVital>
    //to take into consideration replaced vitals
    ServiceResponse<IPatientEncounterVital> findPatientEncounterVital(int encounterId, String name);

    ServiceResponse<List<? extends IPatientEncounter>> findAllEncountersByPatientId(int id);

    ServiceResponse<List<? extends IVital>> findAllVitals();

    ServiceResponse<IVital> findVital(String name);

    ServiceResponse<ITreatmentField> findTreatmentField(String name);

    ServiceResponse<List<? extends IMedication>> findAllMedications();

    ServiceResponse<List<? extends IPatientPrescription>> findPrescriptionsByEncounterId(int id);



    ServiceResponse<List<? extends IPatientEncounterTreatmentField>> findTreatmentField(int encounterId, String name);

    ServiceResponse<Map<Integer, List<? extends IPatientEncounterHpiField>>> findHpiFieldsByEncounterId(int id);
    ServiceResponse<List<? extends IPatientEncounterHpiField>> findHpiField(int encounterId, String name);

    ServiceResponse<Map<Integer, List<? extends IPatientEncounterPmhField>>> findPmhFieldsByEncounterId(int id);
    ServiceResponse<List<? extends IPatientEncounterPmhField>> findPmhField(int encounterId, String name);


    ServiceResponse<List<? extends IPatientEncounterTreatmentField>> findProblemsByEncounterId(int id);
}
