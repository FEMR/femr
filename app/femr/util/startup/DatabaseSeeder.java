package femr.util.startup;

import com.avaje.ebean.Ebean;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.data.models.*;
import femr.util.calculations.dateUtils;
import femr.util.encryptions.BCryptPasswordEncryptor;
import femr.util.encryptions.IPasswordEncryptor;
import play.Play;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSeeder {

    private final IRepository<User> userRepository;
    private final Repository<Role> roleRepository;
    private final Repository<SystemSetting> systemSettingRepository;
    private final Repository<TabFieldSize> tabFieldSizeRepository;
    private final Repository<TabFieldType> tabFieldTypeRepository;

    public DatabaseSeeder() {
        userRepository = new Repository<>();
        roleRepository = new Repository<>();
        systemSettingRepository = new Repository<>();
        tabFieldSizeRepository = new Repository<>();
        tabFieldTypeRepository = new Repository<>();
    }

    public void seed() {
        seedSystemSettings();
        seedAdminUser();
        seedTabFields();
    }

    /**
     * Seed available system settings
     */
    private void seedSystemSettings(){
        int settingsCount = systemSettingRepository.count(SystemSetting.class);
        if (settingsCount == 0){
            SystemSetting systemSetting = new SystemSetting();
            systemSetting.setActive(false);
            systemSetting.setName("Multiple chief complaints");
            systemSettingRepository.create(systemSetting);
            systemSetting = new SystemSetting();
            systemSetting.setActive(true);
            systemSetting.setName("Medical PMH Tab");
            systemSettingRepository.create(systemSetting);
            systemSetting = new SystemSetting();
            systemSetting.setName("Medical Photo Tab");
            systemSettingRepository.create(systemSetting);
        }
    }

    /**
     * Seed initial tab field values
     */
    private void seedTabFields() {
        int sizeCount = tabFieldSizeRepository.count(TabFieldSize.class);
        if (sizeCount == 0) {
            List<TabFieldSize> tabFieldSizes = new ArrayList<>();
            TabFieldSize tabFieldSize = new TabFieldSize();
             //not using small right now
//            tabFieldSize.setName("small");
//            tabFieldSizes.add(tabFieldSize);

            tabFieldSize = new TabFieldSize();
            tabFieldSize.setName("medium");
            tabFieldSizes.add(tabFieldSize);

            tabFieldSize = new TabFieldSize();
            tabFieldSize.setName("large");
            tabFieldSizes.add(tabFieldSize);

            tabFieldSizeRepository.createAll(tabFieldSizes);
        }
        sizeCount = tabFieldTypeRepository.count(TabFieldType.class);
        if (sizeCount == 0){
            List<TabFieldType> tabFieldTypes = new ArrayList<>();

            TabFieldType tabFieldType = new TabFieldType();
            tabFieldType.setName("text");
            tabFieldTypes.add(tabFieldType);

            //not using numbers right now
//            tabFieldType = new TabFieldType();
//            tabFieldType.setName("number");
//            tabFieldTypes.add(tabFieldType);

            tabFieldTypeRepository.createAll(tabFieldTypes);
        }

    }

    /**
     * Seed the admin user from the configuration file
     * and the super user information.
     */
    private void seedAdminUser() {
        int userCount = userRepository.count(User.class);

        if (userCount == 0) {
            String defaultAdminUsername = Play.application().configuration().getString("default.admin.username");
            String defaultAdminPassword = Play.application().configuration().getString("default.admin.password");
            IPasswordEncryptor encryptor = new BCryptPasswordEncryptor();

            //create the Admin user
            //Admin is used for managing users, creating users, managing inventory, etc
            //Admin information is given to the manager/group leader/whoever is in charge
            User adminUser = new User();
            String encryptedPassword = encryptor.encryptPassword(defaultAdminPassword);
            adminUser.setFirstName("Administrator");
            adminUser.setLastName("");
            adminUser.setEmail(defaultAdminUsername);
            adminUser.setPassword(encryptedPassword);
            adminUser.setLastLogin(dateUtils.getCurrentDateTime());
            adminUser.setDeleted(false);
            Role role = roleRepository.findOne(Ebean.find(Role.class).where().eq("name", "Administrator"));
            adminUser.addRole(role);
            adminUser.setPasswordReset(false);
            userRepository.create(adminUser);

            //SuperUser is currently only used for managing dynamic tabs on the medical page
            //SuperUser is a "backdoor" account that gives access to important configuration
            //settings
            User superUser = new User();
            String encryptedSuperUserPassword = encryptor.encryptPassword("wsu1f8e6m8r");
            superUser.setFirstName("SuperUser");
            superUser.setLastName("");
            superUser.setEmail("superuser");
            superUser.setPassword(encryptedSuperUserPassword);
            superUser.setLastLogin(dateUtils.getCurrentDateTime());
            superUser.setDeleted(false);
            Role role1 = roleRepository.findOne(Ebean.find(Role.class).where().eq("name", "SuperUser"));
            superUser.addRole(role1);
            superUser.setPasswordReset(false);
            userRepository.create(superUser);
        }
    }
}
