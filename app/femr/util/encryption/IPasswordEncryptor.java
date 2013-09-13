package femr.util.encryption;

public interface IPasswordEncryptor {
    String encryptPassword(String password);

    String encryptPassword(String password, int workFactor);

    boolean verifyPassword(String password, String hashedPassword);
}
