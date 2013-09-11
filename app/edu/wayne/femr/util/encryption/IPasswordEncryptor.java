package edu.wayne.femr.util.encryption;

public interface IPasswordEncryptor {
    String hashPassword(String password);

    String hashPassword(String password, int workFactor);

    boolean checkPassword(String password, String hashedPassword);
}
