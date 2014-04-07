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
import java.util.Map;


/**
 * This implements the ResearchService interface through querying the database
 * and returning the ServiceResponse
 */
public class ResearchService implements IResearchService{

    private IRepository<IPatientResearch> patientResearchRepository;
    private IPatientResearch patientResearch;


    @Inject
    public ResearchService(IRepository<IPatientResearch> patientResearchRepository) {

        this.patientResearchRepository = patientResearchRepository;
        this.patientResearch = new PatientResearch();
    }


    @Override
    public Map<String, String> getPatientPropertiesLookup() {
        return this.patientResearch.getPatientPropertiesLookup();
    }

    @Override
    public Map<String, String> getLogicLookup() {
        return this.patientResearch.getLogicLookup();
    }

    @Override
    public Map<String, String> getConditionLookup() {
        return this.patientResearch.getConditionLookup();
    }

    @Override
    public List<String> getPatientPropertiesLookupAsList() {
        return this.patientResearch.getPatientPropertiesLookupAsList();
    }

    @Override
    public List<String> getLogicLookupAsList() {
        return this.patientResearch.getLogicLookupAsList();
    }

    @Override
    public List<String> getConditionLookupAsList() {
        return this.patientResearch.getConditionLookupAsList();
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



    // TODO-RESEARCH: Implement the services


}
