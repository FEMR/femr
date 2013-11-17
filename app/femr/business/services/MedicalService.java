package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatientEncounterHpiField;
import femr.common.models.IPatientEncounterTreatmentField;
import femr.common.models.IPatientPrescription;
import femr.data.daos.IRepository;
import femr.data.models.PatientEncounterHpiField;
import femr.data.models.PatientEncounterTreatmentField;
import femr.data.models.PatientPrescription;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MedicalService implements IMedicalService{

    private IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository;
    private IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository;
    private IRepository<IPatientPrescription> patientPrescriptionRepository;

    @Inject
    public MedicalService(IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository,
                          IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository,
                          IRepository<IPatientPrescription> patientPrescriptionRepository){
        this.patientEncounterTreatmentFieldRepository = patientEncounterTreatmentFieldRepository;
        this.patientEncounterHpiFieldRepository = patientEncounterHpiFieldRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
    }

    @Override
    public ServiceResponse<IPatientEncounterTreatmentField> createPatientEncounterTreatmentField(IPatientEncounterTreatmentField patientEncounterTreatmentField){
        IPatientEncounterTreatmentField newPatientEncounterTreatmentField =
                patientEncounterTreatmentFieldRepository.create(patientEncounterTreatmentField);
        ServiceResponse<IPatientEncounterTreatmentField> response = new ServiceResponse<>();

        if (newPatientEncounterTreatmentField != null){
            response.setResponseObject(newPatientEncounterTreatmentField);
        }
        else{
            response.addError("patientEncounterTreatmentField","Failed to save");
        }
        return response;
    }

    @Override
    public ServiceResponse<IPatientPrescription> createPatientPrescription(IPatientPrescription patientPrescription){

        IPatientPrescription newPatientPrescription = patientPrescriptionRepository.create(patientPrescription);
        ServiceResponse<IPatientPrescription> response = new ServiceResponse<>();

        if (newPatientPrescription != null){
            response.setResponseObject(newPatientPrescription);
        }
        else{
            response.addError("patientPrescription","failed to save");
        }

        return response;
    }

    @Override
    public ServiceResponse<IPatientEncounterHpiField> createPatientEncounterHpiField(IPatientEncounterHpiField patientEncounterHpiField) {
        IPatientEncounterHpiField newPatientEncounterHpiField =
                patientEncounterHpiFieldRepository.create(patientEncounterHpiField);
        ServiceResponse<IPatientEncounterHpiField> response = new ServiceResponse<>();

        if (newPatientEncounterHpiField != null){
            response.setResponseObject(newPatientEncounterHpiField);
        }
        else{
            response.addError("patientEncounterHpiField","Failed to save");
        }
        return response;
    }

    @Override
    public boolean hasPatientBeenCheckedIn(int encounterId){
        ExpressionList<PatientEncounterHpiField> query = getPatientEncounterHpiField().where().eq("patient_encounter_id",encounterId);
        List<? extends IPatientEncounterHpiField> patientEncounterHpiFields = patientEncounterHpiFieldRepository.find(query);

        ExpressionList<PatientEncounterTreatmentField> query2 = getPatientEncounterTreatmentField().where().eq("patient_encounter_id",encounterId);
        List<? extends IPatientEncounterTreatmentField> patientEncounterTreatmentFields = patientEncounterTreatmentFieldRepository.find(query2);

        ExpressionList<PatientPrescription> query3 = getPatientPrescription().where().eq("encounter_id",encounterId);
        List<? extends IPatientPrescription> patientPrescriptions = patientPrescriptionRepository.find(query3);

        if (patientEncounterHpiFields.size() > 0)
            return true;
        if (patientEncounterTreatmentFields.size() > 0)
            return true;
        if (patientPrescriptions.size() > 0)
            return true;
        return false;
    }

    private Query<PatientEncounterHpiField> getPatientEncounterHpiField(){
        return Ebean.find(PatientEncounterHpiField.class);
    }
    private Query<PatientEncounterTreatmentField> getPatientEncounterTreatmentField(){
        return Ebean.find(PatientEncounterTreatmentField.class);
    }
    private Query<PatientPrescription> getPatientPrescription(){
        return Ebean.find(PatientPrescription.class);
    }
}
