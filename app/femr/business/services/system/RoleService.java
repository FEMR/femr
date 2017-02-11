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
package femr.business.services.system;

import com.google.inject.Inject;
import femr.business.services.core.IRoleService;
import femr.common.dtos.ServiceResponse;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.IRole;

import java.util.ArrayList;
import java.util.List;

public class RoleService implements IRoleService {

    private final IUserRepository userRepository;

    @Inject
    public RoleService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> retrieveAllRoles() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();

        try {
            List<? extends IRole> roles = userRepository.retrieveAllRoles();
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
}
