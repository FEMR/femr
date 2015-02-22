package femr.data.models.core.research;

import femr.data.models.core.*;
import femr.data.models.mysql.PatientPrescription;
import femr.data.models.mysql.research.ResearchEncounterVital;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

public interface IResearchEncounter {

    int getId();

    IPatient getPatient();

    void setPatient(IPatient patient);

    List<IChiefComplaint> getChiefComplaints();

    void setChiefComplaints(List<IChiefComplaint> chiefComplaints);

    public Map<Integer, ResearchEncounterVital> getEncounterVitals();
    public void setEncounterVitals(Map<Integer, ResearchEncounterVital> encounterVitals);

    public List<PatientPrescription> getPatientPrescriptions();
    public void setPatientPrescriptions(List<PatientPrescription> patientPrescriptions);

    Integer getWeeksPregnant();

    void setWeeksPregnant(Integer weeksPregnant);

    DateTime getDateOfTriageVisit();

    void setDateOfTriageVisit(DateTime dateOfVisit);

    DateTime getDateOfMedicalVisit();

    void setDateOfMedicalVisit(DateTime dateOfMedicalVisit);

    DateTime getDateOfPharmacyVisit();

    void setDateOfPharmacyVisit(DateTime dateOfPharmacyVisit);

    IUser getDoctor();

    void setDoctor(IUser doctor);

    IUser getPharmacist();

    void setPharmacist(IUser pharmacist);

    IUser getNurse();

    void setNurse(IUser nurse);

    IPatientAgeClassification getPatientAgeClassification();

    void setPatientAgeClassification(IPatientAgeClassification patientAgeClassification);

    IMissionTrip getMissionTrip();

    void setMissionTrip(IMissionTrip missionTrip);

}
