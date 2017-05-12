package femr.ui.models.admin.inventory;

import java.util.List;

public class ExistingViewModelPost {

    //this is a list of IDs that come out of the select2 textbox for adding
    //existing medicine.
    private List<Integer> newConceptMedicationsForInventory;

    public List<Integer> getNewConceptMedicationsForInventory() {
        return newConceptMedicationsForInventory;
    }

    public void setNewConceptMedicationsForInventory(List<Integer> newConceptMedicationsForInventory) {
        this.newConceptMedicationsForInventory = newConceptMedicationsForInventory;
    }
}
