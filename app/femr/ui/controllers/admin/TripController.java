package femr.ui.controllers.admin;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.IMissionTripService;
import femr.business.services.core.ISessionService;
import femr.business.services.core.IUserService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.admin.trips.EditViewModelGet;
import femr.ui.models.admin.trips.EditViewModelPost;
import femr.ui.models.admin.trips.TripViewModelGet;
import femr.ui.models.admin.trips.TripViewModelPost;
import femr.ui.views.html.admin.trips.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.ADMINISTRATOR})
public class TripController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private final IMissionTripService missionTripService;
    private final ISessionService sessionService;
    private final IUserService userService;

    @Inject
    public TripController(AssetsFinder assetsFinder,
                          FormFactory formFactory,
                          IMissionTripService missionTripService,
                          ISessionService sessionService,
                          IUserService userService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.missionTripService = missionTripService;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    public Result manageGet(){

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        TripViewModelGet tripViewModel = createTripViewModelGet(null);

        return ok(manage.render(currentUser, tripViewModel, assetsFinder));
    }

    public Result managePost(){

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        Form<TripViewModelPost> tripViewModelPostForm = formFactory.form(TripViewModelPost.class);
        TripViewModelPost tripViewModelPost = tripViewModelPostForm.bindFromRequest().get();
        List<String> messages = new ArrayList<>();

        //create the trip item from the viewmodel
        TripItem tripItem = new TripItem();
        tripItem.setTeamName(tripViewModelPost.getNewTripTeamName());
        tripItem.setTripCity(tripViewModelPost.getNewTripCity());
        tripItem.setTripCountry(tripViewModelPost.getNewTripCountry());
        tripItem.setTripStartDate(tripViewModelPost.getNewTripStartDate());
        tripItem.setTripEndDate(tripViewModelPost.getNewTripEndDate());

        //send trip item to service layer to create trip
        ServiceResponse<TripItem> newTripItemServiceResponse = missionTripService.createNewTrip(tripItem);
        if (newTripItemServiceResponse.hasErrors()) {
            messages.addAll(
                    newTripItemServiceResponse.getErrors()
                            .keySet()
                            .stream()
                            .map(key -> newTripItemServiceResponse.getErrors().get(key))
                            .collect(Collectors.toList()
                            )
            );
        }

        TripViewModelGet tripViewModel = createTripViewModelGet(messages);

        return ok(manage.render(currentUser, tripViewModel, assetsFinder));
    }

    //for when you click the edit button in the left hand column of the trip table
    public Result editGet(Integer id){

        if (id == null){

            throw new RuntimeException();
        }

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        EditViewModelGet editViewModelGet = new EditViewModelGet();

        editViewModelGet.setTripId(id);

        ServiceResponse<MissionTripItem> missionTripItemServiceResponse = missionTripService.retrieveAllTripInformationByTripId(id);
        if (missionTripItemServiceResponse.hasErrors()){

            throw new RuntimeException();
        }
        editViewModelGet.setTrip(missionTripItemServiceResponse.getResponseObject());

        //retrieve all users for the trip
        ServiceResponse<List<UserItem>> userItemServiceResponse = userService.retrieveUsersByTripId(id);
        if (userItemServiceResponse.hasErrors()){

            throw new RuntimeException();
        }
        editViewModelGet.setUsers(userItemServiceResponse.getResponseObject());

        //retrieve all users in the system
        ServiceResponse<List<UserItem>> allUserItemServiceResponse = userService.retrieveAllUsers();
        if (allUserItemServiceResponse.hasErrors()){

            throw new RuntimeException();
        }
        List<UserItem> allUsers = allUserItemServiceResponse.getResponseObject();
        //allUsers contains the users that will be searchable for adding to a trip.
        //So, remove the ones that already exist in the trip.
        allUsers.removeAll(editViewModelGet.getUsers());
        editViewModelGet.setAllUsers(allUsers);

        return ok(edit.render(currentUser, editViewModelGet, assetsFinder));
    }

    public Result editPost(Integer id){

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        Form<EditViewModelPost> editViewModelPostForm = formFactory.form(EditViewModelPost.class);
        EditViewModelPost editViewModelPost = editViewModelPostForm.bindFromRequest().get();

        if (id != null && editViewModelPost.getNewUsersForTrip() != null){
            ServiceResponse<MissionTripItem> missionTripItemServiceResponse = missionTripService.addUsersToTrip(id, editViewModelPost.getNewUsersForTrip());
            if (missionTripItemServiceResponse.hasErrors()){

                throw new RuntimeException();
            }
        }

        if (id != null && editViewModelPost.getRemoveUsersForTrip() != null){
            ServiceResponse<MissionTripItem> missionTripItemServiceResponse = missionTripService.removeUsersFromTrip(id, editViewModelPost.getRemoveUsersForTrip());
            if (missionTripItemServiceResponse.hasErrors()){

                throw new RuntimeException();
            }
        }

        TripViewModelGet tripViewModel = createTripViewModelGet(null);

        return redirect(routes.TripController.editGet(id));
    }

    public Result citiesGet(){

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        TripViewModelGet tripViewModel = createTripViewModelGet(null);

        return ok(cities.render(currentUser, tripViewModel, assetsFinder));
    }

    public Result citiesPost(){

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        Form<TripViewModelPost> tripViewModelPostForm = formFactory.form(TripViewModelPost.class);
        TripViewModelPost tripViewModelPost = tripViewModelPostForm.bindFromRequest().get();
        List<String> messages = new ArrayList<>();

        ServiceResponse<CityItem> newCityServiceResponse = missionTripService.createNewCity(tripViewModelPost.getNewCity(), tripViewModelPost.getNewCityCountry());
        if (newCityServiceResponse.hasErrors()) {

            messages.addAll(
                    newCityServiceResponse.getErrors()
                            .keySet()
                            .stream()
                            .map(key -> newCityServiceResponse.getErrors().get(key))
                            .collect(Collectors.toList()
                            )
            );
        }

        TripViewModelGet tripViewModel = createTripViewModelGet(messages);

        return ok(cities.render(currentUser, tripViewModel, assetsFinder));
    }

    public Result teamsGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        TripViewModelGet tripViewModel = createTripViewModelGet(null);

        return ok(teams.render(currentUser, tripViewModel, assetsFinder));
    }

    public Result teamsPost() {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        Form<TripViewModelPost> tripViewModelPostForm = formFactory.form(TripViewModelPost.class);
        TripViewModelPost tripViewModelPost = tripViewModelPostForm.bindFromRequest().get();
        List<String> messages = new ArrayList<>();

        TeamItem teamItem = new TeamItem();
        teamItem.setName(tripViewModelPost.getNewTeamName());
        teamItem.setLocation(tripViewModelPost.getNewTeamLocation());
        teamItem.setDescription(tripViewModelPost.getNewTeamDescription());
        ServiceResponse<TeamItem> newTeamItemServiceResponse = missionTripService.createNewTeam(teamItem);
        if (newTeamItemServiceResponse.hasErrors()) {
            messages.addAll(
                    newTeamItemServiceResponse.getErrors()
                            .keySet()
                            .stream()
                            .map(key -> newTeamItemServiceResponse.getErrors().get(key))
                            .collect(Collectors.toList()
                            )
            );
        }



        TripViewModelGet tripViewModel = createTripViewModelGet(messages);

        return ok(teams.render(currentUser, tripViewModel, assetsFinder));
    }

    /**
     * Creates the view model for all views in the superuser/trips views
     *
     * @param messages messages to present the user. Tells them if their actions failed, succeded, blew up the server, etc, may be null
     * @return a populated viewmodel
     */
    private TripViewModelGet createTripViewModelGet(List<String> messages){

        ServiceResponse<List<CityItem>> availableCitiesServiceResponse = missionTripService.retrieveAvailableCities();
        if (availableCitiesServiceResponse.hasErrors())
            throw new RuntimeException();

        ServiceResponse<List<String>> availableCountriesServiceResponse = missionTripService.retrieveAvailableCountries();
        if (availableCountriesServiceResponse.hasErrors())
            throw new RuntimeException();

        ServiceResponse<List<MissionItem>> missionItemServiceResponse = missionTripService.retrieveAllTripInformation();
        if (missionItemServiceResponse.hasErrors())
            throw new RuntimeException();

        TripViewModelGet tripViewModel = new TripViewModelGet();
        tripViewModel.setMissionItems(missionItemServiceResponse.getResponseObject());
        tripViewModel.setAvailableCities(availableCitiesServiceResponse.getResponseObject());
        tripViewModel.setAvailableCountries(availableCountriesServiceResponse.getResponseObject());
        if (messages == null || messages.size() == 0)
            tripViewModel.setMessages(new ArrayList<>());
        else
            tripViewModel.setMessages(messages);

        return tripViewModel;
    }
}
