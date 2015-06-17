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

public class PatientRepository implements IPatientRepository {

    @Inject
    public PatientRepository() {


    }

    @Override
    public IPatient createPatient(IPatient patient) {
        try {

            Ebean.save(patient);
        } catch (Exception ex) {

            Logger.error("PatientRepository-createPatientEncounter", ex.getMessage());
        }
        return patient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatient findPatientById(int id) {

        ExpressionList<Patient> query = getPatientQuery()
                .where()
                .eq("id", id);

        IPatient patient = null;

        try {

            patient = query.findUnique();
        } catch (Exception ex) {

            Logger.error("PatientRepository-findPatientById", ex.getMessage());
        }

        return patient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> findAllPatients() {

        List<? extends IPatient> patients = null;

        try {

            patients = Ebean.find(Patient.class).findList();
        } catch (Exception ex) {

            Logger.error("PatientRepository-findAllPatients", ex.getMessage());
        }

        return patients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> findPatientsByFirstNameAndLastName(String firstName, String lastName) {

        Query<Patient> query = getPatientQuery()
                .where()
                .eq("first_name", firstName)
                .eq("last_name", lastName)
                .order()
                .desc("id");

        List<? extends IPatient> patients = null;

        try {

            patients = query.findList();
        } catch (Exception ex) {

            Logger.error("PatientRepository-findPatientsByFirstNameAndLastName", ex.getMessage());
        }

        return patients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> findPatientsByFirstNameOrLastName(String firstOrLastName) {

        Query<Patient> query = getPatientQuery()
                .where()
                .or(
                        Expr.eq("first_name", firstOrLastName),
                        Expr.eq("last_name", firstOrLastName))
                .order()
                .desc("id");

        List<? extends IPatient> patients = null;

        try {

            patients = query.findList();
        } catch (Exception ex) {

            Logger.error("PatientRepository-findPatientsByFirstNameOrLastName", ex.getMessage());
        }

        return patients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatient updatePatient(IPatient patient) {

        try {

            Ebean.save(patient);
        } catch (Exception ex) {

            Logger.error("PatientRepository-updatePatientEncounter", ex.getMessage());
            patient = null;
        }

        return patient;
    }

    private static Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }
}
