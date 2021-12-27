package mock.femr.data.daos;

import femr.data.daos.core.IUserRepository;
import femr.data.models.core.ILoginAttempt;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import mock.femr.data.models.MockUser;

import java.util.List;

public class MockUserRepository implements IUserRepository {

    public boolean retrieveUserByIdWasCalled = false;

    public IUser user;

    public MockUserRepository() {

        this.user = new MockUser();
    }


    @Override
    public ILoginAttempt createLoginAttempt(String usernameValue, boolean isSuccessful, byte[] ipAddress, Integer userId) {
        return null;
    }

    @Override
    public IRole createRole(int id, String name) {
        return null;
    }

    @Override
    public IUser createUser(IUser user) {
        return null;
    }

    @Override
    public Integer countUsers() {
        return null;
    }

    @Override
    public IUser updateUser(IUser user) {
        return null;
    }

    @Override
    public IUser retrieveUserById(Integer userId) {
        retrieveUserByIdWasCalled = true;
        return user;
    }

    @Override
    public IUser retrieveUserByEmail(String email) {
        return null;
    }

    @Override
    public List<? extends IUser> retrieveAllUsers() {
        return null;
    }

    @Override
    public List<? extends IUser> retrieveUsersByTripId(Integer tripId) {
        return null;
    }

    @Override
    public List<? extends IRole> retrieveAllRoles() {
        return null;
    }

    @Override
    public List<? extends IRole> retrieveRolesByName(List<String> roleNames) {
        return null;
    }

    @Override
    public IRole retrieveRoleByName(String roleName) {
        return null;
    }
}