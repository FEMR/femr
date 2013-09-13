package femr.util.dependencyinjection.providers;

import com.google.inject.Provider;
import femr.common.models.IUser;
import femr.data.models.User;

public class UserProvider implements Provider<IUser> {
    @Override
    public IUser get() {
        return new User();
    }
}
