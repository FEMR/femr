package femr.business.services;

import femr.data.models.IUser;

public interface IUserService {
    IUser createUser(String firstName, String lastName, String email, String password);
    IUser findByEmail(String email);
    IUser findById(int id);
}
