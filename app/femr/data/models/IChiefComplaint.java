package femr.data.models;

import java.util.Collection;

/**
 * Created by kevin on 7/20/14.
 */
public interface IChiefComplaint {
    int getId();

    String getValue();

    void setValue(String value);

    IPatientEncounter getPatientEncounter();

    void setPatientEncounter(IPatientEncounter patientEncounter);

//    Collection<PatientEncounterTabField> getPatientEncounterTabFields();
//
//    void setPatientEncounterTabFields(Collection<PatientEncounterTabField> patientEncounterTabFields);
}
