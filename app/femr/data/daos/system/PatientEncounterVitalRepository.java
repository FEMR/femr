package femr.data.daos.system;

import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IPatientEncounterVitalRepository;
import femr.data.models.core.IPatientEncounterVital;
import femr.data.models.mysql.PatientEncounterVital;
import io.ebean.ExpressionList;
import play.Logger;

import java.util.List;

public class PatientEncounterVitalRepository<T extends IPatientEncounterVital> implements IPatientEncounterVitalRepository {

    @Override
    public List<? extends IPatientEncounterVital> getAllByEncounter(int patientEncounterId) {
        try {
            ExpressionList<PatientEncounterVital> patient_encounter_vitals = QueryProvider.getPatientEncounterVitalQuery()
                    .where()
                    .eq("patient_encounter_id", patientEncounterId);

            return patient_encounter_vitals.findList();
        } catch(Exception ex){
            Logger.error("MedicationRepository-getAllByEncounter", ex.getMessage(), "patientEncounterId: " + patientEncounterId);
            throw ex;
        }
    }


}
