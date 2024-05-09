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

import femr.data.models.core.*;
import org.joda.time.DateTime;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patient_encounters")
public class PatientEncounter implements IPatientEncounter {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false, referencedColumnName = "id")
    private Patient patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_triage", nullable = false)
    private User nurse;
    @Column(name = "date_of_triage_visit", nullable = false)
    private DateTime dateOfTriageVisit;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "patientEncounter")
    private List<ChiefComplaint> chiefComplaints;

    @Column(name = "date_of_medical_visit", nullable = true)
    private DateTime dateOfMedicalVisit;
    @Column(name = "date_of_pharmacy_visit", nullable = true)
    private DateTime dateOfPharmacyVisit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_medical", nullable = true)
    private User doctor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_pharmacy", nullable = true)
    private User pharmacist;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_age_classification_id")
    private PatientAgeClassification patientAgeClassification;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mission_trip_id", nullable = true)
    private MissionTrip missionTrip;
    @Column(name = "date_of_diabetes_screen", nullable = true)
    private DateTime dateOfDiabeteseScreen;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_diabetes_screen", nullable = true)
    private User diabetesScreener;
    @Column(name="is_diabetes_screened", nullable = true)
    private Boolean isDiabetesScreened;
    @Column(name = "isDeleted", nullable = true)
    private DateTime isDeleted;
    @Column(name = "deleted_by_user_id", unique = false, nullable = true)
    private Integer deletedByUserId;
    @Column(name = "reason_deleted", nullable = true)
    private String reasonEncounterDeleted;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public IPatient getPatient() {
        return patient;
    }

    @Override
    public void setPatient(IPatient patient) {
        this.patient = (Patient) patient;
    }

    @Override
    public List<IChiefComplaint> getChiefComplaints() {
        List<IChiefComplaint> temp = new ArrayList<>();
        for (ChiefComplaint cc : chiefComplaints) {
            temp.add(cc);
        }
        return temp;
    }

    @Override
    public void setChiefComplaints(List<IChiefComplaint> chiefComplaints) {
        for (IChiefComplaint cc : chiefComplaints) {
            this.chiefComplaints.add((ChiefComplaint) cc);
        }
    }

    @Override
    public DateTime getDateOfTriageVisit() {
        return dateOfTriageVisit;
    }

    @Override
    public void setDateOfTriageVisit(DateTime dateOfTriageVisit) {
        this.dateOfTriageVisit = dateOfTriageVisit;
    }

    @Override
    public DateTime getDateOfMedicalVisit() {
        return dateOfMedicalVisit;
    }

    @Override
    public void setDateOfMedicalVisit(DateTime dateOfMedicalVisit) {
        this.dateOfMedicalVisit = dateOfMedicalVisit;
    }

    @Override
    public DateTime getDateOfPharmacyVisit() {
        return dateOfPharmacyVisit;
    }

    @Override
    public void setDateOfPharmacyVisit(DateTime dateOfPharmacyVisit) {
        this.dateOfPharmacyVisit = dateOfPharmacyVisit;
    }

    @Override
    public IUser getDoctor() {
        return doctor;
    }

    @Override
    public void setDoctor(IUser doctor) {
        this.doctor = (User) doctor;
    }

    @Override
    public IUser getPharmacist() {
        return pharmacist;
    }

    @Override
    public void setPharmacist(IUser pharmacist) {
        this.pharmacist = (User) pharmacist;
    }

    @Override
    public IUser getNurse() {
        return nurse;
    }

    @Override
    public void setNurse(IUser nurse) {
        this.nurse = (User) nurse;
    }

    @Override
    public IPatientAgeClassification getPatientAgeClassification() {
        return patientAgeClassification;
    }

    @Override
    public void setPatientAgeClassification(IPatientAgeClassification patientAgeClassification) {
        this.patientAgeClassification =  (PatientAgeClassification) patientAgeClassification;
    }

    @Override
    public IMissionTrip getMissionTrip() {
        return missionTrip;
    }

    @Override
    public void setMissionTrip(IMissionTrip missionTrip) {
        this.missionTrip = (MissionTrip) missionTrip;
    }

    @Override
    public DateTime getDateOfDiabeteseScreen() {
        return dateOfDiabeteseScreen;
    }

    @Override
    public void setDateOfDiabeteseScreen(DateTime dateOfDiabeteseScreen) {
        this.dateOfDiabeteseScreen = dateOfDiabeteseScreen;
    }

    @Override
    public IUser getDiabetesScreener() {
        return diabetesScreener;
    }

    @Override
    public void setDiabetesScreener(IUser diabetesScreener) {
        this.diabetesScreener = (User) diabetesScreener;
    }

    @Override
    public Boolean getIsDiabetesScreened() {return isDiabetesScreened;}

    @Override
    public void setIsDiabetesScreened(Boolean isDiabetesScreened) {this.isDiabetesScreened = isDiabetesScreened;}
    @Override
    public DateTime getEncounterDeleted() {
        return isDeleted;
    }

    @Override
    public void setEncounterDeleted(DateTime isDeleted) {
        this.isDeleted = isDeleted;
        }
    @Override
    public Integer getDeletedByUserId() {
        return deletedByUserId;
    }

    @Override
    public void setDeletedByUserId(Integer userId) {
        this.deletedByUserId= userId;
    }

    @Override
    public String getReasonDeleted() { return reasonEncounterDeleted; }

    @Override
    public void setReasonDeleted(String reason) { this.reasonEncounterDeleted = reason; }

}
