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

import femr.data.models.core.IConceptMedicationUnit;
import femr.data.models.core.IMedicationGeneric;
import femr.data.models.core.IMedicationGenericStrength;

import javax.persistence.*;

@Entity
@Table(name = "concept_medication_generic_strengths")
public class ConceptMedicationGenericStrength implements IMedicationGenericStrength {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "concept_medication_unit_id")
    private ConceptMedicationUnit conceptMedicationUnit;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "concept_medication_generic_id")
    private ConceptMedicationGeneric conceptMedicationGeneric;
    @Column(name = "isDenominator", nullable = false)
    private boolean isDenominator;
    @Column(name = "value", unique = true, nullable = false)
    private Double value;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public IConceptMedicationUnit getConceptMedicationUnit() {
        return conceptMedicationUnit;
    }

    @Override
    public void setConceptMedicationUnit(IConceptMedicationUnit conceptMedicationUnit) {
        this.conceptMedicationUnit = (ConceptMedicationUnit) conceptMedicationUnit;
    }

    @Override
    public IMedicationGeneric getMedicationGeneric() {
        return conceptMedicationGeneric;
    }

    @Override
    public void setMedicationGeneric(IMedicationGeneric conceptMedicationGeneric) {
        this.conceptMedicationGeneric = (ConceptMedicationGeneric) conceptMedicationGeneric;
    }

    @Override
    public boolean isDenominator() {
        return isDenominator;
    }

    @Override
    public void setDenominator(boolean isDenominator) {
        this.isDenominator = isDenominator;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }
}
