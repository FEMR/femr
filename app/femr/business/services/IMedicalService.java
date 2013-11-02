package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatientEncounterHpiField;
import femr.common.models.IPatientEncounterTreatmentField;

public interface IMedicalService {
    ServiceResponse<IPatientEncounterTreatmentField> createPatientEncounterTreatmentField(IPatientEncounterTreatmentField patientEncounterTreatmentField);

    ServiceResponse<IPatientEncounterHpiField> createPatientEncounterHpiField(IPatientEncounterHpiField patientEncounterHpiField);

    String getCurrentDateTime();
}
