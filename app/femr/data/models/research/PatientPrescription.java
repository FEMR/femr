package femr.data.models.research;

import femr.common.models.research.IPatientPrescription;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Implements the patient prescription for research
 */
@Entity
@Table(name = "patient_prescriptions")
public class PatientPrescription implements IPatientPrescription {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "encounter_id", nullable = false)
    private int encounterId;
    @Column(name = "medication_name", nullable = false)
    private String medicationName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(int encounterId) {
        this.encounterId = encounterId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
}
