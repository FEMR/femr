package edu.wayne.femr.business.services;

import com.google.inject.Inject;
import edu.wayne.femr.data.models.User;

public class SessionService implements ISessionService {

    private IUserService userService;

    @Inject
    public SessionService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public User createSession(String email, String password) {
        User user = userService.findByEmail(email);

        return user;
    }
}
