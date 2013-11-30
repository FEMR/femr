package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.*;

import java.util.*;

public class SearchService implements ISearchService {
    private IRepository<IMedication> medicationRepository;
    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounter> patientEncounterRepository;
    private IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository;
    private IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private IRepository<IPatientPrescription> patientPrescriptionRepository;
    private IRepository<IVital> vitalRepository;
    private IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository;

    @Inject
    public SearchService(IRepository<IPatient> patientRepository,
                         IRepository<IPatientEncounter> patientEncounterRepository,
                         IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
                         IRepository<IVital> vitalRepository,
                         IRepository<IPatientPrescription> patientPrescriptionRepository,
                         IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository,
                         IRepository<IMedication> medicationRepository,
                         IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository) {
        this.medicationRepository = medicationRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTreatmentFieldRepository = patientEncounterTreatmentFieldRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.vitalRepository = vitalRepository;
        this.patientEncounterHpiFieldRepository = patientEncounterHpiFieldRepository;
    }

    @Override
    public ServiceResponse<IPatient> findPatientById(int id) {
        ExpressionList<Patient> query = getPatientQuery().where().eq("id", id);
        IPatient savedPatient = patientRepository.findOne(query);

        ServiceResponse<IPatient> response = new ServiceResponse<>();
        if (savedPatient == null) {
            response.addError("id", "id does not exist");
        } else {
            response.setResponseObject(savedPatient);
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatientEncounter> findPatientEncounterById(int id) {
        ExpressionList<PatientEncounter> query = getPatientEncounterQuery().where().eq("id", id);
        IPatientEncounter patientEncounter = patientEncounterRepository.findOne(query);

        ServiceResponse<IPatientEncounter> response = new ServiceResponse<>();
        if (patientEncounter == null) {
            response.addError("id", "id does not exist");
        } else {
            response.setResponseObject(patientEncounter);
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatientEncounter> findCurrentEncounterByPatientId(int id) {
        ExpressionList<PatientEncounter> query =
                getPatientEncounterQuery().where().eq("patient_id", id);
        List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.find(query);

        ServiceResponse<IPatientEncounter> response = new ServiceResponse<>();
        if (patientEncounters.size() < 1) {
            response.addError("id", "No encounters exist for that id");
        } else {
            int size = patientEncounters.size();
            response.setResponseObject(patientEncounters.get(size - 1));
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatient>> findPatientByName(String firstName, String lastName) {
        ExpressionList<Patient> query;
        if (!firstName.isEmpty() && lastName.isEmpty()) {
            query = getPatientQuery().where().eq("first_name", firstName);
        } else if (firstName.isEmpty() && !lastName.isEmpty()) {
            query = getPatientQuery().where().eq("last_name", lastName);
        } else {
            query = getPatientQuery().where().eq("first_name", firstName).eq("last_name", lastName);
        }

        List<? extends IPatient> savedPatients = patientRepository.find(query);
        ServiceResponse<List<? extends IPatient>> response = new ServiceResponse<>();
        if (savedPatients == null || savedPatients.size() == 0) {
            response.addError("first name/last name", "patient could not be found by name");
        } else {
            response.setResponseObject(savedPatients);
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatientEncounterVital> findPatientEncounterVitalByVitalIdAndEncounterId(int vitalId, int encounterId) {
        Query<PatientEncounterVital> query = getPatientEncounterVitalQuery().where().eq("vital_id", vitalId).eq("patient_encounter_id", encounterId).order().desc("date_taken").setMaxRows(1);
        List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query);
        IPatientEncounterVital patientEncounterVital = null;

        if (patientEncounterVitals.size() == 1) {
            patientEncounterVital = patientEncounterVitals.get(0);
        }

        ServiceResponse<IPatientEncounterVital> response = new ServiceResponse<>();
        if (patientEncounterVital == null) {
            response.addError("patientEncounterVital", "could not find vital");
        } else {
            response.setResponseObject(patientEncounterVital);
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatientEncounter>> findAllEncountersByPatientId(int id) {
        ExpressionList<PatientEncounter> query = getPatientEncounterQuery().where().eq("patient_id", id);
        List<? extends IPatientEncounter> patientEncounters = patientEncounterRepository.find(query);
        ServiceResponse<List<? extends IPatientEncounter>> response = new ServiceResponse<>();
        if (patientEncounters.size() > 0) {
            response.setResponseObject(patientEncounters);
        } else {
            response.addError("encounters", "could not find any encounters");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatientPrescription>> findPrescriptionsByEncounterId(int id) {
        ExpressionList<PatientPrescription> query = getPatientPrescriptionQuery().where().eq("encounter_id", id);
        List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query);
        ServiceResponse<List<? extends IPatientPrescription>> response = new ServiceResponse<>();
        if (patientPrescriptions.size() > 0) {
            response.setResponseObject(patientPrescriptions);
        } else {
            response.addError("prescriptions", "No prescriptions found");
        }
        return response;
    }

    @Override
    public ServiceResponse<Map<Integer,List<? extends IPatientEncounterTreatmentField>>> findTreatmentFieldsByEncounterId(int id) {
        Map<Integer,List<? extends IPatientEncounterTreatmentField>> mappedTreatmentFields = new LinkedHashMap<>();

        Query<PatientEncounterTreatmentField> query;
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields;

        for (int treatmentFieldId = 1; treatmentFieldId < 5; treatmentFieldId++){
            query = getPatientEncounterTreatmentFieldQuery().where().eq("patient_encounter_id", id).eq("treatment_field_id",treatmentFieldId).order().desc("date_taken");
            patientEncounterTreatmentFields = patientEncounterTreatmentFieldRepository.find(query);
            mappedTreatmentFields.put(treatmentFieldId,patientEncounterTreatmentFields);
        }

        ServiceResponse<Map<Integer,List<? extends IPatientEncounterTreatmentField>>> response = new ServiceResponse<>();
        if (mappedTreatmentFields.isEmpty()){
            response.addError("treatmentFields", "No treatment fields found");
        }
        else{
            response.setResponseObject(mappedTreatmentFields);
        }
        return response;




//        for (int treatmentFieldIndex = 0; treatmentFieldIndex < patientEncounterTreatmentFields.size(); treatmentFieldIndex++){
//            if (mappedTreatmentFields.get(patientEncounterTreatmentFields.get(treatmentFieldIndex).getId()) == null){
//                //no array list assigned yet, initalize it
//                //    mappedTreatmentFields.put(patientEncounterTreatmentFields.get(treatmentFieldIndex).getId(), new ArrayList<IPatientEncounterTreatmentField>());
//            }
//            //  mappedTreatmentFields.get(patientEncounterTreatmentFields.get(treatmentFieldIndex).getId()).add(patientEncounterTreatmentFields.get(treatmentFieldIndex));
//        }



//        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> response = new ServiceResponse<>();
//        if (patientEncounterTreatmentFields.size() > 0) {
//            response.setResponseObject(patientEncounterTreatmentFields);
//        } else {
//            response.addError("treatmentFields", "No treatment fields found");
//        }
//        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatientEncounterHpiField>> findHpiFieldsByEncounterId(int id) {
        ExpressionList<PatientEncounterHpiField> query = getPatientEncounterHpiFieldQuery().where().eq("patient_encounter_id", id);
        List<? extends IPatientEncounterHpiField> patientEncounterHpiFields = patientEncounterHpiFieldRepository.find(query);

        ServiceResponse<List<? extends IPatientEncounterHpiField>> response = new ServiceResponse<>();
        if (patientEncounterHpiFields.size() > 0) {
            response.setResponseObject(patientEncounterHpiFields);
        } else {
            response.addError("hpiFields", "could not find any hpi fields");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatientEncounterTreatmentField>> findProblemsByEncounterId(int id) {
        ExpressionList<PatientEncounterTreatmentField> query = getPatientEncounterTreatmentFieldQuery().where().eq("patient_encounter_id", id).eq("treatment_field_id", 2);
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields = patientEncounterTreatmentFieldRepository.find(query);
        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> response = new ServiceResponse<>();
        if (patientEncounterTreatmentFields.size() > 0) {
            response.setResponseObject(patientEncounterTreatmentFields);
        } else {
            response.addError("problems", "could not find any problems");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IVital>> findAllVitals() {
        List<? extends IVital> vitals = vitalRepository.findAll(Vital.class);
        ServiceResponse<List<? extends IVital>> response = new ServiceResponse<>();
        if (vitals.size() > 0) {
            response.setResponseObject(vitals);
        } else {
            response.addError("vitals", "no vitals available");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IMedication>> findAllMedications() {
        List<? extends IMedication> medications = medicationRepository.findAll(Medication.class);
        ServiceResponse<List<? extends IMedication>> response = new ServiceResponse<>();
        if (medications.size() > 0) {
            response.setResponseObject(medications);
        } else {
            response.addError("medications", "no medications available");
        }
        return response;
    }

    private Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }

    private Query<PatientEncounter> getPatientEncounterQuery() {
        return Ebean.find(PatientEncounter.class);
    }

    private Query<PatientEncounterVital> getPatientEncounterVitalQuery() {
        return Ebean.find(PatientEncounterVital.class);
    }

    private Query<PatientPrescription> getPatientPrescriptionQuery() {
        return Ebean.find(PatientPrescription.class);
    }

    private Query<PatientEncounterTreatmentField> getPatientEncounterTreatmentFieldQuery() {
        return Ebean.find(PatientEncounterTreatmentField.class);
    }

    private Query<PatientEncounterHpiField> getPatientEncounterHpiFieldQuery() {
        return Ebean.find(PatientEncounterHpiField.class);
    }
}
