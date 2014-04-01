package femr.ui.controllers.research;


import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.IResearchService;
import femr.business.services.ISessionService;

import femr.common.models.IPatientResearch;
import femr.common.models.Roles;
import femr.data.models.PatientResearch;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.research.CreateViewModelGet;
import femr.ui.models.research.CreateViewModelPost;
import femr.ui.models.research.QueryObjectPatientModel;
import femr.ui.views.html.research.index;
import femr.util.DataStructure.Pair;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the controller for the research page
 */
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.RESEARCHER})
public class ResearchController extends Controller {
    private final Form<CreateViewModelPost> createViewModelForm = Form.form(CreateViewModelPost.class);
    private IResearchService researchService;
    private ISessionService sessionService;

    /**
     * Research Controller constructer that Injects the services indicated by the parameters
     *
     * @param sessionService  {@link ISessionService}
     * @param researchService {@link IResearchService}
     */
    @Inject
    public ResearchController(ISessionService sessionService, IResearchService researchService) {
        this.researchService = researchService;
        this.sessionService = sessionService;
    }

    /**
     * Creates the model and renders the view for the index page of Research
     *
     * @return The models for the index page
     */
    public Result index() {
        CurrentUser currentUserSession = sessionService.getCurrentUserSession();

        // Create the viewModel
        CreateViewModelGet viewModel = new CreateViewModelGet();
        viewModel.setPatientModel(CreatePatientModel());

        return ok(index.render(currentUserSession, viewModel));
    }

    /**
     * Creates and populates the Patient object model with all the possible values the
     * user can search by as well as the conditions to combine them
     *
     * @return The {@link QueryObjectPatientModel} that was created
     */
    private QueryObjectPatientModel CreatePatientModel() {
        QueryObjectPatientModel patientModel = new QueryObjectPatientModel();

        // create a list of conditional
        List<String> conditionList = new ArrayList<>();
        conditionList.add("AND");
        conditionList.add("OR");
        conditionList.add("NOT");
        conditionList.add("XOR");

        patientModel.setConditionList(conditionList);

        // create a list of comparison symbols
        List<String> comparisonList = new ArrayList<>();
        comparisonList.add("=");
        comparisonList.add("!=");
        comparisonList.add("<");
        comparisonList.add("<=");
        comparisonList.add(">");
        comparisonList.add(">=");

        patientModel.setComparisonList(comparisonList);

        // Create the tempoaray patient info
        List<Pair<String, Object>> patientProperties = new ArrayList<>();
        patientProperties.add(new Pair<String, Object>("ID", Integer.class));
        patientProperties.add(new Pair<String, Object>("Age", Integer.class));
        patientProperties.add(new Pair<String, Object>("City", String.class));
        patientProperties.add(new Pair<String, Object>("Sex", String.class));
        patientProperties.add(new Pair<String, Object>("Date Taken", String.class));
        patientProperties.add(new Pair<String, Object>("Medication", String.class));
        patientProperties.add(new Pair<String, Object>("Treatment", String.class));

        patientModel.setPatientProperties(patientProperties);
        // TODO-RESEARCH: Add the properties associated with a patient that the user can choose from

        return patientModel; // temporary replace

    }


    //TODO-RESEARCH: Add the code for rest the Research controller here

    /**
     * Gets the generated search query and parses it then sends it to the service layer and displays the results
     * @return The Rendered results the the view
     */
    public Result createPost() {
        //bind QueryString from POST request
        CreateViewModelPost viewModelPost = createViewModelForm.bindFromRequest().get();

        // This is siimple parsing for now just proof of concept
        Map<String,String> patientMap = new HashMap<>();
        patientMap.put("ID","p.id");
        patientMap.put("Age","p.age");
        patientMap.put("City","p.city");
        patientMap.put("Sex","p.sex");
        patientMap.put("Date Taken","p.date_taken");
        patientMap.put("Medication","pp.medication_name");
        patientMap.put("Treatment","petf.treatment");

        String sql = " WHERE ";
        // split the string by spaces
        String[] splitStr = viewModelPost.getQueryString().split("\\s+");
        int count = 1;  // indicates what we are looking for
        for(String word : splitStr)
        {
            // if count is 1 then get the property and returns its mapped name in the database
            if(count == 1) {
                sql += patientMap.get(word) + " ";
                count ++;
            }
            // if count is 2 then get the comparison symbol
            else if(count == 2) {
                sql += word + " ";
                count ++;
            }
            // if count is 3 get the value and put it in single quotes
            else if(count == 3) {
                sql += "'" + word + "' ";
                count ++;
            }
            // if count is 4 gets the logic value and resets count to 1
            else if(count == 4){
                sql += word + " ";
                count = 1;
            }
        }

        // execute query
        ResultSet resultSet = researchService.ManualSqlQuery(sql);
       try{
            while(resultSet.next())
            {

                System.out.println(resultSet.getString("patient_id") + " " + resultSet.getString("encounter_id") + " " +
                        resultSet.getString("sex") + " " + resultSet.getString("age") + " " + resultSet.getString("medication_name"));
            }
        } catch(Exception e) {
           e.printStackTrace();
       }

        System.out.println(sql);





        //do a redirect because what the fuck else would i do
        return redirect("/research");
    }
}
