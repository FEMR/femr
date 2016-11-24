package femr.data.daos.system;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.IUser;
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
    public IUser create(IUser user){
        try {
            Ebean.save(user);
        } catch (Exception ex) {
            Logger.error("UserRepository- create", ex.getMessage());
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser update(IUser user){
        try {
            Ebean.update(user);
        } catch (Exception ex) {
            Logger.error("UserRepository- update", ex.getMessage());
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
            Logger.error("UserRepository- retrieveUserById", ex.getMessage());
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
            Logger.error("UserRepository- retrieveUserByEmail", ex.getMessage());
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
            Logger.error("UserRepository- retrieveAllUsers", ex.getMessage());
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
            Logger.error("UserRepository- retrieveUsersByTripId", ex.getMessage());
            throw ex;
        }

        return users;
    }
}
