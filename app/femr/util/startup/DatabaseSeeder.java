package femr.util.startup;

import com.avaje.ebean.Ebean;
import femr.common.models.custom.ICustomFieldSize;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.data.models.Role;
import femr.data.models.User;
import femr.data.models.custom.CustomFieldSize;
import femr.data.models.custom.CustomFieldType;
import femr.util.calculations.dateUtils;
import femr.util.encryptions.BCryptPasswordEncryptor;
import femr.util.encryptions.IPasswordEncryptor;
import play.Play;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSeeder {

    private final IRepository<User> userRepository;
    private final Repository<Role> roleRepository;
    private final Repository<CustomFieldSize> customFieldSizeRepository;
    private final Repository<CustomFieldType> customFieldTypeRepository;

    public DatabaseSeeder() {
        userRepository = new Repository<>();
        roleRepository = new Repository<>();
        customFieldSizeRepository = new Repository<>();
        customFieldTypeRepository = new Repository<>();
    }

    public void seed() {
        seedAdminUser();
        seedCustomFields();
    }

    private void seedCustomFields() {
        int sizeCount = customFieldSizeRepository.count(CustomFieldSize.class);
        if (sizeCount == 0) {
            List<CustomFieldSize> customFieldSizes = new ArrayList<>();

            CustomFieldSize customFieldSize = new CustomFieldSize();
            customFieldSize.setName("small");
            customFieldSizes.add(customFieldSize);

            customFieldSize = new CustomFieldSize();
            customFieldSize.setName("medium");
            customFieldSizes.add(customFieldSize);

            customFieldSize = new CustomFieldSize();
            customFieldSize.setName("large");
            customFieldSizes.add(customFieldSize);

            customFieldSizeRepository.createAll(customFieldSizes);
        }
        sizeCount = customFieldTypeRepository.count(CustomFieldType.class);
        if (sizeCount == 0){
            List<CustomFieldType> customFieldTypes = new ArrayList<>();

            CustomFieldType customFieldType = new CustomFieldType();
            customFieldType.setName("text");
            customFieldTypes.add(customFieldType);

            customFieldType = new CustomFieldType();
            customFieldType.setName("number");
            customFieldTypes.add(customFieldType);

            customFieldTypeRepository.createAll(customFieldTypes);
        }

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
            adminUser.setPasswordReset(false);


            userRepository.create(adminUser);
        }
    }
}
