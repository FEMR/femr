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
package femr.common.models;

import java.util.ArrayList;
import java.util.List;

public class MedicationItem {
    private int id;
    private Integer quantity_current;
    private Integer quantity_total;
    private String form;
    //name is only the name of the medication
    //ex: "Vicodin"
    private String name;
    //full name includes the active ingredients
    //ex: "Vicodin 10mg hydrocodone/325mg acetominophen"
    private String fullName;
    //active ingredients uses an inner class because
    //a medication can have more than one active ingredient.
    private List<ActiveIngredient> activeIngredients;


    public MedicationItem(){
        this.activeIngredients = new ArrayList<>();
    }

    /**
     * Add an active ingredient to the medication item. ActiveIngredient is an
     * inner class of MedicationItem
     *
     * @param name name of the active ingredient
     * @param unit unit of measurement for the active ingredient
     * @param value strength of the active ingredient
     * @param isDenominator is the active ingredient a denominator when displayed to the user?
     */
    public void addActiveIngredient(String name, String unit, int value, boolean isDenominator) {
        ActiveIngredient activeIngredient = new ActiveIngredient();
        activeIngredient.setDenominator(isDenominator);
        activeIngredient.setName(name);
        activeIngredient.setUnit(unit);
        activeIngredient.setValue(value);
        this.activeIngredients.add(activeIngredient);
    }

    public List<ActiveIngredient> getActiveIngredients() {
        return activeIngredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity_current() {
        return quantity_current;
    }

    public void setQuantity_current(Integer quantity_current) {
        this.quantity_current = quantity_current;
    }

    public Integer getQuantity_total() {
        return quantity_total;
    }

    public void setQuantity_total(Integer quantity_total) {
        this.quantity_total = quantity_total;
    }


    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public class ActiveIngredient{
        private String name;
        private String unit;
        private int value;
        private boolean isDenominator;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public boolean isDenominator() {
            return isDenominator;
        }

        public void setDenominator(boolean isDenominator) {
            this.isDenominator = isDenominator;
        }
    }
}
