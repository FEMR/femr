package femr.data.models.core;

/**
 * The reason for replacing a prescription.
 */
public interface IPatientPrescriptionReplacementReason {

    int getId();

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);
}
