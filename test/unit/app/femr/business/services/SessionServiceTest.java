//package unit.app.femr.business.services;
//
//import femr.common.dto.CurrentUser;
//import femr.common.dto.ServiceResponse;
//import femr.business.services.ISessionService;
//import femr.business.services.SessionService;
//import mock.femr.business.services.MockUserService;
//import mock.femr.business.wrappers.sessions.MockSessionHelper;
//import mock.femr.data.models.MockUser;
//import mock.femr.util.encryptions.MockPasswordEncryptor;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import static org.fest.assertions.Assertions.assertThat;
//
//public class SessionServiceTest {
//
//    public ISessionService sessionService;
//    public MockUserService mockUserService;
//    public MockPasswordEncryptor mockPasswordEncryptor;
//    public MockSessionHelper mockSessionHelper;
//
//    @Before
//    public void setUp() {
//        mockUserService = new MockUserService();
//        mockPasswordEncryptor = new MockPasswordEncryptor();
//        mockSessionHelper = new MockSessionHelper();
//
//        sessionService = new SessionService(mockUserService, mockPasswordEncryptor, mockSessionHelper);
//    }
//
//    @Test
//    public void testCreateSessionCallsFindByEmailWithCorrectArguments() {
//        String expectedEmail = "fakeEmail";
//
//        assertThat(mockUserService.findByEmailWasCalled).isFalse();
//        sessionService.createSession(expectedEmail, "");
//        assertThat(mockUserService.emailPassedIn).isEqualTo(expectedEmail);
//        assertThat(mockUserService.findByEmailWasCalled).isTrue();
//    }
//
//    @Test
//    public void testCreateSessionReturnsAnErrorWhenNoEmailMatches() {
//        ServiceResponse<CurrentUser> response = sessionService.createSession("", "");
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("")).isTrue();
//        assertThat(response.getErrors().get("")).isEqualTo("Invalid email or password.");
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void testCreateSessionCallsVerifyPasswordWithCorrectArguments() {
//        String expectedPassword = "fakePassword";
//        String expectedHashPassword = "fakeHashPassword";
//        MockUser mockUser = new MockUser();
//        mockUser.password = expectedHashPassword;
//        mockUserService.findByEmailReturnUser = mockUser;
//
//        assertThat(mockPasswordEncryptor.verifyPasswordWasCalled).isFalse();
//        sessionService.createSession("", expectedPassword);
//        assertThat(mockPasswordEncryptor.passwordPassedIn).isEqualTo(expectedPassword);
//        assertThat(mockPasswordEncryptor.hashedPasswordPassedIn).isEqualTo(expectedHashPassword);
//        assertThat(mockPasswordEncryptor.verifyPasswordWasCalled).isTrue();
//    }
//
//    @Test
//    public void testCreateSessionReturnsAnErrorUserWhenPasswordDoesNotMatch() {
//        MockUser mockUser = new MockUser();
//        mockUserService.findByEmailReturnUser = mockUser;
//
//        assertThat(mockUserService.findByEmail("")).isNotNull();
//        ServiceResponse<CurrentUser> response = sessionService.createSession("", "");
//        assertThat(response.hasErrors()).isTrue();
//        assertThat(response.getErrors().size()).isEqualTo(1);
//        assertThat(response.getErrors().containsKey("")).isTrue();
//        assertThat(response.getErrors().get("")).isEqualTo("Invalid email or password.");
//        assertThat(response.getResponseObject()).isNull();
//    }
//
//    @Test
//    public void testCreateSessionWillSetSessionVariableWhenGivenCorrectCredentials() {
//        MockUser mockUser = new MockUser();
//        mockUser.id = 121;
//        mockUserService.findByEmailReturnUser = mockUser;
//        mockPasswordEncryptor.verifyPasswordResult = true;
//
//        sessionService.createSession("", "");
//
//        assertThat(mockSessionHelper.session.size()).isEqualTo(1);
//        assertThat(mockSessionHelper.session.containsKey("currentUser")).isTrue();
//        assertThat(mockSessionHelper.session.get("currentUser")).isEqualTo("121");
//    }
//
//    @Test
//    public void testCreateSessionWillReturnCorrectResponseWhenGivenCorrectCredentials() {
//        MockUser mockUser = new MockUser();
//        mockUser.id = 121;
//        mockUser.firstName = "Test";
//        mockUser.lastName = "User";
//        mockUser.email = "blah@test.com";
//        mockUserService.findByEmailReturnUser = mockUser;
//        mockPasswordEncryptor.verifyPasswordResult = true;
//
//        ServiceResponse<CurrentUser> response = sessionService.createSession("", "");
//
//        assertThat(response.hasErrors()).isFalse();
//        assertThat(response.getResponseObject()).isNotNull();
//
//        CurrentUser currentUser = response.getResponseObject();
//
//        assertThat(currentUser.getEmail()).isEqualTo(mockUser.email);
//        assertThat(currentUser.getFirstName()).isEqualTo(mockUser.firstName);
//        assertThat(currentUser.getLastName()).isEqualTo(mockUser.lastName);
//    }
//
//    @Test
//    public void testGetSessionReturnsNullIfNoSessionKeyFound() {
//        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
//        assertThat(currentUser).isNull();
//        assertThat(mockUserService.findByIdWasCalled).isFalse();
//    }
//
//    @Test
//    public void testGetSessionReturnsNullOnFailedCast() {
//        mockSessionHelper.session.put("currentUser", "hello");
//        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
//        assertThat(currentUser).isNull();
//        assertThat(mockUserService.findByIdWasCalled).isFalse();
//    }
//
//    @Test
//    public void testGetSessionReturnsCurrentUserObjectWithCorrectSession() {
//        String expectedSessionKey = "currentUser";
//        String mockUserIdString = "99";
//        int expectedId = 99;
//
//        mockSessionHelper.session.put(expectedSessionKey, mockUserIdString);
//        mockSessionHelper.integerToReturn = expectedId;
//
//        MockUser expectedUser = new MockUser();
//        expectedUser.firstName = "Bob";
//        expectedUser.lastName = "Dole";
//        expectedUser.email = "my@whitehouse.com";
//        mockUserService.findByIdReturnObject = expectedUser;
//
//        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
//
//        assertThat(mockSessionHelper.sessionKeyPassedIn).isEqualTo(expectedSessionKey);
//        assertThat(mockUserService.findByIdWasCalled).isTrue();
//        assertThat(mockUserService.idPassedIn).isEqualTo(expectedId);
//        assertThat(currentUser).isNotNull();
//        assertThat(currentUser.getEmail()).isEqualTo(expectedUser.email);
//        assertThat(currentUser.getFirstName()).isEqualTo(expectedUser.firstName);
//        assertThat(currentUser.getLastName()).isEqualTo(expectedUser.lastName);
//    }
//
//    @Test
//    public void testInvalidateSessionCallsSessionHelperRemoveSession() {
//        String expected = "currentUser";
//
//        assertThat(mockSessionHelper.deleteWasCalled).isFalse();
//        sessionService.invalidateCurrentUserSession();
//        assertThat(mockSessionHelper.deleteWasCalled).isTrue();
//        assertThat(mockSessionHelper.sessionKeyPassedIn).isEqualTo(expected);
//    }
//
//    @Test
//    @Ignore
//    public void testSessionWithRolesInAboveTests() {
//    }
//}
