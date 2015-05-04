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
import femr.data.daos.Repository;
import femr.data.daos.core.IChiefComplaintRepository;
import femr.data.models.core.IChiefComplaint;
import femr.data.models.mysql.ChiefComplaint;
import play.Logger;

import java.util.List;

public class ChiefComplaintRepository extends Repository<IChiefComplaint> implements IChiefComplaintRepository{

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IChiefComplaint> createAll(List<? extends IChiefComplaint> chiefComplaints){

        try{
            chiefComplaints = super.createAll(chiefComplaints);
        }
        catch( Exception ex ){

            Logger.error("ChiefComplaintRepository-createAll", ex);
        }
        return chiefComplaints;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IChiefComplaint> findAllByPatientEncounterId(int encounterId){

        ExpressionList<ChiefComplaint> chiefComplaintExpressionList = QueryProvider.getChiefComplaintQuery()
                .where()
                .eq("patient_encounter_id", encounterId);
        List<? extends IChiefComplaint> chiefComplaints = null;

        try{

            chiefComplaints = super.find(chiefComplaintExpressionList);
        }
        catch( Exception ex ){

            Logger.error("ChiefComplaintRepository-findAllByPatientEncounterId", ex);
        }
        return chiefComplaints;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IChiefComplaint> findAllByPatientEncounterIdOrderBySortOrderAsc(int encounterId){


        Query<ChiefComplaint> chiefComplaintExpressionList = getChiefComplaintQuery()
                .where()
                .eq("patient_encounter_id", encounterId)
                .order()
                .asc("sortOrder");
        List<? extends IChiefComplaint> chiefComplaints = null;

        try{

            chiefComplaints = super.find(chiefComplaintExpressionList);
        }
        catch( Exception ex ){

            Logger.error("ChiefComplaintRepository-findAllBy", ex);
        }
        return chiefComplaints;
    }

    private Query<ChiefComplaint> getChiefComplaintQuery() {
        return Ebean.find(ChiefComplaint.class);
    }
}
