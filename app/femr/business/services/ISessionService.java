package femr.business.services;

import femr.business.models.CurrentUser;
import femr.business.models.ServiceResponse;

public interface ISessionService {
    ServiceResponse<CurrentUser> createSession(String email, String password);
    ServiceResponse<CurrentUser> getCurrentUserSession();
    void invalidateCurrentUserSession();
}
