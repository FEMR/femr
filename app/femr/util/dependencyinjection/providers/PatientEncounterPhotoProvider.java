package femr.util.dependencyinjection.providers;

import femr.data.models.IPatientEncounterPhoto;
import femr.data.models.PatientEncounterPhoto;

import javax.inject.Provider;

/**
 * Created by kevin on 5/31/14.
 */
public class PatientEncounterPhotoProvider implements Provider<IPatientEncounterPhoto> {
    @Override
    public IPatientEncounterPhoto get() {
        return new PatientEncounterPhoto();
    }
}
