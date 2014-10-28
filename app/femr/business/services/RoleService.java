/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.business.services;

import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.common.dto.ServiceResponse;
import femr.data.models.IRole;
import femr.data.daos.IRepository;
import femr.data.models.Role;

import java.util.ArrayList;
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
                .ne("name", "SuperUser");
        List<? extends IRole> roles = roleRepository.find(query);
        return roles;
    }

    @Override
    public ServiceResponse<List<String>> getAllRolesString() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();
        ExpressionList<Role> query = QueryProvider.getRoleQuery()
                .where()
                .ne("name", "SuperUser");
        try {
            List<? extends IRole> roles = roleRepository.find(query);
            List<String> stringRoles = new ArrayList<>();
            for (IRole role : roles) {
                stringRoles.add(role.getName());
            }
            response.setResponseObject(stringRoles);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }


        return response;
    }

    @Override
    public List<? extends IRole> getRolesFromIds(List<Integer> checkValuesAsIntegers) {
        ExpressionList<Role> query = QueryProvider.getRoleQuery()
                .where()
                .in("id", checkValuesAsIntegers);
        return roleRepository.find(query);
    }
}
