package femr.ui.models.research;

import play.data.validation.ValidationError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FilterViewModel {

    private String primaryDataset;
    private String secondaryDataset;
    private String graphType;
    private String startDate;
    private String endDate;
    private boolean groupPrimary;
    private Integer groupFactor;
    private Float rangeStart;
    private Float rangeEnd;
    private Integer medicationId;
    private Map<Integer, String> medicationsList;

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

    public Float getRangeStart() { return rangeStart; }

    public void setRangeStart(Float rangeStart) { this.rangeStart = rangeStart; }

    public Float getRangeEnd() { return rangeEnd; }

    public void setRangeEnd(Float rangeEnd) { this.rangeEnd = rangeEnd; }

    public Integer getMedicationId() { return medicationId; }

    public void setMedicationId(Integer medicationId) { this.medicationId = medicationId; }

    public Map<Integer, String> getMedicationsList() { return medicationsList; }

    public void setMedicationsList(Map<Integer, String> medicationsList) { this.medicationsList = medicationsList; }
}
