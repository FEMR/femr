package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;

public interface ITriageService {
    ServiceResponse<IPatient> createPatient(IPatient patient);

    ServiceResponse<IPatientEncounter> createPatientEncounter(IPatientEncounter patientEncounter);

    ServiceResponse<IPatientEncounterVital> createPatientEncounterVital(IPatientEncounterVital patientEncounterVital);

    String getCurrentDateTime();

}
