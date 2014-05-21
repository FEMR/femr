package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.IRole;
import femr.common.models.IUser;
import femr.data.daos.IRepository;
import femr.data.models.User;
import femr.util.encryptions.IPasswordEncryptor;

import java.util.List;

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
    public IUser findByEmail(String email) {
        ExpressionList<User> query = getQuery().fetch("roles").where().eq("email", email);

        return userRepository.findOne(query);
    }

    @Override
    public IUser findById(int id) {
        ExpressionList<User> query = getQuery().fetch("roles").where().eq("id", id);

        return userRepository.findOne(query);
    }

    @Override
    public ServiceResponse<List<? extends IUser>> findAllUsers(){

        ExpressionList<User> query = getQuery()
                .fetch("roles")
                .where()
                .ne("roles.name", "SuperUser");
        List<? extends IUser> users = userRepository.find(query);
        ServiceResponse<List<? extends IUser>> response = new ServiceResponse<>();
        if (users.size() > 0){
            response.setResponseObject(users);
        }
        else{
            response.addError("users","could not find any users");
        }
        return response;
    }

    @Override
    public List<? extends IRole> findRolesForUser(int id) {
        ExpressionList<User> query = getQuery().fetch("roles").where().eq("id", id);
        IUser user = userRepository.findOne(query);
        return user.getRoles();
    }

    @Override
    public ServiceResponse<IUser> update(IUser currentUser, Boolean isNewPassword){
        ServiceResponse<IUser> response = new ServiceResponse<>();
        if (isNewPassword){
            encryptAndSetUserPassword(currentUser);
        }

        currentUser = userRepository.update(currentUser);
        if (currentUser != null){
            response.setResponseObject(currentUser);
        }
        else{
            response.addError("","Could not update user");
        }
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
        IUser existingUser = findByEmail(email);

        if (existingUser != null) {
            response.addError("", "User with email exists.");
            return true;
        }
        return false;
    }
}