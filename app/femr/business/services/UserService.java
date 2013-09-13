package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.Provider;
import femr.data.daos.IRepository;
import femr.data.models.IUser;
import femr.data.models.User;
import femr.util.encryption.IPasswordEncryptor;

public class UserService implements IUserService {

    private IRepository<IUser> userRepository;
    private IPasswordEncryptor passwordEncryptor;
    private Provider<IUser> userProvider;

    @Inject
    public UserService(IRepository<IUser> userRepository, IPasswordEncryptor passwordEncryptor,
                       Provider<IUser> userProvider) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
        this.userProvider = userProvider;
    }

    @Override
    public IUser createUser(String firstName, String lastName, String email, String password) {
        IUser newUser = encryptPasswordAndCreateUser(firstName, lastName, email, password);

        return userRepository.create(newUser);
    }

    @Override
    public IUser findByEmail(String email) {
        ExpressionList<User> query = getQuery().where().eq("email", email);

        return userRepository.findOne(query);
    }

    @Override
    public IUser findById(int id) {
        ExpressionList<User> query = getQuery().where().eq("id", id);

        return userRepository.findOne(query);
    }

    private Query<User> getQuery() {
        return Ebean.find(User.class);
    }

    private IUser encryptPasswordAndCreateUser(String firstName, String lastName, String email, String password) {
        String encryptedPassword = passwordEncryptor.encryptPassword(password);

        IUser newUser = userProvider.get();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(encryptedPassword);

        return newUser;
    }
}