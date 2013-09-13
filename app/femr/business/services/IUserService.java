package femr.business.services;

import femr.common.models.IUser;

public interface IUserService {
    IUser createUser(IUser user);

    IUser findByEmail(String email);

    IUser findById(int id);
}
