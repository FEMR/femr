package femr.business.wrappers.sessions;

public interface ISessionHelper {
    int getInt(String sessionKey);
    void set(String sessionKey, String sessionObject);
    void delete(String sessionKey);
}
