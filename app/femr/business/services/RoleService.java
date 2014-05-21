package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.common.models.IRole;
import femr.data.daos.IRepository;
import femr.data.models.Role;

import java.util.List;

public class RoleService implements IRoleService {

    private IRepository<IRole> roleRepository;

    @Inject
    public RoleService(IRepository<IRole> roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<? extends IRole> getAllRoles() {
        ExpressionList<Role> query = getQuery()
                .where()
                .ne("name", "SuperUser");
        List<? extends IRole> roles = roleRepository.find(query);
        return roles;
    }

    @Override
    public List<? extends IRole> getRolesFromIds(List<Integer> checkValuesAsIntegers) {
        ExpressionList<Role> query = getQuery().where().in("id", checkValuesAsIntegers);
        return roleRepository.find(query);
    }

    private Query<Role> getQuery() {
        return Ebean.find(Role.class);
    }
}
