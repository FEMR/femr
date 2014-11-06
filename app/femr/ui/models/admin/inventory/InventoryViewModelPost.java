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

import femr.util.stringhelpers.StringUtils;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class InventoryViewModelPost {
    private Integer medicationQuantity;
    private String medicationForm;
    private String medicationName;
    private List<Integer> medicationStrength;
    private List<String> medicationUnit;
    private List<String> medicationIngredient;

    public List<ValidationError> validate(){
        List<ValidationError> errors = new ArrayList<>();
        if (medicationQuantity == null)
            errors.add(new ValidationError("medicationQuantity", "quantity is a required field"));
        if (StringUtils.isNullOrWhiteSpace(medicationName))
            errors.add(new ValidationError("medicationName", "name is a required field"));
        if (StringUtils.isNullOrWhiteSpace(medicationForm))
            errors.add(new ValidationError("medicationForm", "a form is required"));
        for (Integer i : medicationStrength){
            if (i == null)
                errors.add(new ValidationError("medicationStrength", "all strength fields are required"));
        }
        for (String ms : medicationUnit){
            if (StringUtils.isNullOrWhiteSpace(ms))
                errors.add(new ValidationError("medicationUnit", "all units are required"));
        }
        for (String mi : medicationIngredient){
            if (StringUtils.isNullOrWhiteSpace(mi))
                errors.add(new ValidationError("medicationIngredient", "all ingredients required"));
        }

        return errors.isEmpty() ? null : errors;
    }

    public Integer getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(Integer medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }

    public String getMedicationForm() {
        return medicationForm;
    }

    public void setMedicationForm(String medicationForm) {
        this.medicationForm = medicationForm;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }


    public List<Integer> getMedicationStrength() {
        return medicationStrength;
    }

    public void setMedicationStrength(List<Integer> medicationStrength) {
        this.medicationStrength = medicationStrength;
    }

    public List<String> getMedicationUnit() {
        return medicationUnit;
    }

    public void setMedicationUnit(List<String> medicationUnit) {
        this.medicationUnit = medicationUnit;
    }

    public List<String> getMedicationIngredient() {
        return medicationIngredient;
    }

    public void setMedicationIngredient(List<String> medicationIngredient) {
        this.medicationIngredient = medicationIngredient;
    }
}
