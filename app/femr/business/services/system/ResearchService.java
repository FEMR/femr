/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.business.services.system;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.services.core.IResearchService;
import femr.business.helpers.QueryProvider;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.data.daos.core.IVitalRepository;
import femr.data.models.core.research.IResearchEncounter;
import femr.data.models.mysql.PatientPrescription;
import femr.data.models.mysql.research.ResearchEncounter;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.research.ResearchEncounterVital;
import femr.util.calculations.dateUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ResearchService implements IResearchService {

    private final IRepository<IResearchEncounter> researchEncounterRepository;
    private final IVitalRepository vitalRepository;

    /**
     * Initializes the research service and injects the dependence
     */
    @Inject
    public ResearchService(IRepository<IResearchEncounter> researchEncounterRepository,
                           IVitalRepository vitalRepository) {

        this.researchEncounterRepository = researchEncounterRepository;
        this.vitalRepository = vitalRepository;
    }

    @Override
    public ServiceResponse<ResearchResultSetItem> retrieveGraphData(ResearchFilterItem filters){

        ServiceResponse<ResearchResultSetItem> response = new ServiceResponse<>();

        try{

            List<? extends IResearchEncounter> patientEncounters = queryPatientData(filters);

            ResearchResultSetItem results; // = new ResearchResultSetItem();
            if( filters.getPrimaryDataset().equals("age") ) {

                results = buildAgeResultSet(patientEncounters, filters);
            }
            else if( filters.getPrimaryDataset().equals("pregnancyStatus") ||
                     filters.getPrimaryDataset().equals("pregnancyTime") ){

                results = buildPregnancyResultSet(patientEncounters, filters);
            }
            else if( filters.getPrimaryDataset().equals("gender") ){

                results = buildGenderResultSet(patientEncounters, filters);
            }
            else if( filters.getPrimaryDataset().equals("height") ){

                results = buildHeightResultSet(patientEncounters, filters);
            }
            // Check for medication filters
            else if( filters.getPrimaryDataset().equals("prescribedMeds") ||
                    filters.getPrimaryDataset().equals("dispensedMeds") ){

                results = buildMedicationResultSet(patientEncounters, filters);
            }
            // non-special situations are all considered vitals
            else{

                results = buildVitalResultSet(patientEncounters, filters);
            }

            // Handle Grouping, if needed
            results = groupData(results, filters);
            response.setResponseObject(results);

        } catch (Exception ex) {

            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * take filters and make appropriate query, get list of matching patient encounters
     * @param filters an object that contains all possible filters for the data
     * @return a list of the encounters
     */
    private List<? extends IResearchEncounter> queryPatientData(ResearchFilterItem filters){

        String datasetName = filters.getPrimaryDataset();

        String startDateString = filters.getStartDate();
        String endDateString = filters.getEndDate();
        Date startDateObj;
        Date endDateObj;
        SimpleDateFormat sqlFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {

            // Set Start Date to start of day
            String startParseDate = startDateString + " 00:00:00";
            startDateObj = sqlFormat.parse(startParseDate);

            // Set End Date to end of day
            String parseEndDate = endDateString + " 23:59:59";
            endDateObj = sqlFormat.parse(parseEndDate);
        }
        catch(ParseException e){

            startDateObj = null;
            endDateObj = null;
        }

        // Build Query based on Filters
        Query<ResearchEncounter> q = QueryProvider.getResearchEncounterQuery();
        q.fetch("patient");

        if( datasetName.equals("prescribedMeds") || datasetName.equals("dispensedMeds") ){

            q.fetch("patientPrescriptions");
        }

        // filtering by medication, so make sure to fetch the medication info
        if( filters.getMedicationName() != null && filters.getMedicationName().length() > 0 ){

            q.fetch("patientPrescriptions.medication");
        }

        ExpressionList<ResearchEncounter> e = q.where();

        // filter by date - can have only start, or only end date
        if( startDateObj != null ) {
            e.gt("dateOfTriageVisit", sqlFormat.format(startDateObj));
        }
        if( endDateObj != null ) {
            e.lt("dateOfTriageVisit", sqlFormat.format(endDateObj));
        }

        // filtering by medication if the name is set
        if( filters.getMedicationName() != null && filters.getMedicationName().length() > 0 ){

            e.like("patientPrescriptions.medication.name", "%" + filters.getMedicationName() + "%");
        }

        // add age specific parameters
        if( datasetName.equals("age") ) {

            e.ne("patient.age", null);
            e.orderBy().desc("patient.age");
        }

        e.findList();
        return researchEncounterRepository.find(e);

    }

    private ResearchResultSetItem groupData(ResearchResultSetItem results, ResearchFilterItem filters){

        // Force grouping if not already chosen
        /*
        if( !filters.isGroupPrimary() ){

            if( results.getDataset().size() > 30 ){

                filters.setGroupPrimary(true);
                // default to 20 groups
                //int groupFactor = results.getDataset().size() / 20;
                int groupFactor = 10;
                filters.setGroupFactor(groupFactor);
            }
        }
        */

        // Build group key values -- don't bother if there will only be 1 group
        // make happen automatically too? -- results.getDataset().size() > 20
        if( filters.isGroupPrimary() && results.getDataset().size() > filters.getGroupFactor() ) {

            int groupFactor = filters.getGroupFactor();

            // Start first range at lowest multiple near the first item
            // Ex: first item is 25, group factor is 10 -- firstLowKey 20
            int firstLowKey = (int)Math.floor(results.getDataRangeLow() / groupFactor) * groupFactor;

            // End range set at the highest multiple near last item
            // Ex: last item is 47, group factor is 10 -- lastLowKey is 40
            int lastLowKey = (int)Math.floor(results.getDataRangeHigh() / groupFactor) * groupFactor;

            List<ResearchResultItem> resultDataset = results.getDataset();
            List<ResearchResultItem> newResultDataset = new ArrayList<>();
            Set<String> secondaryKeys = null;

            for( int lowKey = firstLowKey; lowKey <= lastLowKey; lowKey+=groupFactor ){

                /* -- Start first range at first value in the range
                int lowTemp = lowKey;
                if( lowKey < filters.getFilterRangeStart() ){

                    lowTemp = filters.getFilterRangeStart().intValue();
                }
                */

                int highKey = lowKey + (groupFactor - 1);
                /*
                if( highKey > filters.getFilterRangeEnd() ){

                    highKey = filters.getFilterRangeEnd().intValue();
                }
                */

                String groupIndexString = String.format("%d - %d", lowKey, highKey);

                ResearchResultItem newResultItem = new ResearchResultItem();
                newResultItem.setPrimaryName(groupIndexString);

                Iterator<ResearchResultItem> itr = resultDataset.listIterator();
                while(itr.hasNext()) {

                    ResearchResultItem item = itr.next();

                    String itemNameString = item.getPrimaryName();
                    Float itemNameValue;
                    try {
                        itemNameValue = Float.valueOf(itemNameString);
                    }
                    catch(NumberFormatException e){
                        // skip iteration
                        continue;
                    }

                    if( itemNameValue < highKey ) {

                        // add total for current item to grouped total
                        Float currVal = newResultItem.getPrimaryValue();
                        newResultItem.setPrimaryValue(currVal + item.getPrimaryValue());

                        // copy over secondary data too
                        if( item.getSecondaryData() != null ) {

                            Map<String, Float> oldSecondaryData = item.getSecondaryData();
                            if( secondaryKeys == null ){
                                // each item should contain all keys, even if zero
                                // only need to get this value once
                                secondaryKeys = oldSecondaryData.keySet();
                            }

                            // get grouped secondary dataset and ensure its initialized
                            Map<String, Float> newSecondaryData = newResultItem.getSecondaryData();
                            if(newSecondaryData == null ){

                                newSecondaryData = new HashMap<>();
                            }

                            // always check each item for all possible secondary keys
                            for (String key : secondaryKeys) {

                                Float oldVal = oldSecondaryData.get(key);
                                if (newSecondaryData.containsKey(key)) {

                                    Float newVal = newSecondaryData.get(key);
                                    newSecondaryData.put(key, newVal + oldVal);

                                } else {

                                    newSecondaryData.put(key, oldVal);
                                }
                            }
                            newResultItem.setSecondaryData(newSecondaryData);
                        }

                        // once item is added to new list remove from current list
                        itr.remove();
                    }
                    else{

                        // values are sorted, so exit an continue to next group
                        // no items exist for current range
                        break;
                    }

                } // end while

                // if secondary keys exist, make the the item has at least 0 values for each key
                // this will happen when no items match for the current range
                if( newResultItem.getSecondaryData() == null && secondaryKeys != null ){

                    // if there is secondary data, make sure 0 values are set for each key
                    Map<String, Float> newSecondaryData = new HashMap<>();
                    for (String key : secondaryKeys) {

                        newSecondaryData.put(key, 0.0f);
                    }
                    newResultItem.setSecondaryData(newSecondaryData);
                }

                newResultDataset.add(newResultItem);

            } // end foreach
            results.setDataset(newResultDataset);

        }
        return results;
    }


    // do stuff specific to vitals request
    private ResearchResultSetItem buildMedicationResultSet(List<? extends IResearchEncounter> encounters, ResearchFilterItem filters) {

        // do nothing if encounters is empty
        if( encounters.isEmpty() ) return new ResearchResultSetItem();

        // used to calculate average
        float encountersTotal = 0;

        // Map to keep track of total patient for each vital_value
        // Keep keys in sorted order while totaling patients
        Map<Float, ResearchResultItem> datasetBuilder = new TreeMap<>();

        // Keep track of patients, eliminate duplicate encounters
        Set<Integer> patientIds = new HashSet<>();

        // Object to send return
        ResearchResultSetItem resultSet = new ResearchResultSetItem();
        resultSet.setDataType(filters.getPrimaryDataset());

        // Loop through encounters, get data and build stats
        for (IResearchEncounter encounter : encounters) {

            IPatient patient = encounter.getPatient();

            // If patient age has already been counted, don't count again
            if( patientIds.contains(patient.getId()) ) continue;
            patientIds.add(patient.getId());

            encountersTotal++;

            Float medicationId;
            ResearchResultItem resultItem;
            if( filters.getPrimaryDataset().equals("prescribedMeds") ){

                List<PatientPrescription> prescriptions = encounter.getPatientPrescriptions();
                Map<Float, String> primaryValuemap = resultSet.getPrimaryValueMap();
                if( primaryValuemap == null ){

                    primaryValuemap = new HashMap<>();
                }

                for( PatientPrescription script : prescriptions ) {

                    // skip items with replacement Id
                    if( script.getReplacementId() != null ) continue;

                    medicationId =  (float)script.getId();
                    // total patients for each value in map
                    if (datasetBuilder.containsKey(medicationId)){

                        resultItem = datasetBuilder.get(medicationId);

                    } else {

                        resultItem = new ResearchResultItem();
                        resultItem.setPrimaryName(Float.toString(script.getId()));
                    }
                    // increment total by 1
                    float currentValue = resultItem.getPrimaryValue();
                    resultItem.setPrimaryValue(currentValue + 1);

                    if( !primaryValuemap.containsKey(medicationId) ){

                        primaryValuemap.put(medicationId, script.getMedication().getName());
                    }
                    // put result item back into map
                    datasetBuilder.put(medicationId, resultItem);
                }
                resultSet.setPrimaryValueMap(primaryValuemap);

            }else if( filters.getPrimaryDataset().equals("dispensedMeds") ){

                List<PatientPrescription> prescriptions = encounter.getPatientPrescriptions();
                Map<Float, String> primaryValuemap = resultSet.getPrimaryValueMap();
                if( primaryValuemap == null ){

                    primaryValuemap = new HashMap<>();
                }

                for( PatientPrescription script : prescriptions ) {

                    // only count medications actually dispensed
                    if( !script.isDispensed() ) continue;

                    medicationId =  (float)script.getId();
                    // total patients for each value in map
                    if (datasetBuilder.containsKey(medicationId)){

                        resultItem = datasetBuilder.get(medicationId);

                    } else {

                        resultItem = new ResearchResultItem();
                        resultItem.setPrimaryName(Float.toString(script.getId()));
                    }
                    // increment total by 1
                    float currentValue = resultItem.getPrimaryValue();
                    resultItem.setPrimaryValue(currentValue + 1);

                    if( !primaryValuemap.containsKey(medicationId) ){

                        primaryValuemap.put(medicationId, script.getMedication().getName());
                    }
                    // put result item back into map
                    datasetBuilder.put(medicationId, resultItem);
                }
                resultSet.setPrimaryValueMap(primaryValuemap);

            }

        }

        // save builder map as list in result set
        resultSet.setDataset(new ArrayList<ResearchResultItem>(datasetBuilder.values()));
        resultSet.setTotal(encountersTotal);

        return resultSet;
    }


    // do stuff specific to vitals request
    private ResearchResultSetItem buildVitalResultSet(List<? extends IResearchEncounter> encounters, ResearchFilterItem filters) {

        // do nothing if encounters is empty
        if( encounters.isEmpty() ) return new ResearchResultSetItem();

        // Get vital obj to use vitalId in Encounter vital_value map
        String vitalName = filters.getPrimaryDataset();
        IVital vital = vitalRepository.findVitalByName(vitalName);

        if( vital == null ){

            // no results if requested vital doesn't exist
            return new ResearchResultSetItem();
        }

        // used to calculate average
        float totalForAvg = 0;
        float encountersTotal = 0;

        // Map to keep track of total patient for each vital_value
        // Keep keys in sorted order while totaling patients
        Map<Float, ResearchResultItem> datasetBuilder = new TreeMap<>();

        // Keep track of patients, eliminate duplicate encounters
        Set<Integer> patientIds = new HashSet<>();

        // Object to send return
        ResearchResultSetItem resultSet = new ResearchResultSetItem();
        resultSet.setDataType(vitalName);
        resultSet.setUnitOfMeasurement(vital.getUnitOfMeasurement());

        // Loop through encounters, get data and build stats
        for (IResearchEncounter encounter : encounters) {

            IPatient patient = encounter.getPatient();

            // If patient age has already been counted, don't count again
            if( patientIds.contains(patient.getId()) ) continue;
            patientIds.add(patient.getId());

            // Get vital value
            ResearchEncounterVital vitals = encounter.getEncounterVitals().get(vital.getId());

            // end loop if needed vital does not exist
            if( vitals == null ) continue;

            Float vitalValue = vitals.getVitalValue();
            //Float vitalValue = (float) encounter.getEncounterVital().getVitalValue();

            if( vitalValue == null ) continue;

            // skip encounter if age is out of range
            if( vitalValue < filters.getFilterRangeStart() || vitalValue > filters.getFilterRangeEnd() ) continue;

            // increment total to calculate average
            totalForAvg += vitalValue;
            encountersTotal++;

            // set RANGE LOW and HIGH if needed
            if (vitalValue > resultSet.getDataRangeHigh()) {

                resultSet.setDataRangeHigh(vitalValue);
            }
            if (vitalValue < resultSet.getDataRangeLow()) {

                resultSet.setDataRangeLow(vitalValue);
            }

            // total patients for each value in map
            ResearchResultItem resultItem;
            if (datasetBuilder.containsKey(vitalValue)) {

                resultItem = datasetBuilder.get(vitalValue);

            } else {

                resultItem = new ResearchResultItem();
                resultItem.setPrimaryName(Float.toString(vitalValue));
            }
            // increment total by 1
            float currentValue = resultItem.getPrimaryValue();

            resultItem.setPrimaryValue(currentValue + 1);

            // get secondary data
            if (filters.getSecondaryDataset() != null) {
                if (filters.getSecondaryDataset().equals("gender")) {

                    // Set valuemap if not already
                    if (resultSet.getSecondaryValueMap() == null) {

                        Map<Float, String> secondaryResultMap = new HashMap<>();
                        secondaryResultMap.put(0.0f, "Male");
                        secondaryResultMap.put(1.0f, "Female");
                        secondaryResultMap.put(2.0f, "N/A");

                        resultSet.setSecondaryValueMap(secondaryResultMap);
                    }

                    String gender = "2.0";
                    if (patient.getSex() == null) {
                        gender = "2.0";
                    } else if (patient.getSex().matches("(?i:Male)")) {
                        gender = "0.0";
                    } else if (patient.getSex().matches("(?i:Female)")) {
                        gender = "1.0";
                    }

                    Map<String, Float> secondaryData = resultItem.getSecondaryData();
                    // Initialize secondary data
                    if (secondaryData == null) {

                        secondaryData = new HashMap<String, Float>();

                        // Make sure all keys exist
                        secondaryData.put("0.0", 0.0f);
                        secondaryData.put("1.0", 0.0f);
                        secondaryData.put("2.0", 0.0f);
                    }

                    // Add patient to secondary running total
                    // key will exist after initialization above
                    Float secTotal = secondaryData.get(gender);
                    secondaryData.put(gender, secTotal + 1.0f);

                    resultItem.setSecondaryData(secondaryData);

                } else if (filters.getSecondaryDataset().equals("pregnancyStatus")) {

                    // Set valuemap if not already
                    if (resultSet.getSecondaryValueMap() == null) {

                        Map<Float, String> secondaryResultMap = new HashMap<>();
                        secondaryResultMap.put(0.0f, "No");
                        secondaryResultMap.put(1.0f, "Yes");

                        resultSet.setSecondaryValueMap(secondaryResultMap);
                    }

                    Integer wksPregnant = encounter.getWeeksPregnant();
                    if (wksPregnant == null) wksPregnant = 0;

                    String pregnancyStatus = "0.0";
                    if (wksPregnant > 0) {
                        pregnancyStatus = "1.0";
                    }

                    Map<String, Float> secondaryData = resultItem.getSecondaryData();
                    // Initialize secondary data
                    if (secondaryData == null) {

                        secondaryData = new HashMap<String, Float>();

                        // Make sure all keys exist
                        secondaryData.put("0.0", 0.0f);
                        secondaryData.put("1.0", 0.0f);
                    }

                    // Add patient to secondary running total
                    // key will exist after initialization above
                    Float secTotal = secondaryData.get(pregnancyStatus);
                    secondaryData.put(pregnancyStatus, secTotal + 1.0f);

                    resultItem.setSecondaryData(secondaryData);
                }
            }

            // put result item back into map
            datasetBuilder.put(vitalValue, resultItem);
        }

        // save builder map as list in result set
        resultSet.setDataset(new ArrayList<ResearchResultItem>(datasetBuilder.values()));
        resultSet.setTotal(encountersTotal);

        // save AVERAGE
        float average = totalForAvg / encountersTotal;
        resultSet.setAverage(average);

        // Standard Deviation -- might be used to detect outliers
        double devSum = 0.0;
        for( ResearchResultItem item : resultSet.getDataset() ){

            //(indexed value - mean)^2
            devSum += Math.pow(item.getPrimaryValue() - average, 2);
        }
        resultSet.setStandardDeviation(Math.sqrt(devSum));

        return resultSet;

    }

    // do stuff specific to height request - use total in inches
    private ResearchResultSetItem buildHeightResultSet(List<? extends IResearchEncounter> encounters, ResearchFilterItem filters) {

        // do nothing if encounters is empty
        if( encounters.isEmpty() ) return new ResearchResultSetItem();

        // Get vital obj to use vitalId in Encounter vital_value map
        String vitalName = filters.getPrimaryDataset();

        IVital vital = vitalRepository.findHeightFeetVital();

        Integer heightFeetId = vital.getId();

        vital = vitalRepository.findHeightInchesVital();

        Integer heightInchesId = vital.getId();

        // used to calculate average
        float totalForAvg = 0;
        float encountersTotal = 0;

        // Map to keep track of total patient for each vital_value
        // Keep keys in sorted order while totaling patients
        Map<Float, ResearchResultItem> datasetBuilder = new TreeMap<>();

        // Keep track of patients, eliminate duplicate encounters
        Set<Integer> patientIds = new HashSet<>();

        // Object to send return
        ResearchResultSetItem resultSet = new ResearchResultSetItem();
        resultSet.setDataType(vitalName);
        resultSet.setUnitOfMeasurement(vital.getUnitOfMeasurement());

        // Loop through encounters, get data and build stats
        for (IResearchEncounter encounter : encounters) {

            IPatient patient = encounter.getPatient();

            // If patient age has already been counted, don't count again
            if( patientIds.contains(patient.getId()) ) continue;
            patientIds.add(patient.getId());

            // Get vital value - heightFeet and heightInches
            ResearchEncounterVital vitalFeet = encounter.getEncounterVitals().get(heightFeetId);
            ResearchEncounterVital vitalInches = encounter.getEncounterVitals().get(heightInchesId);

            // height values may not exist
            Float vitalValue = 0.0f;
            if( vitalFeet != null ){

                vitalValue += vitalFeet.getVitalValue() * 12;
            }
            if( vitalInches != null ){

                vitalValue += vitalInches.getVitalValue();
            }

            // if feet or inches were not found, skip to next encounter
            if( vitalValue == 0.0f ) continue;

            // skip encounter if age is out of range
            if( vitalValue < filters.getFilterRangeStart() || vitalValue > filters.getFilterRangeEnd() ) continue;

            // increment total to calculate average
            totalForAvg += vitalValue;
            encountersTotal++;

            // set RANGE LOW and HIGH if needed
            if (vitalValue > resultSet.getDataRangeHigh()) {

                resultSet.setDataRangeHigh(vitalValue);
            }
            if (vitalValue < resultSet.getDataRangeLow()) {

                resultSet.setDataRangeLow(vitalValue);
            }

            // total patients for each value in map
            ResearchResultItem resultItem;
            if (datasetBuilder.containsKey(vitalValue)) {

                resultItem = datasetBuilder.get(vitalValue);

            } else {

                resultItem = new ResearchResultItem();
                resultItem.setPrimaryName(Float.toString(vitalValue));
            }
            // increment total by 1
            float currentValue = resultItem.getPrimaryValue();

            resultItem.setPrimaryValue(currentValue + 1);

            // get secondary data
            if (filters.getSecondaryDataset() != null) {
                if (filters.getSecondaryDataset().equals("gender")) {

                    // Set valuemap if not already
                    if (resultSet.getSecondaryValueMap() == null) {

                        Map<Float, String> secondaryResultMap = new HashMap<>();
                        secondaryResultMap.put(0.0f, "Male");
                        secondaryResultMap.put(1.0f, "Female");
                        secondaryResultMap.put(2.0f, "N/A");

                        resultSet.setSecondaryValueMap(secondaryResultMap);
                    }

                    String gender = "2.0";
                    if (patient.getSex() == null) {
                        gender = "2.0";
                    } else if (patient.getSex().matches("(?i:Male)")) {
                        gender = "0.0";
                    } else if (patient.getSex().matches("(?i:Female)")) {
                        gender = "1.0";
                    }

                    Map<String, Float> secondaryData = resultItem.getSecondaryData();
                    // Initialize secondary data
                    if (secondaryData == null) {

                        secondaryData = new HashMap<String, Float>();

                        // Make sure all keys exist
                        secondaryData.put("0.0", 0.0f);
                        secondaryData.put("1.0", 0.0f);
                        secondaryData.put("2.0", 0.0f);
                    }

                    // Add patient to secondary running total
                    // key will exist after initialization above
                    Float secTotal = secondaryData.get(gender);
                    secondaryData.put(gender, secTotal + 1.0f);

                    resultItem.setSecondaryData(secondaryData);

                } else if (filters.getSecondaryDataset().equals("pregnancyStatus")) {

                    // Set valuemap if not already
                    if (resultSet.getSecondaryValueMap() == null) {

                        Map<Float, String> secondaryResultMap = new HashMap<>();
                        secondaryResultMap.put(0.0f, "No");
                        secondaryResultMap.put(1.0f, "Yes");

                        resultSet.setSecondaryValueMap(secondaryResultMap);
                    }

                    Integer wksPregnant = encounter.getWeeksPregnant();
                    if (wksPregnant == null) wksPregnant = 0;

                    String pregnancyStatus = "0.0";
                    if (wksPregnant > 0) {
                        pregnancyStatus = "1.0";
                    }

                    Map<String, Float> secondaryData = resultItem.getSecondaryData();
                    // Initialize secondary data
                    if (secondaryData == null) {

                        secondaryData = new HashMap<String, Float>();

                        // Make sure all keys exist
                        secondaryData.put("0.0", 0.0f);
                        secondaryData.put("1.0", 0.0f);
                    }

                    // Add patient to secondary running total
                    // key will exist after initialization above
                    Float secTotal = secondaryData.get(pregnancyStatus);
                    secondaryData.put(pregnancyStatus, secTotal + 1.0f);

                    resultItem.setSecondaryData(secondaryData);
                }
                else if( filters.getSecondaryDataset().equals("age") ){

                    // Get patient Age - as of encounter date (Triage Visit)
                    Float age = (float) Math.floor(dateUtils.getAgeAsOfDateFloat(patient.getAge(), encounter.getDateOfTriageVisit()));

                }
            }

            // put result item back into map
            datasetBuilder.put(vitalValue, resultItem);
        }

        // save builder map as list in result set
        resultSet.setDataset(new ArrayList<ResearchResultItem>(datasetBuilder.values()));
        resultSet.setTotal(encountersTotal);

        // save AVERAGE
        float average = totalForAvg / encountersTotal;
        resultSet.setAverage(average);

        return resultSet;
    }

    // do stuff specific to age request
    private ResearchResultSetItem buildAgeResultSet(List<? extends IResearchEncounter> encounters, ResearchFilterItem filters){

        // do nothing if encounters is empty
        if( encounters.isEmpty() ) return new ResearchResultSetItem();

        // used to calculate average
        float totalForAvg = 0;
        float encountersTotal = 0;

        // Map to keep track of total patient for each age
        Map<Float, ResearchResultItem> datasetBuilder = new LinkedHashMap<>();
        // Keep track of patients, eliminate duplicate encounters
        Set<Integer> patientIds = new HashSet<>();
        // Object to send return
        ResearchResultSetItem resultSet = new ResearchResultSetItem();
        resultSet.setDataType("age");
        resultSet.setUnitOfMeasurement("years");


        // Loop through encounters, get data and build stats
        for (IResearchEncounter encounter : encounters) {

            IPatient patient = encounter.getPatient();

            // If patient age has already been counted, don't count again
            if( patientIds.contains(patient.getId()) ) continue;
            patientIds.add(patient.getId());

            // Get patient Age - as of encounter date (Triage Visit)
            Float age = (float) Math.floor(dateUtils.getAgeAsOfDateFloat(patient.getAge(), encounter.getDateOfTriageVisit()));

            // skip encounter if age is out of range
            if( age < filters.getFilterRangeStart() || age > filters.getFilterRangeEnd() ) continue;

            // increment total to calculate average
            totalForAvg += age;
            encountersTotal++;

            // set RANGE LOW and HIGH if needed
            if (age > resultSet.getDataRangeHigh()) {

                resultSet.setDataRangeHigh(age);
            }
            if (age < resultSet.getDataRangeLow()) {

                resultSet.setDataRangeLow(age);
            }

            // total patients for each value in map
            ResearchResultItem resultItem;
            if (datasetBuilder.containsKey(age)) {

                resultItem = datasetBuilder.get(age);

            } else {

                resultItem = new ResearchResultItem();
                resultItem.setPrimaryName(Float.toString(age));
            }
            // increment total by 1
            float currentValue = resultItem.getPrimaryValue();

            resultItem.setPrimaryValue(currentValue + 1);

            // @TODO - get secondary data
            if (filters.getSecondaryDataset() != null) {
                if (filters.getSecondaryDataset().equals("gender")) {

                    // Set valuemap if not already
                    if (resultSet.getSecondaryValueMap() == null) {

                        Map<Float, String> secondaryResultMap = new HashMap<>();
                        secondaryResultMap.put(0.0f, "Male");
                        secondaryResultMap.put(1.0f, "Female");
                        secondaryResultMap.put(2.0f, "N/A");

                        resultSet.setSecondaryValueMap(secondaryResultMap);
                    }

                    String gender = "2.0";
                    if (patient.getSex() == null) {
                        gender = "2.0";
                    } else if (patient.getSex().matches("(?i:Male)")) {
                        gender = "0.0";
                    } else if (patient.getSex().matches("(?i:Female)")) {
                        gender = "1.0";
                    }

                    Map<String, Float> secondaryData = resultItem.getSecondaryData();
                    // Initialize secondary data
                    if (secondaryData == null) {

                        secondaryData = new HashMap<String, Float>();

                        // Make sure all keys exist
                        secondaryData.put("0.0", 0.0f);
                        secondaryData.put("1.0", 0.0f);
                        secondaryData.put("2.0", 0.0f);
                    }

                    // Add patient to secondary running total
                    // key will exist after initialization above
                    Float secTotal = secondaryData.get(gender);
                    secondaryData.put(gender, secTotal + 1.0f);

                    resultItem.setSecondaryData(secondaryData);

                } else if (filters.getSecondaryDataset().equals("pregnancyStatus")) {

                    // Set valuemap if not already
                    if (resultSet.getSecondaryValueMap() == null) {

                        Map<Float, String> secondaryResultMap = new HashMap<>();
                        secondaryResultMap.put(0.0f, "No");
                        secondaryResultMap.put(1.0f, "Yes");

                        resultSet.setSecondaryValueMap(secondaryResultMap);
                    }

                    Integer wksPregnant = encounter.getWeeksPregnant();
                    if (wksPregnant == null) wksPregnant = 0;

                    String pregnancyStatus = "0.0";
                    if (wksPregnant > 0) {
                        pregnancyStatus = "1.0";
                    }

                    Map<String, Float> secondaryData = resultItem.getSecondaryData();
                    // Initialize secondary data
                    if (secondaryData == null) {

                        secondaryData = new HashMap<String, Float>();

                        // Make sure all keys exist
                        secondaryData.put("0.0", 0.0f);
                        secondaryData.put("1.0", 0.0f);
                    }

                    // Add patient to secondary running total
                    // key will exist after initialization above
                    Float secTotal = secondaryData.get(pregnancyStatus);
                    secondaryData.put(pregnancyStatus, secTotal + 1.0f);

                    resultItem.setSecondaryData(secondaryData);
                }
            }

            // put result item back into map
            datasetBuilder.put(age, resultItem);

        }

        // save builder map as list in result set
        resultSet.setDataset(new ArrayList<ResearchResultItem>(datasetBuilder.values()));
        resultSet.setTotal(encountersTotal);

        // save average
        float average = totalForAvg / encountersTotal;
        resultSet.setAverage(average);

        return resultSet;
    }

    // do stuff specific to pregnancy requests
    private ResearchResultSetItem buildPregnancyResultSet(List<? extends IResearchEncounter> encounters, ResearchFilterItem filters){

        // do nothing if encounters is empty
        if( encounters.isEmpty() ) return new ResearchResultSetItem();

        float totalForAvg = 0.0f;
        float encountersTotal = 0.0f;

        // Map to keep track of total patient for each age
        Map<Float, ResearchResultItem> datasetBuilder = new LinkedHashMap<>();
        // Keep track of patients, eliminate duplicate encounters
        Set<Integer> patientIds = new HashSet<>();
        // Object to send return
        ResearchResultSetItem resultSet = new ResearchResultSetItem();
        resultSet.setDataType(filters.getPrimaryDataset());

        // Set measurement unit for Weeks Pregnant
        if( filters.getPrimaryDataset().equals("pregnancyTime")) {
            resultSet.setUnitOfMeasurement("weeks");
        }

        // Loop through encounters, get data and build stats
        for (IResearchEncounter encounter : encounters) {

            IPatient patient = encounter.getPatient();

            // If patient age has already been counted, don't count again
            if( patientIds.contains(patient.getId()) ) continue;
            patientIds.add(patient.getId());

            if( filters.getPrimaryDataset().equals("pregnancyStatus")) {

                // increment total encounters
                encountersTotal++;

                Map<Float, String> resultMap = new HashMap<>();
                resultMap.put(0.0f, "No");
                resultMap.put(1.0f, "Yes");
                resultSet.setPrimaryValueMap(resultMap);

                Integer wksPregnant = encounter.getWeeksPregnant();
                if (wksPregnant == null) wksPregnant = 0;
                float pregnancyStatus = 0.0f;
                if (wksPregnant > 0.0f) {
                    pregnancyStatus = 1.0f;
                }

                // total patients for each value in map
                ResearchResultItem resultItem;
                if (datasetBuilder.containsKey(pregnancyStatus)) {

                    resultItem = datasetBuilder.get(pregnancyStatus);

                } else {

                    resultItem = new ResearchResultItem();
                    resultItem.setPrimaryName(Float.toString(pregnancyStatus));
                }
                // increment total by 1
                float currentValue = resultItem.getPrimaryValue();
                resultItem.setPrimaryValue(currentValue + 1);

                // put result item back into map
                datasetBuilder.put(pregnancyStatus, resultItem);
            }
            else if( filters.getPrimaryDataset().equals("pregnancyTime")){

                Integer wksPregnant = encounter.getWeeksPregnant();

                // only count patients who are actually pregnant
                if (wksPregnant == null || wksPregnant == 0 ) continue;

                if( wksPregnant < filters.getFilterRangeStart() || wksPregnant > filters.getFilterRangeEnd() ) continue;

                // set RANGE LOW and HIGH if needed
                if (wksPregnant > resultSet.getDataRangeHigh()) {

                    resultSet.setDataRangeHigh(wksPregnant);
                }
                if (wksPregnant < resultSet.getDataRangeLow()) {

                    resultSet.setDataRangeLow(wksPregnant);
                }

                // increment total encounters
                encountersTotal++;
                totalForAvg += wksPregnant;

                // total patients for each value in map
                ResearchResultItem resultItem;
                if (datasetBuilder.containsKey((float)wksPregnant)) {

                    resultItem = datasetBuilder.get((float)wksPregnant);

                } else {

                    resultItem = new ResearchResultItem();
                    resultItem.setPrimaryName(Float.toString(wksPregnant));
                }
                // increment total by 1
                float currentValue = resultItem.getPrimaryValue();
                resultItem.setPrimaryValue(currentValue + 1);

                // put result item back into map
                datasetBuilder.put((float)wksPregnant, resultItem);
            }

        }

        // save builder map as list in result set
        resultSet.setDataset(new ArrayList<>(datasetBuilder.values()));
        resultSet.setTotal(encountersTotal);

        // save average
        if( totalForAvg > 0.0f ) {
            float average = totalForAvg / encountersTotal;
            resultSet.setAverage(average);
        }

        return resultSet;
    }

    // do stuff specific to gender requests
    private ResearchResultSetItem buildGenderResultSet(List<? extends IResearchEncounter> encounters, ResearchFilterItem filters) {

        // do nothing if encounters is empty
        if( encounters.isEmpty() ) return new ResearchResultSetItem();

        float encountersTotal = 0.0f;

        // Map to keep track of total patient for each age
        Map<Float, ResearchResultItem> datasetBuilder = new LinkedHashMap<>();
        // Keep track of patients, eliminate duplicate encounters
        Set<Integer> patientIds = new HashSet<>();
        // Object to send return
        ResearchResultSetItem resultSet = new ResearchResultSetItem();
        resultSet.setDataType(filters.getPrimaryDataset());

        // Loop through encounters, get data and build stats
        for (IResearchEncounter encounter : encounters) {

            IPatient patient = encounter.getPatient();

            // If patient age has already been counted, don't count again
            if( patientIds.contains(patient.getId()) ) continue;
            patientIds.add(patient.getId());

            // increment total encounters
            encountersTotal++;

            Map<Float, String> resultMap = new HashMap<>();
            resultMap.put(0.0f, "Male");
            resultMap.put(1.0f, "Female");
            resultMap.put(2.0f, "N/A");
            resultSet.setPrimaryValueMap(resultMap);

            float gender = 2.0f;
            // Do case in-sensitive comparison to be safe
            //1 = female
            //0 = male
            //2 = no sex
            if (patient.getSex() == null){
                gender = 2.0f;
            }else if (patient.getSex().matches("(?i:Male)")) {
                gender = 0.0f;
            } else if (patient.getSex().matches("(?i:Female)")) {
                gender = 1.0f;
            }

            // total patients for each value in map
            ResearchResultItem resultItem;
            if (datasetBuilder.containsKey(gender)) {

                resultItem = datasetBuilder.get(gender);

            } else {

                resultItem = new ResearchResultItem();
                resultItem.setPrimaryName(Float.toString(gender));
            }
            // increment total by 1
            float currentValue = resultItem.getPrimaryValue();
            resultItem.setPrimaryValue(currentValue + 1);

            // put result item back into map
            datasetBuilder.put(gender, resultItem);
        }

        // save builder map as list in result set
        resultSet.setDataset(new ArrayList<>(datasetBuilder.values()));
        resultSet.setTotal(encountersTotal);

        return resultSet;

    }

}