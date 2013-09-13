package femr.business.services;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IUser;
import femr.util.encryptions.IPasswordEncryptor;
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
    public ServiceResponse<CurrentUser> createSession(String email, String password) {
        IUser user = userService.findByEmail(email);
        ServiceResponse<CurrentUser> response = new ServiceResponse<>();

        if (user == null || !passwordEncryptor.verifyPassword(password, user.getPassword())) {
            response.setValid(false);
            response.addError("", "Invalid email or password.");
            return response;
        }

        session("currentUser", String.valueOf(user.getId()));

        response.setResponseObject(createCurrentUser(user));

        return response;
    }

    @Override
    public ServiceResponse<CurrentUser> getCurrentUserSession() {
        ServiceResponse<CurrentUser> response = new ServiceResponse<>();
        String currentUserIdString = session("currentUser");

        if (currentUserIdString != null || currentUserIdString == "") {
            try {
                int currentUserId = Integer.parseInt(currentUserIdString);
                IUser currentUser = userService.findById(currentUserId);

                if (currentUser != null) {
                    response.setResponseObject(createCurrentUser(currentUser));
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

    private CurrentUser createCurrentUser(IUser user) {
        return new CurrentUser(user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
