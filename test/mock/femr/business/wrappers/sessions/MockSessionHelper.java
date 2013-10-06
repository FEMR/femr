package mock.femr.business.wrappers.sessions;

import femr.business.wrappers.sessions.ISessionHelper;

import java.util.HashMap;
import java.util.Map;

public class MockSessionHelper implements ISessionHelper {

    public Map<String, String> session = new HashMap<String, String>();
    public String sessionKeyPassedIn;
    public int integerToReturn = 0;
    public boolean deleteWasCalled = false;

    @Override
    public int getInt(String sessionKey) {
        sessionKeyPassedIn = sessionKey;
        return integerToReturn;
    }

    @Override
    public void set(String sessionKey, String sessionObject) {
        session.put(sessionKey, sessionObject);
    }

    @Override
    public void delete(String sessionKey) {
        sessionKeyPassedIn = sessionKey;
        deleteWasCalled = true;
    }
}
