package femr.util.encryptions;

import org.mindrot.jbcrypt.BCrypt;
import play.Play;

public class BCryptPasswordEncryptor implements IPasswordEncryptor {
    @Override
    public String encryptPassword(String password) {
        return this.encryptPassword(password, Play.application().configuration().getInt("bcrypt.workFactor"));
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
