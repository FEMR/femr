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
import com.google.inject.name.Named;
import femr.business.services.core.IUserService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.UserItem;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import femr.util.calculations.dateUtils;
import femr.util.encryptions.IPasswordEncryptor;
import femr.util.stringhelpers.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IPasswordEncryptor passwordEncryptor;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public UserService(IUserRepository userRepository,
                       IPasswordEncryptor passwordEncryptor,
                       IDataModelMapper dataModelMapper,
                       @Named("identified") IItemModelMapper itemModelMapper) {

        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<UserItem> createUser(UserItem user, String password) {
        ServiceResponse<UserItem> response = new ServiceResponse<>();
        try {

            List<? extends IRole> roles = userRepository.retrieveRoles(user.getRoles());


            IUser newUser = dataModelMapper.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), dateUtils.getCurrentDateTime(), user.getNotes(), password, false, false, roles);
            encryptAndSetUserPassword(newUser);


            if (userExistsWithEmail(user.getEmail())) {
                response.addError("", "A user already exists with that email address.");
                return response;
            }

            newUser = userRepository.saveUser(newUser);
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

        List<? extends IUser> users = userRepository.retrieveAllUsers();

        ServiceResponse<List<UserItem>> response = new ServiceResponse<>();
        List<UserItem> userItems = new ArrayList<>();
        if (users.size() > 0) {
            for (IUser user : users) {
                userItems.add(itemModelMapper.createUserItem(user));
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

        try {

            IUser user = userRepository.retrieveUserById(id);
            user.setDeleted(!user.getDeleted());
            user = userRepository.saveUser(user);
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

        try {

            IUser user = userRepository.retrieveUserById(id);
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

        List<? extends IRole> allRoles = userRepository.retrieveAllRoles();

        try {

            IUser user = userRepository.retrieveUserById(userItem.getId());
            if (StringUtils.isNotNullOrWhiteSpace(newPassword)) {
                user.setPassword(newPassword);
                encryptAndSetUserPassword(user);
            }
            user.setFirstName(userItem.getFirstName());
            user.setLastName(userItem.getLastName());
            user.setNotes(userItem.getNotes());
            List<IRole> newRoles = new ArrayList<>();
            for (IRole role : allRoles) {
                if (userItem.getRoles().contains(role.getName()))
                    newRoles.add(role);
            }
            user.setRoles(newRoles);
            user.setPasswordReset(userItem.isPasswordReset());
            user = userRepository.saveUser(user);
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

        return userRepository.retrieveUserByEmailEagerFetchRoles(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser retrieveById(int id) {

        return userRepository.retrieveUserByIdEagerFetchRoles(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IRole> retrieveRolesForUser(int id) {

        IUser user = userRepository.retrieveUserByIdEagerFetchRoles(id);
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

        currentUser = userRepository.saveUser(currentUser);
        if (currentUser != null) {
            response.setResponseObject(currentUser);
        } else {
            response.addError("", "Could not updatePatientEncounter user");
        }
        return response;
    }

    private void encryptAndSetUserPassword(IUser user) {
        String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());

        user.setPassword(encryptedPassword);
    }

    private boolean userExistsWithEmail(String email) {
        IUser existingUser = retrieveByEmail(email);
        return existingUser != null;
    }
}