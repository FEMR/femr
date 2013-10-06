package mock.femr.business.services;

import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISessionService;

public class MockSessionsService implements ISessionService {
    public boolean createSessionWasCalled = false;

    @Override
    public ServiceResponse<CurrentUser> createSession(String email, String password) {
        return null;
    }

    @Override
    public CurrentUser getCurrentUserSession() {
        return null;
    }

    @Override
    public void invalidateCurrentUserSession() {
    }
}
