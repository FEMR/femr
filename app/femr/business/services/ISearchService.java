package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ISearchService {
    ServiceResponse<IPatient> findPatientById(int id);

    ServiceResponse<List<? extends IPatient>> findPatientByName(String firstName, String lastName);

    ServiceResponse<IPatientEncounter> findPatientEncounterById(int id);
    ServiceResponse<IPatientEncounter> findCurrentEncounterByPatientId(int id);
    ServiceResponse<List<? extends IPatientEncounter>> findAllEncountersByPatientId(int id);
    ServiceResponse<List<? extends IPatientPrescription>> findPrescriptionsByEncounterId(int id);
    ServiceResponse<List<? extends IPatientEncounterTreatmentField>> findProblemsByEncounterId(int id);

    ServiceResponse<List<? extends IPatientEncounterVital>> findPatientEncounterVitals(int encounterId, String name);
    ServiceResponse<List<? extends IPatientEncounterTreatmentField>> findTreatmentFields(int encounterId, String name);
    ServiceResponse<List<? extends IPatientEncounterHpiField>> findHpiFields(int encounterId, String name);
    ServiceResponse<List<? extends IPatientEncounterPmhField>> findPmhFields(int encounterId, String name);

    ServiceResponse<IVital> findVital(String name);
    ServiceResponse<ITreatmentField> findTreatmentField(String name);
    ServiceResponse<IHpiField> findHpiField(String name);
    ServiceResponse<IPmhField> findPmhField(String name);
    ServiceResponse<List<? extends IVital>> findAllVitals();
    ServiceResponse<List<? extends ITreatmentField>> findAllTreatmentFields();
    ServiceResponse<List<? extends IHpiField>> findAllHpiFields();
    ServiceResponse<List<? extends IPmhField>> findAllPmhFields();
    ServiceResponse<List<? extends IMedication>> findAllMedications();

    ServiceResponse<IPatientPrescription> findPatientPrescriptionById(int id);
}
