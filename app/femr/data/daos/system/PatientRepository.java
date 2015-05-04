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
import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.data.daos.Repository;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.IPatient;
import femr.data.models.mysql.Patient;
import play.Logger;
import java.util.List;

public class PatientRepository extends Repository<IPatient> implements IPatientRepository {

    @Inject
    public PatientRepository() {


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatient findById(int id) {

        ExpressionList<Patient> query = getPatientQuery()
                .where()
                .eq("id", id);

        IPatient patient = null;

        try {

            patient = super.findOne(query);
        } catch (Exception ex) {

            Logger.error("PatientRepository-findById", ex.getMessage());
        }

        return patient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> findAll() {

        List<? extends IPatient> patients = null;

        try {

            patients = super.findAll(Patient.class);
        } catch (Exception ex) {

            Logger.error("PatientRepository-findAll", ex.getMessage());
        }

        return patients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> findByFirstNameAndLastName(String firstName, String lastName) {

        Query<Patient> query = getPatientQuery()
                .where()
                .eq("first_name", firstName)
                .eq("last_name", lastName)
                .order()
                .desc("id");

        List<? extends IPatient> patients = null;

        try {

            patients = super.find(query);
        } catch (Exception ex) {

            Logger.error("PatientRepository-findByFirstNameAndLastName", ex.getMessage());
        }

        return patients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> findByFirstNameOrLastName(String firstOrLastName) {

        Query<Patient> query = getPatientQuery()
                .where()
                .or(
                        Expr.eq("first_name", firstOrLastName),
                        Expr.eq("last_name", firstOrLastName))
                .order()
                .desc("id");

        List<? extends IPatient> patients = null;

        try {

            patients = super.find(query);
        } catch (Exception ex) {

            Logger.error("PatientRepository-findByFirstNameOrLastName", ex.getMessage());
        }

        return patients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatient update(IPatient patient) {

        try {

            patient = super.update(patient);
        } catch (Exception ex) {

            Logger.error("PatientRepository-update", ex.getMessage());
            patient = null;
        }

        return patient;
    }

    private static Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }
}
