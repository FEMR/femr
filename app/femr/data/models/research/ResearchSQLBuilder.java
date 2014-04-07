package femr.data.models.research;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds the SQL WHERE BODY from the given input
 *
 */
public class ResearchSQLBuilder {

    private Map<String,String> tableLookup;
    private Map<String,String> logicLookup;
    private String inputSt;


    public ResearchSQLBuilder(String input) {
        InitializeTableLookup();
        InitializeLogicLookup();
        inputSt = input;

    }

    /**
     * Builds the sql query and sends it to the service layer
     * @return The results from the database
     */
    public ResultSet BuildQuery() {

        String tempSQL = " WHERE ";

        int step = 1;
        String[] splitStr = inputSt.split("\\s+");
        String temp = "";


        for(String word : splitStr)
        {
            temp = handleInput(step, word);
            if(temp == null) {
                return null;
            }
            tempSQL += temp + " ";
            step = (step == 4 ? 1 : step++);
        }







        return null;

    }


    /**
     * Takes the current step and the current word or symbol being processed
     * and returns the correct value for the sql query
     *
     * @param step An integer from 1 to 4 indicating witch part of the input we have:
     *             1 is property, 2 is the comparison symbol, 3 the user input value,
     *             4 logical value to combine another group of statements
     * @param input the value to be added
     * @return The correct mapped value
     */
    private String handleInput(int step, String input) {

        switch(step)
        {
            case 1:
                return tableLookup.get(input);
            case 2:
                return logicLookup.get(input);
            case 3:
                return "'" + input + "'";
            case 4:
                return logicLookup.get(input);


        }

        return null;
    }



    /**
     * populates the table lookup map.  Add new tables to this function to they will be in the map
     */
    private void InitializeTableLookup() {

        tableLookup = new HashMap<>();

        tableLookup.put("ID","p.id");
        tableLookup.put("Age","p.age");
        tableLookup.put("City","p.city");
        tableLookup.put("Sex","p.sex");
        tableLookup.put("Date Taken","p.date_taken");
        tableLookup.put("Medication","pp.medication_name");
        tableLookup.put("Treatment","petf.treatment");

    }

    /**
     * Populates the LocgicLookup map.
     */
    private void InitializeLogicLookup() {

        logicLookup = new HashMap<>();

        logicLookup.put("AND","AND");
        logicLookup.put("OR","OR");
        logicLookup.put("NOT","NOT");
        logicLookup.put("XOR","XOR");
        logicLookup.put("=","=");
        logicLookup.put("!=","!=");
        logicLookup.put("<","<");
        logicLookup.put("<=","<=");
        logicLookup.put(">",">");
        logicLookup.put(">=",">=");

    }


}
