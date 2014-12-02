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

import femr.common.dto.ServiceResponse;
import femr.common.models.UserItem;
import femr.data.models.IRole;
import femr.data.models.IUser;

import java.util.List;

public interface IUserService {
    /**
     * Create a brand new user
     * @param user user to be created
     * @param password the users password to be encrypted
     * @return a new user
     */
    ServiceResponse<UserItem> createUser(UserItem user, String password);

    /**
     *
     * @return all users that exist
     */
    ServiceResponse<List<UserItem>> findAllUsers();

    /**
     * If a user is deactivated, activate them and vice versa
     *
     * @param id primary id of the user
     * @return
     */
    ServiceResponse<UserItem> toggleUser(int id);

    /**
     * Find a user by their ID
     * @param id primary id of the user
     * @return
     */
    ServiceResponse<UserItem> findUser(int id);

    /**
     * Updates a user based on ID. Email can be changed.
     *
     * @param userItem user item where id is the identifier
     * @param newPassword if not null, changes the users password
     * @return
     */
    ServiceResponse<UserItem> updateUser(UserItem userItem, String newPassword);

    IUser findByEmail(String email);

    IUser findById(int id);

    List<? extends IRole> findRolesForUser(int id);





    ServiceResponse<IUser> update(IUser currentUser, Boolean isNewPassword);
}
