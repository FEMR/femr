package femr.ui.controllers;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.business.services.system.UpdatesService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.InternetStatusItem;
import femr.common.models.UserItem;
import femr.data.models.core.IRole;
import femr.data.models.core.INetworkStatus;
import femr.data.models.core.IUser;
import femr.data.models.mysql.NetworkStatus;
import femr.data.models.mysql.Roles;
import femr.data.models.mysql.SystemSetting;
import femr.ui.models.sessions.CreateViewModel;
import femr.ui.views.html.sessions.create;
import femr.ui.views.html.sessions.editPassword;
import femr.util.ThreadHelper;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import play.Environment;
import play.mvc.Call;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import femr.util.InternetConnnection.InternetCheck;

public class SessionsController extends Controller {

    private static final Map<String, String> PASSWORD_RESET_MESSAGE_KEYS = createPasswordResetMessageKeys();
    private static final Map<String, String> PASSWORD_RESET_FALLBACKS = createPasswordResetFallbacks();

    private final AssetsFinder assetsFinder;
    private final Environment environment;
    private final FormFactory formFactory;
    private final ISessionService sessionsService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final IUpdatesService internetStatusService;

    @Inject
    public SessionsController(AssetsFinder assetsFinder, Environment environment, FormFactory formFactory, ISessionService sessionsService, IUserService userService,
                              IRoleService roleService, IUpdatesService internetStatusService) {

        this.assetsFinder = assetsFinder;
        this.environment = environment;
        this.formFactory = formFactory;
        this.sessionsService = sessionsService;
        this.userService = userService;
        this.roleService = roleService;
        this.internetStatusService = internetStatusService;
    }

    public Result createGet() {
        CurrentUser currentUser = sessionsService.retrieveCurrentUserSession();

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);


        if (currentUser != null) {
            return redirect(resolvePostLoginDestination(currentUser));
        }

