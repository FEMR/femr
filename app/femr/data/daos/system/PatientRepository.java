package femr.data.daos.system;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.Patient;
import femr.data.models.mysql.PatientAgeClassification;
import femr.util.stringhelpers.StringUtils;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class PatientRepository implements IPatientRepository {

    @Override
    public List<? extends IPatientAgeClassification> retrieveAllPatientAgeClassifications(){

        List<? extends IPatientAgeClassification> response = null;
        try {

            Query<PatientAgeClassification> patientAgeClassificationExpressionList = QueryProvider.getPatientAgeClassificationQuery()
                    .where()
                    .eq("isDeleted", false)
                    .order()
                    .asc("sortOrder");

            response = patientAgeClassificationExpressionList.findList();
        } catch (Exception ex) {

            Logger.error("PatientRepository-retrieveAllPatientAgeClassifications", ex);
            ex.printStackTrace();
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> retrieveAllPatients() {

        List<? extends IPatient> response = null;
        try {

            ExpressionList<Patient> patientExpressionList = QueryProvider.getPatientQuery()
                    .select("*")
                    .where()
                    .isNull("isDeleted");

            response = patientExpressionList.findList();
        } catch (Exception ex) {

            Logger.error("PatientRepository-retrieveAllPatients", ex);
            ex.printStackTrace();
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> retrievePatientsInCountry(String country) {

        List<? extends IPatient> response = null;
        try {

            ExpressionList<Patient> patientExpressionList = QueryProvider.getPatientQuery()
                    .select("*")
                    .fetch("patientEncounters")
                    .fetch("patientEncounters.missionTrip")
                    .fetch("patientEncounters.missionTrip.missionCity")
                    .fetch("patientEncounters.missionTrip.missionCity.missionCountry")
                    .where()
                    .isNull("isDeleted")
                    .eq("patientEncounters.missionTrip.missionCity.missionCountry.name", country);

            response = patientExpressionList.findList();
        } catch (Exception ex) {

            Logger.error("country: " + country);
            Logger.error("PatientRepository-retrievePatientsInCountry", ex);
            ex.printStackTrace();
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatient retrievePatientById(Integer id) {

        IPatient response = null;
        try {

            ExpressionList<Patient> query = QueryProvider.getPatientQuery()
                    .where()
                    .eq("id", id)
                    .isNull("isDeleted");

            response = query.findUnique();
        } catch (Exception ex) {

            Logger.error("id: " + id);
            Logger.error("PatientRepository-retrievePatientById", ex);
            ex.printStackTrace();
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> retrievePatientsByName(String firstName, String lastName) {

        List<? extends IPatient> response = null;
        try {

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

                response = query.findList();
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

                response = query.findList();
            } else {

                response = new ArrayList<>(); // We didn't actually search for anything, return empty list
            }
        } catch (Exception ex) {

            Logger.error("firstName & lastName: " + firstName + "&" + lastName);
            Logger.error("PatientRepository-retrievePatientsByName", ex);
            ex.printStackTrace();
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatient savePatient(IPatient patient) {

        IPatient response = null;
        try {

            Ebean.save(patient);
        } catch (Exception ex) {

            Logger.error("PatientRepository-savePatient", ex);
            ex.printStackTrace();
            throw ex;
        }

        return patient;
    }
}
