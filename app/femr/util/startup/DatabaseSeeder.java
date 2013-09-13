package femr.util.startup;

import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.data.models.User;
import femr.util.encryption.BCryptPasswordEncryptor;
import femr.util.encryption.IPasswordEncryptor;
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
        int userCount = userRepository.count(User.class);

        if (userCount == 0) {
            String defaultAdminUsername = Play.application().configuration().getString("default.admin.username");
            String defaultAdminPassword = Play.application().configuration().getString("default.admin.password");

            IPasswordEncryptor encryptor = new BCryptPasswordEncryptor();
            String encryptedPassword = encryptor.encryptPassword(defaultAdminPassword);

            User adminUser = new User();
            adminUser.setFirstName("Administrator");
            adminUser.setLastName("");
            adminUser.setEmail(defaultAdminUsername);
            adminUser.setPassword(encryptedPassword);

            userRepository.create(adminUser);
        }
    }
}
