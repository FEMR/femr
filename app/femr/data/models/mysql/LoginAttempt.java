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
package femr.data.models.mysql;

import femr.data.models.core.ILoginAttempt;
import femr.data.models.core.IUser;
import org.joda.time.DateTime;
import javax.persistence.*;

@Entity
@Table(name = "login_attempts")
public class LoginAttempt implements ILoginAttempt {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = false, nullable = true)
    private User user;
    @Column(name = "ip_address", unique = false, nullable = false)
    private byte[] ip_address;
    @Column(name = "date_of_attempt", unique = false, nullable = true)
    private DateTime loginDate;
    @Column(name = "isSuccessful", nullable = false)
    private Boolean isSuccessful;
    @Column(name = "username_attempt", nullable = false)
    private String usernameAttempt;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(IUser user) {
        this.user = (User) user;
    }

    @Override
    public DateTime getLoginDate() {
        return loginDate;
    }

    @Override
    public void setLoginDate(DateTime loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public Boolean getIsSuccessful() {
        return isSuccessful;
    }

    @Override
    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    @Override
    public String getUsernameAttempt() {
        return usernameAttempt;
    }

    @Override
    public void setUsernameAttempt(String usernameAttempt) {
        this.usernameAttempt = usernameAttempt;
    }

    @Override
    public byte[] getIp_address() {
        return ip_address;
    }

    @Override
    public void setIp_address(byte[] ip_address) {
        this.ip_address = ip_address;
    }
}
