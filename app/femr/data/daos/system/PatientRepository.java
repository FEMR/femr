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
import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository extends Repository<IPatient> implements IPatientRepository {

    @Inject
    public PatientRepository() {


    }

    @Override
    public IPatient findById(int id) {

        ExpressionList<Patient> query = getPatientQuery()
                .where()
                .eq("id", id);

        return super.findOne(query);
    }

    @Override
    public List<IPatient> findByFirstNameAndLastName(String firstName, String lastName) {

        Query<Patient> query = getPatientQuery()
                .where()
                .eq("first_name", firstName)
                .eq("last_name", lastName)
                .order()
                .desc("id");
        //generic repository returns "? extends IPatient"
        List<? extends IPatient> patients_covariant = super.find(query);

        //use a for loop to convert
        List<IPatient> patients_static = new ArrayList<>();
        //this will circumvent any warnings
        for (IPatient patient : patients_covariant){
            patients_static.add(patient);
        }

        return patients_static;
    }

    @Override
    public List<IPatient> findByFirstNameOrLastName(String firstOrLastName) {

        Query<Patient> query = getPatientQuery()
                .where()
                .or(
                        Expr.eq("first_name", firstOrLastName),
                        Expr.eq("last_name", firstOrLastName))
                .order()
                .desc("id");

        List<? extends IPatient> patients_covariant = super.find(query);

        List<IPatient> patients_static = new ArrayList<>();
        //this will circumvent any warnings
        for (IPatient patient : patients_covariant){
            patients_static.add(patient);
        }

        return patients_static;
    }

    @Override
    public IPatient update(IPatient patient) {

        return super.update(patient);
    }


    private static Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }
}
