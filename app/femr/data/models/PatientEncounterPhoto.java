package femr.data.models;

import femr.common.models.IPatientEncounterPhoto;

import javax.persistence.*;

@Entity
@Table(name = "patient_encounter_photos")
public class PatientEncounterPhoto implements IPatientEncounterPhoto {

    @Id
    @Column(name = "photo_id", nullable = false)
    private int _photoId;

    @Id
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
