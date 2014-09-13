package femr.business.helpers;

import com.avaje.ebean.Query;
import femr.common.models.VitalItem;
import femr.data.daos.IRepository;
import femr.data.models.IPatientEncounterVital;
import femr.data.models.PatientEncounterVital;

import java.util.List;

public class QueryHelper {

    public static Float findPatientWeight(IRepository<IPatientEncounterVital> patientEncounterVitalRepository, int encounterId){
        Float weight = null;
        Query<PatientEncounterVital> query2 = QueryProvider.getPatientEncounterVitalQuery()
                .fetch("vital")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("vital.name", "weight")
                .order().desc("date_taken");
        List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query2);
        if (patientEncounterVitals.size() > 0) {
            weight = patientEncounterVitals.get(0).getVitalValue();
        }
        return weight;
    }

    public static Integer findPatientHeightFeet(IRepository<IPatientEncounterVital> patientEncounterVitalRepository, int encounterId){
        Integer heightFeet = null;
        Query<PatientEncounterVital> query1 = QueryProvider.getPatientEncounterVitalQuery()
                .fetch("vital")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("vital.name", "heightFeet")
                .order().desc("date_taken");
        List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query1);
        if (patientEncounterVitals.size() > 0) {
            heightFeet = Math.round(patientEncounterVitals.get(0).getVitalValue());
        }
        return heightFeet;
    }

    public static Integer findPatientHeightInches(IRepository<IPatientEncounterVital> patientEncounterVitalRepository, int encounterId){
        Integer heightInches = null;
        Query<PatientEncounterVital> query1 = QueryProvider.getPatientEncounterVitalQuery()
                .fetch("vital")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("vital.name", "heightInches")
                .order().desc("date_taken");
        List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query1);
        if (patientEncounterVitals.size() > 0) {
            heightInches = Math.round(patientEncounterVitals.get(0).getVitalValue());
        }
        return heightInches;
    }
}
