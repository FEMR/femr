package mock.femr.data.models;

import femr.data.models.core.*;
import femr.data.models.mysql.ChiefComplaint;
import femr.data.models.mysql.User;
import org.joda.time.DateTime;

public class MockPatientEncounterTabField implements IPatientEncounterTabField {

    int id;
    int userId;
    int patientEncounterId;
    IVital vital;
    String userName;
    ITabField tabField;
    String tabFieldValue;
    DateTime dateTaken;
    DateTime isDeleted;
    IChiefComplaint complaint;
    int deletedByUserId;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public int getPatientEncounterId() {
        return 0;
    }

    @Override
    public void setPatientEncounterId(int patientEncounterId) {
        this.patientEncounterId = patientEncounterId;
    }

    @Override
    public ITabField getTabField() {
        return this.tabField;
    }

    @Override
    public void setTabField(ITabField tabField) {
        this.tabField = tabField;
    }

    @Override
    public String getTabFieldValue() {
        return this.tabFieldValue;
    }

    @Override
    public void setTabFieldValue(String tabFieldValue) {
        this.tabFieldValue = tabFieldValue;
    }

    @Override
    public DateTime getDateTaken() {
        return dateTaken;
    }

    @Override
    public void setDateTaken(DateTime dateTaken) {
        this.dateTaken = dateTaken;
    }

    @Override
    public ChiefComplaint getChiefComplaint() {
        return (ChiefComplaint) complaint;
    }

    @Override
    public void setChiefComplaint(IChiefComplaint chiefComplaint) {
        this.complaint = chiefComplaint;
    }

    @Override
    public DateTime getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(DateTime time) {
        this.isDeleted = time;
    }

    @Override
    public Integer getDeletedByUserId() {
        return deletedByUserId;
    }

    @Override
    public void setDeletedByUserId(Integer userId) {
        this.deletedByUserId = userId;
    }
}
