package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.*;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MedicalService implements IMedicalService {

    private IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository;
    private IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository;
    private IRepository<IPatientEncounterPmhField> patientEncounterPmhFieldRepository;
    private IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private IRepository<IPatientPrescription> patientPrescriptionRepository;
    private IRepository<IHpiField> hpiFieldRepository;
    private IRepository<IPmhField> pmhFieldRepository;
    private IRepository<ITreatmentField> treatmentFieldRepository;
    private IRepository<IVital> vitalFieldRepository;


    @Inject
    public MedicalService(IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository,
                          IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository,
                          IRepository<IPatientEncounterPmhField> patientEncounterPmhFieldRepository,
                          IRepository<IPatientPrescription> patientPrescriptionRepository,
                          IRepository<IHpiField> hpiFieldRepository,
                          IRepository<IPmhField> pmhFieldRepository,
                          IRepository<ITreatmentField> treatmentFieldRepository,
                          IRepository<IVital> vitalFieldRepository,
                          IRepository<IPatientEncounterVital> patientEncounterVitalRepository) {
        this.patientEncounterTreatmentFieldRepository = patientEncounterTreatmentFieldRepository;
        this.patientEncounterHpiFieldRepository = patientEncounterHpiFieldRepository;
        this.patientEncounterPmhFieldRepository = patientEncounterPmhFieldRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.hpiFieldRepository = hpiFieldRepository;
        this.pmhFieldRepository = pmhFieldRepository;
        this.treatmentFieldRepository = treatmentFieldRepository;
        this.vitalFieldRepository = vitalFieldRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
    }

    @Override
    public ServiceResponse<List<? extends IPatientEncounterTreatmentField>> createPatientEncounterTreatmentFields(List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields) {
        List<? extends IPatientEncounterTreatmentField> newPatientEncounterTreatmentFields = patientEncounterTreatmentFieldRepository.createAll(patientEncounterTreatmentFields);
        ServiceResponse<List<? extends IPatientEncounterTreatmentField>> response = new ServiceResponse<>();

        if (newPatientEncounterTreatmentFields != null) {
            response.setResponseObject(newPatientEncounterTreatmentFields);
        } else {
            response.addError("patientEncounterTreatmentField", "Failed to save");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatientPrescription>> createPatientPrescriptions(List<? extends IPatientPrescription> patientPrescriptions) {
        List<? extends IPatientPrescription> newPatientPrescriptions = patientPrescriptionRepository.createAll(patientPrescriptions);
        ServiceResponse<List<? extends IPatientPrescription>> response = new ServiceResponse<>();

        if (newPatientPrescriptions != null) {
            response.setResponseObject(newPatientPrescriptions);
        } else {
            response.addError("patientPrescription", "failed to save");
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatientPrescription> createPatientPrescription(IPatientPrescription patientPrescription) {
        IPatientPrescription newPatientPrescription = patientPrescriptionRepository.create(patientPrescription);
        ServiceResponse<IPatientPrescription> response = new ServiceResponse<>();

        if (newPatientPrescription != null) {
            response.setResponseObject(newPatientPrescription);
        } else {
            response.addError("patientPrescription", "failed to save");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatientEncounterHpiField>> createPatientEncounterHpiFields(List<? extends IPatientEncounterHpiField> patientEncounterHpiFields) {
        List<? extends IPatientEncounterHpiField> newPatientEncounterHpiFields = patientEncounterHpiFieldRepository.createAll(patientEncounterHpiFields);
        ServiceResponse<List<? extends IPatientEncounterHpiField>> response = new ServiceResponse<>();

        if (newPatientEncounterHpiFields != null) {
            response.setResponseObject(newPatientEncounterHpiFields);
        } else {
            response.addError("patientEncounterHpiField", "Failed to save");
        }
        return response;
    }

    @Override
    public ServiceResponse<List<? extends IPatientEncounterPmhField>> createPatientEncounterPmhFields(List<? extends IPatientEncounterPmhField> patientEncounterPmhFields) {
        List<? extends IPatientEncounterPmhField> newPatientEncounterPmhFields = patientEncounterPmhFieldRepository.createAll(patientEncounterPmhFields);
        ServiceResponse<List<? extends IPatientEncounterPmhField>> response = new ServiceResponse<>();

        if (newPatientEncounterPmhFields != null) {
            response.setResponseObject(newPatientEncounterPmhFields);
        } else {
            response.addError("patientEncounterPmhField", "Failed to save");
        }
        return response;
    }

    /*
    Determines if a patient has been seen by a doctor by checking for filled out
    fields in Hpi, Treatment, Prescription, or Pmh.
     */
    @Override
    public boolean hasPatientBeenCheckedInByPhysician(int encounterId) {
        ExpressionList<PatientEncounterHpiField> query = getPatientEncounterHpiField().where().eq("patient_encounter_id", encounterId);
        List<? extends IPatientEncounterHpiField> patientEncounterHpiFields = patientEncounterHpiFieldRepository.find(query);

        ExpressionList<PatientEncounterTreatmentField> query2 = getPatientEncounterTreatmentField().where().eq("patient_encounter_id", encounterId);
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields = patientEncounterTreatmentFieldRepository.find(query2);

        ExpressionList<PatientPrescription> query3 = getPatientPrescription().where().eq("encounter_id", encounterId);
        List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query3);

        ExpressionList<PatientEncounterPmhField> query4 = getPatientEncounterPmhField().where().eq("patient_encounter_id", encounterId);
        List<? extends IPatientEncounterPmhField> patientEncounterPmhFields = patientEncounterPmhFieldRepository.find(query4);

        if (patientEncounterHpiFields.size() > 0) {
            return true;
        }
        if (patientEncounterPmhFields.size() > 0) {
            return true;
        }
        if (patientEncounterTreatmentFields.size() > 0) {
            return true;
        }
        if (patientPrescriptions.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public ServiceResponse<DateTime> getDateOfCheckIn(int encounterId) {
        ExpressionList<PatientEncounterHpiField> query = getPatientEncounterHpiField().where().eq("patient_encounter_id", encounterId);
        List<? extends IPatientEncounterHpiField> patientEncounterHpiFields = patientEncounterHpiFieldRepository.find(query);

        ExpressionList<PatientEncounterTreatmentField> query2 = getPatientEncounterTreatmentField().where().eq("patient_encounter_id", encounterId);
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields = patientEncounterTreatmentFieldRepository.find(query2);

        ExpressionList<PatientPrescription> query3 = getPatientPrescription().where().eq("encounter_id", encounterId);
        List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query3);

        ExpressionList<PatientEncounterPmhField> query4 = getPatientEncounterPmhField().where().eq("patient_encounter_id", encounterId);
        List<? extends IPatientEncounterPmhField> patientEncounterPmhFields = patientEncounterPmhFieldRepository.find(query4);

        ServiceResponse<DateTime> response = new ServiceResponse<>();

        if (patientEncounterHpiFields.size() > 0) {
            response.setResponseObject(patientEncounterHpiFields.get(0).getDateTaken());
        } else if (patientEncounterPmhFields.size() > 0) {
            response.setResponseObject(patientEncounterPmhFields.get(0).getDateTaken());
        } else if (patientEncounterTreatmentFields.size() > 0) {
            response.setResponseObject(patientEncounterTreatmentFields.get(0).getDateTaken());
        } else if (patientPrescriptions.size() > 0) {
            response.setResponseObject(patientPrescriptions.get(0).getDateTaken());
        } else {
            response.addError("values", "That patient has not yet been seen");
        }
        return response;
    }

    @Override
    public ServiceResponse<Map<String, List<? extends IPatientEncounterPmhField>>> findPmhFieldsByEncounterId(int encounterId) {
        ServiceResponse<Map<String, List<? extends IPatientEncounterPmhField>>> response = new ServiceResponse<>();
        Map<String, List<? extends IPatientEncounterPmhField>> pmhValueMap = new LinkedHashMap<>();
        Query<PatientEncounterPmhField> query;

        List<? extends IPmhField> pmhFields = pmhFieldRepository.findAll(PmhField.class);
        List<? extends IPatientEncounterPmhField> patientPmh;
        String pmhFieldName;
        for (int pmhFieldIndex = 0; pmhFieldIndex < pmhFields.size(); pmhFieldIndex++) {
            pmhFieldName = pmhFields.get(pmhFieldIndex).getName().trim();
            query = getPatientEncounterPmhFieldQuery().fetch("pmhField").where().eq("patient_encounter_id", encounterId).eq("pmhField.name", pmhFieldName).order().desc("date_taken");
            patientPmh = patientEncounterPmhFieldRepository.find(query);
            pmhValueMap.put(pmhFieldName, patientPmh);
        }
        response.setResponseObject(pmhValueMap);
        return response;
    }

    @Override
    public ServiceResponse<Map<String, List<? extends IPatientEncounterTreatmentField>>> findTreatmentFieldsByEncounterId(int encounterId) {
        ServiceResponse<Map<String, List<? extends IPatientEncounterTreatmentField>>> response = new ServiceResponse<>();
        Map<String, List<? extends IPatientEncounterTreatmentField>> treatmentFieldMap = new LinkedHashMap<>();
        Query<PatientEncounterTreatmentField> query;

        List<? extends ITreatmentField> treatmentFields = treatmentFieldRepository.findAll(TreatmentField.class);
        List<? extends IPatientEncounterTreatmentField> patientTreatment;
        String treatmentFieldName;
        //loop through each available treatment field and build the map
        for (int treatmentFieldIndex = 0; treatmentFieldIndex < treatmentFields.size(); treatmentFieldIndex++) {
            treatmentFieldName = treatmentFields.get(treatmentFieldIndex).getName().trim();

            query = getPatientEncounterTreatmentFieldQuery().fetch("treatmentField").where().eq("patient_encounter_id", encounterId).eq("treatmentField.name", treatmentFieldName).order().desc("date_taken");
            patientTreatment = patientEncounterTreatmentFieldRepository.find(query);
            treatmentFieldMap.put(treatmentFieldName, patientTreatment);
        }
        response.setResponseObject(treatmentFieldMap);
        return response;
    }

    @Override
    public ServiceResponse<Map<String, List<? extends IPatientEncounterHpiField>>> findHpiFieldsByEncounterId(int encounterId) {
        ServiceResponse<Map<String, List<? extends IPatientEncounterHpiField>>> response = new ServiceResponse<>();
        Query<PatientEncounterHpiField> query;
        Map<String, List<? extends IPatientEncounterHpiField>> hpiValueMap = new LinkedHashMap<>();

        List<? extends IHpiField> hpiFields = hpiFieldRepository.findAll(HpiField.class);
        List<? extends IPatientEncounterHpiField> patientHpi;
        String hpiFieldName;
        for (int hpiFieldIndex = 0; hpiFieldIndex < hpiFields.size(); hpiFieldIndex++) {
            hpiFieldName = hpiFields.get(hpiFieldIndex).getName().trim();
            query = getPatientEncounterHpiFieldQuery().fetch("hpiField").where().eq("patient_encounter_id", encounterId).eq("hpiField.name", hpiFieldName).order().desc("date_taken");
            patientHpi = patientEncounterHpiFieldRepository.find(query);
            hpiValueMap.put(hpiFieldName, patientHpi);
        }
        response.setResponseObject(hpiValueMap);
        return response;
    }

    @Override
    public ServiceResponse<Map<String, List<? extends IPatientEncounterVital>>> findVitalsByEncounterId(int encounterId) {
        ServiceResponse<Map<String, List<? extends IPatientEncounterVital>>> response = new ServiceResponse<>();
        List<? extends IVital> vitals = vitalFieldRepository.findAll(Vital.class);
        Query<PatientEncounterVital> query;

        Map<String, List<? extends IPatientEncounterVital>> vitalMap = new LinkedHashMap<>();
        List<? extends IPatientEncounterVital> patientEncounterVitals;
        String vitalFieldName;
        for (int vitalFieldIndex = 0; vitalFieldIndex < vitals.size(); vitalFieldIndex++) {
            vitalFieldName = vitals.get(vitalFieldIndex).getName().trim();
            query = getPatientEncounterVitalQuery().fetch("vital").where().eq("patient_encounter_id", encounterId).eq("vital.name", vitalFieldName).order().desc("date_taken");
            patientEncounterVitals = patientEncounterVitalRepository.find(query);
            vitalMap.put(vitalFieldName, patientEncounterVitals);
        }
        response.setResponseObject(vitalMap);
        return response;
    }

    private Query<PatientEncounterHpiField> getPatientEncounterHpiField() {
        return Ebean.find(PatientEncounterHpiField.class);
    }

    private Query<PatientEncounterPmhField> getPatientEncounterPmhField() {
        return Ebean.find(PatientEncounterPmhField.class);
    }

    private Query<PatientEncounterTreatmentField> getPatientEncounterTreatmentField() {
        return Ebean.find(PatientEncounterTreatmentField.class);
    }

    private Query<PatientPrescription> getPatientPrescription() {
        return Ebean.find(PatientPrescription.class);
    }

    private Query<PatientEncounterTreatmentField> getPatientEncounterTreatmentFieldQuery() {
        return Ebean.find(PatientEncounterTreatmentField.class);
    }

    private Query<PatientEncounterHpiField> getPatientEncounterHpiFieldQuery() {
        return Ebean.find(PatientEncounterHpiField.class);
    }

    private Query<PatientEncounterPmhField> getPatientEncounterPmhFieldQuery() {
        return Ebean.find(PatientEncounterPmhField.class);
    }

    private Query<PatientEncounterVital> getPatientEncounterVitalQuery() {
        return Ebean.find(PatientEncounterVital.class);
    }
}
