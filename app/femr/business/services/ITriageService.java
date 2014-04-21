package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IPhoto;
import femr.ui.models.data.PatientEncounterItem;
import femr.ui.models.data.PatientItem;
import femr.ui.models.data.VitalItem;

import java.util.List;
import java.util.Map;

public interface ITriageService {
    ServiceResponse<PatientItem> findPatientAndUpdateSex(int id, String sex);
    ServiceResponse<List<VitalItem>> findAllVitalItems();

    ServiceResponse<PatientItem> createPatient(PatientItem patient);
    ServiceResponse<PatientEncounterItem> createPatientEncounter(PatientEncounterItem patientEncounterItem);
    ServiceResponse<List<? extends IPatientEncounterVital>> createPatientEncounterVitals(Map<String,Float> patientEncounterVital, int userId, int encounterId);
    ServiceResponse<List<VitalItem>> createPatientEncounterVitalItems(Map<String,Float> patientEncounterVital, int userId, int encounterId);

    public ServiceResponse<String> getDateOfTriageCheckIn(int encounterId);
}
