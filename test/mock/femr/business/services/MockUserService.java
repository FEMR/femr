package mock.femr.business.services;

import femr.business.dtos.ServiceResponse;
import femr.business.services.IUserService;
import femr.common.models.IRole;
import femr.common.models.IUser;
import femr.util.dependencyinjection.providers.UserProvider;

import java.util.List;

public class MockUserService implements IUserService {

    public boolean findByEmailWasCalled = false;
    public String emailPassedIn;
    public IUser findByEmailReturnUser = null;
    public IUser findByIdReturnObject;
    public int idPassedIn;
    public boolean findByIdWasCalled = false;

    @Override
    public ServiceResponse<IUser> createUser(IUser user) {
        return null;
    }

    @Override
    public IUser findByEmail(String email) {
        findByEmailWasCalled = true;
        emailPassedIn = email;
        return findByEmailReturnUser;
    }

    @Override
    public IUser findById(int id) {
        findByIdWasCalled = true;
        idPassedIn = id;
        return findByIdReturnObject;
    }

    @Override
    public ServiceResponse<List<? extends IUser>> findAllUsers(){
        ServiceResponse<List<? extends IUser>> response = new ServiceResponse<>();
        return response;
    }

    @Override
    public List<? extends IRole> findRolesForUser(int id) {
        return null;
    }
}

