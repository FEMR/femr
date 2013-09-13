package femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
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
    public IUser createUser(IUser user) {
        encryptAndSetUserPassword(user);

        return userRepository.create(user);
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

    private void encryptAndSetUserPassword(IUser user) {
        String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());

        user.setPassword(encryptedPassword);
    }
}