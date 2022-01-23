package femr.common.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

//One ShoppingListExportItem per line in the shopping list CSV export
public class ShoppingListExportItem {

    @SerializedName("Medication")       private String name;
    @SerializedName("Quantity")         private Integer quantity;

    static public List<String> getFieldOrder() {
        return Arrays.asList("Medication", "Quantity");
    }

    public ShoppingListExportItem(MedicationItem med, int q) {
        name = med.getFullName();
        quantity = q;
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

