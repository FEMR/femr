package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IPhoto;

import java.util.List;
import java.util.Map;

public interface ITriageService {
    ServiceResponse<IPatient> createPatient(IPatient patient);
    ServiceResponse<IPatient> setPhotoId(int id, int photoId);

    ServiceResponse<IPatientEncounter> createPatientEncounter(IPatientEncounter patientEncounter);

    //ServiceResponse<IPatientEncounterVital> createPatientEncounterVital(IPatientEncounterVital patientEncounterVital);

    ServiceResponse<List<? extends IPatientEncounterVital>> createPatientEncounterVitals(Map<String,Float> patientEncounterVital, int userId, int encounterId);

    ServiceResponse<IPatient> updatePatient(IPatient patient);

    public ServiceResponse<String> getDateOfTriageCheckIn(int encounterId);

}
