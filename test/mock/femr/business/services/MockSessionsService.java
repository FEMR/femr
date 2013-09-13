package mock.femr.business.services;

import femr.business.models.CurrentUser;
import femr.business.models.ServiceResponse;
import femr.business.services.ISessionService;

public class MockSessionsService implements ISessionService {
    public boolean createSessionWasCalled = false;

    @Override
    public ServiceResponse<CurrentUser> createSession(String email, String password) {
        return null;
    }

    @Override
    public ServiceResponse<CurrentUser> getCurrentUserSession() {
        return null;
    }

    @Override
    public void invalidateCurrentUserSession() {
    }
}
