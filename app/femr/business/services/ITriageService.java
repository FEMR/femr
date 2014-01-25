package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;

import java.util.List;

public interface ITriageService {
    ServiceResponse<IPatient> createPatient(IPatient patient);

    ServiceResponse<IPatientEncounter> createPatientEncounter(IPatientEncounter patientEncounter);

    ServiceResponse<IPatientEncounterVital> createPatientEncounterVital(IPatientEncounterVital patientEncounterVital);
    ServiceResponse<List<? extends IPatientEncounterVital>> createPatientEncounterVitals(List<? extends IPatientEncounterVital> patientEncounterVital);

    ServiceResponse<IPatient> updatePatientSex(int id, String sex);
}