        return ok(create.render(createViewModelForm, 0,null, assetsFinder, new ArrayList<String>()));
    }

    public Result createPost() {

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        ServiceResponse<CurrentUser> response = sessionsService.createSession(viewModel.getEmail(), viewModel.getPassword(), request().remoteAddress());

        if (response.hasErrors()) {
            if (response.getErrors().containsValue("Not Activated.")) {
                return ok(create.render(createViewModelForm.bindFromRequest(), 3, null, assetsFinder, new ArrayList<String>()));
            } else {
                return ok(create.render(createViewModelForm.bindFromRequest(), 1, null, assetsFinder, new ArrayList<String>()));
            }
        }else{
            IUser user = userService.retrieveById(response.getResponseObject().getId());
            user.setLastLogin(dateUtils.getCurrentDateTime());
            ServiceResponse<IUser> userResponse = userService.update(user, false);
            if (userResponse.hasErrors()){
                throw new RuntimeException();
            }

            DateTime start = new DateTime(user.getPasswordCreatedDate());
            DateTime stop = new DateTime(DateTime.now());
            int daysBetween = Days.daysBetween(start, stop).getDays();

            if(daysBetween > 90 && !isDefaultSeedUser(user)){
                user.setPasswordReset(true);
                ServiceResponse<IUser> stalePasswordResponse = userService.update(user, false);
                if (stalePasswordResponse.hasErrors()) {
                    throw new RuntimeException();
                }
            }

            if (isDefaultSeedUser(user) && Boolean.TRUE.equals(user.getPasswordReset())) {
                user.setPasswordReset(false);
                ServiceResponse<IUser> clearResetResponse = userService.update(user, false);
                if (clearResetResponse.hasErrors()) {
                    throw new RuntimeException();
                }
            }

            if (user.getPasswordReset() == true){
                return renderEditPassword(user);
            }

            ThreadHelper threadHelper = new ThreadHelper(internetStatusService);
            Thread t = new Thread(threadHelper);
            t.start();

            return redirect(resolvePostLoginDestination(response.getResponseObject()));
        }
    }


    public Result editPasswordGet() {
        CurrentUser currentUser = sessionsService.retrieveCurrentUserSession();
        if (currentUser == null) {
            return redirect(routes.SessionsController.createGet());
        }

        IUser user = userService.retrieveById(currentUser.getId());
        if (user == null) {
            sessionsService.invalidateCurrentUserSession();
            return redirect(routes.SessionsController.createGet());
        }

        return renderEditPassword(user);
    }

    private Result renderEditPassword(IUser user){
        return renderEditPassword(user, new ArrayList<String>());
    }

    private Result renderEditPassword(IUser user, List<String> messages){
        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        String languageCode = resolveLanguageCode(user);
        Map<String, String> translations = loadPasswordResetTranslations(languageCode);

        return ok(editPassword.render(user.getFirstName(), user.getLastName(), languageCode, translations, createViewModelForm, translatePasswordResetMessages(messages, translations), assetsFinder));
    }

    public Result editRegisterPost() {
        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        Form<CreateViewModel> form = createViewModelForm.bindFromRequest();

        ServiceResponse<List<String>> roleServiceResponse = roleService.retrieveAllRoles();
        if (roleServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        List<String> messages = new ArrayList<>();

        return ok(create.render(form, 2, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
    }

    public Result createAccountPost() {
        CurrentUser currentUser = sessionsService.retrieveCurrentUserSession();

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        Form<CreateViewModel> form = createViewModelForm.bindFromRequest();

        ServiceResponse<List<String>> roleServiceResponse = roleService.retrieveAllRoles();
        if (roleServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        List<String> messages = new ArrayList<>();

        if (!form.field("email").getValue().isPresent()) {
            return badRequest(create.render(form, 2, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
        }

        if (form.field("email").getValue().isPresent() &&
                !form.field("email").getValue().get().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$")) {
            messages.add("Invalid Email");
            return badRequest(create.render(form, 2, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
        }

        if (form.hasErrors()) {
            return badRequest(create.render(form, 2, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
        } else {
            CreateViewModel viewModel = form.bindFromRequest().get();
            UserItem user = createUserItem(viewModel);

            if (currentUser != null) {
                user.setLanguageCode(currentUser.getLanguageCode());
            }

            ServiceResponse<UserItem> response = userService.createUser(user, viewModel.getPassword(), 0);
            if (response.hasErrors()) {
                messages.add(response.getErrors().get(""));
                return ok(create.render(form, 2, messages, assetsFinder,  roleServiceResponse.getResponseObject()));
            } else {
                //added user's last name to be displayed[FEMR-161]
                //Contributed by Harsha Peswani during the CEN5035 course at FSU
                if (StringUtils.isNullOrWhiteSpace(viewModel.getLastName()))
                    messages.add("An account for " + user.getFirstName() + " was created successfully. You may begin creating a new user.");
                else
                    messages.add("An account for " + user.getFirstName() + " " + user.getLastName() + " was created successfully. You may begin creating a new user.");

                return ok(create.render(form, 0, messages, assetsFinder, roleServiceResponse.getResponseObject()));
            }
        }
    }

    public Result editPasswordPost(){

        final Form<CreateViewModel> createViewModelForm = formFactory.form(CreateViewModel.class);
        CreateViewModel viewModel = createViewModelForm.bindFromRequest().get();
        CurrentUser currentUser = sessionsService.retrieveCurrentUserSession();
        IUser user = userService.retrieveById(currentUser.getId());
        Boolean isNewPassword = false;

        Pattern hasLowercase = Pattern.compile("[a-z]");

        Pattern hasUppercase = Pattern.compile("[A-Z]");
        Pattern hasNumber = Pattern.compile("\\d");
        ArrayList<String> messages = new ArrayList<>();
        if (StringUtils.isNullOrWhiteSpace(viewModel.getNewPassword()))
            messages.add("password is a required field");
        else
        {
            if(viewModel.getNewPassword().length() < 8)        //AJ Saclayan Password Constraints
                messages.add("password is less than 8 characters");
            if(!hasLowercase.matcher(viewModel.getNewPassword()).find())
                messages.add("password must have a lowercase character");
            if (!hasUppercase.matcher(viewModel.getNewPassword()).find())
                messages.add("password must have an uppercase character");

            if (!hasNumber.matcher(viewModel.getNewPassword()).find())
                messages.add("password must have a number");

            if(!viewModel.getNewPassword().equals(viewModel.getNewPasswordVerify()))
                messages.add("passwords do not match");
            //check if new password is equal to the old password
            if(userService.checkOldPassword(viewModel.getNewPassword(),userService.retrieveById(currentUser.getId()).getPassword()))
                messages.add("password must not be the same one used before reset");


        }

        if(!messages.isEmpty())
            return renderEditPassword(user, messages);
        else
        {
            user.setPassword(viewModel.getNewPassword());
            user.setPasswordCreatedDate(DateTime.now());
            user.setPasswordReset(false);
            isNewPassword = true;
        }

        ServiceResponse<IUser> userResponse = userService.update(user, isNewPassword);
        if (userResponse.hasErrors()){
            throw new RuntimeException();
        }
        return redirect(resolvePostLoginDestination(currentUser));
    }

    public Result delete() {
        sessionsService.invalidateCurrentUserSession();

        return redirect(routes.HomeController.index());
    }

    private UserItem createUserItem(CreateViewModel viewModel) {
        UserItem user = new UserItem();
        user.setFirstName(viewModel.getFirstName());
        user.setLastName(viewModel.getLastName());
        user.setEmail(viewModel.getEmail());
        user.setDeleted(true);
        user.setPasswordReset(false);
        user.setNotes(viewModel.getNotes());
        user.setRoles(viewModel.getRoles());
        user.setDateCreated(viewModel.getDateCreated());
        user.setLanguageCode(viewModel.getLanguage());
        return user;
    }

    private boolean isDefaultSeedUser(IUser user) {
        if (user == null || StringUtils.isNullOrWhiteSpace(user.getEmail())) {
            return false;
        }

        String email = user.getEmail().trim().toLowerCase();
        return "admin".equals(email) || "superuser".equals(email);
    }

    private String resolveLanguageCode(IUser user) {
        Http.Cookie languageCookie = request().cookie("languageCode");
        if (languageCookie != null && !StringUtils.isNullOrWhiteSpace(languageCookie.value())) {
            return languageCookie.value();
        }

        if (user == null || StringUtils.isNullOrWhiteSpace(user.getLanguageCode())) {
            return "en";
        }

        return user.getLanguageCode();
    }

    private Map<String, String> loadPasswordResetTranslations(String languageCode) {
        Map<String, String> translations = new HashMap<>(PASSWORD_RESET_FALLBACKS);

        try {
            File languagesFile = environment.getFile("public/json/languages.json");
            String languagesJson = new String(Files.readAllBytes(languagesFile.toPath()), StandardCharsets.UTF_8);
            JSONObject languageData = new JSONObject(languagesJson);
            JSONObject englishStrings = languageData.getJSONObject("en");
            JSONObject selectedStrings = languageData.has(languageCode) ? languageData.getJSONObject(languageCode) : englishStrings;

            for (String key : PASSWORD_RESET_FALLBACKS.keySet()) {
                if (englishStrings.has(key)) {
                    translations.put(key, englishStrings.getString(key));
                }
                if (selectedStrings.has(key)) {
                    translations.put(key, selectedStrings.getString(key));
                }
            }
        } catch (Exception e) {
            return translations;
        }

        return translations;
    }

    private List<String> translatePasswordResetMessages(List<String> messages, Map<String, String> translations) {
        List<String> translatedMessages = new ArrayList<>();

        for (String message : messages) {
            String translationKey = PASSWORD_RESET_MESSAGE_KEYS.get(message);
            translatedMessages.add(translationKey == null ? message : translations.getOrDefault(translationKey, message));
        }

        return translatedMessages;
    }

    private static Map<String, String> createPasswordResetMessageKeys() {
        Map<String, String> messageKeys = new HashMap<>();
        messageKeys.put("password is a required field", "sessions_edit_password_error_required");
        messageKeys.put("password is less than 8 characters", "sessions_edit_password_error_min_length");
        messageKeys.put("password must have a lowercase character", "sessions_edit_password_error_lowercase");
        messageKeys.put("password must have an uppercase character", "sessions_edit_password_error_uppercase");
        messageKeys.put("password must have a number", "sessions_edit_password_error_number");
        messageKeys.put("passwords do not match", "sessions_edit_password_error_match");
        messageKeys.put("password must not be the same one used before reset", "sessions_edit_password_error_reused");
        return messageKeys;
    }

    private static Map<String, String> createPasswordResetFallbacks() {
        Map<String, String> fallbacks = new HashMap<>();
        fallbacks.put("sessions_edit_password_heading", "Hello, {name}, your password is older than 90 days.");
        fallbacks.put("sessions_edit_password_prompt", "Please choose a new password:");
        fallbacks.put("sessions_edit_password_enter_password", "Enter password:");
        fallbacks.put("sessions_edit_password_reenter_password", "Re-enter password:");
        fallbacks.put("sessions_edit_password_submit", "Submit");
        fallbacks.put("sessions_edit_password_error_required", "password is a required field");
        fallbacks.put("sessions_edit_password_error_min_length", "password is less than 8 characters");
        fallbacks.put("sessions_edit_password_error_lowercase", "password must have a lowercase character");
        fallbacks.put("sessions_edit_password_error_uppercase", "password must have an uppercase character");
        fallbacks.put("sessions_edit_password_error_number", "password must have a number");
        fallbacks.put("sessions_edit_password_error_match", "passwords do not match");
        fallbacks.put("sessions_edit_password_error_reused", "password must not be the same one used before reset");
        return fallbacks;
    }

    private Call resolvePostLoginDestination(CurrentUser currentUser) {
        if (currentUser != null && currentUser.getRoles() != null) {
            for (IRole role : currentUser.getRoles()) {
                if (role != null && (role.getId() == Roles.ADMINISTRATOR || role.getId() == Roles.SUPERUSER)) {
                    return femr.ui.controllers.admin.routes.AdminController.index();
                }
            }
        }

        return routes.HomeController.index();
    }
}
