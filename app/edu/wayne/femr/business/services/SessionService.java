package edu.wayne.femr.business.services;

import com.google.inject.Inject;
import edu.wayne.femr.business.models.ServiceResponse;
import edu.wayne.femr.data.models.User;
import edu.wayne.femr.util.encryption.IPasswordEncryptor;
import play.Logger;

import static play.mvc.Controller.session;

public class SessionService implements ISessionService {

    private IUserService userService;
    private IPasswordEncryptor passwordEncryptor;

    @Inject
    public SessionService(IUserService userService, IPasswordEncryptor passwordEncryptor) {
        this.userService = userService;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public ServiceResponse<User> createSession(String email, String password) {
        User user = userService.findByEmail(email);
        ServiceResponse<User> response = new ServiceResponse<User>();

        if (user == null || !passwordEncryptor.verifyPassword(password, user.getPassword())) {
            response.setValid(false);
            response.addError("", "Invalid email or password.");
            return response;
        }

        session("currentUser", String.valueOf(user.getId()));

        response.setResponseObject(user);

        return response;
    }

    @Override
    public ServiceResponse<User> getCurrentUserSession() {
        ServiceResponse<User> response = new ServiceResponse<>();
        String currentUserIdString = session("currentUser");

        if (currentUserIdString != null || currentUserIdString == "") {
            try {
                int currentUserId = Integer.parseInt(currentUserIdString);
                User currentUser = userService.findById(currentUserId);
                if (currentUser != null) {
                    response.setResponseObject(currentUser);
                    return response;
                }
            } catch (Exception exception) {
                Logger.warn("INVALID USER ID STORED IN SESSION");
            }
        }

        response.setValid(false);

        return response;
    }

    @Override
    public void invalidateCurrentUserSession() {
        session().remove("currentUser");
    }
}
