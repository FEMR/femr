package femr.data.daos.system;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;
import com.google.inject.Provider;
import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IEncounterRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.PatientEncounter;
import femr.util.calculations.dateUtils;
import org.joda.time.DateTime;
import play.Logger;

import javax.inject.Inject;
import java.util.List;

public class EncounterRepository implements IEncounterRepository {

    private final Provider<IMissionTrip> missionTripProvider;
    private final Provider<IPatient> patientProvider;
    private final Provider<IPatientAgeClassification> patientAgeClassificationProvider;
    private final Provider<IPatientEncounter> patientEncounterProvider;
    private final Provider<IUser> userProvider;

    @Inject
    public EncounterRepository(Provider<IMissionTrip> missionTripProvider,
                               Provider<IPatient> patientProvider,
                               Provider<IPatientAgeClassification> patientAgeClassificationProvider,
                               Provider<IPatientEncounter> patientEncounterProvider,
                               Provider<IUser> userProvider){

        this.missionTripProvider = missionTripProvider;
        this.patientProvider = patientProvider;
        this.patientAgeClassificationProvider = patientAgeClassificationProvider;
        this.patientEncounterProvider = patientEncounterProvider;
        this.userProvider = userProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientEncounter createPatientEncounter(int patientID, DateTime date, int userId, Integer patientAgeClassificationId, Integer tripId){

        IPatientEncounter patientEncounter = patientEncounterProvider.get();



        try{

            patientEncounter.setDateOfTriageVisit(date);
            //provide a proxy patient for the encounter
            patientEncounter.setPatient(Ebean.getReference(patientProvider.get().getClass(), patientID));
            patientEncounter.setNurse(Ebean.getReference(userProvider.get().getClass(), userId));
            if (patientAgeClassificationId != null)
                patientEncounter.setPatientAgeClassification(Ebean.getReference(patientAgeClassificationProvider.get().getClass(), patientAgeClassificationId));
            if (tripId != null)
                patientEncounter.setMissionTrip(Ebean.getReference(missionTripProvider.get().getClass(), tripId));

            Ebean.save(patientEncounter);
        }catch (Exception ex){

            Logger.error("EncounterRepository-createPatientEncounter", ex);
            throw ex;
        }

        return patientEncounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientEncounter deletePatientEncounter(int encounterId, String reason, int userId) {

        IPatientEncounter patientEncounter;
        ExpressionList<PatientEncounter> query = QueryProvider.getPatientEncounterQuery()
                .where()
                .eq("id", encounterId);

        patientEncounter = query.findOne();
        if (patientEncounter != null) {

            patientEncounter.setEncounterDeleted(dateUtils.getCurrentDateTime());
            patientEncounter.setDeletedByUserId(userId);
            patientEncounter.setReasonDeleted(reason);
            Ebean.save(patientEncounter);
        }

        return patientEncounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounter> retrievePatientEncounters(DateTime from, DateTime to, Integer tripId) {

        ExpressionList<PatientEncounter> query = QueryProvider.getPatientEncounterQuery().where().isNull("isDeleted");
        if (from != null) {

            query = query.where().ge("date_of_triage_visit", from);
        }
        if (to != null) {

            query = query.where().le("date_of_triage_visit", to);
        }
        if (tripId != null) {

            query = query.where().eq("mission_trip_id", tripId);
        }
        List<? extends IPatientEncounter> response;
        try {

            response = query.findList();
        } catch (Exception ex) {

            Logger.error("Encounter-Repository-retrievePatientEncounters", ex);
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounter> retrievePatientEncountersByPatientIdAsc(int patientId) {

        List<? extends IPatientEncounter> response = null;

        try {

            Query<PatientEncounter> query = QueryProvider.getPatientEncounterQuery()
                    .where()
                    .isNull("isDeleted")
                    .eq("patient_id", patientId)
                    .order()
                    .asc("date_of_triage_visit");
            response = query.findList();
        } catch (Exception ex) {

            Logger.error("EncounterRepository-retrievePatientEncountersByPatientIdAsc", ex);
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IPatientEncounter> retrievePatientEncountersByPatientIdDesc(int patientId) {

        List<? extends IPatientEncounter> response = null;

        try {

            Query<PatientEncounter> query = QueryProvider.getPatientEncounterQuery()
                    .where()
                    .isNull("isDeleted")
                    .eq("patient_id", patientId)
                    .order()
                    .desc("date_of_triage_visit");
            response = query.findList();
        } catch (Exception ex) {

            Logger.error("EncounterRepository-retrievePatientEncountersByPatientIdAsc", ex);
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientEncounter retrievePatientEncounterById(int id) {

        IPatientEncounter response = null;

        try {

            ExpressionList<PatientEncounter> query = QueryProvider.getPatientEncounterQuery()
                    .where()
                    .isNull("isDeleted")
                    .eq("id", id);
            response = query.findOne();
        } catch (Exception ex) {

            Logger.error("EncounterRepository-retreivePatientEncounterById", ex);
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientEncounter savePatientEncounterDiabetesScreening(int encounterId, int userId, DateTime date, Boolean isScreened) {

        if (date == null || isScreened == null)
            return null;

        IPatientEncounter response = null;
        try {

            response = retrievePatientEncounterById(encounterId);
            response.setDateOfDiabeteseScreen(date);
            response.setDiabetesScreener(Ebean.getReference(userProvider.get().getClass(), userId));
            response.setIsDiabetesScreened(isScreened);
            Ebean.save(response);
        } catch (Exception ex) {

            Logger.error("EncounterRepository-savePatientEncounterDiabetesScreening", ex);
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientEncounter savePatientEncounterMedicalCheckin(int encounterId, int userId, DateTime date) {

        IPatientEncounter response = null;

        if (date == null){

            return null;
        }

        try {

            response = retrievePatientEncounterById(encounterId);
            response.setDateOfMedicalVisit(date);
            response.setDoctor(Ebean.getReference(userProvider.get().getClass(), userId));
            Ebean.save(response);
        } catch (Exception ex) {

            Logger.error("EncounterRepository-savePatientEncounterMedicalCheckin", ex);
            throw ex;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPatientEncounter savePatientEncounterPharmacyCheckin(int encounterId, int userId, DateTime date) {

        IPatientEncounter response = null;

        if (date == null){

            return null;
        }

        try {

            response = retrievePatientEncounterById(encounterId);
            response.setDateOfPharmacyVisit(date);
            response.setPharmacist(Ebean.getReference(userProvider.get().getClass(), userId));
            Ebean.save(response);
        } catch (Exception ex) {

            Logger.error("EncounterRepository-savePatientEncounterPharmacyCheckin", ex);
            throw ex;
        }

        return response;
    }
}
