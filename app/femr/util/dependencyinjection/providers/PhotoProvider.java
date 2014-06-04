package femr.util.dependencyinjection.providers;

import femr.data.models.IPhoto;
import femr.data.models.Photo;

import javax.inject.Provider;

/**
 * Created by kevin on 5/31/14.
 */
public class PhotoProvider implements Provider<IPhoto> {
    @Override
    public IPhoto get() {
        return new Photo();
    }
}
