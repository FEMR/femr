package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.PatientEncounterHpiField;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

public interface IMedicalService {
    /* Maps for HPI, Treatment, and PMH:
     * Map<String, List<>>
      *     Where string is the name value and list is a list of actual values sorted by time*/
    ServiceResponse<Map<String, List<? extends IPatientEncounterHpiField>>> findHpiFieldsByEncounterId(int encounterId);

    ServiceResponse<Map<String, List<? extends IPatientEncounterPmhField>>> findPmhFieldsByEncounterId(int encounterId);

    ServiceResponse<Map<String, List<? extends IPatientEncounterTreatmentField>>> findTreatmentFieldsByEncounterId(int encounterId);


    /* Create Actions */
    ServiceResponse<List<? extends IPatientEncounterTreatmentField>> createPatientEncounterTreatmentFields(List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields);
    ServiceResponse<List<? extends IPatientEncounterHpiField>> createPatientEncounterHpiFields(List<? extends IPatientEncounterHpiField> patientEncounterHpiFields);
    ServiceResponse<List<? extends IPatientEncounterPmhField>> createPatientEncounterPmhFields(List<? extends IPatientEncounterPmhField> patientEncounterPmhFields);
    ServiceResponse<IPatientPrescription> createPatientPrescription(IPatientPrescription patientPrescription);
    ServiceResponse<List<? extends IPatientEncounterVital>> createPatientEncounterVitals(Map<String,Float> patientEncounterVital, int userId, int encounterId);
    ServiceResponse<List<? extends IPatientPrescription>> createPatientPrescriptions(List<? extends IPatientPrescription> patientPrescriptions);

    boolean hasPatientBeenCheckedInByPhysician(int encounterId);

    public ServiceResponse<DateTime> getDateOfCheckIn(int encounterId);

    ServiceResponse<List<String>> getCustomTabs();
}
