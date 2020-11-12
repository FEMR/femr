package mock.femr.data.models;

import femr.data.models.core.ILoginAttempt;
import femr.data.models.core.IUser;
import femr.data.models.mysql.User;
import org.joda.time.DateTime;

public class MockLoginAttempt implements ILoginAttempt {
    private int id = -1;
    private User mockUser = new User();

    public int getId() {
        return id;
    }

    public User getUser() {
        return mockUser;
    }

    public void setUser(IUser user) {
    }

    public DateTime getLoginDate() {
        return null;
    }

    public void setLoginDate(DateTime date) {

    }

    public Boolean getIsSuccessful() {
        return null;
    }

    public void setIsSuccessful(Boolean isSuccessful) {

    }

    public String getUsernameAttempt() {
        return null;
    }

    public void setUsernameAttempt(String usernameAttempt) {

    }

    public byte[] getIp_address() {
        return null;

    }

    public void setIp_address(byte[] ip_address) {
    }
}
