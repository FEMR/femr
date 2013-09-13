package edu.wayne.femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import edu.wayne.femr.data.daos.IRepository;
import edu.wayne.femr.data.models.IUser;
import edu.wayne.femr.data.models.User;
import edu.wayne.femr.util.encryption.IPasswordEncryptor;

public class UserService implements IUserService {

    private IRepository<IUser> userRepository;
    private IPasswordEncryptor passwordEncryptor;

    @Inject
    public UserService(IRepository<IUser> userRepository, IPasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public IUser createUser(String firstName, String lastName, String email, String password) {
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        User newUser = new User(firstName, lastName, email, encryptedPassword);

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
}