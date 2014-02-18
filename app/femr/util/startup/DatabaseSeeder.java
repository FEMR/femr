package femr.util.startup;

import com.avaje.ebean.Ebean;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.data.models.Role;
import femr.data.models.User;
import femr.util.calculations.dateUtils;
import femr.util.encryptions.BCryptPasswordEncryptor;
import femr.util.encryptions.IPasswordEncryptor;
import play.Play;

public class DatabaseSeeder {

    private final IRepository<User> userRepository;
    private final Repository<Role> roleRepository;

    public DatabaseSeeder() {
        userRepository = new Repository<User>();
        roleRepository = new Repository<Role>();
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
            adminUser.setLastLogin(dateUtils.getCurrentDateTime());
            adminUser.setDeleted(false);
            Role role = roleRepository.findOne(Ebean.find(Role.class).where().eq("name", "Administrator"));
            adminUser.addRole(role);

            userRepository.create(adminUser);
        }
    }
}
