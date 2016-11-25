package femr.business.services.core;

public class InputPrescriptionForNewMedication {
    private final String medicationName;
    private Integer administrationId;
    private final int encounterId;
    private final int userId;
    private final int amount;
    private final String specialInstructions;

    /**
     * @param medicationName name of the medication being prescribed, not null
     * @param administrationId how the medication is administered (BID, etc), may be null.
     * @param encounterId id of the patient encounter, not null.
     * @param userId id of the user dispensing the medication, not null.
     * @param amount how much of the medication is dispensed, not null.
     * @param specialInstructions any special instructions for the prescription, may be null.
     */
    public InputPrescriptionForNewMedication(String medicationName, Integer administrationId, int encounterId, int userId, int amount, String specialInstructions) {
        this.medicationName = medicationName;
        this.administrationId = administrationId;
        this.encounterId = encounterId;
        this.userId = userId;
        this.amount = amount;
        this.specialInstructions = specialInstructions;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public Integer getAdministrationId() {
        return administrationId;
    }

    public int getEncounterId() {
        return encounterId;
    }

    public int getUserId() {
        return userId;
    }

    public int getAmount() {
        return amount;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setAdministrationId(Integer administrationId) {
        this.administrationId = administrationId;
    }
}
