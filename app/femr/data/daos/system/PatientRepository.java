package femr.data.daos.system;

import io.ebean.Ebean;
import io.ebean.Expr;
import io.ebean.ExpressionList;
import io.ebean.Query;
import femr.data.models.mysql.RankedPatientMatch;
import io.ebean.*;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientRepository implements IPatientRepository {

    private final Provider<IPatientAgeClassification> patientAgeClassificationProvider;

    @Inject
    public PatientRepository(Provider<IPatientAgeClassification> patientAgeClassificationProvider) {

        this.patientAgeClassificationProvider = patientAgeClassificationProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientAgeClassification createPatientAgeClassification(String name, String description, int sortOrder) {

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
    public List<? extends IPatientAgeClassification> retrieveAllPatientAgeClassifications() {

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
    public List<? extends IPatientAgeClassification> retrieveAllPatientAgeClassifications(boolean isDeleted) {

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
    public IPatient retrievePatientByGuid(Integer guid) {

        IPatient response = null;
        try {

            ExpressionList<Patient> query = QueryProvider.getPatientQuery()
                    .where()
                    .eq("globally_unique_id", guid)
                    .isNull("isDeleted");

            response = query.findOne();
        } catch (Exception ex) {

            Logger.error("PatientRepository-retrievePatientByGuid", ex.getMessage(), "id: " + guid);
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
            if (StringUtils.isNotNullOrWhiteSpace(phoneNumber)) {
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
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IRankedPatientMatch> retrievePatientMatchesFromTriageFields(String firstName, String lastName, String phone, String addr, String gender, Long age, String city) {

        List<? extends IRankedPatientMatch> response = null;
        try {

            Query<? extends IRankedPatientMatch> query = QueryProvider.getRankedPatientMatchQuery();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ageString = "";
            if (age != null) {
                ageString = dateFormat.format(new Date(age));
            }


            String sql
                    = "select id, user_id, first_name, last_name, phone_number, age, sex, address, city, isDeleted, deleted_by_user_id, reason_deleted, (" +
                    (phone != null && !phone.equals("") ? "case when phone_number = " + phone + " then 40 else 0 end + " : "") +
                    "case when last_name = \"" + lastName + "\" then 15 else 0 end + " +
                    "case when first_name = \"" + firstName + "\" then 10 else 0 end + " +
                    "case when dm_last_name = dm(\"" + lastName + "\") then 10 else 0 end + " +
                    "case when dm_first_name = dm(\"" + firstName + "\") then 10 else 0 end + " +
                    (addr != null && !addr.equals("") ? "case when address = \"" + addr + "\" then 15 else 0 end + " : "") +
                    (age != null ? "case when age != null and abs(datediff(age, \"" + ageString + "\") / 365) <= 5.0 then 5 else 0 end + " : "") +
                    (age != null ? "case when age != null and abs(datediff(age, \"" + ageString + "\") / 365) <= 2.0 then 5 else 0 end + " : "") +
                    (age != null ? "case when age != null and abs(datediff(age, \"" + ageString + "\") / 365) > 5.0 then -5 else 0 end + " : "") +
                    (age != null ? "case when age != null and abs(datediff(age, \"" + ageString + "\") / 365) > 10.0 then -5 else 0 end + " : "") +
                    (age != null ? "case when age != null and abs(datediff(age, \"" + ageString + "\") / 365) > 15.0 then -10 else 0 end + " : "") +
                    "case when sex = \"" + gender + "\" then 10 else 0 end + " +
                    "case when city like \"" + city + "\" then 10 else 0 end) " +
                    "as priority " +
                    "from patients having priority >= 30 and isDeleted is null order by priority desc limit 15";

            RawSql rawSql = RawSqlBuilder
                    .parse(sql)
                    .columnMapping("id", "patient.id")
                    .columnMapping("user_id", "patient.userId")
                    .columnMapping("first_name", "patient.firstName")
                    .columnMapping("last_name", "patient.lastName")
                    .columnMapping("phone_number", "patient.phoneNumber")
                    .columnMapping("age", "patient.age")
                    .columnMapping("sex", "patient.sex")
                    .columnMapping("address", "patient.address")
                    .columnMapping("city", "patient.city")
                    .columnMapping("isDeleted", "patient.isDeleted")
                    .columnMapping("deleted_by_user_id", "patient.deletedByUserId")
                    .columnMapping("reason_deleted", "patient.reasonDeleted")
                    .columnMapping("priority", "rank")
                    .create();

            query.setRawSql(rawSql);

            response = query.findList();

        } catch (Exception ex) {

            Logger.error("PatientRepository-retrievePatientMatchesFromTriageFields", ex.getMessage());
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

