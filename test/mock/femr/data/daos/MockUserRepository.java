package mock.femr.data.daos;

import com.google.inject.Inject;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.ILoginAttempt;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;

import java.util.List;

public class MockUserRepository implements IUserRepository {
    public boolean createUserWasCalled = false;

    public ILoginAttempt createLoginAttempt(String usernameValue, boolean isSuccessful, byte[] ipAddress, Integer userId) {
        return null;
    }

    public IRole createRole(int id, String name) {
        return null;
    }

    public IUser createUser(IUser user) {
        createUserWasCalled = true;
        return user;
    }

    public Integer countUsers() {
        return null;
    }

    public IUser updateUser(IUser user) {
        return null;
    }

    public IUser retrieveUserById(Integer userId) {
        return null;
    }

    public IUser retrieveUserByEmail(String email) {
        return null;
    }

    public List<? extends IUser> retrieveAllUsers() {
        return null;
    }

    public List<? extends IUser> retrieveUsersByTripId(Integer tripId) {
        return null;
    }

    public List<? extends IRole> retrieveAllRoles() {
        return null;
    }

    public List<? extends IRole> retrieveRolesByName(List<String> roleNames) {
        return null;
    }

    public IRole retrieveRoleByName(String roleName) {
        return null;
    }
}
