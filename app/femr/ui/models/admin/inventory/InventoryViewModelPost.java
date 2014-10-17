/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.ui.models.admin.inventory;

import femr.common.models.MedicationItem;
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
