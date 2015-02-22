package femr.ui.models.research.json;

import java.util.List;
import java.util.Map;

//technically shared in the service layer right now
public class ResearchGraphDataModel {

    private float average;
    private float median;
    private float rangeLow;
    private float rangeHigh;
    private float total;
    private List<ResearchItemModel> graphData;
    private String xAxisTitle;
    private String yAxisTitle;
    private String unitOfMeasurement;
    private Map<Float, String> primaryValuemap;
    private Map<Float, String> secondaryValuemap;

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public float getMedian() {
        return median;
    }

    public void setMedian(float median) {
        this.median = median;
    }

    public float getRangeLow() {
        return rangeLow;
    }

    public void setRangeLow(float rangeLow) {
        this.rangeLow = rangeLow;
    }

    public float getRangeHigh() {
        return rangeHigh;
    }

    public void setRangeHigh(float rangeHigh) {
        this.rangeHigh = rangeHigh;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getxAxisTitle() {
        return xAxisTitle;
    }

    public void setxAxisTitle(String xAxisTitle) {
        this.xAxisTitle = xAxisTitle;
    }

    public String getyAxisTitle() { return yAxisTitle; }

    public void setyAxisTitle(String yAxisTitle) { this.yAxisTitle = yAxisTitle; }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public List<ResearchItemModel> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<ResearchItemModel> graphData) {
        this.graphData = graphData;
    }

    public Map<Float, String> getPrimaryValuemap() { return primaryValuemap; }

    public void setPrimaryValuemap(Map<Float, String> primaryValuemap) { this.primaryValuemap = primaryValuemap; }

    public Map<Float, String> getSecondaryValuemap() { return secondaryValuemap; }

    public void setSecondaryValuemap(Map<Float, String> secondaryValuemap) { this.secondaryValuemap = secondaryValuemap; }
}
