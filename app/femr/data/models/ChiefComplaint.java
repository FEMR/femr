package femr.data.models;

import javax.persistence.*;

@Entity
@Table(name = "chief_complaints")
public class ChiefComplaint implements IChiefComplaint{

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_encounter_id")
    private PatientEncounter patientEncounter;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
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
