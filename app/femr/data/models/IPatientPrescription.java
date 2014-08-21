package femr.data.models;

import org.joda.time.DateTime;

public interface IPatientPrescription {

    int getId();


    IUser getPhysician();

    void setPhysician(IUser physician);

    int getAmount();

    void setAmount(int amount);

    Integer getReplacementId();

    void setReplacementId(Integer replacementId);

    String getMedicationName();

    void setMedicationName(String medicationName);

    DateTime getDateTaken();

    void setDateTaken(DateTime dateTaken);

    IPatientEncounter getPatientEncounter();

    void setPatientEncounter(IPatientEncounter patientEncounter);
}
