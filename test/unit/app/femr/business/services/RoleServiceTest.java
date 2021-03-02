package unit.app.femr.business.services;
import femr.business.services.system.RoleService;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.IRole;
import femr.util.dependencyinjection.providers.RoleProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class RoleServiceTest {

    RoleService roleService;
    Provider<IRole> roleProvider;
    IUserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = mock(IUserRepository.class);
        roleProvider = new RoleProvider();
        roleService = new RoleService(userRepository);
    }

    private List<IRole> getSampleRoleList() {
        List<IRole> roleList = new ArrayList<>();

        IRole role = roleProvider.get();
        role.setName("nurse");
        role.setId(1);

        IRole role2 = roleProvider.get();
        role2.setName("physician");
        role2.setId(2);
        roleList.add(role);
        roleList.add(role2);

        return roleList;
    }


    @Test
    public void retrieveAllRolesTest() {
        List<IRole> roleList = getSampleRoleList();
        doReturn(roleList).when(userRepository).retrieveAllRoles();

        List<String> names = new ArrayList<>(Arrays.asList("nurse", "physician"));
        Assert.assertEquals(names, roleService.retrieveAllRoles().getResponseObject());
    }


    @Test
    public void retrieveAllRolesEmptyListTest() {
        List<IRole> roleList = new ArrayList<>();
        doReturn(roleList).when(userRepository).retrieveAllRoles();
        List<String> names = new ArrayList<>();
        Assert.assertEquals(names, roleService.retrieveAllRoles().getResponseObject());
    }

    @Test
    public void retrieveAllRolesExceptionTest() {
        when(userRepository.retrieveAllRoles()).thenThrow(new RuntimeException());
        Assert.assertEquals(true, roleService.retrieveAllRoles().hasErrors());
    }




}
