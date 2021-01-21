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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResearchResultSetItem {

    private float average;
    private float dataRangeLow;
    private float dataRangeHigh;
    private float totalPatients;
    private float totalEncounters;
    private double standardDeviation;

    private String dataType;
    private String unitOfMeasurement;
    private List<ResearchResultItem> dataset;

    // Used for non-number items - map float to String
    private Map<Float, String> primaryValueMap;
    private Map<Float, String> secondaryValueMap;

    public ResearchResultSetItem(){

        // set default statitics
        dataRangeHigh = -1 * Float.MAX_VALUE;
        dataRangeLow = Float.MAX_VALUE;
        average = 0;
        dataType = "";
        unitOfMeasurement = "";

        dataset = new ArrayList<>();
        primaryValueMap = new HashMap<>();
        secondaryValueMap = new HashMap<>();
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public float getDataRangeLow() {
        return dataRangeLow;
    }

    public void setDataRangeLow(float dataRangeLow) {
        this.dataRangeLow = dataRangeLow;
    }

    public float getDataRangeHigh() {
        return dataRangeHigh;
    }

    public void setDataRangeHigh(float dataRangeHigh) {
        this.dataRangeHigh = dataRangeHigh;
    }

    public float getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(float totalPatients) {
        this.totalPatients = totalPatients;
    }

    public float getTotalEncounters() {
        return totalEncounters;
    }

    public void setTotalEncounters(float totalEncounters) {
        this.totalEncounters = totalEncounters;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public List<ResearchResultItem> getDataset() {

        if( dataset == null ){

            return new ArrayList<>();
        }
        return dataset;
    }

    public void setDataset(List<ResearchResultItem> dataset) {
        this.dataset = dataset;
    }

    public Map<Float, String> getPrimaryValueMap() {
        return primaryValueMap;
    }

    public void setPrimaryValueMap(Map<Float, String> primaryValueMap) {
        this.primaryValueMap = primaryValueMap;
    }

    public Map<Float, String> getSecondaryValueMap() {
        return secondaryValueMap;
    }

    public void setSecondaryValueMap(Map<Float, String> secondaryValueMap) {
        this.secondaryValueMap = secondaryValueMap;
    }
}
