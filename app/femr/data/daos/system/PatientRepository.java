package femr.data.daos.system;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.Patient;
import femr.util.stringhelpers.StringUtils;
import play.Logger;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository implements IPatientRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> findAllPatients() {
      ExpressionList<Patient> patientExpressionList = QueryProvider.getPatientQuery()
              .select("*")
              .where()
              .isNull("isDeleted");

      return patientExpressionList.findList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> findPatientsInCountry(String country) {
      ExpressionList<Patient> patientExpressionList = QueryProvider.getPatientQuery()
              .select("*")
              .fetch("patientEncounters")
              .fetch("patientEncounters.missionTrip")
              .fetch("patientEncounters.missionTrip.missionCity")
              .fetch("patientEncounters.missionTrip.missionCity.missionCountry")
              .where()
              .isNull("isDeleted")
              .eq("patientEncounters.missionTrip.missionCity.missionCountry.name", country);

      return patientExpressionList.findList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatient findPatientById(Integer id) {
        ExpressionList<Patient> query = QueryProvider.getPatientQuery()
                .where()
                .eq("id", id);
        return query.findUnique();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> findPatientsById(Integer id) {
      Query<Patient> query = QueryProvider.getPatientQuery()
              .where()
              .eq("id", id)
              .isNull("isDeleted")
              .order()
              .desc("id");
      return query.findList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> findPatientsByName(String firstName, String lastName) {
        Query<Patient> query;

        if (StringUtils.isNotNullOrWhiteSpace(firstName) && StringUtils.isNotNullOrWhiteSpace(lastName)) {
          //if we have a first and last name
          //this is the second most ideal scenario
            query = QueryProvider.getPatientQuery()
                  .where()
                  .eq("first_name", firstName)
                  .eq("last_name", lastName)
                  .isNull("isDeleted")
                  .order()
                  .desc("id");

        } else if (StringUtils.isNotNullOrWhiteSpace(firstName)) {
            //if we have a word that could either be a first name or a last name
            query = QueryProvider.getPatientQuery()
                  .where()
                  .or(
                          Expr.eq("first_name", firstName),
                          Expr.eq("last_name", firstName))
                  .isNull("isDeleted")
                  .order()
                  .desc("id");

        } else {
            return new ArrayList<>(); // We didn't actually search for anything, return empty list
        }

        return query.findList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatient save(IPatient patient) {
        Ebean.save(patient);
        return patient;
    }
}
