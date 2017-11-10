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

public class PrescriptionItem {
    private int id;
    private int replacementId;
    private String name;
    private String originalMedicationName;
    private String replacementMedicationName;
    private String prescriberFirstName;
    private String prescriberLastName;
    private Integer administrationID;
    private String administrationName;
    private Float administrationModifier;
    private Integer amount;
    private Integer medicationID;
    //medicationName is used for prescriptions that don't have an ID
    private String medicationName;
    private String medicationForm;
    private List<MedicationItem.ActiveIngredient> medicationActiveDrugs;
    //was the checkbox checked for this prescription indicating the patient was counseled by the pharmacist
    private Boolean isCounseled;

    private MedicationItem medicationItem;
    private String formularyMessage;

    public PrescriptionItem(String name){
        medicationActiveDrugs = new ArrayList<MedicationItem.ActiveIngredient>();
        this.name = name;
    }

    public PrescriptionItem() {
        medicationActiveDrugs = new ArrayList<MedicationItem.ActiveIngredient>();
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

    public Integer getAdministrationID() {
        return administrationID;
    }

    public void setAdministrationID(Integer administrationID) {
        this.administrationID = administrationID;
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

    public Integer getAmountWithNull(){
        return amount;
    }

    public String printFullPrescriptionName() {
        String fullPrescriptionName = "";
        if (amount != null)
            fullPrescriptionName = fullPrescriptionName + amount + " - ";
        else
            fullPrescriptionName = fullPrescriptionName + "N/A - ";

        if (medicationItem != null) {
            fullPrescriptionName = fullPrescriptionName + " " + medicationItem.getFullName();
        }

        if (administrationName != null)
            fullPrescriptionName = fullPrescriptionName + " - " + getAdministrationName();
        else
            fullPrescriptionName = fullPrescriptionName + " - " + "N/A";

        return fullPrescriptionName;
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

    public List<MedicationItem.ActiveIngredient> getMedicationActiveDrugs() {
        return medicationActiveDrugs;
    }

    public void setMedicationActiveDrugs(List<MedicationItem.ActiveIngredient> medicationActiveDrugs) {
        this.medicationActiveDrugs = medicationActiveDrugs;
    }

    public String getOriginalMedicationName(){
        return originalMedicationName;
    }

    public void setOriginalMedicationName(String originalMedicationName){
        this.originalMedicationName = originalMedicationName;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Boolean getCounseled() {
        return isCounseled;
    }

    public void setCounseled(Boolean counseled) {
        isCounseled = counseled;
    }

    public String getReplacementMedicationName() {
        return replacementMedicationName;
    }

    public void setReplacementMedicationName(String replacementMedicationName) {
        this.replacementMedicationName = replacementMedicationName;
    }

    public int getReplacementId() {
        return replacementId;
    }

    public void setReplacementId(int replacementId) {
        this.replacementId = replacementId;
    }

    public MedicationItem getMedicationItem() {
        return medicationItem;
    }

    public void setMedicationItem(MedicationItem medicationItem) {
        this.medicationItem = medicationItem;
    }

    public String getFormularyMessage() {
        return formularyMessage;
    }

    public void setFormularyMessage(String formularyMessage) {
        this.formularyMessage = formularyMessage;
    }
}
