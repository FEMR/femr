package femr.business.wrappers.sessions;

import static play.mvc.Controller.session;

public class SessionHelper implements ISessionHelper {

    @Override
    public int getInt(String sessionKey) {
        return session(sessionKey) != null ? Integer.parseInt(session(sessionKey)) : 0;
    }

    @Override
    public void set(String sessionKey, String sessionObject) {
        session(sessionKey, sessionObject);
    }

    @Override
    public void delete(String sessionKey) {
        session().remove(sessionKey);
    }
}
