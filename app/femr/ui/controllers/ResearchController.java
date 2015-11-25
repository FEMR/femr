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
import femr.business.services.core.IMedicationService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.ResearchFilterItem;
import femr.common.models.ResearchResultItem;
import femr.common.models.ResearchResultSetItem;
import femr.ui.models.research.json.ResearchGraphDataModel;
import femr.common.dtos.CurrentUser;
import femr.business.services.core.IResearchService;
import femr.business.services.core.ISessionService;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.research.json.ResearchItemModel;
import femr.ui.views.html.research.index;
import femr.ui.models.research.FilterViewModel;
import femr.util.stringhelpers.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ResearchController extends Controller {

    private final Form<FilterViewModel> FilterViewModelForm = Form.form(FilterViewModel.class);

    private IResearchService researchService;
    private IMedicationService medicationService;
    private ISessionService sessionService;

    /**
     * Research Controller constructor that Injects the services indicated by the parameters
     *
     * @param sessionService    {@link ISessionService}
     * @param researchService   {@link IResearchService}
     * @param medicationService {@link IMedicationService}
     */
    @Inject
    public ResearchController(ISessionService sessionService, IResearchService researchService, IMedicationService medicationService) {
        this.researchService = researchService;
        this.medicationService = medicationService;
        this.sessionService = sessionService;
    }

    public Result indexGet() {

        FilterViewModel filterViewModel = new FilterViewModel();

        // Set Default Start (30 Days Ago) and End Date (Today)
        Calendar today = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        filterViewModel.setEndDate(dateFormat.format(today.getTime()));
        today.add(Calendar.DAY_OF_MONTH, -120);
        filterViewModel.setStartDate(dateFormat.format(today.getTime()));

        CurrentUser currentUserSession = sessionService.retrieveCurrentUserSession();
        return ok(index.render(currentUserSession, filterViewModel));
    }

    /**
     * Called when user clicks apply on the selected filters.
     */
    public Result indexPost() {

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

        FilterViewModel filterViewModel = FilterViewModelForm.bindFromRequest().get();

        ResearchFilterItem filterItem = createResearchFilterItem(filterViewModel);

        ServiceResponse<File> exportServiceResponse = researchService.retrieveCsvExportFile(filterItem);
        File csvFile = exportServiceResponse.getResponseObject();

        response().setContentType("application/x-download");
        response().setHeader("Content-disposition", "attachment; filename=" + csvFile.getName());

        return ok(csvFile);
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
