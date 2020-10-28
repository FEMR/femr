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
package femr.util.encryptions;

import com.google.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;
import com.typesafe.config.Config;

public class BCryptPasswordEncryptor implements IPasswordEncryptor {

    private final Config configuration;

    @Inject
    public BCryptPasswordEncryptor(Config configuration){

        this.configuration = configuration;
    }


    @Override
    public String encryptPassword(String password) {
        return this.encryptPassword(password, configuration.getInt("bcrypt.workFactor"));
    }

    @Override
    public String encryptPassword(String password, int workFactor) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(workFactor));
        return hashedPassword;
    }

    @Override
    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
