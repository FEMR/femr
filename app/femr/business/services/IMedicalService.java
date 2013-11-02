package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatientEncounterTreatmentField;

public interface IMedicalService {
    ServiceResponse<IPatientEncounterTreatmentField> createPatientEncounterTreatmentField(IPatientEncounterTreatmentField patientEncounterTreatmentField);

    String getCurrentDateTime();
}
