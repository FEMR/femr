package femr.data.models.mysql;

import femr.data.models.core.IPatientPrescription;
import femr.data.models.core.IPatientPrescriptionReplacement;
import femr.data.models.core.IPatientPrescriptionReplacementReason;

import javax.persistence.*;

@Entity
@Table(name = "patient_prescription_replacements")
public class PatientPrescriptionReplacement implements IPatientPrescriptionReplacement {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "patient_prescription_id_original", nullable = false)
    private PatientPrescription originalPrescription;
    @ManyToOne
    @JoinColumn(name = "patient_prescription_id_replacement", nullable = false)
    private PatientPrescription replacementPrescription;
    @ManyToOne
    @JoinColumn(name = "patient_prescription_replacement_reason_id")
    private PatientPrescriptionReplacementReason patientPrescriptionReplacementReason;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public IPatientPrescription getOriginalPrescription() {
        return originalPrescription;
    }

    @Override
    public void setOriginalPrescription(IPatientPrescription originalPrescription) {
        this.originalPrescription = (PatientPrescription) originalPrescription;
    }

    @Override
    public IPatientPrescription getReplacementPrescription() {
        return replacementPrescription;
    }

    @Override
    public void setReplacementPrescription(IPatientPrescription replacementPrescription) {
        this.replacementPrescription = (PatientPrescription) replacementPrescription;
    }

    @Override
    public IPatientPrescriptionReplacementReason getPatientPrescriptionReplacementReason() {
        return patientPrescriptionReplacementReason;
    }

    @Override
    public void setPatientPrescriptionReplacementReason(IPatientPrescriptionReplacementReason patientPrescriptionReplacementReason) {
        this.patientPrescriptionReplacementReason = (PatientPrescriptionReplacementReason) patientPrescriptionReplacementReason;
    }
}
