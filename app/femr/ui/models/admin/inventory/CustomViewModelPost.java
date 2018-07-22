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
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

@Constraints.Validate
public class CustomViewModelPost implements Constraints.Validatable<List<ValidationError>> {

    private Integer medicationQuantity;
    private String medicationForm;
    private String medicationName;
    private List<Double> medicationStrength;
    private List<String> medicationUnit;
    private List<String> medicationIngredient;

    @Override
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        //if nothing is entered for quantity, default to 0
        if (medicationQuantity == null) {

            medicationQuantity = 0;
            //errors.add(new ValidationError("medicationQuantity", "quantity is a required field"));
        }
        // Based on fEMR-95 in JIRA.  medicationForm is used to be able to add medication to inventory.
        if (StringUtils.isNullOrWhiteSpace(medicationName))
            errors.add(new ValidationError("medicationName", "name is a required field"));
        if (StringUtils.isNullOrWhiteSpace(medicationForm))
            errors.add(new ValidationError("medicationForm", "a form is required"));

        // Based on the error from JIRA fEMR-278, generic name is required
        for (int i = 0; i < medicationIngredient.size(); i++) {
            if (StringUtils.isNullOrWhiteSpace(medicationIngredient.get(i)) || medicationStrength.get(i) == null) {
                errors.add(new ValidationError("medicationGeneric", "a generic name is required"));
            }
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


    public List<Double> getMedicationStrength() {
        return medicationStrength;
    }

    public void setMedicationStrength(List<Double> medicationStrength) {
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
