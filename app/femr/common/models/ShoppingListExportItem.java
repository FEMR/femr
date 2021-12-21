package femr.common.models;

import java.util.*;
import com.google.gson.annotations.SerializedName;

//One ShoppingListExportItem per line in the shopping list CSV export
public class ShoppingListExportItem {

    @SerializedName("ID")               private Integer medicationId;
    @SerializedName("Medication")       private String name;
    @SerializedName("Quantity")         private Integer quantity;

    static public List<String> getFieldOrder() {
        return Arrays.asList("ID", "Medication", "Quantity");
    }

    public ShoppingListExportItem(MedicationItem med, int q) {
        medicationId = med.getId();
        name = med.getFullName();
        quantity = q;
    }

    public Integer getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Integer medicationId) {
        this.medicationId = medicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

