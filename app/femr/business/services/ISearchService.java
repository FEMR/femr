package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.*;

import java.util.List;
import java.util.Map;

public interface ISearchService {
    ServiceResponse<IPatient> findPatientById(int id);

    ServiceResponse<IPatient> findPatientByName(String firstName, String lastName);

    ServiceResponse<IPatientEncounter> findPatientEncounterById(int id);

    List<? extends IPatientEncounter> findAllEncountersByPatientId(int id);

    ServiceResponse<IPatientEncounter> findCurrentEncounterByPatientId(int id);

    List<? extends IVital> findAllVitals();

    List<? extends IPatientPrescription> findPrescriptionsByEncounterId(int id);

    List<? extends IPatientEncounterTreatmentField> findProblemsByEncounterId(int id);

    ServiceResponse<IPatientEncounterVital> findPatientEncounterVitalByVitalIdAndEncounterId(int vitalId, int encounterId);

}
