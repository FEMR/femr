package femr.data.daos.system;

import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IPatientEncounterTabFieldRepository;
import femr.data.models.core.IPatientEncounterTabField;
import femr.data.models.mysql.PatientEncounterTabField;
import io.ebean.ExpressionList;
import play.Logger;

import java.util.List;

public class PatientEncounterTabFieldsRepository implements IPatientEncounterTabFieldRepository {

    @Override
    public List<? extends IPatientEncounterTabField> getAllByEncounter(int patientEncounterId) {
        try {
            ExpressionList<PatientEncounterTabField> patient_encounter_tab_fields = QueryProvider.getPatientEncounterTabFieldQuery()
                    .where()
                    .eq("patient_encounter_id", patientEncounterId);

            return patient_encounter_tab_fields.findList();
        } catch(Exception ex){
            Logger.error("PatientEncounterTabFieldsRepository", ex.getMessage(), "patientEncounterId: " + patientEncounterId);
            throw ex;
        }
    }


}
