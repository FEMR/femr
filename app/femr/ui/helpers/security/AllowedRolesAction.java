package femr.ui.helpers.security;

import com.google.inject.Inject;
import femr.business.services.core.IUserService;
import femr.data.models.core.IRole;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AllowedRolesAction extends Action<AllowedRoles> {

    private IUserService userService;

    @Inject
    public AllowedRolesAction(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public CompletionStage<Result> call(Http.Context context) {
        String currentUser = context.session().get("currentUser");
        int currentUserId = Integer.parseInt(currentUser);
        int[] roleIds = configuration.value();
        List<Integer> arrayListOfRoleIds = createArrayListOfRoleIds(roleIds);

        List<? extends IRole> response = userService.retrieveRolesForUser(currentUserId);

        boolean isUserInAuthorizedRoleGroup = false;

        for (IRole role : response) {
            if (arrayListOfRoleIds.contains(role.getId())) {
                isUserInAuthorizedRoleGroup = true;
                break;
            }
        }

        if (!isUserInAuthorizedRoleGroup) {
            return CompletableFuture.completedFuture(redirect("/"));
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