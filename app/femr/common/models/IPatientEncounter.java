package femr.common.models;

import org.joda.time.DateTime;

public interface IPatientEncounter {
    int getId();

    int getPatientId();

    void setPatientId(int patientId);

    int getUserId();

    void setUserId(int userId);

    String getChiefComplaint();

    void setChiefComplaint(String chiefComplaint);

    Integer getWeeksPregnant();

    void setWeeksPregnant(Integer weeksPregnant);

    DateTime getDateOfVisit();

    void setDateOfVisit(DateTime dateOfVisit);

    DateTime getDateOfMedicalVisit();

    void setDateOfMedicalVisit(DateTime dateOfMedicalVisit);

    DateTime getDateOfPharmacyVisit();

    void setDateOfPharmacyVisit(DateTime dateOfPharmacyVisit);

    IUser getDoctor();

    void setDoctor(IUser doctor);

    IUser getPharmacist();

    void setPharmacist(IUser pharmacist);
}
