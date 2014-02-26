package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatientEncounterHpiField;
import femr.common.models.IPatientEncounterPmhField;
import femr.common.models.IPatientEncounterTreatmentField;
import femr.common.models.IPatientPrescription;
import femr.data.models.PatientEncounterHpiField;
import org.joda.time.DateTime;

import java.util.List;

public interface IMedicalService {
    ServiceResponse<List<? extends IPatientEncounterTreatmentField>> createPatientEncounterTreatmentFields(List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields);

    ServiceResponse<List<? extends IPatientEncounterHpiField>> createPatientEncounterHpiFields(List<? extends IPatientEncounterHpiField> patientEncounterHpiFields);

    ServiceResponse<List<? extends IPatientEncounterPmhField>> createPatientEncounterPmhFields(List<? extends IPatientEncounterPmhField> patientEncounterPmhFields);

    ServiceResponse<IPatientPrescription> createPatientPrescription(IPatientPrescription patientPrescription);
    ServiceResponse<List<? extends IPatientPrescription>> createPatientPrescriptions(List<? extends IPatientPrescription> patientPrescriptions);

    boolean hasPatientBeenCheckedIn(int encounterId);
    public ServiceResponse<DateTime> getDateOfCheckIn(int encounterId);
}
