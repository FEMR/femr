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

    private final Repository<MedicationMeasurementUnit> medicationMeasurementUnitRepository;
    private final Repository<MedicationForm> medicationFormRepository;
    private final IRepository<User> userRepository;
    private final Repository<Role> roleRepository;
    private final Repository<SystemSetting> systemSettingRepository;
    private final Repository<TabField> tabFieldRepository;
    private final Repository<TabFieldSize> tabFieldSizeRepository;
    private final Repository<TabFieldType> tabFieldTypeRepository;
    private final Repository<Tab> tabRepository;

    public DatabaseSeeder() {
        medicationMeasurementUnitRepository = new Repository<>();
        medicationFormRepository = new Repository<>();
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
        seedMedicationUnits();
        seedMedicationForms();
    }

    private void seedMedicationForms() {

        List<? extends IMedicationForm> medicationForms = medicationFormRepository.findAll(MedicationForm.class);

        List<MedicationForm> newMedicationForms = new ArrayList<>();
        MedicationForm medicationForm;
        if (medicationForms != null && !containForm(medicationForms, "B/S")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("B/S");
            medicationForm.setDescription("bite and swallow");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "caps")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("caps");
            medicationForm.setDescription("capsules");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "crm")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("crm");
            medicationForm.setDescription("cream");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "elix")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("elix");
            medicationForm.setDescription("elixir");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "gtts")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("gtts");
            medicationForm.setDescription("drops");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "MDI")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("MDI");
            medicationForm.setDescription("metered dose inhaler");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "nebs")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("nebs");
            medicationForm.setDescription("solution for nebulization");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "NPO")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("NPO");
            medicationForm.setDescription("nothing by mouth");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "PO")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("PO");
            medicationForm.setDescription("by mouth, orally , or swallowed");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "PR")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("PR");
            medicationForm.setDescription("suppository");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "SL")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("SL");
            medicationForm.setDescription("sublingual form");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "soln")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("soln");
            medicationForm.setDescription("solution");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "supp")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("supp");
            medicationForm.setDescription("suppository");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "susp")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("susp");
            medicationForm.setDescription("suspension");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "syr")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("syr");
            medicationForm.setDescription("syrup");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "tabs")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("tabs");
            medicationForm.setDescription("tablets");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        if (medicationForms != null && !containForm(medicationForms, "ung")) {
            medicationForm = new MedicationForm();
            medicationForm.setName("ung");
            medicationForm.setDescription("ointment");
            medicationForm.setIsDeleted(false);
            newMedicationForms.add(medicationForm);
        }
        medicationFormRepository.createAll(newMedicationForms);
    }

    private void seedMedicationUnits() {
        List<? extends IMedicationMeasurementUnit> medicationUnits = medicationMeasurementUnitRepository.findAll(MedicationMeasurementUnit.class);

        List<MedicationMeasurementUnit> newMedicationMeasurementUnits = new ArrayList<>();
        MedicationMeasurementUnit medicationMeasurementUnit;
        if (medicationUnits != null && !containUnit(medicationUnits, "%")) {
            medicationMeasurementUnit = new MedicationMeasurementUnit();
            medicationMeasurementUnit.setName("%");
            medicationMeasurementUnit.setDescription("g/dL");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "g")) {
            medicationMeasurementUnit = new MedicationMeasurementUnit();
            medicationMeasurementUnit.setName("g");
            medicationMeasurementUnit.setDescription("gram");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "gr")) {
            medicationMeasurementUnit = new MedicationMeasurementUnit();
            medicationMeasurementUnit.setName("gr");
            medicationMeasurementUnit.setDescription("grain");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "IU")) {
            medicationMeasurementUnit = new MedicationMeasurementUnit();
            medicationMeasurementUnit.setName("IU");
            medicationMeasurementUnit.setDescription("international units");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "mg")) {
            medicationMeasurementUnit = new MedicationMeasurementUnit();
            medicationMeasurementUnit.setName("mg");
            medicationMeasurementUnit.setDescription("milligram");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "U")) {
            medicationMeasurementUnit = new MedicationMeasurementUnit();
            medicationMeasurementUnit.setName("U");
            medicationMeasurementUnit.setDescription("unit");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        medicationMeasurementUnitRepository.createAll(newMedicationMeasurementUnits);
    }


    /**
     * Seed available system settings
     */
    private void seedSystemSettings() {
        List<? extends ISystemSetting> systemSettings = systemSettingRepository.findAll(SystemSetting.class);

        SystemSetting systemSetting;
        if (systemSettings != null && !containSetting(systemSettings, "Multiple chief complaints")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Multiple chief complaints");
            systemSetting.setActive(false);
            systemSettingRepository.create(systemSetting);
        }
        if (systemSettings != null && !containSetting(systemSettings, "Medical PMH Tab")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Medical PMH Tab");
            systemSetting.setActive(true);
            systemSettingRepository.create(systemSetting);
        }
        if (systemSettings != null && !containSetting(systemSettings, "Medical Photo Tab")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Medical Photo Tab");
            systemSetting.setActive(true);
            systemSettingRepository.create(systemSetting);
        }
        if (systemSettings != null && !containSetting(systemSettings, "Medical HPI Consolidate")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Medical HPI Consolidate");
            systemSetting.setActive(false);
            systemSettingRepository.create(systemSetting);
        }
    }

    private static boolean containForm(List<? extends IMedicationForm> medicationForms, String form) {
        for (IMedicationForm mf : medicationForms) {
            if (mf.getName().equals(form)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containUnit(List<? extends IMedicationMeasurementUnit> medicationMeasurementUnits, String unit) {
        for (IMedicationMeasurementUnit mmu : medicationMeasurementUnits) {
            if (mmu.getName().equals(unit)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containSetting(List<? extends ISystemSetting> systemSettings, String setting) {
        for (ISystemSetting ss : systemSettings) {
            if (ss.getName().equals(setting)) {
                return true;
            }
        }
        return false;
    }


    private void seedDefaultTabFields() {
        int sizeCount = tabFieldRepository.count(TabField.class);
        if (sizeCount == 0) {
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


    private void seedDefaultTabNames() {
        int sizeCount = tabRepository.count(Tab.class);
        if (sizeCount == 0) {
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
        if (sizeCount == 0) {
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
