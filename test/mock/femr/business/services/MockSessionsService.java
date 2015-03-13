package mock.femr.business.services;

import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.business.services.core.ISessionService;

public class MockSessionsService implements ISessionService {
    public boolean createSessionWasCalled = false;

    @Override
    public ServiceResponse<CurrentUser> createSession(String email, String password) {
        return null;
    }

    @Override
    public CurrentUser retrieveCurrentUserSession() {
        return null;
    }

    @Override
    public void invalidateCurrentUserSession() {
    }
}
