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

import femr.data.models.core.IPatientEncounterPhoto;

import javax.persistence.*;

@Entity
@Table(name = "patient_encounter_photos")
public class PatientEncounterPhoto implements IPatientEncounterPhoto {
    @Id
    @Column(name = "photo_id", nullable = false)
    private int _photoId;

    @Column(name = "patient_encounter_id", nullable = false)
    private int _patientEncounterId;

    @Override
    public int getPatientEncounterId() {
        return _patientEncounterId;
    }

    @Override
    public void setPatientEncounterId(int id) {
        _patientEncounterId = id;
    }

    @Override
    public int getPhotoId() {
        return _photoId;
    }

    @Override
    public void setPhotoId(int id) {
        _photoId = id;
    }
}
