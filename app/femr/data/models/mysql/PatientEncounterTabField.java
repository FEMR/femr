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
package femr.data.models.mysql;

import femr.data.models.core.IChiefComplaint;
import femr.data.models.core.IPatientEncounterTabField;
import femr.data.models.core.ITabField;
import org.joda.time.DateTime;
import javax.persistence.*;

@Entity
@Table(name="patient_encounter_tab_fields")
public class PatientEncounterTabField implements IPatientEncounterTabField {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, nullable = false)
    private User user;
    @Column(name = "patient_encounter_id", nullable = false)
    private int patientEncounterId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tab_field_id", nullable = false)
    private TabField tabField;
    @Column(name = "tab_field_value", nullable = false)
    private String tabFieldValue;
    @Column(name = "date_taken", nullable = false)
    private DateTime dateTaken;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chief_complaint_id", nullable = true)
    private ChiefComplaint chiefComplaint;
    @Column(name = "IsDeleted", nullable = true)
    private DateTime IsDeleted;
    @Column(name = "DeletedByUserId", nullable = true)
    private Integer DeletedByUserId;


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
    public String getUserName() { return user.getFirstName() + " " + user.getLastName(); }

    @Override
    public void setUser(User user) { this.user = user; }

    @Override
    public int getPatientEncounterId() {
        return patientEncounterId;
    }

    @Override
    public void setPatientEncounterId(int patientEncounterId) {
        this.patientEncounterId = patientEncounterId;
    }

    @Override
    public ITabField getTabField() {
        return tabField;
    }

    @Override
    public void setTabField(ITabField tabField) {
        this.tabField = (TabField) tabField;
    }

    @Override
    public String getTabFieldValue() {
        return tabFieldValue;
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
        return chiefComplaint;
    }

    @Override
    public void setChiefComplaint(IChiefComplaint chiefComplaint) {
        this.chiefComplaint = (ChiefComplaint) chiefComplaint;
    }

    @Override
    public DateTime getIsDeleted(){ return IsDeleted; };

    @Override
    public void setIsDeleted(DateTime time){ this.IsDeleted = time; };

    @Override
    public Integer getDeletedByUserId(){ return DeletedByUserId; };

    @Override
    public void setDeletedByUserId(Integer userId){ this.DeletedByUserId = userId; };
}
