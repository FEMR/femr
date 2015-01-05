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

import femr.data.models.core.IPatientEncounterVital;
import femr.data.models.core.IVital;

import javax.persistence.*;

@Entity
@Table(name = "patient_encounter_vitals")
public class PatientEncounterVital implements IPatientEncounterVital {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "patient_encounter_id", nullable = false)
    private int patientEncounterId;
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
    public int getPatientEncounterId() {
        return patientEncounterId;
    }

    @Override
    public void setPatientEncounterId(int patientEncounterId) {
        this.patientEncounterId = patientEncounterId;
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
