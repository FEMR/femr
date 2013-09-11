package edu.wayne.femr.business.services;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import edu.wayne.femr.data.daos.IRepository;
import edu.wayne.femr.data.models.User;
import edu.wayne.femr.util.encryption.IPasswordEncryptor;

public class UserService implements IUserService {

    private IRepository<User> userRepository;
    private IPasswordEncryptor passwordEncryptor;

    @Inject
    public UserService(IRepository<User> userRepository, IPasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password) {
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        User newUser = new User(firstName, lastName, email, encryptedPassword);

        return userRepository.create(newUser);
    }

    @Override
    public User findByEmail(String email) {
        ExpressionList<User> query = Ebean.find(User.class).where().eq("email", email);

        return userRepository.findOne(query);
    }
}