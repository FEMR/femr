package unit.app.femr.business.services;

import femr.business.services.core.IUserService;
import femr.business.services.system.UserService;
import femr.common.IItemModelMapper;
import femr.common.models.UserItem;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import femr.util.calculations.dateUtils;
import femr.util.encryptions.IPasswordEncryptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    IUserService userService;
    IUserRepository userRepository;
    IPasswordEncryptor passwordEncryptor;
    IDataModelMapper dataModelMapper;
    IItemModelMapper itemModelMapper;

    @Before
    public void setUp() {
        userRepository = mock(IUserRepository.class);
        passwordEncryptor = mock(IPasswordEncryptor.class);
        dataModelMapper = mock(IDataModelMapper.class);
        itemModelMapper = mock(IItemModelMapper.class);
        userService = new UserService(userRepository, passwordEncryptor, dataModelMapper, itemModelMapper, null);
    }

    @Test
    public void retrieveAllUsers() {
        UserItem userItem = new UserItem();
        userItem.setFirstName("FirstName");
        userItem.setLastName("LastName");
        userItem.setEmail("Email");

        List<? extends IRole> roles = userRepository.retrieveRolesByName(userItem.getRoles());
        IUser newUser = dataModelMapper.createUser(userItem.getFirstName(), userItem.getLastName(), userItem.getEmail(), dateUtils.getCurrentDateTime(), userItem.getNotes(), "password", false, false, roles, -1);
        Assert.assertEquals(null, userRepository.createUser(newUser));

        List<UserItem> userList = new ArrayList<>();
        userList.add(userItem);
        doReturn(userList).when(userRepository).retrieveAllUsers();
        Assert.assertEquals(null, userService.createUser(userItem, "", -1).getResponseObject());
    }
}
