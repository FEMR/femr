package femr.business.services;

import femr.common.dto.ServiceResponse;
import femr.data.models.IRole;
import femr.data.models.IUser;

import java.util.List;

public interface IUserService {
    ServiceResponse<IUser> createUser(IUser user);

    IUser findByEmail(String email);

    IUser findById(int id);

    List<? extends IRole> findRolesForUser(int id);

    ServiceResponse<List<? extends IUser>> findAllUsers();

    ServiceResponse<IUser> update(IUser currentUser, Boolean isNewPassword);
}
