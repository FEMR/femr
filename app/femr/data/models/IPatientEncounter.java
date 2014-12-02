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
package femr.data.models;

import org.joda.time.DateTime;
import java.util.List;

public interface IPatientEncounter {
    int getId();

    IPatient getPatient();

    void setPatient(IPatient patient);

    List<IChiefComplaint> getChiefComplaints();

    void setChiefComplaints(List<IChiefComplaint> chiefComplaints);

    Integer getWeeksPregnant();

    void setWeeksPregnant(Integer weeksPregnant);

    DateTime getDateOfTriageVisit();

    void setDateOfTriageVisit(DateTime dateOfVisit);

    DateTime getDateOfMedicalVisit();

    void setDateOfMedicalVisit(DateTime dateOfMedicalVisit);

    DateTime getDateOfPharmacyVisit();

    void setDateOfPharmacyVisit(DateTime dateOfPharmacyVisit);

    IUser getDoctor();

    void setDoctor(IUser doctor);

    IUser getPharmacist();

    void setPharmacist(IUser pharmacist);

    IUser getNurse();

    void setNurse(IUser nurse);

    IPatientAgeClassification getPatientAgeClassification();

    void setPatientAgeClassification(IPatientAgeClassification patientAgeClassification);
}
