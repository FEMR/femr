package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatientEncounterHpiField;
import femr.common.models.IPatientEncounterPmhField;
import femr.common.models.IPatientEncounterTreatmentField;
import femr.common.models.IPatientPrescription;
import org.joda.time.DateTime;

public interface IMedicalService {
    ServiceResponse<IPatientEncounterTreatmentField> createPatientEncounterTreatmentField(IPatientEncounterTreatmentField patientEncounterTreatmentField);

    ServiceResponse<IPatientEncounterHpiField> createPatientEncounterHpiField(IPatientEncounterHpiField patientEncounterHpiField);

    ServiceResponse<IPatientEncounterPmhField> createPatientEncounterPmhField(IPatientEncounterPmhField patientEncounterPmhField);

    ServiceResponse<IPatientPrescription> createPatientPrescription(IPatientPrescription patientPrescription);

    boolean hasPatientBeenCheckedIn(int encounterId);
    public ServiceResponse<DateTime> getDateOfCheckIn(int encounterId);
}
