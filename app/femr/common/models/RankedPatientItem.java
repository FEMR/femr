package femr.common.models;

public class RankedPatientItem {
    private PatientItem patientItem;
    private Integer rank;

    public RankedPatientItem() {
        this.patientItem = null;
        this.rank = 0;
    }

    public RankedPatientItem(PatientItem patientItem, Integer rank) {
        this.patientItem = patientItem;
        this.rank = rank;
    }

    public PatientItem getPatientItem() {
        return patientItem;
    }

    public Integer getRank() {
        return rank;
    }
}
