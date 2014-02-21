package femr.ui.models.admin.users;

import femr.common.models.IRole;
import femr.common.models.IUser;

import java.util.List;

/**
 * Created by Kevin on 2/20/14.
 */
public class EditUserViewModel {
    private IUser user;
    private List<? extends IRole> allRoles;

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public List<? extends IRole> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(List<? extends IRole> allRoles) {
        this.allRoles = allRoles;
    }
}
