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
package femr.business.wrappers.sessions;

import static play.mvc.Controller.session;

public class SessionHelper implements ISessionHelper {

    @Override
    public int getInt(String sessionKey) {
        return session(sessionKey) != null ? Integer.parseInt(session(sessionKey)) : 0;
    }

    @Override
    public void set(String sessionKey, String sessionObject) {
        session(sessionKey, sessionObject);
    }

    @Override
    public void delete(String sessionKey) {
        session().remove(sessionKey);
    }
}
