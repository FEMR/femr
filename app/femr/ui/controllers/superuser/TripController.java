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

        return ok(manage.render(currentUser));

    }

    public Result menuGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        ServiceResponse<List<MissionItem>> missionItemServiceResponse = missionTripService.retrieveAllTripInformation();
        if (missionItemServiceResponse.hasErrors())
            throw new RuntimeException();

        ServiceResponse<List<String>> availableTeamsServiceResponse = missionTripService.retrieveAvailableTeams();
        if (availableTeamsServiceResponse.hasErrors())
            throw new RuntimeException();

        ServiceResponse<List<CityItem>> availableCitiesServiceResponse = missionTripService.retrieveAvailableCities();
        if (availableCitiesServiceResponse.hasErrors())
            throw new RuntimeException();

        ServiceResponse<List<String>> availableCountriesServiceResponse = missionTripService.retrieveAvailableCountries();
        if (availableCountriesServiceResponse.hasErrors())
            throw new RuntimeException();

        TripViewModelGet tripViewModel = new TripViewModelGet();
        tripViewModel.setMissionItems(missionItemServiceResponse.getResponseObject());
        tripViewModel.setAvailableTeams(availableTeamsServiceResponse.getResponseObject());
        tripViewModel.setAvailableCities(availableCitiesServiceResponse.getResponseObject());
        tripViewModel.setAvailableCountries(availableCountriesServiceResponse.getResponseObject());


        return ok(menu.render(currentUser, tripViewModel));
    }

    public Result menuPost() {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        TripViewModelPost tripViewModelPost = tripViewModelPostForm.bindFromRequest().get();

        //creating a new team or trip or city-

        //Create a new city if the user has entered the city and country
        if (StringUtils.isNotNullOrWhiteSpace(tripViewModelPost.getNewCity()) &&
                StringUtils.isNotNullOrWhiteSpace(tripViewModelPost.getNewCityCountry())) {

            ServiceResponse<CityItem> newCityServiceResponse = missionTripService.createNewCity(tripViewModelPost.getNewCity(), tripViewModelPost.getNewCityCountry());
            if (newCityServiceResponse.hasErrors())
                throw new RuntimeException();
        }

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

        return ok(femr.ui.views.html.superuser.index.render(currentUser));
    }
}
