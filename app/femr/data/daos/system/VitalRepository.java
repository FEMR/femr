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
package femr.data.daos.system;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IVitalRepository;
import femr.data.models.core.IPatientEncounterVital;
import femr.data.models.core.IVital;
import femr.data.models.mysql.PatientEncounterVital;
import femr.data.models.mysql.Vital;
import play.Logger;

import java.util.List;

public class VitalRepository implements IVitalRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounterVital> createAll(List<? extends IPatientEncounterVital> patientEncounterVitals) {

        try {

            Ebean.save(patientEncounterVitals);
        } catch (Exception ex) {

            Logger.error("VitalRepository-createAll", ex);
        }

        return patientEncounterVitals;
    }

    /**
     * {@inheritDoc}
     */
    public List<? extends IPatientEncounterVital> find(int encounterId) {

        Query<PatientEncounterVital> query = getPatientEncounterVitalQuery()
                .where()
                .eq("patient_encounter_id", encounterId)
                .order()
                .desc("date_taken");

        List<? extends IPatientEncounterVital> patientEncounterVitals = null;
        try {

            patientEncounterVitals = query.findList();
        } catch (Exception ex) {

            Logger.error("VitalRepository-find", ex);
        }

        return patientEncounterVitals;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IVital> findAll() {

        List<? extends IVital> vitals = null;

        try {

            vitals = getVitalQuery().findList();
        } catch (Exception ex) {

            Logger.error("VitalRepository-findAll", ex);
        }

        return vitals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IVital findByName(String name) {

        ExpressionList<Vital> expressionList = getVitalQuery().where().eq("name", name);

        IVital vital = null;

        try {

            vital = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("VitalRepository-findByName", ex);
        }

        return vital;
    }

    /**
     * {@inheritDoc}
     */
    public List<? extends IPatientEncounterVital> findHeightFeetValues(int encounterId) {

        Query<PatientEncounterVital> query = getPatientEncounterVitalQuery()
                .fetch("vital")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("vital.name", "heightFeet")
                .order().desc("date_taken");

        List<? extends IPatientEncounterVital> patientEncounterVitals = null;

        try {

            patientEncounterVitals = query.findList();
        } catch (Exception ex) {

            Logger.error("VitalRepository-findHeightFeetValues", ex);
        }


        return patientEncounterVitals;
    }

    /**
     * {@inheritDoc}
     */
    public List<? extends IPatientEncounterVital> findHeightInchesValues(int encounterId) {

        Query<PatientEncounterVital> query = getPatientEncounterVitalQuery()
                .fetch("vital")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("vital.name", "heightInches")
                .order().desc("date_taken");

        List<? extends IPatientEncounterVital> patientEncounterVitals = null;

        try {

            patientEncounterVitals = query.findList();
        } catch (Exception ex) {

            Logger.error("VitalRepository-findHeightInchesValues", ex);
        }

        return patientEncounterVitals;
    }

    /**
     * {@inheritDoc}
     */
    public List<? extends IPatientEncounterVital> findWeightValues(int encounterId) {

        Query<PatientEncounterVital> query = getPatientEncounterVitalQuery()
                .fetch("vital")
                .where()
                .eq("patient_encounter_id", encounterId)
                .eq("vital.name", "weight")
                .order().desc("date_taken");

        List<? extends IPatientEncounterVital> patientEncounterVitals = null;

        try {

            patientEncounterVitals = query.findList();
        } catch (Exception ex) {

            Logger.error("VitalRepository-findWeightValues", ex);
        }

        return patientEncounterVitals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IVital findHeightFeet() {

        ExpressionList<Vital> expressionList = getVitalQuery().where().eq("name", "heightFeet");

        IVital vital = null;

        try {

            vital = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("VitalRepository-findHeightFeet", ex);
        }

        return vital;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IVital findHeightInches() {

        ExpressionList<Vital> expressionList = getVitalQuery().where().eq("name", "heightInches");

        IVital vital = null;

        try {

            vital = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("VitalRepository-findHeightInches", ex);
        }

        return vital;
    }

    private static Query<Vital> getVitalQuery() {
        return Ebean.find(Vital.class);
    }

    private static Query<PatientEncounterVital> getPatientEncounterVitalQuery() {
        return Ebean.find(PatientEncounterVital.class);
    }
}

