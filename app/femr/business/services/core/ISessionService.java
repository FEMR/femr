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

import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;

public interface ISessionService {

    /**
     * Creates a new user session.
     *
     * @param email    the username of the user (doesn't necessarily have to be email), not null
     * @param password a valid password for the user, not null
     * @param ipAddress IP address of the device attempting to login
     * @return a service response that contains a CurrentUser representing the sessions's current user
     * and/or errors if they exist.
     */
    ServiceResponse<CurrentUser> createSession(String email, String password, String ipAddress);

    /**
     * Gets the user that is currently logged in.
     *
     * @return a CurrentUser
     */
    CurrentUser retrieveCurrentUserSession();

    /**
     * invalidate the current user session - log the user out.
     */
    void invalidateCurrentUserSession();
}
