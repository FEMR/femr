package femr.data.daos.core;
import femr.data.models.core.ILoginAttempt;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import java.util.List;

/**
 * Created by ajsaclayan on 11/20/16.
 */
public interface IUserRepository {

    /**
     * Log an attempt by a user trying to log in
     *
     * @param usernameValue the value that the device/person submitted to the server as a username, not null
     * @param isSuccessful whether or not the login attempt was successful, not null
     * @param ipAddress the ip address of the device trying to log in - in binary form, not null
     * @param userId id of the user account that is trying to be logged into, may be null
     * @return a new login attempt
     */
    ILoginAttempt createLoginAttempt(String usernameValue, boolean isSuccessful, byte[] ipAddress, Integer userId);

    /**
     * Create role in database
     *
     * @param id id of the role, not null
     * @param name name of the role to be created, not null
     * @return the new role
     */
    IRole createRole(int id, String name);

    /**
     * Create user in database
     *
     * @param user to create
     * @return the provided user
     */
    IUser createUser(IUser user);

    /**
     * Counts the number of users in the database
     *
     * @return the number of users in the database
     */
    Integer countUsers();

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

    /**
     * Retrieves all user roles except the role named "SuperUser"
     *
     * @return a list of all roles
     */
    List<? extends IRole> retrieveAllRoles();

    /**
     * Retrieves a list of roles based on the name of the role
     *
     * @param roleNames list of roles as strings, not null
     * @return list of roles as data models
     */
    List<? extends IRole> retrieveRolesByName(List<String> roleNames);

    /**
     * Retrieves a unique role by its name
     *
     * @param roleName exact name of the role, not null
     * @return the role or null if not found
     */
    IRole retrieveRoleByName(String roleName);
}
