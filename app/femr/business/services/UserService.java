package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IUser;
import femr.data.daos.IRepository;
import femr.data.models.User;
import femr.util.encryptions.IPasswordEncryptor;

public class UserService implements IUserService {

    private IRepository<IUser> userRepository;
    private IPasswordEncryptor passwordEncryptor;

    @Inject
    public UserService(IRepository<IUser> userRepository, IPasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public ServiceResponse<IUser> createUser(IUser user) {
        ServiceResponse<IUser> response = new ServiceResponse<>();
        encryptAndSetUserPassword(user);

        if (userExistsWithEmail(user.getEmail(), response)) {
            return response;
        }

        IUser newUser = userRepository.create(user);
        response.setResponseObject(newUser);

        return response;
    }

    @Override
    public ServiceResponse<IUser> findByEmail(String email) {
        ServiceResponse<IUser> response = new ServiceResponse<>();
        ExpressionList<User> query = getQuery().fetch("roles").where().eq("email", email);

        IUser user = userRepository.findOne(query);
        response.setResponseObject(user);

        return response;
    }

    @Override
    public ServiceResponse<IUser> findById(int id) {
        ServiceResponse<IUser> response = new ServiceResponse<>();
        ExpressionList<User> query = getQuery().fetch("roles").where().eq("id", id);

        IUser user = userRepository.findOne(query);
        response.setResponseObject(user);

        return response;
    }

    private Query<User> getQuery() {
        return Ebean.find(User.class);
    }

    private void encryptAndSetUserPassword(IUser user) {
        String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());

        user.setPassword(encryptedPassword);
    }

    private boolean userExistsWithEmail(String email, ServiceResponse<IUser> response) {
        ServiceResponse<IUser> existingUserResponse = findByEmail(email);

        if (!existingUserResponse.isNullResponse()) {
            response.setSuccessful(false);
            response.addError("global", "User with email exists.");
            return true;
        }
        return false;
    }
}