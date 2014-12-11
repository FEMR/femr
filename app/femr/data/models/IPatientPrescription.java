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

public interface IPatientPrescription {

    int getId();

    IMedication getMedication();

    void setMedication(IMedication medication);

    IMedicationAdministration getMedicationAdministration();

    void setMedicationAdministration(IMedicationAdministration medicationAdministration);

    IUser getPhysician();

    void setPhysician(IUser physician);

    int getAmount();

    void setAmount(int amount);

    Integer getReplacementId();

    void setReplacementId(Integer replacementId);

    DateTime getDateTaken();

    void setDateTaken(DateTime dateTaken);

    IPatientEncounter getPatientEncounter();

    void setPatientEncounter(IPatientEncounter patientEncounter);

    String getSpecialInstructions();

    void setSpecialInstructions(String specialInstructions);

    boolean isCounseled();

    void setCounseled(boolean isCounseled);

    boolean isDispensed();

    void setDispensed(boolean isDispensed);
}
