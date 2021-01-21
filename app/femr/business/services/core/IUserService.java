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
package femr.business.services.core;

import femr.common.dtos.ServiceResponse;
import femr.common.models.UserItem;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;

import java.util.List;

public interface IUserService {

    ServiceResponse<Boolean> createFeedback(String feedback);

    /**
     * Create a brand new user.
     *
     * @param user user to be created, TODO: change to parameters
     * @param password the users password to be encrypted, nto null
     * @param userId id of the user creating the user
     * @return ServiceResponse that contains the new UserItem
     * and/or errors if they exist.
     */
    ServiceResponse<UserItem> createUser(UserItem user, String password, int userId);

    /**
     * Find all users.
     *
     * @return ServiceResponse that contains a list of all UserItems
     * and/or errors if they exist.
     */
    ServiceResponse<List<UserItem>> retrieveAllUsers();

    /**
     * If a user is deactivated, activate them and vice versa
     *
     * @param id primary id of the user, not null
     * @return ServiceResponse that contains the toggled UserItem
     * and/or errors if they exist.
     */
    ServiceResponse<UserItem> toggleUser(int id);

    /**
     * Find a user by their ID
     *
     * @param id primary id of the user, not null
     * @return ServiceResponse that contains the UserItem
     * and/or errors if they exist.
     */
    ServiceResponse<UserItem> retrieveUser(int id);

    /**
     * Retrieve a list of users that attended a specific mission trip.
     *
     * @param tripId id of the mission trip
     * @return ServiceResponse that contains a list of UserItems
     * and/or errors if they exist
     */
    ServiceResponse<List<UserItem>> retrieveUsersByTripId(int tripId);

    /**
     * Updates a user based on ID. Email can be changed.
     *
     * @param userItem user item where id is the identifier, TODO: change to parameters
     * @param newPassword if not null, changes the users password
     * @return ServiceResponse that contains the updated UserItem
     * and/or errors if they exist.
     */
    ServiceResponse<UserItem> updateUser(UserItem userItem, String newPassword);

    /**
     * Retrieves a user by their email address. TODO: stop returning data models
     *
     * @param email email address of the user aka username, not null
     * @return the IUser
     */
    IUser retrieveByEmail(String email);

    /**
     * Retrieves a user by their id. TODO: stop returning data models
     *
     * @param id id of the user, not null
     * @return the IUser
     */
    IUser retrieveById(int id);

    /**
     * Retrieves all the roles for a user. TODO: stop returning data models
     *
     * @param id id of the user, not null
     * @return a list of IRoles
     */
    List<? extends IRole> retrieveRolesForUser(int id);

    /**
     * Update the user
     *
     * @param currentUser the current user, not null
     * @param isNewPassword if the password is new, may be null
     * @return ServiceResponse that contains the updated IUser
     * and/or errors if they exist.
     */
    ServiceResponse<IUser> update(IUser currentUser, Boolean isNewPassword);

        /**
     * Check if the old password is equal to the new password
     *
     * @param newpassword, the new password (not hashed), not null
     * @param oldpassword, the old password (hashed), not null
     * @return Boolean that returns true if the passwords are equal and false if not
     */
    Boolean checkOldPassword(String newpassword,String oldpassword);
}
