package edu.wayne.femr.business.services;

import edu.wayne.femr.business.models.CurrentUser;
import edu.wayne.femr.business.models.ServiceResponse;

public interface ISessionService {
    ServiceResponse<CurrentUser> createSession(String email, String password);
    ServiceResponse<CurrentUser> getCurrentUserSession();
    void invalidateCurrentUserSession();
}
