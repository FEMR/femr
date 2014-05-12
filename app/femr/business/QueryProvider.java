package femr.business;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import femr.data.models.*;

/**
 * Responsible for providing all queries
 */
public class QueryProvider {

    public static Query<Medication> getMedicationQuery() {
        return Ebean.find(Medication.class);
    }

    public static Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }

    public static Query<PatientEncounter> getPatientEncounterQuery() {
        return Ebean.find(PatientEncounter.class);
    }

    public static Query<PatientEncounterPhoto> getPatientEncounterPhotoQuery() {
        return Ebean.find(PatientEncounterPhoto.class);
    }

    public static Query<PatientEncounterTabField> getPatientEncounterTabFieldQuery() {
        return Ebean.find(PatientEncounterTabField.class);
    }

    public static Query<PatientEncounterVital> getPatientEncounterVitalQuery() {
        return Ebean.find(PatientEncounterVital.class);
    }

    public static Query<PatientPrescription> getPatientPrescriptionQuery() {
        return Ebean.find(PatientPrescription.class);
    }

    public static Query<PatientResearch> getPatientResearchQuery() {
        return Ebean.find(PatientResearch.class);
    }

    public static Query<Photo> getPhotoQuery() {
        return Ebean.find(Photo.class);
    }

    public static Query<Tab> getTabQuery() {
        return Ebean.find(Tab.class);
    }

    public static Query<TabField> getTabFieldQuery() {
        return Ebean.find(TabField.class);
    }

    public static Query<TabFieldType> getTabFieldTypeQuery() {
        return Ebean.find(TabFieldType.class);
    }

    public static Query<TabFieldSize> getTabFieldSizeQuery() {
        return Ebean.find(TabFieldSize.class);
    }

    public static Query<Role> getRoleQuery() {
        return Ebean.find(Role.class);
    }

    public static Query<User> getUserQuery() {
        return Ebean.find(User.class);
    }

    public static Query<Vital> getVitalQuery() {
        return Ebean.find(Vital.class);
    }
}
