package femr.common.models;


public class ResearchFilterItem {

    private String primaryDataset;
    private String secondaryDataset;
    private String graphType;
    private String startDate;
    private String endDate;
    private boolean groupPrimary;
    private Integer groupFactor;
    private Float rangeStart;
    private Float rangeEnd;
    private String medicationName;

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

    public void setRangeStart(Float rangeStart) {

        if (rangeStart == null) {

            this.rangeStart = Float.MIN_VALUE;

        } else {

            this.rangeStart = rangeStart;
        }
    }

    public Float getRangeEnd() { return rangeEnd; }

    public void setRangeEnd(Float rangeEnd) {

        if (rangeEnd == null) {

            this.rangeEnd = Float.MAX_VALUE;

        } else {

            this.rangeEnd = rangeEnd;
        }
    }

    public String getMedicationName() { return medicationName; }

    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }
}
