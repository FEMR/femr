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
package femr.ui.models.research;


import femr.data.models.mysql.MissionCity;
import femr.data.models.mysql.User;
import play.data.Form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilterViewModel {

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
    private String MissionTripName; //Andrew Fix
    private ArrayList<String> list;
    Form<MissionCity> userForm = Form.form(MissionCity.class);
    Form test;


//        Form<User> userForm = Form.form(User.class);
//
//        Map<String,String> anyData = new HashMap();
//        ArrayList<String> list = new ArrayList<>();
//        anyData.put("email", "bob@gmail.com");
//        anyData.put("password", "secret");
//
//        User user = userForm.bind(anyData).get(); //Andrew Change


    /*
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<ValidationError>();

        if (primaryDataset == null || primaryDataset.length() == 0 ) {
            errors.add(new ValidationError("primaryDataset", "Choose a primary dataset"));
        }

        Date startDateObj;
        Date endDateObj;
        Date today = new Date();
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            // Set Start Date to start of day
            String startParseDate = startDate + " 00:00:00";
            startDateObj = sqlFormat.parse(startParseDate);

            // Set End Date to end of day
            String parseEndDate = endDate + " 23:59:59";
            endDateObj = sqlFormat.parse(parseEndDate);

        }
        catch(ParseException e){

            startDateObj = new Date();
            endDateObj = new Date();
        }

        if( startDateObj.getTime() > endDateObj.getTime() ){

            errors.add(new ValidationError("startDate", "Start Date cannot be after End Date"));
        }
        if( startDateObj.getTime() > today.getTime() ){

            errors.add(new ValidationError("startDate", "Start Date cannot be in the future"));
        }
        if( endDateObj.getTime() > today.getTime() ){

            errors.add(new ValidationError("endDate", "End Date cannot be in the future"));
        }

        return errors.isEmpty() ? null : errors;
    }
    */

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

    public void setFilterRangeStart(Float filterRangeStart) { this.filterRangeStart = filterRangeStart; }

    public Float getFilterRangeEnd() { return filterRangeEnd; }

    public void setFilterRangeEnd(Float filterRangeEnd) { this.filterRangeEnd = filterRangeEnd; }

    public String getMedicationName() { return medicationName; }

    public void setMedicationName(String medicationId) { this.medicationName = medicationId; }

    public String getMissionTripName() { return MissionTripName; } //Andrew Fix

    public void setMissionTripName(String cityName) { this.MissionTripName = cityName; } //Andrew Fix

    public ArrayList<String> getList(){
        return list;
    }

    public void setList(ArrayList<String> list){
        this.list = list;
    }

    public Form<MissionCity> getUser(){
        return userForm;
    }

    public void setUser(Form<MissionCity> user){
        this.userForm = user;
    }


}

