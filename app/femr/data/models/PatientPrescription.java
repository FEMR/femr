package femr.data.models;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "patient_prescriptions")
public class PatientPrescription implements IPatientPrescription {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id", nullable = false)
    private PatientEncounter patientEncounter;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User physician;
    @Column(name = "amount", nullable = true)
    private  int amount;
    @Column(name = "replacement_id", nullable = true)
    private Integer replacementId;
    @Column(name = "medication_name", nullable = false)
    private String medicationName;
    @Column(name = "date_taken", nullable = false)
    private DateTime dateTaken;

    @Override
    public int getId() {
        return id;
    }



    @Override
    public IUser getPhysician() {
        return physician;
    }

    @Override
    public void setPhysician(IUser physician) {
        this.physician = (User) physician;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public Integer getReplacementId() {
        return replacementId;
    }

    @Override
    public void setReplacementId(Integer replacementId) {
        this.replacementId = replacementId;
    }

    @Override
    public String getMedicationName() {
        return medicationName;
    }

    @Override
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    @Override
    public DateTime getDateTaken() {
        return dateTaken;
    }

    @Override
    public void setDateTaken(DateTime dateTaken) {
        this.dateTaken = dateTaken;
    }


    @Override
    public IPatientEncounter getPatientEncounter() {
        return patientEncounter;
    }

    @Override
    public void setPatientEncounter(IPatientEncounter patientEncounter) {
        this.patientEncounter = (PatientEncounter) patientEncounter;
    }
}
