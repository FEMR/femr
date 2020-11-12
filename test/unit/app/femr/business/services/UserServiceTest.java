package unit.app.femr.business.services;

import femr.business.services.core.IUserService;
import femr.business.services.system.UserService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.UserItem;
import femr.data.models.core.IUser;
import mock.femr.common.MockItemModelMapper;
import mock.femr.data.MockDataModelMapper;
import mock.femr.data.daos.MockUserRepository;
import mock.femr.util.encryptions.MockPasswordEncryptor;
import mock.femr.data.models.MockUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {

    private IUserService userService;
    private MockUserRepository mockUserRepository;
    private MockPasswordEncryptor mockPasswordEncryptor;
    private MockDataModelMapper mockDataModelMapper;
    private MockItemModelMapper mockItemModelMapper;

    @Before
    public void setUp() {
        mockUserRepository = new MockUserRepository();
        mockPasswordEncryptor = new MockPasswordEncryptor();
        mockDataModelMapper = new MockDataModelMapper();
        mockItemModelMapper = new MockItemModelMapper();

        userService = new UserService(mockUserRepository, mockPasswordEncryptor, mockDataModelMapper, mockItemModelMapper, null);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void createUser_parametersProvided() {

        //arrange
        ServiceResponse<UserItem> response;
        IUser mockUser = new MockUser();

        UserItem userItem = new UserItem();
        userItem.setFirstName(mockUser.getFirstName());
        userItem.setLastName(mockUser.getLastName());

        //act
        response = userService.createUser(userItem, "", 0);
        UserItem userItemResponse = response.getResponseObject();
        //assert
        assertFalse(response.hasErrors());
        assertTrue(mockDataModelMapper.createUserWasCalled);
        assertTrue(mockUserRepository.createUserWasCalled);
        assertTrue(mockItemModelMapper.createUserItemWasCalled);

        //from MockUser data values
        assertEquals(userItemResponse.getId(), -1);
        assertEquals(userItemResponse.getFirstName(), "test_firstName");
        assertEquals(userItemResponse.getLastName(), "test_lastName");
    }
}
