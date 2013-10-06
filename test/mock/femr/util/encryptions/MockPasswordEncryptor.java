package mock.femr.util.encryptions;

import femr.util.encryptions.IPasswordEncryptor;

public class MockPasswordEncryptor implements IPasswordEncryptor {

    public boolean verifyPasswordWasCalled = false;
    public String passwordPassedIn;
    public String hashedPasswordPassedIn;
    public boolean verifyPasswordResult = false;

    @Override
    public String encryptPassword(String password) {
        return null;
    }

    @Override
    public String encryptPassword(String password, int workFactor) {
        return null;
    }

    @Override
    public boolean verifyPassword(String password, String hashedPassword) {
        verifyPasswordWasCalled = true;
        passwordPassedIn = password;
        hashedPasswordPassedIn = hashedPassword;
        return verifyPasswordResult;
    }
}
