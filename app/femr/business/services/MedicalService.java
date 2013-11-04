package femr.business.services;

import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatientEncounterHpiField;
import femr.common.models.IPatientEncounterTreatmentField;
import femr.common.models.IPatientPrescription;
import femr.data.daos.IRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    public String getCurrentDateTime(){
        Date dt = new Date();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateTimeFormat.format(dt);
        return currentTime;
    }
}
