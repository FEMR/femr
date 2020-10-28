package femr.data.daos.system;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.ILoginAttempt;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import femr.data.models.mysql.Role;
import femr.data.models.mysql.User;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import play.Logger;

import javax.inject.Provider;
import java.util.List;

/**
 * Created by ajsaclayan on 11/20/16.
 */
public class UserRepository implements IUserRepository {

    private final Provider<ILoginAttempt> loginAttemptProvider;
    private final Provider<IRole> roleProvider;
    private final Provider<IUser> userProvider;

    @Inject
    public UserRepository(Provider<ILoginAttempt> loginAttemptProvider,
                          Provider<IUser> userProvider,
                          Provider<IRole> roleProvider) {

        this.loginAttemptProvider = loginAttemptProvider;
        this.roleProvider = roleProvider;
        this.userProvider = userProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ILoginAttempt createLoginAttempt(String usernameValue, boolean isSuccessful, byte[] ipAddress, Integer userId) {

        ILoginAttempt loginAttempt = loginAttemptProvider.get();

        if (StringUtils.isNullOrWhiteSpace(usernameValue) || ipAddress == null) {

            return null;
        }

        try {

            loginAttempt.setLoginDate(dateUtils.getCurrentDateTime());
            loginAttempt.setIsSuccessful(isSuccessful);
            loginAttempt.setUsernameAttempt(usernameValue);
            loginAttempt.setIp_address(ipAddress);

            if (userId == null)
                loginAttempt.setUser(null);
            else
                loginAttempt.setUser(Ebean.getReference(userProvider.get().getClass(), userId));

            Ebean.save(loginAttempt);
        } catch (Exception ex) {

            Logger.error("UserRepository-createLoginAttempt", ex);
            throw ex;
        }

        return loginAttempt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRole createRole(int id, String name){

        if (StringUtils.isNullOrWhiteSpace(name)) {

            return null;
        }

        IRole role = roleProvider.get();
        role.setId(id);
        role.setName(name);

        try {

            Ebean.save(role);
        } catch (Exception ex) {

            Logger.error("UserRepository-createRole", ex);
            throw ex;
        }

        return role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser createUser(IUser user){
        try {
            Ebean.save(user);
        } catch (Exception ex) {

            Logger.error("UserRepository-createUser", ex);
            throw ex;
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer countUsers() {

        Integer count = 0;

        //get an empty user for ebean to specify the table
        IUser user = userProvider.get();

        try {

            count = Ebean.find(user.getClass()).findCount();
        } catch (Exception ex) {

            Logger.error("UserRepository-countUsers", ex);
            throw ex;
        }

        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser updateUser(IUser user){
        try {
            Ebean.update(user);
        } catch (Exception ex) {

            Logger.error("UserRepository-updateUser", ex);
            throw ex;
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser retrieveUserById(Integer userId){
        ExpressionList<User> userQuery = QueryProvider.getUserQuery().fetch("roles").where().eq("id", userId);

        IUser user = null;
        try {
            user = userQuery.findOne();
        } catch (Exception ex) {

            Logger.error("UserRepository-retrieveUserById", ex);
            throw ex;
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser retrieveUserByEmail(String email){
        ExpressionList<User> userQuery = QueryProvider.getUserQuery().fetch("roles").where().eq("email", email);

        IUser user = null;
        try{
            user = userQuery.findOne();
        } catch (Exception ex) {

            Logger.error("UserRepository-retrieveUserByEmail", ex);
            throw ex;
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<?extends IUser> retrieveAllUsers() {
        Query<User> userQuery = QueryProvider.getUserQuery()
                .where()
                .ne("email", "superuser")
                .ne("email", "admin")
                .orderBy("lastName");

        List<? extends IUser> users;
        try{
            users = userQuery.findList();
        }catch(Exception ex){

            Logger.error("UserRepository-retrieveAllUsers", ex);
            throw ex;
        }
        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<?extends IUser> retrieveUsersByTripId(Integer tripId){
        ExpressionList<User> userQuery = QueryProvider.getUserQuery()
                .fetch("missionTrips")
                .where()
                .eq("missionTrips.id", tripId);
        List<? extends IUser> users;
        try{
            users = userQuery.findList();
        }catch(Exception ex){

            Logger.error("UserRepository-retrieveUsersByTripId", ex);
            throw ex;
        }

        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IRole> retrieveAllRoles(){

        ExpressionList<Role> roleQuery = QueryProvider.getRoleQuery()
                .where()
                .ne("name", "SuperUser");

        List<? extends IRole> allRoles;
        try{

            allRoles = roleQuery.findList();
        } catch (Exception ex){

            Logger.error("UserRepository-retrieveAllRoles", ex);
            throw ex;
        }

        return allRoles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IRole> retrieveRolesByName(List<String> roleNames){

        if (roleNames == null){

            return null;
        }

        ExpressionList<Role> query = QueryProvider.getRoleQuery()
                .where()
                .in("name", roleNames);

        List<? extends IRole> roles;

        try{

            roles = query.findList();
        } catch(Exception ex){

            Logger.error("UserRepository-retrieveRolesByName", ex);
            throw ex;
        }

        return roles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRole retrieveRoleByName(String roleName) {

        if (StringUtils.isNullOrWhiteSpace(roleName)) {

            return null;
        }

        IRole role = roleProvider.get();

        try {

            role = Ebean.find(role.getClass())
                    .where()
                    .eq("name", roleName)
                    .findOne();
        } catch (Exception ex) {

            Logger.error("UserRepository-retrieveRoleByName", ex);
            throw ex;
        }

        return role;
    }
}
