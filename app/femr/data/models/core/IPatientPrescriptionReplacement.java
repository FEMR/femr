package femr.data.models.core;

/**
 * Join table to track the replacement of prescriptions along with the reason for replacement.
 */
public interface IPatientPrescriptionReplacement {

    int getId();

    IPatientPrescription getOriginalPrescription();

    void setOriginalPrescription(IPatientPrescription originalPrescription);

    IPatientPrescription getReplacementPrescription();

    void setReplacementPrescription(IPatientPrescription replacementPrescription);

    IPatientPrescriptionReplacementReason getPatientPrescriptionReplacementReason();

    void setPatientPrescriptionReplacementReason(IPatientPrescriptionReplacementReason patientPrescriptionReplacementReason);
}
