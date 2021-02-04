package femr.ui.views.partials.helpers;

import femr.data.models.core.IRole;
import femr.data.models.mysql.Roles;

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

    public static boolean showSuperUserMenu(List<IRole> roles) {
        for (IRole role : roles) {
            if (role.getId() == Roles.SUPERUSER) {
                return true;
            }
        }

        return false;
    }

    public static boolean showManagerMenu(List<IRole> roles) {
        for (IRole role : roles) {
            if (role.getId() == Roles.MANAGER) {
                return true;
            }
        }

        return false;
    }
}
