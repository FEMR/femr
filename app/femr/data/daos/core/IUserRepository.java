package femr.data.daos.core;
import femr.data.models.core.IUser;
import java.util.List;

/**
 * Created by ajsaclayan on 11/20/16.
 */
public interface IUserRepository {

    /**
     * Create user in database
     * @param user to create
     * @return the provided user
     */
    IUser createUser(IUser user);

    /**
     * Update user in database
     * @param user to update
     * @return the provided user
     */
    IUser updateUser(IUser user);

    /**
     * Retrieve user by id
     * @param userId the id of the user to retrieve
     * @return the provided user
     */
    IUser retrieveUserById(Integer userId);

    /**
     * Retrieve user by email
     * @param email the email of the user to retrieve
     * @return the provided user
     */
    IUser retrieveUserByEmail(String email);

    /**
     * Retrieve all users except admin/superuser
     *
     * @return all users
     */
    List<? extends IUser> retrieveAllUsers();

    /**
     * Retrieve all users by trip id
     * @param tripId id of the trip of the users who have been on the trip
     * @return all users that were on that trip
     */
    List<? extends IUser> retrieveUsersByTripId(Integer tripId);
}
