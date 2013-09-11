package edu.wayne.femr.util.encryption;

import org.mindrot.jbcrypt.BCrypt;
import play.Play;

public class BCryptPasswordEncryptor implements IPasswordEncryptor {
    @Override
    public String hashPassword(String password) {
        return this.hashPassword(password, Play.application().configuration().getInt("bcrypt.workFactor"));
    }

    @Override
    public String hashPassword(String password, int workFactor) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(workFactor));
        return hashedPassword;
    }

    @Override
    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
