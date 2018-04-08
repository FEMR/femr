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

import com.google.gson.Gson;
import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.IMissionTripService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.models.mysql.*;
import femr.ui.models.research.json.ResearchGraphDataModel;
import femr.common.dtos.CurrentUser;
import femr.business.services.core.IResearchService;
import femr.business.services.core.ISessionService;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.research.json.ResearchItemModel;
import femr.ui.views.html.research.index;
import femr.ui.models.research.FilterViewModel;
import femr.util.stringhelpers.StringUtils;
import org.apache.commons.text.WordUtils;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ResearchController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private IResearchService researchService;
    private ISessionService sessionService;
    private IMissionTripService missionTripService; //Andrew Trip Filter

    /**
     * Research Controller constructor that Injects the services indicated by the parameters
     *
     * @param sessionService    {@link ISessionService}
     * @param researchService   {@link IResearchService}
     */
    @Inject
    public ResearchController(AssetsFinder assetsFinder, FormFactory formFactory, ISessionService sessionService, IResearchService researchService, IMissionTripService missionTripService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.researchService = researchService;
        this.sessionService = sessionService;
        this.missionTripService = missionTripService; //Andrew Trip Filter
    }

    public Result indexGet() {

        FilterViewModel filterViewModel = new FilterViewModel();

        //Grabbing mission city ID's Andrew Trip Filter
        ServiceResponse<List<MissionItem>> missionItemServiceResponse = missionTripService.retrieveAllTripInformation();
        if (missionItemServiceResponse.hasErrors())
            throw new RuntimeException();
        filterViewModel.setMissionTrips(missionItemServiceResponse.getResponseObject());

        // Set Default Start (30 Days Ago) and End Date (Today)
        Calendar today = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        filterViewModel.setEndDate(dateFormat.format(today.getTime()));
        today.add(Calendar.DAY_OF_MONTH, -120);
        filterViewModel.setStartDate(dateFormat.format(today.getTime()));


        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();

        return ok(index.render(currentUserSession, filterViewModel, assetsFinder));
    }

    /**
     * Called when user clicks apply on the selected filters.
     */
    public Result indexPost() {

        final Form<FilterViewModel> FilterViewModelForm = formFactory.form(FilterViewModel.class);
        FilterViewModel filterViewModel = FilterViewModelForm.bindFromRequest().get();
        ResearchFilterItem researchFilterItem = createResearchFilterItem(filterViewModel);

        ServiceResponse<ResearchResultSetItem> response = researchService.retrieveGraphData(researchFilterItem);
        ResearchGraphDataModel graphModel = new ResearchGraphDataModel();
        if (!response.hasErrors()) {

            ResearchResultSetItem results = response.getResponseObject();
            graphModel = buildGraphModel(results);
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(graphModel);
        return ok(jsonString);
    }

    /**
     * Called when a user wants to export the data to a CSV file.
     */
    public Result exportPost() {

        final Form<FilterViewModel> FilterViewModelForm = formFactory.form(FilterViewModel.class);
        FilterViewModel filterViewModel = FilterViewModelForm.bindFromRequest().get();

        ResearchFilterItem filterItem = createResearchFilterItem(filterViewModel);

        // This does weird stuff and isn't reliable.
        //ServiceResponse<File> exportServiceResponse = researchService.retrieveCsvExportFile(filterItem);
        ServiceResponse<File> exportServiceResponse = researchService.exportPatientsByTrip(filterItem.getMissionTripId());

        File csvFile = exportServiceResponse.getResponseObject();

        response().setHeader("Content-disposition", "attachment; filename=" + csvFile.getName());

        return ok(csvFile).as("application/x-download");
    }

    /**
     * Generate and provide an instance of ResearchFilterItem.
     * Moved from an implementation of IItemModelMapper on 6-10-2015 by Kevin
     *
     * @param filterViewModel a viewmodel, not null
     * @return ResearchFilterItem or null if processing fails
     */
    private ResearchFilterItem createResearchFilterItem(FilterViewModel filterViewModel) {

        if (filterViewModel == null) {

            return null;
        }

        ResearchFilterItem filterItem = new ResearchFilterItem();

        filterItem.setPrimaryDataset(filterViewModel.getPrimaryDataset());
        filterItem.setSecondaryDataset(filterViewModel.getSecondaryDataset());
        filterItem.setGraphType(filterViewModel.getGraphType());
        filterItem.setStartDate(filterViewModel.getStartDate());
        filterItem.setEndDate(filterViewModel.getEndDate());

        Integer groupFactor = filterViewModel.getGroupFactor();
        filterItem.setGroupFactor(groupFactor);
        if (groupFactor != null && groupFactor > 0) {

            filterItem.setGroupPrimary(filterViewModel.isGroupPrimary());
        } else {

            filterItem.setGroupPrimary(false);
        }

        filterItem.setFilterRangeStart(filterViewModel.getFilterRangeStart());
        filterItem.setFilterRangeEnd(filterViewModel.getFilterRangeEnd());
        filterItem.setMedicationName(filterViewModel.getMedicationName());
        filterItem.setMissionTripId(filterViewModel.getMissionTripId()); //Andrew Trip Filter




        return filterItem;
    }

    private ResearchGraphDataModel buildGraphModel(ResearchResultSetItem results) {

        ResearchGraphDataModel graphModel = new ResearchGraphDataModel();

        graphModel.setAverage(results.getAverage());
        if (results.getDataRangeLow() > -1 * Float.MAX_VALUE) {
            graphModel.setRangeLow(results.getDataRangeLow());
        } else {
            graphModel.setRangeLow(0.0f);
        }
        if (results.getDataRangeHigh() < Float.MAX_VALUE) {
            graphModel.setRangeHigh(results.getDataRangeHigh());
        } else {
            graphModel.setRangeHigh(0.0f);
        }
        graphModel.setTotalPatients(results.getTotalPatients());
        graphModel.setTotalEncounters(results.getTotalEncounters());

        graphModel.setUnitOfMeasurement(results.getUnitOfMeasurement());
        graphModel.setxAxisTitle(WordUtils.capitalize(StringUtils.splitCamelCase(results.getDataType())));
        graphModel.setyAxisTitle("Number of Patients");

        graphModel.setPrimaryValuemap(results.getPrimaryValueMap());
        graphModel.setSecondaryValuemap(results.getSecondaryValueMap());

        // @TODO - This go in item mapper?
        List<ResearchItemModel> graphData = new ArrayList<>();
        for (ResearchResultItem item : results.getDataset()) {

            ResearchItemModel resultItem = new ResearchItemModel();
            resultItem.setPrimaryName(item.getPrimaryName());
            resultItem.setPrimaryValue(item.getPrimaryValue());
            resultItem.setSecondaryData(item.getSecondaryData());
            graphData.add(resultItem);
        }
        graphModel.setGraphData(graphData);

        return graphModel;
    }
}
