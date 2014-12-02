package femr.ui.models.admin.users;

import femr.common.models.UserItem;
import java.util.List;

public class ManageViewModelGet {
    private List<UserItem> users;

    public UserItem getUser(int i){
        return users.get(i);
    }

    public void setUsers(List<UserItem> users) {
        this.users = users;
    }

    public List<UserItem> getUsers() {
        return users;
    }
}
