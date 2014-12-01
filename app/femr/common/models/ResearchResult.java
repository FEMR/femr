package femr.common.models;


import java.util.HashMap;
import java.util.Map;

public class ResearchResult {

    private String dataType;
    private String unitOfMeasurement;
    private Map<Integer, Float> dataset;

    // Used for non-number items - map float to String
    private Map<Float, String> valueMap;

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

    public Map<Integer, Float> getDataset() {

        if( dataset == null ){

            return new HashMap<Integer, Float>();
        }
        return dataset;
    }

    public void setDataset(Map<Integer, Float> dataset) {
        this.dataset = dataset;
    }

    public Map<Float, String> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<Float, String> valueMap) {
        this.valueMap = valueMap;
    }
}
