//package mock.femr.data.models;
//
//import femr.data.models.*;
//import org.joda.time.DateTime;
//import java.util.ArrayList;
//import java.util.List;
//import femr.data.models.core.IPatientEncounter;
//
//public class MockPatientEncounter implements IPatientEncounter {
//
//    private int id;
//    private Patient patient;
//    private User nurse;
//    private DateTime dateOfTriageVisit;
//    private List<ChiefComplaint> chiefComplaints;
//    private Integer weeksPregnant;
//    private DateTime dateOfMedicalVisit;
//    private DateTime dateOfPharmacyVisit;
//    private User doctor;
//    private User pharmacist;
//    private PatientAgeClassification patientAgeClassification;
//
//    @Override
//    public int getId() {
//        return id;
//    }
//
//    @Override
//    public IPatient getPatient() {
//        return patient;
//    }
//
//    @Override
//    public void setPatient(IPatient patient) {
//        this.patient = (Patient) patient;
//    }
//
//    @Override
//    public List<IChiefComplaint> getChiefComplaints() {
//        List<IChiefComplaint> temp = new ArrayList<>();
//        for (ChiefComplaint cc : chiefComplaints) {
//            temp.add(cc);
//        }
//        return temp;
//    }
//
//    @Override
//    public void setChiefComplaints(List<IChiefComplaint> chiefComplaints) {
//        for (IChiefComplaint cc : chiefComplaints) {
//            this.chiefComplaints.add((ChiefComplaint) cc);
//        }
//    }
//
//    @Override
//    public Integer getWeeksPregnant() {
//        return weeksPregnant;
//    }
//
//    @Override
//    public void setWeeksPregnant(Integer weeksPregnant) {
//        this.weeksPregnant = weeksPregnant;
//    }
//
//    @Override
//    public DateTime getDateOfTriageVisit() {
//        return dateOfTriageVisit;
//    }
//
//    @Override
//    public void setDateOfTriageVisit(DateTime dateOfTriageVisit) {
//        this.dateOfTriageVisit = dateOfTriageVisit;
//    }
//
//    @Override
//    public DateTime getDateOfMedicalVisit() {
//        return dateOfMedicalVisit;
//    }
//
//    @Override
//    public void setDateOfMedicalVisit(DateTime dateOfMedicalVisit) {
//        this.dateOfMedicalVisit = dateOfMedicalVisit;
//    }
//
//    @Override
//    public DateTime getDateOfPharmacyVisit() {
//        return dateOfPharmacyVisit;
//    }
//
//    @Override
//    public void setDateOfPharmacyVisit(DateTime dateOfPharmacyVisit) {
//        this.dateOfPharmacyVisit = dateOfPharmacyVisit;
//    }
//
//    @Override
//    public IUser getDoctor() {
//        return doctor;
//    }
//
//    @Override
//    public void setDoctor(IUser doctor) {
//        this.doctor = (User) doctor;
//    }
//
//    @Override
//    public IUser getPharmacist() {
//        return pharmacist;
//    }
//
//    @Override
//    public void setPharmacist(IUser pharmacist) {
//        this.pharmacist = (User) pharmacist;
//    }
//
//    @Override
//    public IUser getNurse() {
//        return nurse;
//    }
//
//    @Override
//    public void setNurse(IUser nurse) {
//        this.nurse = (User) nurse;
//    }
//
//    @Override
//    public IPatientAgeClassification getPatientAgeClassification() {
//        return patientAgeClassification;
//    }
//
//    @Override
//    public void setPatientAgeClassification(IPatientAgeClassification patientAgeClassification) {
//        this.patientAgeClassification =  (PatientAgeClassification) patientAgeClassification;
//    }
//
//}
//
