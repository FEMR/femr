package femr.ui.views.partials.helpers;

import femr.common.models.IRole;
import femr.common.models.Roles;

import java.util.List;

public class AuthenticatedPartialHelper {

    public static boolean showAdminMenu(List<IRole> roles) {
        for (IRole role : roles) {
            if (role.getId() == Roles.ADMINISTRATOR) {
                return true;
            }
        }

        return false;
    }

    public static boolean showMedicalPersonnelMenu(List<IRole> roles) {
        for (IRole role : roles) {
            if (role.getId() == Roles.PHARMACIST || role.getId() == Roles.PHYSICIAN || role.getId() == Roles.NURSE) {
                return true;
            }
        }

        return false;
    }

    public static boolean showResearcherMenu(List<IRole> roles) {
        for (IRole role : roles) {
            if (role.getId() == Roles.RESEARCHER) {
                return true;
            }
        }

        return false;
    }
}
