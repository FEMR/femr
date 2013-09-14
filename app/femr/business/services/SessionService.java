package femr.business.services;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IUser;
import femr.util.encryptions.IPasswordEncryptor;
import femr.util.stringhelpers.StringUtils;
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
        ServiceResponse<IUser> userWithEmail = userService.findByEmail(email);
        ServiceResponse<CurrentUser> response = new ServiceResponse<>();

        if (userWithEmail.isNullResponse()) {
            return invalidCredentials(response);
        }

        IUser user = userWithEmail.getResponseObject();
        if (!passwordEncryptor.verifyPassword(password, user.getPassword())) {
            return invalidCredentials(response);
        }

        session("currentUser", String.valueOf(user.getId()));
        response.setResponseObject(createCurrentUser(user));

        return response;
    }

    private ServiceResponse<CurrentUser> invalidCredentials(ServiceResponse<CurrentUser> response) {
        response.setSuccessful(false);
        response.addError("global", "Invalid email or password.");
        return response;
    }

    @Override
    public ServiceResponse<CurrentUser> getCurrentUserSession() {
        ServiceResponse<CurrentUser> response = new ServiceResponse<>();
        String currentUserIdString = session("currentUser");

        if (StringUtils.isNotNullOrWhiteSpace(currentUserIdString)) {
            try {
                int currentUserId = Integer.parseInt(currentUserIdString);
                ServiceResponse<IUser> findByIdResponse = userService.findById(currentUserId);

                if (!findByIdResponse.isNullResponse()) {
                    IUser user = findByIdResponse.getResponseObject();
                    response.setResponseObject(createCurrentUser(user));

                    return response;
                }
            } catch (Exception exception) {
                Logger.warn("INVALID USER ID STORED IN SESSION");
            }
        }

        response.setSuccessful(false);

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
