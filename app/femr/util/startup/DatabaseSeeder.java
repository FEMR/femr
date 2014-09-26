/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
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
    private final Repository<TabField> tabFieldRepository;
    private final Repository<TabFieldSize> tabFieldSizeRepository;
    private final Repository<TabFieldType> tabFieldTypeRepository;
    private final Repository<Tab> tabRepository;

    public DatabaseSeeder() {
        userRepository = new Repository<>();
        roleRepository = new Repository<>();
        systemSettingRepository = new Repository<>();
        tabFieldRepository = new Repository<>();
        tabFieldSizeRepository = new Repository<>();
        tabFieldTypeRepository = new Repository<>();
        tabRepository = new Repository<>();
    }

    public void seed() {
        seedSystemSettings();
        seedAdminUser();
        seedDefaultTabNames();
        seedTabFieldTypesAndSizes();
        seedDefaultTabFields();
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


    private void seedDefaultTabFields(){
        int sizeCount = tabFieldRepository.count(TabField.class);
        if (sizeCount == 0){
            List<TabField> tabFields = new ArrayList<>();
            TabField tabField = new TabField();
            tabField.setName("onset");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 1));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("severity");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 1));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 2));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("radiation");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 1));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("quality");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 1));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("provokes");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 1));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("palliates");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 1));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("timeOfDay");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 1));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("physicalExamination");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 1));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("narrative");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 1));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("assessment");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 3));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("problem");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 3));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("treatment");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 3));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("medicalSurgicalHistory");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 2));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("socialHistory");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 2));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("currentMedication");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 2));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabField = new TabField();
            tabField.setName("familyHistory");
            tabField.setIsDeleted(false);
            tabField.setTab(Ebean.getReference(Tab.class, 2));
            tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, 1));
            tabFields.add(tabField);

            tabFieldRepository.createAll(tabFields);
        }

    }


    private void seedDefaultTabNames(){
        int sizeCount = tabRepository.count(Tab.class);
        if (sizeCount == 0){
            List<Tab> tabs = new ArrayList<>();
            Tab tab = new Tab();
            tab.setName("HPI");
            tab.setId(1);
            tab.setIsDeleted(false);
            tab.setDateCreated(dateUtils.getCurrentDateTime());
            tab.setUserId(null);
            tab.setLeftColumnSize(2);
            tab.setRightColumnSize(2);
            tab.setIsCustom(false);
            tabs.add(tab);

            tab = new Tab();
            tab.setName("PMH");
            tab.setId(2);
            tab.setIsDeleted(false);
            tab.setDateCreated(dateUtils.getCurrentDateTime());
            tab.setUserId(null);
            tab.setLeftColumnSize(0);
            tab.setRightColumnSize(0);
            tab.setIsCustom(false);
            tabs.add(tab);

            tab = new Tab();
            tab.setName("Treatment");
            tab.setId(3);
            tab.setIsDeleted(false);
            tab.setDateCreated(dateUtils.getCurrentDateTime());
            tab.setUserId(null);
            tab.setLeftColumnSize(0);
            tab.setRightColumnSize(0);
            tab.setIsCustom(false);
            tabs.add(tab);

            tab = new Tab();
            tab.setName("Photos");
            tab.setId(4);
            tab.setIsDeleted(false);
            tab.setDateCreated(dateUtils.getCurrentDateTime());
            tab.setUserId(null);
            tab.setLeftColumnSize(0);
            tab.setRightColumnSize(0);
            tab.setIsCustom(false);
            tabs.add(tab);

            tabRepository.createAll(tabs);
        }

    }

    /**
     * Seed initial tab field values
     */
    private void seedTabFieldTypesAndSizes() {
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
            tabFieldType.setId(1);
            tabFieldTypes.add(tabFieldType);

            tabFieldType = new TabFieldType();
            tabFieldType.setName("number");
            tabFieldType.setId(2);
            tabFieldTypes.add(tabFieldType);

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
