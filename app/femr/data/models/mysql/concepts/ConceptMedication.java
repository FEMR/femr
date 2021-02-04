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
package femr.data.models.mysql.concepts;

import femr.data.models.core.IConceptMedicationForm;
import femr.data.models.core.IMedication;
import femr.data.models.core.IMedicationGenericStrength;
import femr.data.models.mysql.MedicationInventory;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "concept_medications")
public class ConceptMedication implements IMedication {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = false, nullable = true)
    private String name;
    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "concept_medication_form_id")
    private ConceptMedicationForm conceptMedicationForm;
    @ManyToMany(fetch = FetchType.EAGER,
            targetEntity = ConceptMedicationGenericStrength.class,
            cascade = CascadeType.ALL)
    @JoinTable(name = "concept_medication_concept_generic_strengths",
            joinColumns = {@JoinColumn(name = "concept_medication_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "concept_medication_generic_strength_id", referencedColumnName = "id")})
    private List<IMedicationGenericStrength> conceptMedicationGenericStrengths;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public IConceptMedicationForm getConceptMedicationForm() {
        return conceptMedicationForm;
    }

    @Override
    public void setConceptMedicationForm(IConceptMedicationForm conceptMedicationForm) {
        this.conceptMedicationForm = (ConceptMedicationForm) conceptMedicationForm;
    }

    @Override
    public List<IMedicationGenericStrength> getMedicationGenericStrengths() {
        return conceptMedicationGenericStrengths;
    }

    @Override
    public void setMedicationGenericStrengths(List<IMedicationGenericStrength> conceptMedicationGenericStrengths) {
        this.conceptMedicationGenericStrengths = conceptMedicationGenericStrengths;
    }

    @Override
    public List<MedicationInventory> getMedicationInventory(){

        // Medication Concepts do not have an inventory, so emulate a Medication without inventory
        return null;
    }

    @Override
    public void setMedicationInventory(List<MedicationInventory> medicationInventory){

        // Medication Concepts do not have an inventory, so nothing to return
    }
}
