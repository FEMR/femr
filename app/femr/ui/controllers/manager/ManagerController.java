package femr.ui.controllers.manager;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MissionTripItem;
import femr.common.models.PatientEncounterItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.manager.IndexViewModelGet;

import femr.ui.views.html.manager.index;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.MANAGER})
public class ManagerController extends Controller {

    private final AssetsFinder assetsFinder;
    private final ISessionService sessionService;
    private final IEncounterService encounterService;
    private final IMissionTripService missionTripService;


    @Inject
    public ManagerController(AssetsFinder assetsFinder,
                             ISessionService sessionService,
                             IEncounterService encounterService,
                             IMissionTripService missionTripService) {

        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
        this.encounterService = encounterService;
        this.missionTripService = missionTripService;
    }

    public Result indexGet() {

//declares empty array lists  view model
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        IndexViewModelGet viewModel = new IndexViewModelGet();
        //if the user is not assigned to a trip renders outpage, with message to user
        if (currentUser.getTripId() == null) {

            return ok(index.render(currentUser, viewModel, assetsFinder));
        }

        //Get the list of patient encounters that were created today, for the current trip and set them in the viewmodel
        ServiceResponse<List<PatientEncounterItem>> patientEncounter = encounterService.retrieveCurrentDayPatientEncounters(currentUser.getTripId());

        viewModel.setPatientEncounter(patientEncounter.getResponseObject());

        //Get the mission trip and set the name of it in the viewmodel
        ServiceResponse<MissionTripItem> missionTripItemServiceResponse = missionTripService.retrieveAllTripInformationByTripId(currentUser.getTripId());
        if (missionTripItemServiceResponse.hasErrors()){

            throw new RuntimeException();
        }

        viewModel.setUserFriendlyTrip(
                StringUtils.generateMissionTripTitle(
                missionTripItemServiceResponse.getResponseObject().getTeamName(),
                missionTripItemServiceResponse.getResponseObject().getTripCountry(),
                missionTripItemServiceResponse.getResponseObject().getTripStartDate(),
                missionTripItemServiceResponse.getResponseObject().getTripEndDate()
                )
        );


        //Get the current day to show the user what day the patients are being displayed for
        viewModel.setUserFriendlyDate(dateUtils.getFriendlyInternationalDate(DateTime.now().toDate()));

        return ok(index.render(currentUser, viewModel, assetsFinder));

    }


}


