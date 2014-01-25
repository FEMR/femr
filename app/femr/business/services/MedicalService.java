package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatientEncounterHpiField;
import femr.common.models.IPatientEncounterPmhField;
import femr.common.models.IPatientEncounterTreatmentField;
import femr.common.models.IPatientPrescription;
import femr.data.daos.IRepository;
import femr.data.models.PatientEncounterHpiField;
import femr.data.models.PatientEncounterPmhField;
import femr.data.models.PatientEncounterTreatmentField;
import femr.data.models.PatientPrescription;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class MedicalService implements IMedicalService {

    private IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository;
    private IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository;
    private IRepository<IPatientEncounterPmhField> patientEncounterPmhFieldRepository;
    private IRepository<IPatientPrescription> patientPrescriptionRepository;

    @Inject
    public MedicalService(IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository,
                          IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository,
                          IRepository<IPatientEncounterPmhField> patientEncounterPmhFieldRepository,
                          IRepository<IPatientPrescription> patientPrescriptionRepository) {
        this.patientEncounterTreatmentFieldRepository = patientEncounterTreatmentFieldRepository;
        this.patientEncounterHpiFieldRepository = patientEncounterHpiFieldRepository;
        this.patientEncounterPmhFieldRepository = patientEncounterPmhFieldRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
    }

    @Override
    public ServiceResponse<List<? extends IPatientEncounterTreatmentField>> createPatientEncounterTreatmentFields(List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields){
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
    public ServiceResponse<IPatientPrescription> createPatientPrescription(IPatientPrescription patientPrescription){
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
    public ServiceResponse<List<? extends IPatientEncounterPmhField>> createPatientEncounterPmhFields(List<? extends IPatientEncounterPmhField> patientEncounterPmhFields){
        List<? extends IPatientEncounterPmhField> newPatientEncounterPmhFields = patientEncounterPmhFieldRepository.createAll(patientEncounterPmhFields);
        ServiceResponse<List<? extends IPatientEncounterPmhField>> response = new ServiceResponse<>();

        if (newPatientEncounterPmhFields != null) {
            response.setResponseObject(newPatientEncounterPmhFields);
        } else {
            response.addError("patientEncounterPmhField", "Failed to save");
        }
        return response;
    }

    @Override
    public boolean hasPatientBeenCheckedIn(int encounterId) {
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
        }else if (patientEncounterPmhFields.size() > 0) {
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
}
