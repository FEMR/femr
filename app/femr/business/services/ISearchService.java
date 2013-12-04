package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.*;

import java.util.List;
import java.util.Map;

public interface ISearchService {
    ServiceResponse<IPatient> findPatientById(int id);

    public  ServiceResponse  <List<? extends IPatient>> findPatientByName(String firstName, String lastName);

    ServiceResponse<IPatientEncounter> findPatientEncounterById(int id);

    ServiceResponse<IPatientEncounter> findCurrentEncounterByPatientId(int id);

    ServiceResponse<IPatientEncounterVital> findPatientEncounterVitalByVitalIdAndEncounterId(int vitalId, int encounterId);

    ServiceResponse<List<? extends IPatientEncounter>> findAllEncountersByPatientId(int id);

    ServiceResponse<List<? extends IVital>> findAllVitals();

    ServiceResponse<List<? extends IMedication>> findAllMedications();

    ServiceResponse<List<? extends IPatientPrescription>> findPrescriptionsByEncounterId(int id);

    ServiceResponse<Map<Integer,List<? extends IPatientEncounterTreatmentField>>> findTreatmentFieldsByEncounterId(int id);

    ServiceResponse<Map<Integer, List<? extends IPatientEncounterHpiField>>> findHpiFieldsByEncounterId(int id);

    ServiceResponse<List<? extends IPatientEncounterTreatmentField>> findProblemsByEncounterId(int id);

    ServiceResponse<List<? extends IPatientEncounterTreatmentField>> findAllTreatmentByEncounterId(int id);
}
