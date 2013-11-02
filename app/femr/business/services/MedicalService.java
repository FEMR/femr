package femr.business.services;

import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IPatientEncounterTreatmentField;
import femr.data.daos.IRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalService implements IMedicalService{

    private IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository;

    @Inject
    public MedicalService(IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository){
        this.patientEncounterTreatmentFieldRepository = patientEncounterTreatmentFieldRepository;

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
    public String getCurrentDateTime(){
        Date dt = new Date();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateTimeFormat.format(dt);
        return currentTime;
    }
}
