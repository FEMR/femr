package femr.business.services;

import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;

public interface ISessionService {
    ServiceResponse<CurrentUser> createSession(String email, String password);

    CurrentUser getCurrentUserSession();

    void invalidateCurrentUserSession();
}
