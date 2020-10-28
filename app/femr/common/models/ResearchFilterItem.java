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
package femr.common.models;

import java.util.List;

public class ResearchFilterItem {

    private String primaryDataset;
    private String secondaryDataset;
    private String graphType;
    private String startDate;
    private String endDate;
    private boolean groupPrimary;
    private Integer groupFactor;
    private Float filterRangeStart;
    private Float filterRangeEnd;
    private String medicationName;
    private String orderBy;
    private Integer MissionTripId; // Andrew Trip Filter

    public String getPrimaryDataset() {
        return primaryDataset;
    }

    public void setPrimaryDataset(String primaryDataset) {
        this.primaryDataset = primaryDataset;
    }

    public String getSecondaryDataset() {
        return secondaryDataset;
    }

    public void setSecondaryDataset(String secondaryDataset) {
        this.secondaryDataset = secondaryDataset;
    }

    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isGroupPrimary() { return groupPrimary; }

    public void setGroupPrimary(boolean groupPrimary) { this.groupPrimary = groupPrimary; }

    public Integer getGroupFactor() { return groupFactor; }

    public void setGroupFactor(Integer groupFactor) { this.groupFactor = groupFactor; }

    public Float getFilterRangeStart() { return filterRangeStart; }

    public void setFilterRangeStart(Float filterRangeStart) {

        if (filterRangeStart == null) {

            this.filterRangeStart = -1 * Float.MAX_VALUE;

        } else {

            this.filterRangeStart = filterRangeStart;
        }
    }

    public Float getFilterRangeEnd() { return filterRangeEnd; }

    public void setFilterRangeEnd(Float filterRangeEnd) {

        if (filterRangeEnd == null) {

            this.filterRangeEnd = Float.MAX_VALUE;

        } else {

            this.filterRangeEnd = filterRangeEnd;
        }
    }

    public String getMedicationName() { return medicationName; }

    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }

    public Integer getMissionTripId() { return MissionTripId; } //Andrew Trip Filter

    public void setMissionTripId(Integer MissionTripId) { this.MissionTripId = MissionTripId; } //Andrew Trip Filter

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
