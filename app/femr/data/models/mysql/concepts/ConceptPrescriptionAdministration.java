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

import femr.data.models.core.IConceptPrescriptionAdministration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "concept_prescription_administrations")
public class ConceptPrescriptionAdministration implements IConceptPrescriptionAdministration {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "daily_modifier", unique = false, nullable = false)
    private float dailyModifier;

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
    public float getDailyModifier() { return dailyModifier; };

    @Override
    public void setDailyModifier(float dailyModifier) { this.dailyModifier = dailyModifier; }
}
