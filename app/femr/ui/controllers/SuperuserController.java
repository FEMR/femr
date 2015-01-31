/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.services.core.IMissionTripService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.business.services.core.ISessionService;
import femr.business.services.core.ICustomTabService;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.superuser.*;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import femr.ui.views.html.superuser.index;
import femr.ui.views.html.superuser.tabs;
import femr.ui.views.html.superuser.fields;
import femr.ui.views.html.superuser.trips;

import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.SUPERUSER})
public class SuperuserController extends Controller {
    private final Form<TabsViewModelPost> TabsViewModelForm = Form.form(TabsViewModelPost.class);
    private final Form<ContentViewModelPost> ContentViewModelForm = Form.form(ContentViewModelPost.class);
    private Form<TripViewModelPost> tripViewModelPostForm = Form.form(TripViewModelPost.class);
    private final ICustomTabService customTabService;
    private final IMissionTripService missionTripService;
    private final ISessionService sessionService;

    @Inject
    public SuperuserController(ICustomTabService customTabService,
                               IMissionTripService missionTripService,
                               ISessionService sessionService) {

        this.customTabService = customTabService;
        this.missionTripService = missionTripService;
        this.sessionService = sessionService;
    }

    public Result indexGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        return ok(index.render(currentUser));
    }

    public Result tripsGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<List<MissionItem>> missionItemServiceResponse = missionTripService.findAllTripInformation();
        if (missionItemServiceResponse.hasErrors())
            throw new RuntimeException();

        ServiceResponse<List<String>> availableTeamsServiceResponse = missionTripService.findAvailableTeams();
        if (availableTeamsServiceResponse.hasErrors())
            throw new RuntimeException();

        ServiceResponse<List<CityItem>> availableCitiesServiceResponse = missionTripService.findAvailableCities();
        if (availableCitiesServiceResponse.hasErrors())
            throw new RuntimeException();

        ServiceResponse<List<String>> availableCountriesServiceResponse = missionTripService.findAvailableCountries();
        if (availableCountriesServiceResponse.hasErrors())
            throw new RuntimeException();

        TripViewModelGet tripViewModel = new TripViewModelGet();
        tripViewModel.setMissionItems(missionItemServiceResponse.getResponseObject());
        tripViewModel.setAvailableTeams(availableTeamsServiceResponse.getResponseObject());
        tripViewModel.setAvailableCities(availableCitiesServiceResponse.getResponseObject());
        tripViewModel.setAvailableCountries(availableCountriesServiceResponse.getResponseObject());


        return ok(trips.render(currentUser, tripViewModel));
    }

    public Result toggleCurrentTripPost(int tripId) {

        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<TripItem> tripItemUpdateServiceResponse = missionTripService.updateCurrentTrip(tripId);
        if (tripItemUpdateServiceResponse.hasErrors()) {
            throw new RuntimeException();
        }

        return ok(index.render(currentUser));
    }

    public Result tripsPost() {

        CurrentUser currentUser = sessionService.getCurrentUserSession();
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

        return ok(index.render(currentUser));
    }

    public Result tabsGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ServiceResponse<List<TabItem>> response;

        response = customTabService.getCustomTabs(false);
        if (response.hasErrors()) {
            throw new RuntimeException();
        }

        TabsViewModelGet viewModelGet = new TabsViewModelGet();
        viewModelGet.setCurrentTabs(response.getResponseObject());

        //get deleted tabs
        response = customTabService.getCustomTabs(true);
        if (response.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setDeletedTabs(response.getResponseObject());

        return ok(tabs.render(currentUser, viewModelGet));
    }

    public Result tabsPost() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        TabsViewModelPost viewModelPost = TabsViewModelForm.bindFromRequest().get();

        //becomes new or edit
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAddTabName())) {
            TabItem tabItem = new TabItem();
            //new
            if (!customTabService.doesTabExist(viewModelPost.getAddTabName()).getResponseObject()) {
                tabItem.setName(viewModelPost.getAddTabName());
                if (viewModelPost.getAddTabLeft() != null) tabItem.setLeftColumnSize(viewModelPost.getAddTabLeft());
                if (viewModelPost.getAddTabRight() != null) tabItem.setRightColumnSize(viewModelPost.getAddTabRight());
                ServiceResponse<TabItem> response = customTabService.createTab(tabItem, currentUser.getId());
                if (response.hasErrors()) {
                    throw new RuntimeException();
                }
            } else {//edit
                if (viewModelPost.getAddTabLeft() == null) tabItem.setLeftColumnSize(0);
                else tabItem.setLeftColumnSize(viewModelPost.getAddTabLeft());

                if (viewModelPost.getAddTabRight() == null) tabItem.setRightColumnSize(0);
                else tabItem.setRightColumnSize(viewModelPost.getAddTabRight());

                tabItem.setName(viewModelPost.getAddTabName());
                ServiceResponse<TabItem> response = customTabService.editTab(tabItem, currentUser.getId());
                if (response.hasErrors()) {
                    throw new RuntimeException();
                }
            }
        }
        //becomes toggle
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getDeleteTab())) {
            ServiceResponse<TabItem> response = customTabService.toggleTab(viewModelPost.getDeleteTab());
            if (response.hasErrors()) {
                throw new RuntimeException();
            }
        }

        return redirect("/superuser/tabs");
    }

    //name = tab name
    public Result contentGet(String name) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ContentViewModelGet viewModelGet = new ContentViewModelGet();

        viewModelGet.setName(name);

        //get current custom fields
        ServiceResponse<List<TabFieldItem>> currentFieldItemsResponse = customTabService.getTabFieldsByTabName(name, false);
        if (currentFieldItemsResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setCurrentCustomFieldItemList(currentFieldItemsResponse.getResponseObject());

        //get removed custom fields
        ServiceResponse<List<TabFieldItem>> removedFieldItemsResponse = customTabService.getTabFieldsByTabName(name, true);
        if (currentFieldItemsResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setRemovedCustomFieldItemList(removedFieldItemsResponse.getResponseObject());

        //get available field types
        ServiceResponse<List<String>> fieldTypesResponse = customTabService.getTypes();
        if (fieldTypesResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setCustomFieldTypes(fieldTypesResponse.getResponseObject());

        //get available fields sizes
        ServiceResponse<List<String>> fieldSizesResponse = customTabService.getSizes();
        if (fieldSizesResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setCustomFieldSizes(fieldSizesResponse.getResponseObject());

        return ok(fields.render(currentUser, viewModelGet));
    }

    //name = tab name
    public Result contentPost(String name) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ContentViewModelPost viewModelPost = ContentViewModelForm.bindFromRequest().get();

        //adding/editing a field
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAddName()) && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAddSize()) && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAddType())) {
            TabFieldItem tabFieldItem = new TabFieldItem();
            tabFieldItem.setName(viewModelPost.getAddName());
            tabFieldItem.setSize(viewModelPost.getAddSize().toLowerCase());
            tabFieldItem.setType(viewModelPost.getAddType().toLowerCase());
            tabFieldItem.setOrder(viewModelPost.getAddOrder());
            tabFieldItem.setPlaceholder(viewModelPost.getAddPlaceholder());
            //edit
            if (customTabService.doesTabFieldExist(viewModelPost.getAddName()).getResponseObject()) {
                customTabService.editTabField(tabFieldItem);
            } else {

                ServiceResponse<TabFieldItem> response = customTabService.createTabField(tabFieldItem, currentUser.getId(), name);
                if (response.hasErrors()) {
                    throw new RuntimeException();
                }
            }
        }
        //deactivating a field
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getToggleName())) {
            ServiceResponse<TabFieldItem> response = customTabService.toggleTabField(viewModelPost.getToggleName(), name);
            if (response.hasErrors()) {
                throw new RuntimeException();
            }
        }

        return redirect("/superuser/tabs/" + name);
    }

}
