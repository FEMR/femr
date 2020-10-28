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
package femr.data.models.mysql;

import femr.data.models.core.IChiefComplaint;
import femr.data.models.core.IPatientEncounter;

import javax.persistence.*;

@Entity
@Table(name = "chief_complaints")
public class ChiefComplaint implements IChiefComplaint {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "value")
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_encounter_id")
    private PatientEncounter patientEncounter;
    @Column(name = "sort_Order")
    private Integer sortOrder;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public IPatientEncounter getPatientEncounter() {
        return patientEncounter;
    }

    @Override
    public void setPatientEncounter(IPatientEncounter patientEncounter) {
        this.patientEncounter = (PatientEncounter) patientEncounter;
    }

    @Override
    public Integer getSortOrder() {
        return sortOrder;
    }

    @Override
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
