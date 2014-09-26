package femr.ui.helpers.security;

import com.google.inject.Inject;
import femr.business.services.IUserService;
import femr.data.models.IRole;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;

import java.util.ArrayList;
import java.util.List;

public class AllowedRolesAction extends Action<AllowedRoles> {

    private IUserService userService;

    @Inject
    public AllowedRolesAction(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public F.Promise<play.mvc.Result> call(Http.Context context) throws Throwable {
        String currentUser = context.session().get("currentUser");
        int currentUserId = Integer.parseInt(currentUser);
        int[] roleIds = configuration.value();
        List<Integer> arrayListOfRoleIds = createArrayListOfRoleIds(roleIds);

        List<? extends IRole> response = userService.findRolesForUser(currentUserId);

        boolean isUserInAuthorizedRoleGroup = false;

        for (IRole role : response) {
            if (arrayListOfRoleIds.contains(role.getId())) {
                isUserInAuthorizedRoleGroup = true;
                break;
            }
        }

        if (!isUserInAuthorizedRoleGroup) {
            return F.Promise.pure(redirect("/"));
        }

        return delegate.call(context);
    }

    private List<Integer> createArrayListOfRoleIds(int[] roleIds) {
        ArrayList<Integer> intList = new ArrayList<>();

        for (int i : roleIds) {
            intList.add(i);
        }

        return intList;
    }
}