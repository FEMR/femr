package femr.business.services;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.Patient;
import femr.data.models.PatientPrescription;

public class PharmacyService implements IPharmacyService {
    private IRepository<IPatientPrescription> patientPrescriptionRepository;

    @Inject
    public PharmacyService(IRepository<IPatientPrescription> patientPrescriptionRepository){
        this.patientPrescriptionRepository = patientPrescriptionRepository;

    }

    @Override
    public ServiceResponse<IPatientPrescription> findPatientPrescriptionByEncounterIdAndPrescriptionName(int id, String name){

        ExpressionList<PatientPrescription> query = getPatientPrescriptionQuery().where().eq("encounter_id",id).eq("medication_name",name);
        IPatientPrescription patientPrescription = patientPrescriptionRepository.findOne(query);
        ServiceResponse<IPatientPrescription> response = new ServiceResponse<>();
        if (patientPrescription == null){
            response.addError("patientPrescription","could not find a prescription");
        }
        else{
            response.setResponseObject(patientPrescription);
        }
        return response;
    }


    private Query<PatientPrescription> getPatientPrescriptionQuery() {
        return Ebean.find(PatientPrescription.class);
    }
}

