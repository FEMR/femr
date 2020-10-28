package femr.ui.models.triage;

/**
 * Created by Jeff on 2016-04-21.
 */
public class DeleteViewModelPost {
    private String reasonDeleted;
    private String reasonEncounterDeleted;
    public void setReasonEncounterDeleted(String reason) { this.reasonEncounterDeleted = reason; }
    public String getReasonEncounterDeleted() { return reasonEncounterDeleted; }
    public void setReasonDeleted(String reason) { this.reasonDeleted = reason; }
    public String getReasonDeleted() { return reasonDeleted; }
}
