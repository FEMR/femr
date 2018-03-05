package femr.ui.models.admin.inventory;

import java.util.List;

//\A
public class ExistingViewModelPost {

    //this is a list of IDs that come out of the select2 textbox for adding
    //existing medicine.
    private List<Integer> newConceptMedicationsForInventory;

    public List<Integer> getNewConceptMedicationsForInventory() {
        System.out.println("In ExistingModelViewPost getNewConceptMedications:");
        System.out.println("medsToGet: ");
//        for(Integer i: newConceptMedicationsForInventory){
//            System.out.print(i);
//        }
//        System.out.println();
        return newConceptMedicationsForInventory;
    }

    public void setNewConceptMedicationsForInventory(List<Integer> newConceptMedicationsForInventory) {
        System.out.println("In ExistingModelViewPost setNewConceptMedicationsForInventory:");
        System.out.println("medsToSet: ");
        for(Integer i: newConceptMedicationsForInventory){
            System.out.print(i);
        }
        System.out.println();
        this.newConceptMedicationsForInventory = newConceptMedicationsForInventory;
    }
}
