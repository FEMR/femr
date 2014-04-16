package femr.business.services;


import com.google.inject.Inject;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.models.PatientResearch;
import femr.data.models.research.ResearchModelBuilder;
import femr.data.models.research.ResearchSQLBuilder;
import femr.ui.models.research.ResearchDataModel;
import femr.util.DataStructure.Pair;
import play.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;


/**
 * This implements the ResearchService interface through querying the database
 * and returning the ServiceResponse
 */
public class ResearchService implements IResearchService{

    private IRepository<IPatientResearch> patientResearchRepository;
    private IPatientResearch patientResearch;

    /**
     * Initializes the research service and injects the dependence
     * @param patientResearchRepository this parameter is dependance injected so don't do anything with it
     */
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
     * @return A list of {@link ResearchDataModel} where each entry in the list is
     *         one row from the result query or null if there is an error
     */
    public List<ResearchDataModel> ManualSqlQuery(String sql) {
        Connection connection = DB.getConnection();
        ResearchSQLBuilder rSQLBuilder = new ResearchSQLBuilder(sql,this.patientResearch);
        if(rSQLBuilder == null)
        {
            return null;
        }

        String sqlSelect = " SELECT p.id as \"patient_id\", pe.id as \"encounter_id\", p.age, p.city, p.sex," +
                " pe.date_of_visit, pp.medication_name, petf_problem.problems, petf_treatment.treatments ";

        String sqlFrom = " FROM patient_encounters pe " +
                " JOIN patients p on pe.patient_id = p.id " +
                " " +
                " LEFT JOIN (SELECT group_concat(medication_name) as medication_name, encounter_id  " +
                "      FROM patient_prescriptions " +
                "      GROUP BY encounter_id) pp ON pe.id = pp.encounter_id " +
                " " +
                " LEFT JOIN (SELECT treatment_field_id, group_concat(treatment_field_value) as problems, patient_encounter_id " +
                "      FROM patient_encounter_treatment_fields petf " +
                "      WHERE petf.treatment_field_id = '2'  " +
                "      GROUP BY patient_encounter_id) petf_problem ON pe.id = petf_problem.patient_encounter_id " +
                " " +
                " LEFT JOIN (SELECT treatment_field_id, group_concat(treatment_field_value) as treatments, patient_encounter_id " +
                "      FROM patient_encounter_treatment_fields petf2 " +
                "      WHERE petf2.treatment_field_id = '3' " +
                "      GROUP BY patient_encounter_id) petf_treatment ON pe.id = petf_treatment.patient_encounter_id " +
                " ";


        // Gets the where statement to use in the preparedStatement and the list of values
        // for the parameters
        Pair<String,List<String>> wherePair = rSQLBuilder.CreatePreparedStatement(sql);
        if(wherePair == null)
        {
            return null;
        }

        String sqlWhere = wherePair.getKey();

        String MasterSQL = sqlSelect + sqlFrom + sqlWhere; // + sqlGroup;


        try {
            // Constructs the preparedStatement
            PreparedStatement ps = connection.prepareStatement(MasterSQL);
            // fills in the parameters with the user provided values
            // we do this to gard against sql injections
            ps = rSQLBuilder.BuildQuery(ps,wherePair.getValue());

            if(ps == null)
            {
                return null;
            }

            ResultSet resultSet = ps.executeQuery();

            return ResearchModelBuilder.CreateResultModel(resultSet);


        } catch(Exception e) {
            return null;
        }


    }


}
