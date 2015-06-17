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
package femr.business.services.system;

import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.business.wrappers.sessions.ISessionHelper;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.ILoginAttempt;
import femr.data.models.core.IRole;
import femr.data.models.core.ISystemSetting;
import femr.data.models.core.IUser;
import femr.data.models.mysql.Role;
import femr.data.models.mysql.SystemSetting;
import femr.util.encryptions.IPasswordEncryptor;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class SessionService implements ISessionService {

    private IUserService userService;
    private IPasswordEncryptor passwordEncryptor;
    private ISessionHelper sessionHelper;
    private final IRepository<ILoginAttempt> loginAttemptRepository;
    private final IDataModelMapper dataModelMapper;
    private final IRepository<ISystemSetting> systemSettingRepository;
    private final IRepository<IRole> roleRepository;

    @Inject
    public SessionService(IUserService userService,
                          IPasswordEncryptor passwordEncryptor,
                          ISessionHelper sessionHelper,
                          IRepository<ILoginAttempt> loginAttemptRepository,
                          IDataModelMapper dataModelMapper,
                          IRepository<ISystemSetting> systemSettingRepository,
                          IRepository<IRole> roleRepository) {

        this.userService = userService;
        this.passwordEncryptor = passwordEncryptor;
        this.sessionHelper = sessionHelper;
        this.loginAttemptRepository = loginAttemptRepository;
        this.dataModelMapper = dataModelMapper;
        this.systemSettingRepository = systemSettingRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<CurrentUser> createSession(String email, String password, String ipAddress) {
        IUser userWithEmail = userService.retrieveByEmail(email);
        boolean isSuccessful = false;
        Integer userId = null;
        byte[] ipAddressBinary;
        try {

            ipAddressBinary = InetAddress.getByName(ipAddress).getAddress();
        } catch (Exception ex) {
            //if shit hits the fan, use an empty ip address
            ipAddressBinary = new byte[]{0, 0, 0, 0};
        }


        ServiceResponse<CurrentUser> response = new ServiceResponse<>();

        if (userWithEmail == null) {
            //user doesn't exist
            response.addError("", "Invalid email or password.");
        } else if (userWithEmail.getDeleted()) {
            //user has been deleted
            userId = userWithEmail.getId();//set the ID of the deleted user for the log
            response.addError("", "Invalid email or password.");
        } else if (!passwordEncryptor.verifyPassword(password, userWithEmail.getPassword())) {
            //wrong password
            userId = userWithEmail.getId();//set the ID of the deleted user for the log
            response.addError("", "Invalid email or password.");
        } else {
            //success!
            isSuccessful = true;
            userId = userWithEmail.getId();//set the ID of the deleted user for the log
            sessionHelper.set("currentUser", String.valueOf(userWithEmail.getId()));//initiate the session
            response.setResponseObject(createCurrentUser(userWithEmail));//send the user back in the response object
        }

        ILoginAttempt loginAttempt = dataModelMapper.createLoginAttempt(email, isSuccessful, ipAddressBinary, userId);
        loginAttemptRepository.create(loginAttempt);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrentUser retrieveCurrentUserSession() {

        int currentUserId = sessionHelper.getInt("currentUser");

        if (currentUserId > 0) {
            IUser userFoundById = userService.retrieveById(currentUserId);
            if (userFoundById == null) {
                return null;
            }

            return createCurrentUser(userFoundById);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidateCurrentUserSession() {
        sessionHelper.delete("currentUser");
    }

    private CurrentUser createCurrentUser(IUser user) {
        user = checkResearchFeature(user);
        return new CurrentUser(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoles());
    }

    /**
     * Checks the research setting and strips the user of roles if it is turned on.
     *
     * @param user the user being evaluated
     * @return the updated user
     */
    private IUser checkResearchFeature(IUser user) {

        //get the setting to check whether or not we need to restrict everyone to the research role
        ExpressionList<SystemSetting> settingQuery = QueryProvider.getSystemSettingQuery().where().eq("name", "Research Only");
        ISystemSetting systemSetting = systemSettingRepository.findOne(settingQuery);

        //if the research setting is active, we need to eliminiate all the roles except researcher and admin (if applicable)
        if (systemSetting.isActive()) {
            //Java 8 could make this way more succinct damnit
            List<? extends IRole> currentRoles = user.getRoles();
            //grab the researcher role
            ExpressionList<Role> researcherRoleQuery = QueryProvider.getRoleQuery().where().eq("name", "Researcher");
            IRole researcherRole = roleRepository.findOne(researcherRoleQuery);
            //grab the admin role
            ExpressionList<Role> adminRoleQuery = QueryProvider.getRoleQuery().where().eq("name", "Administrator");
            IRole adminRole = roleRepository.findOne(adminRoleQuery);
            //grab the superuser role
            ExpressionList<Role> superuserRoleQuery = QueryProvider.getRoleQuery().where().eq("name", "SuperUser");
            IRole superuserRole = roleRepository.findOne(superuserRoleQuery);
            List<IRole> newRoles = new ArrayList<>();
            //make the user a researcher
            newRoles.add(researcherRole);
            //if the user has the admin role, make them an admin as well
            if (currentRoles.contains(adminRole))
                newRoles.add(adminRole);
            if (currentRoles.contains(superuserRole))
                newRoles.add(superuserRole);
            user.setRoles(newRoles);
        }

        return user;
    }
}
