package femr.business.services;


import com.avaje.ebean.*;
import com.google.inject.Inject;
import femr.business.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.Patient;
import femr.data.models.PatientResearch;
import femr.ui.controllers.research.ResearchDataModel;
import play.db.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


/**
 * This implements the ResearchService interface through querying the database
 * and returning the ServiceResponse
 */
public class ResearchService implements IResearchService{
    /*
    private IRepository<IMedication> medicationRepository;
    private IRepository<IPatient> patientRepository;
    private IRepository<IPatientEncounter> patientEncounterRepository;
    private IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository;
    private IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    private IRepository<IPatientPrescription> patientPrescriptionRepository;
    private IRepository<IVital> vitalRepository;
    private IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository;
    private IRepository<IPatientEncounterPmhField> patientEncounterPmhFieldRepository;
    private IRepository<ITreatmentField> treatmentFieldRepository;
    private IRepository<IPmhField> pmhFieldRepository;
    private IRepository<IHpiField> hpiFieldRepository;
    */

    private IRepository<IPatientResearch> patientResearchRepository;


    @Inject
    public ResearchService(IRepository<IPatientResearch> patientResearchRepository) {

        this.patientResearchRepository = patientResearchRepository;
        /*
        IRepository<IMedication> medicationRepository,
             IRepository<IPatient> patientRepository,
             IRepository<IPatientEncounter> patientEncounterRepository,
             IRepository<IPatientEncounterTreatmentField> patientEncounterTreatmentFieldRepository,
             IRepository<IPatientEncounterVital> patientEncounterVitalRepository,
             IRepository<IPatientPrescription> patientPrescriptionRepository,
             IRepository<IVital> vitalRepository,
             IRepository<IPatientEncounterHpiField> patientEncounterHpiFieldRepository,
             IRepository<IPatientEncounterPmhField> patientEncounterPmhFieldRepository,
             IRepository<ITreatmentField> treatmentFieldRepository,
             IRepository<IPmhField> pmhFieldRepository,
             IRepository<IHpiField> hpiFieldRepository
         */

        /*
        this.medicationRepository = medicationRepository;
        this.patientRepository = patientRepository;
        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterTreatmentFieldRepository = patientEncounterTreatmentFieldRepository;
        this.patientEncounterVitalRepository = patientEncounterVitalRepository;
        this.patientPrescriptionRepository = patientPrescriptionRepository;
        this.vitalRepository = vitalRepository;
        this.patientEncounterHpiFieldRepository = patientEncounterHpiFieldRepository;
        this.patientEncounterPmhFieldRepository = patientEncounterPmhFieldRepository;
        this.treatmentFieldRepository = treatmentFieldRepository;
        this.pmhFieldRepository = pmhFieldRepository;
        this.hpiFieldRepository = hpiFieldRepository;
        */

    }


    /**
     * Gets all the basic patient information like name, age, city ...
     * for all the patients in the database and returns it
     * @return A list of the patient info to analyzed
     */
 /*   @Override
    public ServiceResponse<List<? extends IPatient>> findAllPatient() {
        List<? extends IPatient> patients = patientRepository.findAll(Patient.class);
        ServiceResponse<List<? extends IPatient>> response = new ServiceResponse<>();
        if(patients.size() > 0) {
            response.setResponseObject(patients);
        } else {
            response.addError("patients", "No patients available");
        }
        return response;
    } */

    //@Override
    public List<PatientResearch> testModelSQL() {
        String sqlTest = "select patient_encounters.id from patient_encounters";

        //String sql = " select id, pe.patient_id, pe.chief_complaint, pe.date_of_visit, p.age, p.sex, p.city"  //  pp.medication_name
        //        + " from patient_encounters pe"
        //        + " join patients p on p.id = pe.patient_id "
               // + " join patient_prescriptions pp on pp.encounter_id = pe.id "
        //        + " group by pe.id ";

//        RawSql rawSql = RawSqlBuilder.parse(sql)
//                // map the sql result columns to bean properties
//                .columnMapping("id", "researchDataModel.encounterID")
//                .columnMapping("pe.patient_id", "researchDataModel.patientID")
//                .columnMapping("pe.chief_complaint", "researchDataModel.condition")
//                .columnMapping("pe.date_of_visit", "researchDataModel.dateTaken")
//                .columnMapping("p.age", "researchDataModel.age")
//                .columnMapping("p.sex", "researchDataModel.sex")
//                .columnMapping("p.city", "researchDataModel.city")
//               // .columnMapping("pp.medication_name", "researchDataModel.medication")
//                .create();

        RawSql rawSql = RawSqlBuilder.parse(sqlTest)
                .columnMapping("patient_encounters.id", "researchDataModel.encounterID")
                .create();

        Query<PatientResearch> query = Ebean.find(PatientResearch.class);
        query.setRawSql(rawSql)
                .where().eq("researchDataModel.encounterID", 1);

        String poop = query.getGeneratedSql();

        List<PatientResearch> results = null;
        try{
            results = query.findList();

        } catch(Exception e){
            e.printStackTrace();
        }

        ServiceResponse<List<PatientResearch>> response = new ServiceResponse<>();
        if(results.size() > 0) {
            response.setResponseObject(results);
        }
        else {
            response.addError("", "Faild to query database");
        }

        return results;
    }

    /**
     * Takes a sql string generated from user input and querys the databases
     * @param sql The WHERE part of SQL query
     * @return ResultSet or null 
     */
    public ResultSet ManualSqlQuery(String sql) {
        Connection connection = DB.getConnection();

        String sqlSelect = " SELECT p.id as \"patient_id\", pe.id as \"encounter_id\", p.age, p.sex, group_concat(pp.medication_name) as \"medication_name\" ";

        String sqlFrom = " FROM patient_encounters as pe " +
                " JOIN patients as p " +
                "   ON pe.patient_id = p.id " +
                " JOIN patient_prescriptions as pp " +
                "   ON pe.id = pp.encounter_id " +
                " ";


        String sqlWhere = " WHERE p.sex = 'Male' AND SOUNDEX(pp.medication_name) LIKE SOUNDEX('tilenal') ";

        String sqlGroup = " GROUP BY pe.id ";

        String MasterSQL = sqlSelect + sqlFrom + sql + sqlGroup;
        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(MasterSQL);

            return resultSet;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ServiceResponse<List<? extends IPatientResearch>> testModel() {
        Query<PatientResearch> query = getPatientResearchQuery()
                .select("id,patient.age")
                .where()
                .eq("age", "1999-01-09")
                .order().desc("age");

        String sqlStr = query.getGeneratedSql();
        System.out.println("This is the raw QUERY: " + sqlStr);
        List<? extends IPatientResearch> patientResearchs = patientResearchRepository.find(query);
        IPatientResearch patientResearch;
        ServiceResponse<List<? extends IPatientResearch>> response = new ServiceResponse<>();

        if(patientResearchs.isEmpty()) {
            response.addError("PatientResearch", "failed to query database");
        }
        else {
            response.setResponseObject(patientResearchs);
        }

        return response;
    }



    // TODO-RESEARCH: Implement the services


    private Query<PatientResearch> getPatientResearchQuery() {
        return Ebean.find(PatientResearch.class);
    }


}
