package femr.data.models;

import org.joda.time.DateTime;

import java.util.List;

public interface IPatientEncounter {
    int getId();

    IPatient getPatient();

    void setPatient(IPatient patient);

    int getUserId();

    void setUserId(int userId);

    List<IChiefComplaint> getChiefComplaints();

    void setChiefComplaints(List<IChiefComplaint> chiefComplaints);

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
