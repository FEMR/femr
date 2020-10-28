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
import femr.business.services.core.IMissionTripService;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.business.wrappers.sessions.ISessionHelper;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.*;
import femr.util.encryptions.IPasswordEncryptor;

import java.net.InetAddress;
import java.util.Optional;
import com.typesafe.config.Config;

public class SessionService implements ISessionService {

    private IUserService userService;
    private IMissionTripService missionTripService;
    private IPasswordEncryptor passwordEncryptor;
    private ISessionHelper sessionHelper;
    private final IDataModelMapper dataModelMapper;
    private final IUserRepository userRepository;
    private final Config configuration;

    @Inject
    public SessionService(IUserService userService,
                          IMissionTripService missionTripService,
                          IPasswordEncryptor passwordEncryptor,
                          ISessionHelper sessionHelper,
                          IDataModelMapper dataModelMapper,
                          IUserRepository userRepository,
                          Config configuration) {

        this.userService = userService;
        this.missionTripService = missionTripService;
        this.passwordEncryptor = passwordEncryptor;
        this.sessionHelper = sessionHelper;
        this.dataModelMapper = dataModelMapper;
        this.userRepository = userRepository;
        this.configuration = configuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<CurrentUser> createSession(String email, String password, String ipAddress) {

        IUser userWithEmail = userService.retrieveByEmail(email);

        Optional<IMissionTrip> currentTrip;
        Integer tripId = null;
        boolean isSuccessful = false;
        Integer userId = null;
        //set to a default IP address
        byte[] ipAddressBinary = new byte[]{0, 0, 0, 0};
        //try to get the IP address of the incoming request to create a session
        try {
            ipAddressBinary = InetAddress.getByName(ipAddress).getAddress();

        } catch (Exception ex) {

            //don't do anything because the default IP address was initalized
        }


        ServiceResponse<CurrentUser> response = new ServiceResponse<>();

        if (userWithEmail == null) {
            //user doesn't exist

            response.addError("", "Invalid email or password.");
        } else if (userWithEmail.getDeleted() || !passwordEncryptor.verifyPassword(password, userWithEmail.getPassword())) {
            //user has been deleted or they entered a wrong password

            userId = userWithEmail.getId();//set the ID of the deleted user for the log
            response.addError("", "Invalid email or password.");
        } else {
            //success!

            isSuccessful = true;

            userId = userWithEmail.getId();//set the ID of the deleted user for the log
            currentTrip = missionTripService.retrieveCurrentMissionTripForUser(userId);//grab the current trip that the user is on
            if (currentTrip.isPresent()){
                tripId = currentTrip.get().getId();
            }
            sessionHelper.set("currentUser", String.valueOf(userId));//initiate the session
            response.setResponseObject(createCurrentUser(userWithEmail, tripId));//send the user back in the response object
        }

        userRepository.createLoginAttempt(email, isSuccessful, ipAddressBinary, userId);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrentUser retrieveCurrentUserSession() {

        int currentUserId = sessionHelper.getInt("currentUser");

        //if the current user id is 0 then there is no session and the user will be directed back to the
        //login page.
        if (currentUserId > 0) {
            IUser userFoundById = userService.retrieveById(currentUserId);
            if (userFoundById == null) {
                return null;
            }

            Optional<IMissionTrip> currentTrip = missionTripService.retrieveCurrentMissionTripForUser(currentUserId);
            Integer tripId = null;
            if (currentTrip.isPresent()){
                tripId = currentTrip.get().getId();
            }

            return createCurrentUser(userFoundById, tripId);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidateCurrentUserSession() {
        sessionHelper.delete("currentUser");
    }

    private CurrentUser createCurrentUser(IUser user, Integer tripId) {

        long timeout = Long.valueOf(configuration.getString("sessionTimeout")) * 1000 * 60;
        return new CurrentUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoles(), tripId, timeout);
    }
}

