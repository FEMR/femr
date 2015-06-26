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
package femr.data.daos.core;

import femr.data.models.core.IRole;
import femr.data.models.core.IUser;

import java.util.List;

/**
 * A repository to cover the following tables:
 * <ul>
 * <li>users</li>
 * <li>user_roles</li>
 * <li>roles</li>
 * </ul>
 */
public interface IUserRepository {

    /**
     * Retrieve a user by their email
     *
     * @param email the email of the user (unique), not null
     * @return the user or null if parameter is null/an error occured
     */
    IUser retrieveUserByEmail(String email);

    /**
     * Retrieve a user by their email and eager fetches the roles
     *
     * @param email the email of the user (unique), not null
     * @return the user or null if parameter is null/an error occured
     */
    IUser retrieveUserByEmailEagerFetchRoles(String email);

    /**
     * Retrieve a user by their id
     *
     * @param id the id of the user (unique), not null
     * @return the user or null if an error occured
     */
    IUser retrieveUserById(int id);

    /**
     * Retrieve a user by their id and eager fetches the roles
     *
     * @param id the id of the user (unique), not null
     * @return the user or null if an error occured
     */
    IUser retrieveUserByIdEagerFetchRoles(int id);

    /**
     * Retrieves all available roles for a user. This does NOT include the SuperUser role.
     *
     * @return a list of roles or an empty list if an error occured
     */
    List<? extends IRole> retrieveAllRoles();

    /**
     * Retrieve all available users. This does NOT include the SuperUser role
     *
     * @return all users or an empty list if an error occured
     */
    List<? extends IUser> retrieveAllUsers();

    /**
     * Retrieves all roles with names that match an element in the provided list.
     *
     * @param names a list of role names
     * @return 1) a list of roles 2)null if an error occured/the parameter is null or empty 3)the roles
     */
    List<? extends IRole> retrieveRoles(List<String> names);

    /**
     * Updates or creates a user
     *
     * @param user the user to update or create, not null
     * @return the updated or created user or null if parameter is null/an error occured
     */
    IUser saveUser(IUser user);
}
