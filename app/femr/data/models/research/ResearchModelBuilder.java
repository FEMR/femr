package femr.data.models.research;

import femr.ui.models.research.ResearchDataModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  populates the a list of models from the sql results
 */
public class ResearchModelBuilder {


    /**
     * Creates a list of Research models where each model represent one entry in the
     * results from the database.
     * @param resultSet The result from the SQL execution
     * @return A list of {@link ResearchDataModel} or null if there is an error
     */
    public static List<ResearchDataModel> CreateResultModel(ResultSet resultSet) {

        List<ResearchDataModel> resultModel = new ArrayList<>();

        try {
            while(resultSet.next())
            {
                ResearchDataModel tempModel = new ResearchDataModel();

                String medicationStr = resultSet.getString("medication_name");
                List<String> MedList = (medicationStr == null) ? null : Arrays.asList(resultSet.getString("medication_name").split(","));

                String problemsStr = resultSet.getString("problems");
                List<String> ProblemList = (problemsStr == null) ? null : Arrays.asList(resultSet.getString("problems").split(","));


                String treatmentStr = resultSet.getString("treatments");
                List<String> TreatmentList = (treatmentStr == null) ? null : Arrays.asList(resultSet.getString("treatments").split(","));


                tempModel.setPatientID(resultSet.getString("patient_id"));
                tempModel.setEncounterID(resultSet.getString("encounter_id"));
                tempModel.setAge(resultSet.getString("age"));
                tempModel.setSex(resultSet.getString("sex"));
                tempModel.setCity(resultSet.getString("city"));
                tempModel.setDateTaken(resultSet.getString("date_of_visit"));
                tempModel.setProblems(ProblemList);
                tempModel.setMedication(MedList);
                tempModel.setTreatments(TreatmentList);

                resultModel.add(tempModel);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resultModel;
    }
}
