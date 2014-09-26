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
package femr.business.helpers;

import com.avaje.ebean.Query;
import femr.common.models.VitalItem;
import femr.data.daos.IRepository;
import femr.data.models.IPatientEncounterVital;
import femr.data.models.PatientEncounterVital;

import java.util.List;

public class QueryHelper {

    public static Float findPatientWeight(IRepository<IPatientEncounterVital> patientEncounterVitalRepository, int encounterId){
        Float weight = null;
        Query<PatientEncounterVital> query2 = QueryProvider.getPatientEncounterVitalQuery()
                .fetch("vital")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("vital.name", "weight")
                .order().desc("date_taken");
        List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query2);
        if (patientEncounterVitals.size() > 0) {
            weight = patientEncounterVitals.get(0).getVitalValue();
        }
        return weight;
    }

    public static Integer findPatientHeightFeet(IRepository<IPatientEncounterVital> patientEncounterVitalRepository, int encounterId){
        Integer heightFeet = null;
        Query<PatientEncounterVital> query1 = QueryProvider.getPatientEncounterVitalQuery()
                .fetch("vital")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("vital.name", "heightFeet")
                .order().desc("date_taken");
        List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query1);
        if (patientEncounterVitals.size() > 0) {
            heightFeet = Math.round(patientEncounterVitals.get(0).getVitalValue());
        }
        return heightFeet;
    }

    public static Integer findPatientHeightInches(IRepository<IPatientEncounterVital> patientEncounterVitalRepository, int encounterId){
        Integer heightInches = null;
        Query<PatientEncounterVital> query1 = QueryProvider.getPatientEncounterVitalQuery()
                .fetch("vital")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("vital.name", "heightInches")
                .order().desc("date_taken");
        List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(query1);
        if (patientEncounterVitals.size() > 0) {
            heightInches = Math.round(patientEncounterVitals.get(0).getVitalValue());
        }
        return heightInches;
    }
}
