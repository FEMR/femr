package femr.util.encryptions;

public interface IPasswordEncryptor {
    String encryptPassword(String password);

    String encryptPassword(String password, int workFactor);

    boolean verifyPassword(String password, String hashedPassword);
}
