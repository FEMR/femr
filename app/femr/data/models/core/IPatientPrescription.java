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
package femr.data.models.core;

import femr.data.models.mysql.PatientPrescriptionReplacement;
import org.joda.time.DateTime;

import java.util.List;

public interface IPatientPrescription {

    int getId();

    IMedication getMedication();

    void setMedication(IMedication medication);

    IConceptPrescriptionAdministration getConceptPrescriptionAdministration();

    void setConceptPrescriptionAdministration(IConceptPrescriptionAdministration conceptPrescriptionAdministration);

    IUser getPhysician();

    void setPhysician(IUser physician);

    Integer getAmount();

    void setAmount(Integer amount);

    DateTime getDateTaken();

    void setDateTaken(DateTime dateTaken);

    IPatientEncounter getPatientEncounter();

    void setPatientEncounter(IPatientEncounter patientEncounter);

    String getSpecialInstructions();

    void setSpecialInstructions(String specialInstructions);

    boolean isCounseled();

    void setCounseled(boolean isCounseled);

    List<PatientPrescriptionReplacement> getPatientPrescriptionReplacements();

    void setPatientPrescriptionReplacements(List<PatientPrescriptionReplacement> patientPrescriptionReplacements);

    DateTime getDateDispensed();

    void setDateDispensed(DateTime dateDispensed);
}
