package mock.edu.wayne.femr.business.services;

import edu.wayne.femr.business.models.ServiceResponse;
import edu.wayne.femr.business.services.ISessionService;
import edu.wayne.femr.data.models.User;

public class MockSessionsService implements ISessionService {
    public boolean createSessionWasCalled = false;

    @Override
    public ServiceResponse<User> createSession(String email, String password) {
        return null;
    }
}
