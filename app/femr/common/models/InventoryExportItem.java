/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2016  Team fEMR

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
package femr.common.models;

import java.util.*;
import com.google.gson.annotations.SerializedName;

//One InventoryExportItem per line in the research CSV export
public class InventoryExportItem {

    @SerializedName("ID")               private Integer medicationId;
    @SerializedName("Medication")       private String name;
    @SerializedName("Current Quantity") private Integer quantityCurrent;
    @SerializedName("Initial Quantity") private Integer quantityInitial;

    static public List<String> getFieldOrder() {
      return Arrays.asList("ID", "Medication", "Current Quantity", "Initial Quantity");
    }

    public InventoryExportItem(MedicationItem med) {
      medicationId = med.getId();
      name = med.getFullName();
      quantityCurrent = med.getQuantityCurrent();
      quantityInitial = med.getQuantityTotal();
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

    public Integer getQuantityCurrent() {
        return quantityCurrent;
    }

    public void setQuantityCurrent(Integer quantityCurrent) {
        this.quantityCurrent = quantityCurrent;
    }

    public Integer getQuantityInitial() {
        return quantityInitial;
    }

    public void setQuantityInitial(Integer quantityInitial) {
        this.quantityInitial = quantityInitial;
    }
}
