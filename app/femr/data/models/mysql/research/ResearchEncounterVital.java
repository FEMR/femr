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
package femr.data.models.mysql.research;

import femr.data.models.core.IVital;
import femr.data.models.core.research.IResearchEncounterVital;
import femr.data.models.mysql.PatientEncounter;
import femr.data.models.mysql.Vital;

import javax.persistence.*;

@Entity
@Table(name = "patient_encounter_vitals")
public class ResearchEncounterVital implements IResearchEncounterVital {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_encounter_id")
    private ResearchEncounter patientEncounter;

    @Column(name = "vital_id", nullable = false)
    private int vitalId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vital_id", nullable = false)
    private Vital vital;

    @Column(name = "vital_value", nullable = false)
    private float vitalValue;
    @Column(name = "date_taken", nullable = false)
    private String dateTaken;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public ResearchEncounter getPatientEncounter() {
        return patientEncounter;
    }

    @Override
    public void setPatientEncounter(ResearchEncounter patientEncounter) {
        this.patientEncounter = patientEncounter;
    }

    @Override
    public IVital getVital() {
        return vital;
    }

    @Override
    public void setVital(IVital vital) {
        this.vital = (Vital) vital;
    }

    @Override
    public int getVitalId() {
        return vitalId;
    }

    @Override
    public void setVitalId(int vitalId) {
        this.vitalId = vitalId;
    }

    @Override
    public Float getVitalValue() {
        return vitalValue;
    }

    @Override
    public void setVitalValue(float vitalValue) {
        this.vitalValue = vitalValue;
    }

    @Override
    public String getDateTaken() {
        return dateTaken;
    }

    @Override
    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }
}
