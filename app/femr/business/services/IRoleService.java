package femr.business.services;

import femr.common.models.IRole;

import java.util.List;

public interface IRoleService {
    List<? extends IRole> getAllRoles();

    List<? extends IRole> getRolesFromIds(List<Integer> checkValuesAsIntegers);
}
