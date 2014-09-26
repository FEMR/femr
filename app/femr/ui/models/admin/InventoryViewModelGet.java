package femr.ui.models.admin;

import femr.common.models.MedicationItem;

import java.util.List;

/**
 * Created by kevin on 5/12/14.
 */
public class InventoryViewModelGet {
    private List<MedicationItem> medications;

    public List<MedicationItem> getMedications() {
        return medications;
    }

    public void setMedications(List<MedicationItem> medications) {
        this.medications = medications;
    }
}
