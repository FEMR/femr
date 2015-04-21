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

public class PrescriptionItem {
    private int id;
    private String name;
    private Integer replacementId;
    private String prescriberFirstName;
    private String prescriberLastName;
    private Integer administrationId;
    private String administrationName;
    private Float administrationModifier;
    private Integer amount;
    private Integer medicationID;
    private String medicationForm;
    private Integer medicationRemaining;

    public PrescriptionItem(String name){
        this.name = name;
    }

    public PrescriptionItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getReplacementId() {
        return replacementId;
    }

    public void setReplacementId(Integer replacementId) {
        this.replacementId = replacementId;
    }

    public String getPrescriberFirstName() {
        return prescriberFirstName;
    }

    public void setPrescriberFirstName(String prescriberFirstName) {
        this.prescriberFirstName = prescriberFirstName;
    }

    public String getPrescriberLastName() {
        return prescriberLastName;
    }

    public void setPrescriberLastName(String prescriberLastName) {
        this.prescriberLastName = prescriberLastName;
    }

    public Integer getAdministrationId() {
        return administrationId;
    }

    public void setAdministrationId(Integer administrationId) {
        this.administrationId = administrationId;
    }

    public String getAdministrationName() {
        return administrationName;
    }

    public void setAdministrationName(String administrationName) {
        this.administrationName = administrationName;
    }

    public Float getAdministrationModifier() {
        return administrationModifier;
    }

    public void setAdministrationModifier(Float administrationModifier) {
        this.administrationModifier = administrationModifier;
    }

    public Integer getAmount() {
        if (amount == null) return 0;
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(Integer medicationID) {
        this.medicationID = medicationID;
    }

    public String getMedicationForm() {
        return medicationForm;
    }

    public void setMedicationForm(String medicationForm) {
        this.medicationForm = medicationForm;
    }

    public Integer getMedicationRemaining() {
        if (medicationRemaining == null) return 0;
        return medicationRemaining;
    }

    public void setMedicationRemaining(Integer medicationRemaining) {
        this.medicationRemaining = medicationRemaining;
    }
}
