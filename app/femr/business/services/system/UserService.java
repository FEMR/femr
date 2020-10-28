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
import femr.data.daos.IRepository;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.IFeedback;
import femr.data.models.core.IPatientPrescriptionReplacement;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import femr.data.models.mysql.Feedback;
import femr.util.calculations.dateUtils;
import femr.util.encryptions.IPasswordEncryptor;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IPasswordEncryptor passwordEncryptor;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;
    private final IRepository<IFeedback> feedbackRepository;

    @Inject
    public UserService(IUserRepository userRepository,
                       IPasswordEncryptor passwordEncryptor,
                       IDataModelMapper dataModelMapper,
                       @Named("identified") IItemModelMapper itemModelMapper,
                       IRepository<IFeedback> feedbackRepository) {

        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ServiceResponse<Boolean> createFeedback(String feedback) {
        IFeedback newFeedback = new Feedback();

        ServiceResponse<Boolean> response = new ServiceResponse<>();

        newFeedback.setDate(dateUtils.getCurrentDateTime());
        newFeedback.setFeedback(feedback);

        feedbackRepository.create(newFeedback);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<UserItem> createUser(UserItem user, String password, int userId) {

        ServiceResponse<UserItem> response = new ServiceResponse<>();
        try {

            List<? extends IRole> roles = userRepository.retrieveRolesByName(user.getRoles());

            // AJ Saclayan - Password Constraints
            IUser newUser = dataModelMapper.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), dateUtils.getCurrentDateTime(), user.getNotes(), password, false, false, roles, userId);
            encryptAndSetUserPassword(newUser);


            if (userExistsWithEmail(user.getEmail())) {
                response.addError("", "A user already exists with that email address.");
                return response;
            }

            newUser = userRepository.createUser(newUser);
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


        try{

            List<? extends IUser> users = userRepository.retrieveAllUsers();

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



        try {

            List<? extends IUser> users = userRepository.retrieveUsersByTripId(tripId);

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

        try {
            IUser user = userRepository.retrieveUserById(id);
            user.setDeleted(!user.getDeleted());
            user = userRepository.updateUser(user);
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

        if (allRoles == null) {

            response.addError("", "no roles found");
            return response;
        }

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
            for (IRole role : allRoles){
                if (userItem.getRoles().contains(role.getName()))
                    newRoles.add(role);
                }
            user.setRoles(newRoles);
            user.setPasswordReset(userItem.isPasswordReset());
            user.setPasswordCreatedDate(DateTime.now());
            user = userRepository.updateUser(user);
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
        return userRepository.retrieveUserByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser retrieveById(int id) {
        return userRepository.retrieveUserById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IRole> retrieveRolesForUser(int id) {
        return userRepository.retrieveUserById(id).getRoles();
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

        currentUser = userRepository.updateUser(currentUser);
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
