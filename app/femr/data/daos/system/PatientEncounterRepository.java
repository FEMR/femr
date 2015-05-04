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
import femr.data.daos.Repository;
import femr.data.daos.core.IPatientEncounterRepository;
import femr.data.models.core.IPatientEncounter;
import femr.data.models.mysql.PatientEncounter;
import play.Logger;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class PatientEncounterRepository extends Repository<IPatientEncounter> implements IPatientEncounterRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientEncounter create(IPatientEncounter patientEncounter) {

        IPatientEncounter encounter = null;
        try{

            encounter = super.create(patientEncounter);
        }
        catch( Exception ex ){

            Logger.error("PatientEncounterRepository-create", ex);
        }
        return encounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientEncounter findOneById(int encounterId) {

        ExpressionList<PatientEncounter> query = getPatientEncounterQuery()
                .where()
                .eq("id", encounterId);

        IPatientEncounter encounter = null;
        try {
            encounter = super.findOne(query);
        }
        catch( Exception ex ){

            Logger.error("PatientEncounterRepository-findOneById", ex);
        }

        return encounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounter> findByPatientIdOrderByDateOfTriageVisitAsc(int patientId) {

        Query<PatientEncounter> query = getPatientEncounterQuery();
        query.where()
                .eq("patient_id", patientId)
                .order()
                .asc("date_of_triage_visit");

        List<? extends IPatientEncounter> patientEncounters = null;

        try {

            patientEncounters = super.find(query);
        } catch (Exception ex) {

            Logger.error(ex.getMessage());
        }


        return patientEncounters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounter> findByPatientIdOrderByDateOfTriageVisitDesc(int patientId) {

        Query<PatientEncounter> query = getPatientEncounterQuery();
        query.where()
                .eq("patient_id", patientId)
                .order()
                .desc("date_of_triage_visit");

        List<? extends IPatientEncounter> patientEncounters = null;

        try {

            patientEncounters = super.find(query);
        } catch (Exception ex) {

            Logger.error(ex.getMessage());
        }


        return patientEncounters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientEncounter update(IPatientEncounter patientEncounter) {

        IPatientEncounter encounter = null;
        try {
            encounter = super.update(patientEncounter);
        }
        catch( Exception ex ){

            Logger.error("PatientEncounterRepository-update", ex);
        }
        return encounter;
    }


    /**
     * Provides the Ebean object to start building queries
     *
     * @return The patient encounter EQuery object
     */
    private Query<PatientEncounter> getPatientEncounterQuery() {
        return Ebean.find(PatientEncounter.class);
    }
}
