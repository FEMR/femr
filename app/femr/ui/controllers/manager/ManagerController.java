package femr.ui.controllers.manager;

import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.*;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.DailyReportItem;
import femr.common.models.MissionTripItem;
import femr.common.models.WhoReportConfigItem;
import femr.common.models.PatientEncounterItem;
import femr.common.models.SettingItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.manager.DailyReportViewModelGet;
import femr.ui.models.manager.IndexViewModelGet;
import femr.ui.views.html.manager.dailyReport;
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
    private final IDailyReportService dailyReportService;
    private final ISearchService searchService;


    @Inject
    public ManagerController(AssetsFinder assetsFinder,
                             ISessionService sessionService,
                             IEncounterService encounterService,
                             IMissionTripService missionTripService,
                             IDailyReportService dailyReportService,
                             ISearchService searchService) {

        this.assetsFinder = assetsFinder;
        this.sessionService = sessionService;
        this.encounterService = encounterService;
        this.missionTripService = missionTripService;
        this.dailyReportService = dailyReportService;
        this.searchService = searchService;
    }

    public Result indexGet(String date) {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        IndexViewModelGet viewModel = new IndexViewModelGet();

        if (currentUser.getTripId() == null) {
            return ok(index.render(currentUser, viewModel, assetsFinder));
        }

        DateTime selectedDate = parseDate(date);

        ServiceResponse<List<PatientEncounterItem>> patientEncounter = encounterService.retrievePatientEncountersForDate(currentUser.getTripId(), selectedDate);
        viewModel.setPatientEncounter(patientEncounter.getResponseObject());

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

        viewModel.setIsoDate(selectedDate.toString("yyyy-MM-dd"));

        ServiceResponse<SettingItem> settingsResponse = searchService.retrieveSystemSettings();
        viewModel.setSettings(settingsResponse.getResponseObject());

        return ok(index.render(currentUser, viewModel, assetsFinder));

    }

    public Result dailyReportGet(String date) {

        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        DailyReportViewModelGet viewModel = new DailyReportViewModelGet();

        if (currentUser.getTripId() == null) {
            return redirect(femr.ui.controllers.manager.routes.ManagerController.indexGet(null));
        }

        ServiceResponse<SettingItem> settingsResponse = searchService.retrieveSystemSettings();
        SettingItem settings = settingsResponse.getResponseObject();
        if (settings == null || !settings.isWhoReporting()) {
            return redirect(femr.ui.controllers.manager.routes.ManagerController.indexGet(null));
        }

        ServiceResponse<MissionTripItem> missionTripResponse = missionTripService.retrieveAllTripInformationByTripId(currentUser.getTripId());
        if (!missionTripResponse.hasErrors()) {
            viewModel.setUserFriendlyTrip(
                    StringUtils.generateMissionTripTitle(
                            missionTripResponse.getResponseObject().getTeamName(),
                            missionTripResponse.getResponseObject().getTripCountry(),
                            missionTripResponse.getResponseObject().getTripStartDate(),
                            missionTripResponse.getResponseObject().getTripEndDate()
                    )
            );
        }

        DateTime selectedDate = parseDate(date);
        ServiceResponse<DailyReportItem> reportResponse = dailyReportService.generateDailyReport(currentUser.getTripId(), selectedDate);
        if (!reportResponse.hasErrors()) {
            viewModel.setReportItem(reportResponse.getResponseObject());
        } else {
            viewModel.setReportItem(new DailyReportItem());
        }

        ServiceResponse<WhoReportConfigItem> whoConfigResponse = dailyReportService.getWhoReportConfig(currentUser.getTripId());
        viewModel.setWhoConfig(whoConfigResponse.hasErrors() ? new WhoReportConfigItem() : whoConfigResponse.getResponseObject());

        return ok(dailyReport.render(currentUser, viewModel, assetsFinder));
    }

    private DateTime parseDate(String date) {
        if (date != null && !date.isEmpty()) {
            try {
                return org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(date);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return DateTime.now();
    }


}


