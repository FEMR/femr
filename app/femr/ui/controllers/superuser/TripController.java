package femr.ui.controllers.superuser;

import com.google.inject.Inject;
import femr.business.services.core.IMissionTripService;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.CityItem;
import femr.common.models.MissionItem;
import femr.common.models.TeamItem;
import femr.common.models.TripItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.superuser.trips.TripViewModelGet;
import femr.ui.models.superuser.trips.TripViewModelPost;
import femr.ui.views.html.superuser.trips.*;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.SUPERUSER})
public class TripController extends Controller {

    private Form<TripViewModelPost> tripViewModelPostForm = Form.form(TripViewModelPost.class);
    private final IMissionTripService missionTripService;
    private final ISessionService sessionService;

    @Inject
    public TripController(IMissionTripService missionTripService,
                               ISessionService sessionService) {

        this.missionTripService = missionTripService;
        this.sessionService = sessionService;
    }

    public Result manageGet(){

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        TripViewModelGet tripViewModel = createViewModel();

        return ok(manage.render(currentUser, tripViewModel));
    }

    public Result managePost(){

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        TripViewModelPost tripViewModelPost = tripViewModelPostForm.bindFromRequest().get();

        //create a new trip if the user has entered the information
        if (StringUtils.isNotNullOrWhiteSpace(tripViewModelPost.getNewTripTeamName()) &&
                StringUtils.isNotNullOrWhiteSpace(tripViewModelPost.getNewTripCountry()) &&
                StringUtils.isNotNullOrWhiteSpace(tripViewModelPost.getNewTripCity()) &&
                tripViewModelPost.getNewTripStartDate() != null &&
                tripViewModelPost.getNewTripEndDate() != null) {

            TripItem tripItem = new TripItem();
            tripItem.setTeamName(tripViewModelPost.getNewTripTeamName());
            tripItem.setTripCity(tripViewModelPost.getNewTripCity());
            tripItem.setTripCountry(tripViewModelPost.getNewTripCountry());
            tripItem.setTripStartDate(tripViewModelPost.getNewTripStartDate());
            tripItem.setTripEndDate(tripViewModelPost.getNewTripEndDate());
            ServiceResponse<TripItem> newTripItemServiceResponse = missionTripService.createNewTrip(tripItem);
            if (newTripItemServiceResponse.hasErrors())
                throw new RuntimeException();
        }

        TripViewModelGet tripViewModel = createViewModel();

        return ok(manage.render(currentUser, tripViewModel));
    }

    public Result citiesGet(){

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        TripViewModelGet tripViewModel = createViewModel();

        return ok(cities.render(currentUser, tripViewModel));
    }

    public Result citiesPost(){

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        TripViewModelPost tripViewModelPost = tripViewModelPostForm.bindFromRequest().get();

        //Create a new city if the user has entered the city and country
        if (StringUtils.isNotNullOrWhiteSpace(tripViewModelPost.getNewCity()) &&
                StringUtils.isNotNullOrWhiteSpace(tripViewModelPost.getNewCityCountry())) {

            ServiceResponse<CityItem> newCityServiceResponse = missionTripService.createNewCity(tripViewModelPost.getNewCity(), tripViewModelPost.getNewCityCountry());
            if (newCityServiceResponse.hasErrors())
                throw new RuntimeException();
        }

        TripViewModelGet tripViewModel = createViewModel();

        return ok(cities.render(currentUser, tripViewModel));
    }

    public Result teamsGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        TripViewModelGet tripViewModel = createViewModel();

        return ok(teams.render(currentUser, tripViewModel));
    }

    public Result teamsPost() {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        TripViewModelPost tripViewModelPost = tripViewModelPostForm.bindFromRequest().get();


        //Create a new team if the user has entered a team name
        if (StringUtils.isNotNullOrWhiteSpace(tripViewModelPost.getNewTeamName())) {

            TeamItem teamItem = new TeamItem();
            teamItem.setName(tripViewModelPost.getNewTeamName());
            teamItem.setLocation(tripViewModelPost.getNewTeamLocation());
            teamItem.setDescription(tripViewModelPost.getNewTeamDescription());
            ServiceResponse<TeamItem> newTeamItemServiceResponse = missionTripService.createNewTeam(teamItem);
            if (newTeamItemServiceResponse.hasErrors())
                throw new RuntimeException();

        }



        TripViewModelGet tripViewModel = createViewModel();

        return ok(teams.render(currentUser, tripViewModel));
    }

    private TripViewModelGet createViewModel(){

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

        return tripViewModel;
    }

}
