package mock.femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;

import java.util.List;
import java.util.Map;

public class MockTriageService implements ITriageService {
    public ServiceResponse<IPatient> createPatient(IPatient patient){
        return null;
    }

    @Override
    public ServiceResponse<IPatient> setPhotoId(int id, int photoId) {
        return null;
    }

    public ServiceResponse<IPatientEncounter> createPatientEncounter(IPatientEncounter patientEncounter){
        return null;
    }

    @Override
    public ServiceResponse<List<? extends IPatientEncounterVital>> createPatientEncounterVitals(Map<String, Float> patientEncounterVital, int userId, int encounterId) {
        return null;
    }

    public ServiceResponse<IPatientEncounterVital> createPatientEncounterVital(IPatientEncounterVital patientEncounterVital){
        return null;
    }
    public ServiceResponse<List<? extends IPatientEncounterVital>> createPatientEncounterVitals(List<? extends IPatientEncounterVital> patientEncounterVital){
        return null;
    }

    public ServiceResponse<IPatient> updatePatient(IPatient patient){
        return null;
    }

    @Override
    public ServiceResponse<String> getDateOfTriageCheckIn(int encounterId) {
        return null;
    }
}
