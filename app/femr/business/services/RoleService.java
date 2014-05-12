package femr.business.services;

import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import femr.business.QueryProvider;
import femr.common.models.IRole;
import femr.data.daos.IRepository;
import femr.data.models.Role;

import java.util.List;

public class RoleService implements IRoleService {

    private final IRepository<IRole> roleRepository;

    @Inject
    public RoleService(IRepository<IRole> roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<? extends IRole> getAllRoles() {
        ExpressionList<Role> query = QueryProvider.getRoleQuery()
                .where()
                .ne("name", "SuperUser")
                .ne("name", "Researcher");
        List<? extends IRole> roles = roleRepository.find(query);
        return roles;
    }

    @Override
    public List<? extends IRole> getRolesFromIds(List<Integer> checkValuesAsIntegers) {
        ExpressionList<Role> query = QueryProvider.getRoleQuery()
                .where()
                .in("id", checkValuesAsIntegers);
        return roleRepository.find(query);
    }
}
