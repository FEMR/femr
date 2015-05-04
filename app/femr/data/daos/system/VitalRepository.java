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
import femr.data.daos.core.IVitalRepository;
import femr.data.models.core.IVital;
import femr.data.models.mysql.Vital;
import play.Logger;

import java.util.List;

public class VitalRepository extends Repository<IVital> implements IVitalRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IVital> findAll() {

        List<? extends IVital> vitals = null;

        try {

            vitals = super.findAll(Vital.class);
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

            vital = super.findOne(expressionList);
        } catch (Exception ex) {

            Logger.error("VitalRepository-findByName", ex);
        }

        return vital;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IVital findHeightFeet() {

        ExpressionList<Vital> expressionList = getVitalQuery().where().eq("name", "heightFeet");

        IVital vital = null;

        try {

            vital = super.findOne(expressionList);
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

            vital = super.findOne(expressionList);
        } catch (Exception ex) {

            Logger.error("VitalRepository-findHeightInches", ex);
        }

        return vital;
    }

    private static Query<Vital> getVitalQuery() {
        return Ebean.find(Vital.class);
    }
}
