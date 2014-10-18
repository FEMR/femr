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
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.common.dto.ServiceResponse;
import femr.common.models.UserItem;
import femr.data.models.IRole;
import femr.data.models.IUser;
import femr.data.daos.IRepository;
import femr.data.models.Role;
import femr.data.models.User;
import femr.util.encryptions.IPasswordEncryptor;
import femr.util.stringhelpers.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {

    private final IRepository<IUser> userRepository;
    private final IPasswordEncryptor passwordEncryptor;
    private final IRepository<IRole> roleRepository;
    private final DomainMapper domainMapper;

    @Inject
    public UserService(IRepository<IUser> userRepository, IPasswordEncryptor passwordEncryptor, IRepository<IRole> roleRepository, DomainMapper domainMapper) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
        this.roleRepository = roleRepository;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<UserItem> createUser(UserItem user, String password) {
        ServiceResponse<UserItem> response = new ServiceResponse<>();
        try {

            ExpressionList<Role> query = QueryProvider.getRoleQuery()
                    .where()
                    .in("name", user.getRoles());
            List<? extends IRole> roles = roleRepository.find(query);


            IUser newUser = domainMapper.createUser(user, password, false, false, roles);
            encryptAndSetUserPassword(newUser);


            if (userExistsWithEmail(user.getEmail())) {
                response.addError("", "A user already exists with that email address.");
                return response;
            }

            newUser = userRepository.create(newUser);
            response.setResponseObject(DomainMapper.createUserItem(newUser));
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }


        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<UserItem>> findAllUsers() {

        ExpressionList<User> query = QueryProvider.getUserQuery()
                .fetch("roles")
                .where()
                .ne("roles.name", "SuperUser");
        List<? extends IUser> users = userRepository.find(query);

        ServiceResponse<List<UserItem>> response = new ServiceResponse<>();
        List<UserItem> userItems = new ArrayList<>();
        if (users.size() > 0) {
            for (IUser user : users) {
                userItems.add(DomainMapper.createUserItem(user));
            }
            response.setResponseObject(userItems);
        } else {
            response.addError("users", "could not find any users");
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
            response.setResponseObject(DomainMapper.createUserItem(user));
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<UserItem> findUser(int id) {
        ServiceResponse<UserItem> response = new ServiceResponse<>();
        ExpressionList<User> query = QueryProvider.getUserQuery().fetch("roles").where().eq("id", id);
        try {
            IUser user = userRepository.findOne(query);
            UserItem userItem;
            userItem = DomainMapper.createUserItem(user);
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
            user = userRepository.update(user);
            response.setResponseObject(DomainMapper.createUserItem(user));
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    @Override
    public IUser findByEmail(String email) {
        ExpressionList<User> query = QueryProvider.getUserQuery().fetch("roles").where().eq("email", email);

        return userRepository.findOne(query);
    }

    @Override
    public IUser findById(int id) {
        ExpressionList<User> query = QueryProvider.getUserQuery().fetch("roles").where().eq("id", id);

        return userRepository.findOne(query);
    }

    @Override
    public List<? extends IRole> findRolesForUser(int id) {
        ExpressionList<User> query = QueryProvider.getUserQuery().fetch("roles").where().eq("id", id);
        IUser user = userRepository.findOne(query);
        return user.getRoles();
    }

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

    private boolean userExistsWithEmail(String email) {
        IUser existingUser = findByEmail(email);
        return existingUser != null;
    }
}