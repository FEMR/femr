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
package femr.data.models.core.research;

import femr.data.models.core.IVital;
import femr.data.models.mysql.PatientEncounter;
import femr.data.models.mysql.research.ResearchEncounter;

public interface IResearchEncounterVital {

    int getId();

    int getUserId();

    ResearchEncounter getPatientEncounter();

    void setPatientEncounter(ResearchEncounter patientEncounterId);

    int getVitalId();

    void setVitalId(int vitalId);

    IVital getVital();
    void setVital(IVital vital);

    Float getVitalValue();

    void setVitalValue(float vitalValue);

    String getDateTaken();

    void setDateTaken(String dateTaken);

    void setUserId(int userId);

}
