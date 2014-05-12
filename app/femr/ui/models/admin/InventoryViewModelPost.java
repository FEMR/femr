package femr.ui.models.admin;

import femr.business.dtos.MedicationItem;
import play.data.DynamicForm;

import java.util.ArrayList;
import java.util.List;

/**
 * This model is not "dumb" like the others. The constructor contains
 * the logic for dynamic form binding.
 */
public class InventoryViewModelPost {
    private List<MedicationItem> medications;

    public InventoryViewModelPost(DynamicForm requestData){
        int size = requestData.data().size() / 2;
        //make sure user has provided a medication and an amount

        medications = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            MedicationItem medicationItem = new MedicationItem();
            medicationItem.setName(requestData.get("name" + index));
            //check to make sure parsing went as planned
            Integer amount = Integer.parseInt(requestData.get("quantity" + index));
            if (amount == null) {
                continue;
            } else {
                medicationItem.setQuantity_total(amount);
            }

            medications.add(medicationItem);
        }
    }

    public List<MedicationItem> getMedications() {
        return medications;
    }

    public void setMedications(List<MedicationItem> medications) {
        this.medications = medications;
    }
}
