package femr.data.models;

import femr.common.models.IPatientEncounterVital;
import femr.common.models.IVital;

import javax.persistence.*;

@Entity
@Table(name = "patient_encounter_vitals")
public class PatientEncounterVital implements IPatientEncounterVital {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "patient_encounter_id", nullable = false)
    private int patientEncounterId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vital_id", nullable = false)
    private Vital vital;//*eBean doesn't support mapping annotations on an interface...
    @Column(name = "vital_value", nullable = false)
    private float vitalValue;
    @Column(name = "date_taken", nullable = false)
    private String dateTaken;

    @Override
    public int getId() {
        return id;
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
    public int getPatientEncounterId() {
        return patientEncounterId;
    }

    @Override
    public void setPatientEncounterId(int patientEncounterId) {
        this.patientEncounterId = patientEncounterId;
    }

    @Override
    public IVital getVital() {
        return vital;
    }

    @Override
    public void setVital(IVital vital) {
        //cast IVital to Vital to get past the limitation of
        //the bean not being able to map annotations to an
        //interface
        Vital newVital = (Vital)vital;
        this.vital = newVital;

    }

    @Override
    public Float getVitalValue() {
        return vitalValue;
    }

    @Override
    public void setVitalValue(float vitalValue) {
        this.vitalValue = vitalValue;
    }

    @Override
    public String getDateTaken() {
        return dateTaken;
    }

    @Override
    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }
}
