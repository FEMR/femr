package femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterTreatmentField;
import femr.common.models.IPatientPrescription;

import java.util.List;

public interface IPharmacyService {

    ServiceResponse<IPatientPrescription> findPatientPrescription(int id, String name);

    ServiceResponse<IPatientPrescription> updatePatientPrescription(IPatientPrescription patientPrescription);
}
