package mock.femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.business.services.ITriageService;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;

import java.util.List;

public class MockTriageService implements ITriageService {
    public ServiceResponse<IPatient> createPatient(IPatient patient){
        return null;
    }

    public ServiceResponse<IPatientEncounter> createPatientEncounter(IPatientEncounter patientEncounter){
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
}
