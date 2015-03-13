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
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.business.wrappers.sessions.ISessionHelper;
import femr.data.models.core.IUser;
import femr.data.daos.IRepository;
import femr.util.encryptions.IPasswordEncryptor;

//import static play.mvc.Controller.session;

public class SessionService implements ISessionService {

    private IUserService userService;
    private IPasswordEncryptor passwordEncryptor;
    private ISessionHelper sessionHelper;
    private IRepository<IUser> userRepository;

    @Inject
    public SessionService(IUserService userService, IPasswordEncryptor passwordEncryptor,
                          ISessionHelper sessionHelper, IRepository<IUser> userRepository) {

        this.userService = userService;
        this.passwordEncryptor = passwordEncryptor;
        this.sessionHelper = sessionHelper;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<CurrentUser> createSession(String email, String password) {
        IUser userWithEmail = userService.retrieveByEmail(email);
        ServiceResponse<CurrentUser> response = new ServiceResponse<>();

        //user doesn't exist OR
        //password is invalid OR
        //user has been deleted - the if statement responsible for validating the user logging in!!
        if (userWithEmail == null || !passwordEncryptor.verifyPassword(password, userWithEmail.getPassword()) || userWithEmail.getDeleted() == true) {
            response.addError("", "Invalid email or password.");
            return response;
        }

        sessionHelper.set("currentUser", String.valueOf(userWithEmail.getId()));
        response.setResponseObject(createCurrentUser(userWithEmail));

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrentUser retrieveCurrentUserSession() {
        int currentUserId = sessionHelper.getInt("currentUser");

        if (currentUserId > 0) {
            IUser userFoundById = userService.retrieveById(currentUserId);
            if (userFoundById == null) {
                return null;
            }

            return createCurrentUser(userFoundById);
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

    private CurrentUser createCurrentUser(IUser user) {
        return new CurrentUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoles());
    }
}
