package edu.wayne.femr.util.startup;

import edu.wayne.femr.data.daos.IRepository;
import edu.wayne.femr.data.daos.Repository;
import edu.wayne.femr.data.models.User;
import edu.wayne.femr.util.encryption.BCryptPasswordEncryptor;
import edu.wayne.femr.util.encryption.IPasswordEncryptor;
import play.Play;

public class DatabaseSeeder {

    private final IRepository<User> userRepository;

    public DatabaseSeeder() {
        userRepository = new Repository<User>();
    }

    public void seed() {
        seedAdminUser();
    }

    private void seedAdminUser() {
        int userCount = userRepository.count();

        if (userCount == 0) {
            String defaultAdminUsername = Play.application().configuration().getString("default.admin.username");
            String defaultAdminPassword = Play.application().configuration().getString("default.admin.password");

            IPasswordEncryptor encryptor = new BCryptPasswordEncryptor();
            String encryptedPassword = encryptor.encryptPassword(defaultAdminPassword);

            User adminUser = new User("admin", "admin", defaultAdminUsername, encryptedPassword);

            userRepository.create(adminUser);
        }
    }
}
