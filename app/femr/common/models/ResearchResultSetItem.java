package femr.common.models;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResearchResultSetItem {

    private float average;
    private float dataRangeLow;
    private float dataRangeHigh;
    private float total;
    private double standardDeviation;

    // @TODO -- is this really needed? Controller will know what it asked for
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
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
