package mock.femr.business.services;

import femr.common.dto.CurrentUser;
import femr.common.dto.ServiceResponse;
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
