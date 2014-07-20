package femr.common.models;

public class SettingItem {
    private boolean isMultipleChiefComplaint;
    private boolean isPmhTab;
    private boolean isPhotoTab;


    public boolean isMultipleChiefComplaint() {
        return isMultipleChiefComplaint;
    }

    public void setMultipleChiefComplaint(boolean isMultipleChiefComplaint) {
        this.isMultipleChiefComplaint = isMultipleChiefComplaint;
    }

    public boolean isPmhTab() {
        return isPmhTab;
    }

    public void setPmhTab(boolean isPmhTab) {
        this.isPmhTab = isPmhTab;
    }

    public boolean isPhotoTab() {
        return isPhotoTab;
    }

    public void setPhotoTab(boolean isPhotoTab) {
        this.isPhotoTab = isPhotoTab;
    }
}
