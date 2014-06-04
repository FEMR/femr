package femr.ui.models.admin.users;

import femr.data.models.IUser;

/**
 * Created by kevin on 6/27/14.
 */
public class CreateViewModelGet {
    private IUser user;
    private String error;

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
