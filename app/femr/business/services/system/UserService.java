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

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IUserService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.UserItem;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import femr.data.models.mysql.Role;
import femr.data.models.mysql.User;
import femr.util.calculations.dateUtils;
import femr.util.encryptions.IPasswordEncryptor;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUserService {

    private final IRepository<IUser> userRepository;
    private final IPasswordEncryptor passwordEncryptor;
    private final IRepository<IRole> roleRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public UserService(IRepository<IUser> userRepository,
                       IPasswordEncryptor passwordEncryptor,
                       IRepository<IRole> roleRepository,
                       IDataModelMapper dataModelMapper,
                       @Named("identified") IItemModelMapper itemModelMapper) {

        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
        this.roleRepository = roleRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<UserItem> createUser(UserItem user, String password, int userId) {
        ServiceResponse<UserItem> response = new ServiceResponse<>();
        try {

            ExpressionList<Role> query = QueryProvider.getRoleQuery()
                    .where()
                    .in("name", user.getRoles());
            List<? extends IRole> roles = roleRepository.find(query);

            // AJ Saclayan - Password Constraints
            IUser newUser = dataModelMapper.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), dateUtils.getCurrentDateTime(), user.getNotes(), password, false, false, roles, userId);
            encryptAndSetUserPassword(newUser);


            if (userExistsWithEmail(user.getEmail())) {
                response.addError("", "A user already exists with that email address.");
                return response;
            }

            newUser = userRepository.create(newUser);
            response.setResponseObject(itemModelMapper.createUserItem(newUser));
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }


        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<UserItem>> retrieveAllUsers() {

        ServiceResponse<List<UserItem>> response = new ServiceResponse<>();
        List<UserItem> userItems = new ArrayList<>();

        Query<User> query = QueryProvider.getUserQuery()
                .where()
                .ne("email", "superuser")
                .ne("email", "admin")
                .orderBy("lastName");

        try{

            List<? extends IUser> users = userRepository.find(query);

            for (IUser user : users) {

                userItems.add(itemModelMapper.createUserItem(user));
            }

            response.setResponseObject(userItems);

        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<UserItem>> retrieveUsersByTripId(int tripId) {

        ServiceResponse<List<UserItem>> response = new ServiceResponse<>();
        List<UserItem> userItems = new ArrayList<>();

        ExpressionList<User> query = QueryProvider.getUserQuery()
                .fetch("missionTrips")
                .where()
                .eq("missionTrips.id", tripId);

        try {

            List<? extends IUser> users = userRepository.find(query);

            userItems.addAll(users.stream()
                            .map(itemModelMapper::createUserItem)
                            .collect(Collectors.toList())
            );

            response.setResponseObject(userItems);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<UserItem> toggleUser(int id) {
        ServiceResponse<UserItem> response = new ServiceResponse<>();
        ExpressionList<User> query = QueryProvider.getUserQuery().where().eq("id", id);
        try {
            IUser user = userRepository.findOne(query);
            user.setDeleted(!user.getDeleted());
            user = userRepository.update(user);
            response.setResponseObject(itemModelMapper.createUserItem(user));
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<UserItem> retrieveUser(int id) {
        ServiceResponse<UserItem> response = new ServiceResponse<>();
        ExpressionList<User> query = QueryProvider.getUserQuery().fetch("roles").where().eq("id", id);
        try {
            IUser user = userRepository.findOne(query);
            UserItem userItem;
            userItem = itemModelMapper.createUserItem(user);
            response.setResponseObject(userItem);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<UserItem> updateUser(UserItem userItem, String newPassword) {
        ServiceResponse<UserItem> response = new ServiceResponse<>();
        if (userItem == null) {
            response.addError("", "send a user");
            return response;
        }
        ExpressionList<User> query = QueryProvider.getUserQuery().where().eq("id", userItem.getId());
        ExpressionList<Role> roleQuery = QueryProvider.getRoleQuery()
                .where()
                .ne("name", "SuperUser");

        List<? extends IRole> allRoles = roleRepository.find(roleQuery);

        try {
            IUser user = userRepository.findOne(query);
            if (StringUtils.isNotNullOrWhiteSpace(newPassword)) {
                user.setPassword(newPassword);
                encryptAndSetUserPassword(user);
            }
            user.setFirstName(userItem.getFirstName());
            user.setLastName(userItem.getLastName());
            user.setNotes(userItem.getNotes());
            List<IRole> newRoles = new ArrayList<>();
            for (IRole role : allRoles){
                if (userItem.getRoles().contains(role.getName()))
                    newRoles.add(role);
                }
            user.setRoles(newRoles);
            user.setPasswordReset(userItem.isPasswordReset());
            user.setPasswordCreatedDate(DateTime.now());
            user = userRepository.update(user);
            response.setResponseObject(itemModelMapper.createUserItem(user));
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser retrieveByEmail(String email) {
        ExpressionList<User> query = QueryProvider.getUserQuery().fetch("roles").where().eq("email", email);

        return userRepository.findOne(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser retrieveById(int id) {
        ExpressionList<User> query = QueryProvider.getUserQuery().fetch("roles").where().eq("id", id);

        return userRepository.findOne(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IRole> retrieveRolesForUser(int id) {
        ExpressionList<User> query = QueryProvider.getUserQuery().fetch("roles").where().eq("id", id);
        IUser user = userRepository.findOne(query);
        return user.getRoles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<IUser> update(IUser currentUser, Boolean isNewPassword) {
        ServiceResponse<IUser> response = new ServiceResponse<>();
        if (isNewPassword) {
            encryptAndSetUserPassword(currentUser);
        }

        currentUser = userRepository.update(currentUser);
        if (currentUser != null) {
            response.setResponseObject(currentUser);
        } else {
            response.addError("", "Could not update user");
        }
        return response;
    }

    private void encryptAndSetUserPassword(IUser user) {
        String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());

        user.setPassword(encryptedPassword);
    }

    @Override
    public Boolean checkOldPassword(String newpassword, String oldpassword)
    {
        return passwordEncryptor.verifyPassword(newpassword,oldpassword);
    }

    private boolean userExistsWithEmail(String email) {
        IUser existingUser = retrieveByEmail(email);
        return existingUser != null;
    }
}
