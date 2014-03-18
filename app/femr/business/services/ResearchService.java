package femr.business.services;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatient;
import femr.common.models.IPatientEncounter;
import femr.common.models.IPatientEncounterVital;
import femr.common.models.IMedication;
import femr.common.models.IPatientEncounterTreatmentField;
import femr.common.models.IPatientPrescription;
import femr.common.models.IVital;
import femr.common.models.IPatientEncounterHpiField;
import femr.common.models.IPatientEncounterPmhField;
import femr.common.models.ITreatmentField;
import femr.common.models.IPmhField;
import femr.common.models.IHpiField;
import femr.data.daos.IRepository;
import femr.data.models.Patient;

import java.util.List;


/**
 * This implements the ResearchService interface through querying the database
 * and returning the ServiceResponse
 */
public class ResearchService implements IResearchService{
    private IRepository<IMedication> medicationRepository;
    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounter> patientEncounterRepository;
    private IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository;
    private IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private IRepository<IPatientPrescription> patientPrescriptionRepository;
    private IRepository<IVital> vitalRepository;
    private IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository;
    private IRepository<IPatientEncounterPmhField> patientEncounterPmhFieldRepository;
    private IRepository<ITreatmentField> treatmentFieldRepository;
    private IRepository<IPmhField> pmhFieldRepository;
    private IRepository<IHpiField> hpiFieldRepository;

    @Inject
    public ResearchService(IRepository<IMedication> medicationRepository,
             IRepository<IPatient> patientRepository,
             IRepository<IPatientEncounter> patientEncounterRepository,
             IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository,
             IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
             IRepository<IPatientPrescription> patientPrescriptionRepository,
             IRepository<IVital> vitalRepository,
             IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository,
             IRepository<IPatientEncounterPmhField> patientEncounterPmhFieldRepository,
             IRepository<ITreatmentField> treatmentFieldRepository,
             IRepository<IPmhField> pmhFieldRepository,
             IRepository<IHpiField> hpiFieldRepository) {

        this.medicationRepository = medicationRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTreatmentFieldRepository = patientEncounterTreatmentFieldRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.vitalRepository = vitalRepository;
        this.patientEncounterHpiFieldRepository = patientEncounterHpiFieldRepository;
        this.patientEncounterPmhFieldRepository = patientEncounterPmhFieldRepository;
        this.treatmentFieldRepository = treatmentFieldRepository;
        this.pmhFieldRepository = pmhFieldRepository;
        this.hpiFieldRepository = hpiFieldRepository;

    }


    /**
     * Gets all the basic patient information like name, age, city ...
     * for all the patients in the database and returns it
     * @return A list of the patient info to analyzed
     */
    @Override
    public ServiceResponse<List<? extends IPatient>> findAllPatient() {
        List<? extends IPatient> patients = patientRepository.findAll(Patient.class);
        ServiceResponse<List<? extends IPatient>> response = new ServiceResponse<>();
        if(patients.size() > 0) {
            response.setResponseObject(patients);
        } else {
            response.addError("patients", "No patients available");
        }
        return response;
    }

    // TODO-RESEARCH: Implement the services
}
