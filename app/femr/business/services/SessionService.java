package femr.business.services;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.wrappers.sessions.ISessionHelper;
import femr.common.models.IUser;
import femr.data.daos.IRepository;
import femr.util.encryptions.IPasswordEncryptor;
import org.joda.time.DateTime;

//import static play.mvc.Controller.session;

public class SessionService implements ISessionService {

    private IUserService userService;
    private IPasswordEncryptor passwordEncryptor;
    private ISessionHelper sessionHelper;
    private IRepository<IUser> userRepository;

    @Inject
    public SessionService(IUserService userService, IPasswordEncryptor passwordEncryptor,
                          ISessionHelper sessionHelper, IRepository<IUser> userRepository) {
        this.userService = userService;
        this.passwordEncryptor = passwordEncryptor;
        this.sessionHelper = sessionHelper;
        this.userRepository = userRepository;
    }

    @Override
    public ServiceResponse<CurrentUser> createSession(String email, String password) {
        IUser userWithEmail = userService.findByEmail(email);
        ServiceResponse<CurrentUser> response = new ServiceResponse<>();

        if (userWithEmail == null || !passwordEncryptor.verifyPassword(password, userWithEmail.getPassword())) {
            response.addError("", "Invalid email or password.");
            return response;
        }

        userWithEmail.setLastLogin(DateTime.now());
        userService.update(userWithEmail);

        sessionHelper.set("currentUser", String.valueOf(userWithEmail.getId()));
        response.setResponseObject(createCurrentUser(userWithEmail));

        return response;
    }

    @Override
    public CurrentUser getCurrentUserSession() {
        int currentUserId = sessionHelper.getInt("currentUser");

        if (currentUserId > 0) {
            IUser userFoundById = userService.findById(currentUserId);

            return createCurrentUser(userFoundById);
        }

        return null;
    }

    @Override
    public void invalidateCurrentUserSession() {
        sessionHelper.delete("currentUser");
    }

    private CurrentUser createCurrentUser(IUser user) {
        return new CurrentUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoles());
    }
}
