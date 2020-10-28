package femr.data.daos.system;

import io.ebean.Ebean;
import io.ebean.Expr;
import io.ebean.ExpressionList;
import io.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.Provider;
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

    private final Provider<IPatientAgeClassification> patientAgeClassificationProvider;

    @Inject
    public PatientRepository(Provider<IPatientAgeClassification> patientAgeClassificationProvider){

        this.patientAgeClassificationProvider = patientAgeClassificationProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientAgeClassification createPatientAgeClassification(String name, String description, int sortOrder){

        if (StringUtils.isNullOrWhiteSpace(name) || StringUtils.isNullOrWhiteSpace(description)) {

            return null;
        }

        IPatientAgeClassification patientAgeClassification = patientAgeClassificationProvider.get();
        patientAgeClassification.setName(name);
        patientAgeClassification.setDescription(description);
        patientAgeClassification.setIsDeleted(false);
        patientAgeClassification.setSortOrder(sortOrder);

        try {

            Ebean.save(patientAgeClassification);
        } catch (Exception ex) {

            Logger.error("PatientRepository-createPatientAgeClassification", ex.getMessage());
            throw ex;
        }

        return patientAgeClassification;
    }

    /**
     * {@inheritDoc}
     */
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

            Logger.error("PatientRepository-retrieveAllPatientAgeClassifications", ex.getMessage());
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientAgeClassification> retrieveAllPatientAgeClassifications(boolean isDeleted){

        List<? extends IPatientAgeClassification> response = null;
        try {

            Query<PatientAgeClassification> patientAgeClassificationExpressionList = QueryProvider.getPatientAgeClassificationQuery()
                    .where()
                    .eq("isDeleted", isDeleted)
                    .order()
                    .asc("sortOrder");

            response = patientAgeClassificationExpressionList.findList();
        } catch (Exception ex) {

            Logger.error("PatientRepository-retrieveAllPatientAgeClassifications", ex.getMessage());
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientAgeClassification retrievePatientAgeClassification(String ageClassification) {

        IPatientAgeClassification response = null;
        try {

            ExpressionList<PatientAgeClassification> patientAgeClassificationExpressionList = QueryProvider.getPatientAgeClassificationQuery()
                    .where()
                    .eq("name", ageClassification);

            response = patientAgeClassificationExpressionList.findOne();
        } catch (Exception ex) {

            Logger.error("PatientRepository-retrievePatientAgeClassification", ex);
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

            Logger.error("PatientRepository-retrieveAllPatients", ex.getMessage());
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
                    .isNull("patientEncounters.isDeleted")
                    .isNull("isDeleted")
                    .eq("patientEncounters.missionTrip.missionCity.missionCountry.name", country);

            response = patientExpressionList.findList();
        } catch (Exception ex) {

            Logger.error("PatientRepository-retrievePatientsInCountry", ex.getMessage(), "country: " + country);
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

            response = query.findOne();
        } catch (Exception ex) {

            Logger.error("PatientRepository-retrievePatientById", ex.getMessage(), "id: " + id);
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatient> retrievePatientsByPhoneNumber(String phoneNumber) {

        List<? extends IPatient> response = null;
        try {
            Query<Patient> query;
            if(StringUtils.isNotNullOrWhiteSpace(phoneNumber)) {
                query = QueryProvider.getPatientQuery()
                        .where()
                        .eq("phone_number", phoneNumber)
                        .isNull("isDeleted")
                        .order()
                        .desc("id");
                response = query.findList();
            }
        } catch (Exception ex) {

            Logger.error("PatientRepository-retrievePatientByPhoneNumber", ex.getMessage(), "phone number: " + phoneNumber);
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

            Logger.error("PatientRepository-retrievePatientsByName", ex.getMessage(), "firstName & lastName: " + firstName + "&" + lastName);
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

            //is it necessary to pass all details about object in log?
            Logger.error("PatientRepository-savePatient", ex.getMessage());
            throw ex;
        }

        return patient;
    }
}
