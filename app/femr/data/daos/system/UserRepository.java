package femr.data.daos.system;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import femr.data.models.mysql.Role;
import femr.data.models.mysql.User;
import play.Logger;

import java.util.List;

/**
 * Created by ajsaclayan on 11/20/16.
 */
public class UserRepository implements IUserRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser createUser(IUser user){
        try {
            Ebean.save(user);
        } catch (Exception ex) {

            Logger.error("UserRepository-create", ex);
            throw ex;
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser updateUser(IUser user){
        try {
            Ebean.update(user);
        } catch (Exception ex) {

            Logger.error("UserRepository-update", ex);
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
            user = userQuery.findUnique();
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
            user = userQuery.findUnique();
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
}
