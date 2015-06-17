//package mock.femr.business.services;
//
//import com.avaje.ebean.ExpressionList;
//import femr.business.helpers.DomainMapper;
//import femr.business.helpers.QueryProvider;
//import femr.common.dto.ServiceResponse;
//import femr.business.services.IUserService;
//import femr.common.models.UserItem;
//import femr.data.models.IRole;
//import femr.data.models.User;
//import femr.common.dtos.ServiceResponse;
//import femr.business.services.core.IUserService;
//import femr.data.models.core.IRole;
//import femr.data.models.core.IUser;
//import java.util.List;
//
//public class MockUserService implements IUserService {
//
//    public boolean findByEmailWasCalled = false;
//    public String emailPassedIn;
//    public IUser findByEmailReturnUser = null;
//    public IUser findByIdReturnObject;
//    public int idPassedIn;
//    public boolean findByIdWasCalled = false;
//
//    @Override
//    public ServiceResponse<UserItem> createUser(UserItem user, String password) {
//
//        /*
//        ServiceResponse<UserItem> response = new ServiceResponse<>();
//        try {
//
//            ExpressionList<Role> query = QueryProvider.getRoleQuery()
//                    .where()
//                    .in("name", user.getRoles());
//            List<? extends IRole> roles = roleRepository.findPatientEncounterVital(query);
//
//
//            IUser newUser = domainMapper.createUser(user, password, false, false, roles);
//            encryptAndSetUserPassword(newUser);
//
//
//            if (userExistsWithEmail(user.getEmail())) {
//                response.addError("", "A user already exists with that email address.");
//                return response;
//            }
//
//            newUser = userRepository.createPatientEncounter(newUser);
//            response.setResponseObject(DomainMapper.createUserItem(newUser));
//        } catch (Exception ex) {
//            response.addError("", ex.getMessage());
//        }
//        */
//
//        ServiceResponse<UserItem> response = new ServiceResponse<>();
//        response.setResponseObject(null);
//        return response;
//    }
//
//    @Override
//    public IUser findByEmail(String email) {
//        findByEmailWasCalled = true;
//        emailPassedIn = email;
//        return findByEmailReturnUser;
//    }
//
//    @Override
//    public IUser findPatientById(int id) {
//        findByIdWasCalled = true;
//        idPassedIn = id;
//        return findByIdReturnObject;
//    }
//
//    @Override
//    public ServiceResponse<UserItem> findUser(int id) {
//
//        /*
//        ServiceResponse<UserItem> response = new ServiceResponse<>();
//        ExpressionList<User> query = QueryProvider.getUserQuery().fetch("roles").where().eq("id", id);
//        try {
//            IUser user = userRepository.findOne(query);
//            UserItem userItem;
//            userItem = DomainMapper.createUserItem(user);
//            response.setResponseObject(userItem);
//        } catch (Exception ex) {
//            response.addError("", ex.getMessage());
//        }
//        return response;
//        */
//
//        ServiceResponse<UserItem> response = new ServiceResponse<>();
//        response.setResponseObject(null);
//        return response;
//    }
//
//    @Override
//    public ServiceResponse<List<UserItem>> findAllUsers(){
//
//        ServiceResponse<List<UserItem>> response = new ServiceResponse<>();
//        response.setResponseObject(null);
//        return response;
//    }
//
//    @Override
//    public ServiceResponse<UserItem> updateUser(UserItem userItem, String newPassword) {
//
//        /*
//        ServiceResponse<UserItem> response = new ServiceResponse<>();
//        if (userItem == null) {
//            response.addError("", "send a user");
//            return response;
//        }
//        ExpressionList<User> query = QueryProvider.getUserQuery().where().eq("id", userItem.getId());
//        ExpressionList<Role> roleQuery = QueryProvider.getRoleQuery()
//                .where()
//                .ne("name", "SuperUser");
//
//        List<? extends IRole> allRoles = roleRepository.findPatientEncounterVital(roleQuery);
//
//        try {
//            IUser user = userRepository.findOne(query);
//            if (StringUtils.isNotNullOrWhiteSpace(newPassword)) {
//                user.setPassword(newPassword);
//                encryptAndSetUserPassword(user);
//            }
//            user.setFirstName(userItem.getFirstName());
//            user.setLastName(userItem.getLastName());
//            user.setNotes(userItem.getNotes());
//            List<IRole> newRoles = new ArrayList<>();
//            for (IRole role : allRoles){
//                if (userItem.getRoles().contains(role.getName()))
//                    newRoles.add(role);
//            }
//            user.setRoles(newRoles);
//            user.setPasswordReset(userItem.isPasswordReset());
//            user = userRepository.updatePatientEncounter(user);
//            response.setResponseObject(DomainMapper.createUserItem(user));
//        } catch (Exception ex) {
//            response.addError("", ex.getMessage());
//        }
//        */
//
//        ServiceResponse<UserItem> response = new ServiceResponse<>();
//        response.setResponseObject(null);
//        return response;
//    }
//
//    @Override
//    public ServiceResponse<UserItem> toggleUser(int id) {
//        /*
//        ServiceResponse<UserItem> response = new ServiceResponse<>();
//        ExpressionList<User> query = QueryProvider.getUserQuery().where().eq("id", id);
//        try {
//            IUser user = userRepository.findOne(query);
//            user.setDeleted(!user.getDeleted());
//            user = userRepository.updatePatientEncounter(user);
//            response.setResponseObject(DomainMapper.createUserItem(user));
//        } catch (Exception ex) {
//            response.addError("", ex.getMessage());
//        }
//        */
//
//        ServiceResponse<UserItem> response = new ServiceResponse<>();
//        response.setResponseObject(null);
//        return response;
//
//    }
//
//    @Override
//    public ServiceResponse<IUser> updatePatientEncounter(IUser currentUser, Boolean isNewPassword) {
//        return null;
//    }
//
//    @Override
//    public List<? extends IRole> findRolesForUser(int id) {
//        return null;
//    }
//}
//
