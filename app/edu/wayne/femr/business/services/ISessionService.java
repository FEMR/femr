package edu.wayne.femr.business.services;

import edu.wayne.femr.business.models.ServiceResponse;
import edu.wayne.femr.data.models.User;

public interface ISessionService {
    ServiceResponse<User> createSession(String email, String password);
    ServiceResponse<User> getCurrentUserSession();
    void invalidateCurrentUserSession();
}
