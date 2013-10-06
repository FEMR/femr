package femr.business.services;

import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;

public interface ISessionService {
    ServiceResponse<CurrentUser> createSession(String email, String password);

    CurrentUser getCurrentUserSession();

    void invalidateCurrentUserSession();
}
