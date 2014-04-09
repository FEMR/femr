package femr.data.models;



import femr.common.models.IPatientResearch;

import javax.persistence.*;
import java.util.*;

/**
 * Creates the model that will store the return information from a research query
 * This uses RowSql
 *
 * //@Table(name = "patient_encounter")
 */

public class PatientResearch implements IPatientResearch {

    private Map<String, String> patientPropertiesLookup;
    private Map<String, String> logicLookup;
    private Map<String, String> conditionLookup;


    public PatientResearch() {
        InitializePatientPropertiesLookup();
        InitializeLogicLookup();
        InitializeConditionLookup();
    }


    public List<String> getPatientPropertiesLookupAsList() {

        List<String> tempList = new ArrayList<>();
        for(String item: patientPropertiesLookup.keySet()) {
            tempList.add(item);
        }
        return tempList;
    }

    @Override
    public List<String> getLogicLookupAsList() {

        List<String> tempList = new ArrayList<>();
        for(String item: logicLookup.keySet()) {
            tempList.add(item);
        }
        return tempList;
    }

    @Override
    public List<String> getConditionLookupAsList() {

        List<String> tempList = new ArrayList<>();
        for(String item: conditionLookup.keySet()) {
            tempList.add(item);
        }
        return tempList;
    }

    public Map<String, String> getPatientPropertiesLookup() {
        return patientPropertiesLookup;
    }

    public Map<String, String> getLogicLookup() {
        return logicLookup;
    }

    public Map<String, String> getConditionLookup() {
        return conditionLookup;
    }


    private void InitializePatientPropertiesLookup() {
        patientPropertiesLookup = new LinkedHashMap<>();

        patientPropertiesLookup.put("ID","p.id");
        patientPropertiesLookup.put("Age","p.age");
        patientPropertiesLookup.put("City","p.city");
        patientPropertiesLookup.put("Sex","p.sex");
        patientPropertiesLookup.put("Date Taken","pe.date_of_visit");
        patientPropertiesLookup.put("Medication","pp.medication_name");
        patientPropertiesLookup.put("Problem","petf_problem.problems");
        patientPropertiesLookup.put("Treatment","petf_treatment.treatments");

    }

    private void InitializeLogicLookup() {

        logicLookup = new LinkedHashMap<>();

        logicLookup.put("AND","AND");
        logicLookup.put("OR","OR");
        logicLookup.put("NOT","NOT");
        logicLookup.put("XOR","XOR");
    }

    private void InitializeConditionLookup() {

        conditionLookup = new LinkedHashMap<>();

        conditionLookup.put("=","=");
        conditionLookup.put("!=","!=");
        conditionLookup.put("<","<");
        conditionLookup.put("<=","<=");
        conditionLookup.put(">",">");
        conditionLookup.put(">=",">=");
    }





}
