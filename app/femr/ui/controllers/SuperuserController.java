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
import femr.common.models.TabFieldItem;
import femr.common.models.TabItem;
import femr.business.services.core.ISessionService;
import femr.business.services.core.ICustomTabService;
import femr.common.models.TripItem;
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
    private Form<TripViewModel> tripViewModelForm = Form.form(TripViewModel.class);
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

    public Result tripsGet(){
        CurrentUser currentUser = sessionService.getCurrentUserSession();

        ServiceResponse<TripItem> currentMissionTripServiceResponse = missionTripService.findCurrentMissionTrip();
        if (currentMissionTripServiceResponse.getResponseObject() == null){
            //either a trip doesnt exist or more than one exists
        }else{
            //a trip exists
            TripViewModel tripViewModel = new TripViewModel();
            TripItem tripItem = currentMissionTripServiceResponse.getResponseObject();
            tripViewModel.setTeam(tripItem.getTeam());
            tripViewModel.setTeamLocation(tripItem.getTeamLocation());
            tripViewModel.setCity(tripItem.getCity());
            tripViewModel.setCountry(tripItem.getCountry());
            tripViewModel.setDescription(tripItem.getDescription());
            tripViewModel.setStartDate(tripItem.getTripStartDate());
            tripViewModel.setEndDate(tripItem.getTripEndDate());
            tripViewModelForm = tripViewModelForm.fill(tripViewModel);
        }


        return ok(trips.render(currentUser, tripViewModelForm));
    }

    public Result tripsPost(){
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        Form<TripViewModel> form = tripViewModelForm.bindFromRequest();
        if (form.hasErrors()){

            return badRequest(trips.render(currentUser, form));
        }else{
            TripViewModel tripViewModel = form.bindFromRequest().get();

            TripItem tripItem = new TripItem();
            tripItem.setCity(tripViewModel.getCity());
            tripItem.setCountry(tripViewModel.getCountry());
            tripItem.setTeam(tripViewModel.getTeam());
            tripItem.setTeamLocation(tripViewModel.getTeamLocation());
            tripItem.setDescription(tripViewModel.getDescription());
            tripItem.setTripStartDate(tripViewModel.getStartDate());
            tripItem.setTripEndDate(tripViewModel.getEndDate());

            ServiceResponse<TripItem> tripItemServiceResponse = missionTripService.updateTrip(tripItem);
            if (tripItemServiceResponse.hasErrors()){
                throw new RuntimeException();
            }
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

    public Result typeaheadJSONGet() {

        /*ServiceResponse<String> medicationServiceResponse = typeaheadService.getTripInformation();
        if (medicationServiceResponse.hasErrors()){

        }

        return ok(medicationServiceResponse.getResponseObject());*/
        return ok("");
    }

}
