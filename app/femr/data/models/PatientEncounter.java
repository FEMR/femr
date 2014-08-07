package femr.data.models;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient_encounters")
public class PatientEncounter implements IPatientEncounter {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false, referencedColumnName = "id")
    private Patient patient;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "date_of_visit", nullable = false)
    private DateTime dateOfVisit;
    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "patientEncounter")
    private List<ChiefComplaint> chiefComplaints;
    @Column(name = "weeks_pregnant", nullable = true)
    private Integer weeksPregnant;
    @Column(name = "date_of_medical_visit", nullable = true)
    private DateTime dateOfMedicalVisit;
    @Column(name = "date_of_pharmacy_visit", nullable = true)
    private DateTime dateOfPharmacyVisit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id_medical", nullable = true)
    private User doctor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id_pharmacy", nullable = true)
    private User pharmacist;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public IPatient getPatient() {
        return patient;
    }

    @Override
    public void setPatient(IPatient patient) {
        this.patient = (Patient) patient;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }




    @Override
    public List<IChiefComplaint> getChiefComplaints() {
        List<IChiefComplaint> temp = new ArrayList<>();
        for (ChiefComplaint cc : chiefComplaints){
            temp.add(cc);
        }
        return temp;
    }

    @Override
    public void setChiefComplaints(List<IChiefComplaint> chiefComplaints) {
        for (IChiefComplaint cc: chiefComplaints){
            this.chiefComplaints.add((ChiefComplaint) cc);
        }

    }






    @Override
    public Integer getWeeksPregnant() {
        return weeksPregnant;
    }

    @Override
    public void setWeeksPregnant(Integer weeksPregnant) {
        this.weeksPregnant = weeksPregnant;
    }

    @Override
    public DateTime getDateOfVisit() {
        return dateOfVisit;
    }

    @Override
    public void setDateOfVisit(DateTime dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    @Override
    public DateTime getDateOfMedicalVisit() {
        return dateOfMedicalVisit;
    }

    @Override
    public void setDateOfMedicalVisit(DateTime dateOfMedicalVisit) {
        this.dateOfMedicalVisit = dateOfMedicalVisit;
    }

    @Override
    public DateTime getDateOfPharmacyVisit() {
        return dateOfPharmacyVisit;
    }

    @Override
    public void setDateOfPharmacyVisit(DateTime dateOfPharmacyVisit) {
        this.dateOfPharmacyVisit = dateOfPharmacyVisit;
    }

    @Override
    public IUser getDoctor() {
        return doctor;
    }

    @Override
    public void setDoctor(IUser doctor) {
        this.doctor = (User) doctor;
    }

    @Override
    public IUser getPharmacist() {
        return pharmacist;
    }

    @Override
    public void setPharmacist(IUser pharmacist) {
        this.pharmacist = (User) pharmacist;
    }
}
