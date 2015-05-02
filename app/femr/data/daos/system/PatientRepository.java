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
import com.google.inject.Inject;
import femr.data.daos.Repository;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.IPatient;
import femr.data.models.mysql.Patient;

import javax.inject.Provider;

public class PatientRepository extends Repository<IPatient> implements IPatientRepository {

    private final Provider<IPatient> patientProvider;

    @Inject
    public PatientRepository(Provider<IPatient> patientProvider){

        this.patientProvider = patientProvider;
    }

    @Override
    public IPatient findById(int id) {

        ExpressionList<Patient> query = getPatientQuery()
                .where()
                .eq("id", id);

        return findOne(query);
    }

    @Override
    public IPatient update(IPatient patient){

        return update(patient);
    }

    private static Query<Patient> getPatientQuery() {
        return Ebean.find(Patient.class);
    }
}
