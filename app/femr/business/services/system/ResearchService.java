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

import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.services.core.IResearchService;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.common.dtos.ServiceResponse;
import femr.common.models.*;
import femr.ui.models.research.json.ResearchGraphDataItem;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.Medication;
import femr.data.models.mysql.PatientEncounter;
import femr.data.models.mysql.PatientEncounterVital;
import femr.data.models.mysql.PatientPrescription;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ResearchService implements IResearchService {

    private final IRepository<IPatientEncounter> patientEncounterRepository;
    private final IRepository<IPatientEncounterVital> patientEncounterVitalRepository;
    protected final IRepository<IVital> vitalRepository;
    private final IRepository<IPatientPrescription> prescriptionRepository;
    private final IRepository<IMedication> medicationRepository;
    private final DomainMapper domainMapper;

    /**
     * Initializes the research service and injects the dependence
     */
    @Inject
    public ResearchService(IRepository<IPatientEncounter> patientEncounterRepository,
                           IRepository<IPatientEncounterVital> patientEncounterVitaRepository,
                           IRepository<IVital> vitalRepository,
                           IRepository<IPatientPrescription> prescriptionRepository,
                           IRepository<IMedication> medicationRepository,
                           DomainMapper domainMapper) {

        this.patientEncounterRepository = patientEncounterRepository;
        this.patientEncounterVitalRepository = patientEncounterVitaRepository;
        this.vitalRepository = vitalRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.medicationRepository = medicationRepository;
        this.domainMapper = domainMapper;
    }


    @Override
    public ServiceResponse<ResearchGraphDataItem> getGraphData(ResearchFilterItem filters){

        ServiceResponse<ResearchGraphDataItem> response = new ServiceResponse<>();

        String primaryDatasetName = filters.getPrimaryDataset();
        //TODO: gender throws error here due to patients with no sex
        ResearchResult primaryItems = getDatasetItems(primaryDatasetName, filters);

        ResearchResult secondaryItems = new ResearchResult();
        String secondaryDatasetName = filters.getSecondaryDataset();
        if (!secondaryDatasetName.isEmpty()) {

            secondaryItems = getDatasetItems(secondaryDatasetName, filters);
        }

        try{
            ResearchGraphDataItem graphDataItem = createResearchGraphItem(primaryItems, secondaryItems, filters);
            response.setResponseObject(graphDataItem);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    @Override
    public ServiceResponse<Map<Integer, String>> getAllMedications(){

        ServiceResponse<Map<Integer, String>> response = new ServiceResponse<>();

        try {
            List<? extends IMedication> medications = medicationRepository.findAll(Medication.class);

            Map<Integer, String> medicationItems = new HashMap<>();

            for (IMedication medication : medications) {

                medicationItems.put(
                        medication.getId(),
                        medication.getName()
                );
            }
            response.setResponseObject(medicationItems);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    public static ResearchGraphDataItem createResearchGraphItem(ResearchResult primaryResult, ResearchResult secondaryResult, ResearchFilterItem filters) {

        ResearchGraphDataItem graphModel = new ResearchGraphDataItem();
        List<ResearchItem> graphData = new ArrayList<>();
        Map<String, ResearchItem> groupedData = new HashMap<>();

        String yAxisTitle = "Number of Patients";
        String xAxisTitle = WordUtils.capitalize(StringUtils.splitCamelCase(primaryResult.getDataType()));
        String unitOfMeasurement = primaryResult.getUnitOfMeasurement();

        List<Float> sortedPrimary = new ArrayList<>();
        Map<Integer, Float> primaryDataset = primaryResult.getDataset();
        Map<Integer, Float> secondaryDataset = secondaryResult.getDataset();
        int sampleSize = primaryDataset.size();
        float total = 0;
        float rangeHigh = 0;
        float rangeLow = 10000;
        float median = 0;

        // Medications will not group correctly, just bundle and return
        if( filters.getPrimaryDataset().equals("prescribedMeds") ||
                filters.getPrimaryDataset().equals("dispensedMeds") ){

            for (Integer key : primaryDataset.keySet()) {

                // Make sure all secondary keys are set for all items
                Float value = primaryDataset.get(key);

                ResearchItem currItem = new ResearchItem();
                currItem.setPrimaryName(Float.toString((float) key));
                currItem.setPrimaryValue(value);

                graphData.add(currItem);
            }


            graphModel.setAverage(0.0f);
            graphModel.setMedian(median);
            graphModel.setRangeLow(rangeLow);
            graphModel.setRangeHigh(rangeHigh);
            graphModel.setGraphData(graphData);
            graphModel.setPrimaryValuemap(primaryResult.getValueMap());
            graphModel.setSecondaryValuemap(secondaryResult.getValueMap());
            graphModel.setyAxisTitle(yAxisTitle);
            graphModel.setxAxisTitle(xAxisTitle);
            graphModel.setUnitOfMeasurement(unitOfMeasurement);
            return graphModel;
        }


        // total the individual patients based on their value
        Map<Float, ResearchItem> primaryGraphTotals = new HashMap<>();

        // used to ensure all secondary keys are present in all items, just in case they have no data
        Map<String, Integer> secondaryKeyset = new HashMap<>();

        for( Integer key : primaryDataset.keySet() ){

            Float value = primaryDataset.get(key);
            sortedPrimary.add(value);

            // Find current primary Value and add to builder map
            ResearchItem currItem;
            if( primaryGraphTotals.containsKey(value) ){

                currItem = primaryGraphTotals.get(value);
                float currItemTotal = currItem.getPrimaryValue() + 1;
                currItem.setPrimaryValue(currItemTotal);
            }
            else{

                currItem = new ResearchItem();
                currItem.setPrimaryValue(1);
            }
            currItem.setPrimaryName(value.toString());

            // check for and add secondary item to total
            if( secondaryDataset.containsKey(key) ) {

                // Get secondary item form current ResearchItem
                Map<String, Float> secondaryItems = currItem.getSecondaryData();
                Float secondaryValue = secondaryDataset.get(key);
                Float secondaryTotal = 1.0f;
                String secondaryKey = secondaryValue.toString();

                // Keep track of unique keys for secondary items
                // want to make sure all possible keys are present in graphData
                secondaryKeyset.put(secondaryKey, 0);

                if (secondaryItems.containsKey(secondaryKey)) {

                    secondaryTotal = secondaryItems.get(secondaryKey);
                    secondaryTotal++;
                    secondaryItems.put(secondaryKey, secondaryTotal);
                }
                else{

                    secondaryItems.put(secondaryKey, 1.0f);
                }

                // add secondary totals to currItem
                currItem.setSecondaryData(secondaryItems);
            }


            primaryGraphTotals.put(value, currItem);

            // Calculate Stats while building data
            // check range
            if( value > rangeHigh ){
                rangeHigh = value;
            }
            if( value < rangeLow){
                rangeLow = value;
            }

            // sum total for average
            total += value;

        }

        // If no grouping and over 30 items, force groups of 10
        if( primaryGraphTotals.keySet().size() > 30 && !filters.isGroupPrimary()
                && (filters.getGraphType().equals("pie") || filters.getGraphType().equals("stacked-bar") || filters.getGraphType().equals("grouped-bar") ) ){

            filters.setGroupPrimary(true);
            filters.setGroupFactor(10);
        }

        // Build group key values -- don't bother if there will only be 1 group
        // make happen automatically too? -- primaryGraphTotals.keySet().size() > 20
        if( filters.isGroupPrimary() && primaryGraphTotals.keySet().size() > filters.getGroupFactor() ) {

            int groupFactor = filters.getGroupFactor();
            int firstLowKey = (int)(rangeLow / groupFactor) * groupFactor;
            int lastLowKey = (int)(rangeHigh / groupFactor) * groupFactor;

            //int totalGroups = (int)((lastLowKey-firstLowKey) / groupFactor);
            List<String> groupIndexes = new ArrayList<String>();
            for( int low = firstLowKey; low <= lastLowKey; low+=groupFactor ){

                int lowTemp = low;
                if( low < filters.getRangeStart() ){

                    lowTemp = filters.getRangeStart().intValue();
                }

                int high = low + (groupFactor - 1);
                if( high > filters.getRangeEnd() ){

                    high = filters.getRangeEnd().intValue();
                }

                String groupIndex = String.format("%d - %d", lowTemp, high);
                groupIndexes.add(groupIndex);

                ResearchItem blankItem = new ResearchItem(groupIndex);
                groupedData.put(groupIndex, blankItem);
            }

            for (Float key : primaryGraphTotals.keySet()) {

                ResearchItem currItem = primaryGraphTotals.get(key);

                Float newVal = Float.parseFloat(currItem.getPrimaryName());
                Float newCount = currItem.getPrimaryValue();

                int targetGroup = (int)(newVal / groupFactor) - (firstLowKey / groupFactor);
                String groupIndex = groupIndexes.get(targetGroup);

                ResearchItem finalItem;
                if( groupedData.containsKey(groupIndex) ){

                    // add current single item to existing group Item
                    finalItem = groupedData.get(groupIndex);

                    Float currCount = finalItem.getPrimaryValue();
                    currCount += newCount;
                    finalItem.setPrimaryValue(currCount);

                    Map<String, Float> currSecondaryData = currItem.getSecondaryData();
                    for( String sKey : currSecondaryData.keySet() ){

                        Float sCount = currSecondaryData.get(sKey);

                        Map<String, Float> finalSecondaryData = finalItem.getSecondaryData();
                        if( finalSecondaryData.containsKey(sKey) ){

                            Float finalCount = finalSecondaryData.get(sKey);
                            finalCount += sCount;
                            finalSecondaryData.put(sKey, finalCount);
                        }
                        else{

                            finalSecondaryData.put(sKey, sCount);
                        }

                        finalItem.setSecondaryData(finalSecondaryData);
                    }

                }
                else{

                    // haven't encountered item in range before, just set to current single item
                    finalItem = new ResearchItem();
                    finalItem.setPrimaryName(groupIndex);
                    finalItem.setPrimaryValue(newCount);
                    finalItem.setSecondaryData(currItem.getSecondaryData());

                }
                // add item to grouped data
                groupedData.put(groupIndex, finalItem);
            }

            SortedSet<String> keys = new TreeSet<>(new GroupedCompare());
            keys.addAll(groupedData.keySet());
            for (String key : keys) {

                // Make sure all secondary keys are set for all items
                ResearchItem currItem = groupedData.get(key);
                Map<String, Float> secondaryData = currItem.getSecondaryData();
                for (String secondaryKey : secondaryKeyset.keySet()) {

                    if( !secondaryData.containsKey(secondaryKey) ){

                        secondaryData.put(secondaryKey, 0.0f);
                    }
                }

                graphData.add(currItem);
            }

        }
        else{

            SortedSet<Float> keys = new TreeSet<>(primaryGraphTotals.keySet());
            for (Float key : keys) {

                // Make sure all secondary keys are set for all items
                ResearchItem currItem = primaryGraphTotals.get(key);
                Map<String, Float> secondaryData = currItem.getSecondaryData();
                for (String secondaryKey : secondaryKeyset.keySet()) {

                    if( !secondaryData.containsKey(secondaryKey) ){

                        secondaryData.put(secondaryKey, 0.0f);
                    }
                }

                graphData.add(currItem);
            }
        }



        // Sort primary Data
        Collections.sort(sortedPrimary);

        // Get Median Value from sorted list
        float average = total / sampleSize;

        if( sampleSize > 1 ) {
            if (sampleSize % 2 == 0) {

                int i = (sampleSize / 2) - 1;
                int j = i + 1;

                // get vals i and j
                float val1 = sortedPrimary.get(i);
                float val2 = sortedPrimary.get(j);


                //Integer key1 = primaryKeyList.get(i);
                //Integer key2 = primaryKeyList.get(j);

                //float val1 = primaryDataset.get(key1);
                //float val2 = primaryDataset.get(key2);

                median = (val1 + val2) / 2;
            } else {

                int i = (int) Math.floor(sampleSize / 2);

                //Integer key = primaryKeyList.get(i);
                //median = primaryDataset.get(key);

                median = sortedPrimary.get(i);

            }
        }
        else{

            median = sortedPrimary.get(0);
        }

        // build graph model item

        graphModel.setAverage(average);
        graphModel.setMedian(median);
        graphModel.setRangeLow(rangeLow);
        graphModel.setRangeHigh(rangeHigh);
        graphModel.setGraphData(graphData);
        graphModel.setPrimaryValuemap(primaryResult.getValueMap());
        graphModel.setSecondaryValuemap(secondaryResult.getValueMap());
        graphModel.setyAxisTitle(yAxisTitle);
        graphModel.setxAxisTitle(xAxisTitle);
        graphModel.setUnitOfMeasurement(unitOfMeasurement);

        return graphModel;

    }



    private ResearchResult getDatasetItems(String datasetName, ResearchFilterItem filters){

        switch(datasetName){

            // Single Value Vital Items
            case "weight":
            case "temperature":
            case "heartRate":
            case "respiratoryRate":
            case "oxygenSaturation":
            case "glucose":
            case "bloodPressureSystolic":
            case "bloodPressureDiastolic":
                return getPatientVitals(datasetName, filters);

            // Special Case Vital Item
            case "height":
                return getPatientHeights(filters);

            // Patient Specific Items
            case "age":
            case "gender":
            case "pregnancyStatus":
            case "pregnancyTime":
                return getPatientAttribute(datasetName, filters);

            // Medication Items
            case "prescribedMeds":
                return getPrescribedMedications(filters);

            case "dispensedMeds":
                return getDispensedMedications(filters);

            default:

                return new ResearchResult();
        }

    }


    /**
     * {@inheritDoc}
     */
    public ResearchResult getPatientVitals(String vitalName, ResearchFilterItem filters) {

        String startDateString = filters.getStartDate();
        String endDateString = filters.getEndDate();
        Integer medicationID = filters.getMedicationId();

        ResearchResult resultObj = new ResearchResult();
        Map<Integer, Float> resultItems = new HashMap<>();

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

            startDateObj = new Date();
            endDateObj = new Date();
        }

        Query<PatientEncounterVital> q = QueryProvider.getPatientEncounterVitalQuery();

        if( medicationID > 0 ) {

            Query<PatientPrescription> pQ = QueryProvider.getPatientPrescriptionQuery();

            pQ.fetch("patientEncounter").where().eq("medication.id", medicationID);
            List<? extends IPatientPrescription> patientPrescriptions = prescriptionRepository.find(pQ);

            List<Integer> scriptEncounterIds = new ArrayList<>();
            int i = 0;
            for (IPatientPrescription script : patientPrescriptions) {

                scriptEncounterIds.add(script.getPatientEncounter().getId());
            }

            q.where().in("patientEncounterId", scriptEncounterIds);
        }

        q.where()
                .gt("dateTaken", sqlFormat.format(startDateObj))
                .lt("dateTaken", sqlFormat.format(endDateObj))
                .eq("vital.name", vitalName)
                .orderBy("vital_value")
                .findList();

        List<? extends IPatientEncounterVital> patientEncounterVitals = patientEncounterVitalRepository.find(q);



/*
        select t0.id c0, t0.user_id c1, t0.patient_encounter_id c2, t0.vital_value c3, t0.date_taken c4,
        t1.id c5, t1.name c6, t1.data_type c7, t1.unit_of_measurement c8, t1.isDeleted c9
        from patient_encounter_vitals t0
        left outer join vitals as t1 on t1.id = t0.vital_id
        left join patient_prescriptions as t2 on t2.encounter_id = t0.patient_encounter_id
        where t0.date_taken > '2014-10-24 00:00:00'
        and t0.date_taken < '2014-11-05 11:59:59'
        and t1.name = 'temperature'
        and t2.medication_id = 1
        order by vital_value

        select t0.id c0, t0.user_id c1, t0.patient_encounter_id c2, t0.vital_value c3, t0.date_taken c4
        from patient_encounter_vitals t0
        left outer join vitals t1 on t1.id = t0.vital_id
        where t0.date_taken > ?
        and t0.date_taken < ?
        and t1.name = ?
        order by vital_value
*/


        String unitOfMeasurement = "";
        for (IPatientEncounterVital eVital : patientEncounterVitals) {

            if( unitOfMeasurement.isEmpty() ) {
                unitOfMeasurement = eVital.getVital().getUnitOfMeasurement();
            }

            Float vitalValue = eVital.getVitalValue();
            if( vitalValue >= filters.getRangeStart() && vitalValue <= filters.getRangeEnd() ) {

                resultItems.put(
                        eVital.getPatientEncounterId(),
                        vitalValue
                );
            }

        }

        resultObj.setDataType(vitalName);
        resultObj.setUnitOfMeasurement(unitOfMeasurement);
        resultObj.setDataset(resultItems);

        return resultObj;
    }

    /**
     * {@inheritDoc}
     */
    public ResearchResult getPatientAttribute(String attributeName, ResearchFilterItem filters) {

        String startDateString = filters.getStartDate();
        String endDateString = filters.getEndDate();
        Integer medicationID = filters.getMedicationId();

        ResearchResult resultObj = new ResearchResult();
        Map<Integer, Float> resultItems = new HashMap<>();
        Map<Float, String> resultMap = new HashMap<>();

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

            startDateObj = new Date();
            endDateObj = new Date();
        }

        // Get patients that had an encounter between startDate and endDate
        // using dateOfTriageVisit for now -- might want to also check dateOfMedicalVisit & dateOfPharmacyVisit
        Query<PatientEncounter> q = QueryProvider.getPatientEncounterQuery();

        if( medicationID > 0 ) {

            Query<PatientPrescription> pQ = QueryProvider.getPatientPrescriptionQuery();

            pQ.fetch("patientEncounter").where().eq("medication.id", medicationID);
            List<? extends IPatientPrescription> patientPrescriptions = prescriptionRepository.find(pQ);

            List<Integer> scriptEncounterIds = new ArrayList<>();
            int i = 0;
            for (IPatientPrescription script : patientPrescriptions) {

                scriptEncounterIds.add(script.getPatientEncounter().getId());
            }

            q.where().in("id", scriptEncounterIds);
        }

        q.fetch("patient")
                .where()
                .gt("dateOfTriageVisit", sqlFormat.format(startDateObj))
                .lt("dateOfTriageVisit", sqlFormat.format(endDateObj))
                .orderBy("id")
                .findList();

        List<? extends IPatientEncounter> encounters = patientEncounterRepository.find(q);

        String unitOfMeasurement = "";
        switch (attributeName) {

            case "age":

                unitOfMeasurement = "years";
                for (IPatientEncounter encounter : encounters) {
                    IPatient patient = encounter.getPatient();

                    Float age = (float) Math.floor(dateUtils.getAgeFloat(patient.getAge()));

                    if( age >= filters.getRangeStart() && age <= filters.getRangeEnd() ) {

                        resultItems.put(
                                encounter.getId(),
                                age
                        );
                    }
                }
                break;

            case "gender":

                resultMap.put(0.0f, "Male");
                resultMap.put(1.0f, "Female");
                resultMap.put(2.0f, "N/A");

                for (IPatientEncounter encounter : encounters) {
                    IPatient patient = encounter.getPatient();

                    float gender = -1;
                    // Do case in-sensitve comparison to be safe
                    //1 = female
                    //0 = male
                    //2 = no sex
                    if (patient.getSex() == null){
                        gender = 2;
                    }else if (patient.getSex().matches("(?i:Male)")) {
                        gender = 0;
                    } else if (patient.getSex().matches("(?i:Female)")) {
                        gender = 1;
                    }
                    resultItems.put(
                            encounter.getId(),
                            gender
                    );
                }
                break;

            case "pregnancyStatus":

                for (IPatientEncounter encounter : encounters) {

                    resultMap.put(0.0f, "No");
                    resultMap.put(1.0f, "Yes");

                    Integer wksPregnant = encounter.getWeeksPregnant();
                    if (wksPregnant == null) wksPregnant = 0;
                    float pregnancyStatus = 0;
                    if (wksPregnant > 0) {
                        pregnancyStatus = 1;
                    }
                    resultItems.put(
                            encounter.getId(),
                            pregnancyStatus
                    );
                }
                break;

            case "pregnancyTime":

                unitOfMeasurement = "weeks";
                for (IPatientEncounter encounter : encounters) {

                    Integer weeksPregnant = encounter.getWeeksPregnant();
                    if (weeksPregnant == null) weeksPregnant = 0;

                    if( weeksPregnant >= filters.getRangeStart() && weeksPregnant <= filters.getRangeEnd() ) {

                        resultItems.put(
                                encounter.getId(),
                                (float) weeksPregnant
                        );
                    }
                }
                break;
        }


        resultObj.setDataType(attributeName);
        resultObj.setUnitOfMeasurement(unitOfMeasurement);
        resultObj.setDataset(resultItems);
        resultObj.setValueMap(resultMap);

        return resultObj;
    }


    public ResearchResult getPatientHeights(ResearchFilterItem filters){

        String startDateString = filters.getStartDate();
        String endDateString = filters.getEndDate();
        Integer medicationID = filters.getMedicationId();

        ResearchResult resultObj = new ResearchResult();
        Map<Integer, Float> buildItems = new HashMap<>();

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

            startDateObj = new Date();
            endDateObj = new Date();
        }


        Query<PatientEncounterVital> q = QueryProvider.getPatientEncounterVitalQuery();

        List<Integer> scriptEncounterIds = new ArrayList<>();
        if( medicationID > 0 ) {

            Query<PatientPrescription> pQ = QueryProvider.getPatientPrescriptionQuery();

            pQ.fetch("patientEncounter").where().eq("medication.id", medicationID);
            List<? extends IPatientPrescription> patientPrescriptions = prescriptionRepository.find(pQ);

            int i = 0;
            for (IPatientPrescription script : patientPrescriptions) {

                scriptEncounterIds.add(script.getPatientEncounter().getId());
            }

            q.where().in("patientEncounterId", scriptEncounterIds);
        }

        q.fetch("vital")
                .where()
                .gt("dateTaken", sqlFormat.format(startDateObj))
                .lt("dateTaken", sqlFormat.format(endDateObj))
                .eq("vital.name", "heightFeet")
                .orderBy("patient_encounter_id")
                .findList();
        List<? extends IPatientEncounterVital> patientFeet = patientEncounterVitalRepository.find(q);

        q = QueryProvider.getPatientEncounterVitalQuery();

        if( medicationID > 0 && scriptEncounterIds.size() > 0 ) {

            q.where().in("patientEncounterId", scriptEncounterIds);
        }
        q.fetch("vital")
                .where()
                .gt("dateTaken", sqlFormat.format(startDateObj))
                .lt("dateTaken", sqlFormat.format(endDateObj))
                .eq("vital.name", "heightInches")
                .orderBy("patient_encounter_id")
                .findList();
        List<? extends IPatientEncounterVital> patientInches = patientEncounterVitalRepository.find(q);

        //Map<Integer, ResearchItem> researchItems = new HashMap<>();
        // Convert feet to inches
        String unitOfMeasurement = "feet/inches";
        for (IPatientEncounterVital eVital : patientFeet) {

            float heightInches = 12 * eVital.getVitalValue();
            buildItems.put(
                    eVital.getPatientEncounterId(),
                    heightInches
            );

        }

        for(IPatientEncounterVital eVital : patientInches){

            if( buildItems.containsKey(eVital.getPatientEncounterId()) ){

                float heightInches = buildItems.get(eVital.getPatientEncounterId());
                heightInches = heightInches + eVital.getVitalValue();
                buildItems.put(eVital.getPatientEncounterId(), heightInches);
            }
        }

        Map<Integer, Float> resultItems = new HashMap<>();
        for( Integer key : buildItems.keySet() ){

            Float value = buildItems.get(key);
            if( value >= filters.getRangeStart() && value <= filters.getRangeEnd() ){

                resultItems.put(key, value);
            }

        }

        resultObj.setDataType("height");
        resultObj.setUnitOfMeasurement(unitOfMeasurement);
        resultObj.setDataset(resultItems);

        return resultObj;

    }




    public ResearchResult getPrescribedMedications(ResearchFilterItem filters){

        String startDateString = filters.getStartDate();
        String endDateString = filters.getEndDate();

        ResearchResult resultObj = new ResearchResult();
        Map<Integer, Float> resultItems = new HashMap<>();
        Map<Float, String> resultMap = new HashMap<>();

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

            startDateObj = new Date();
            endDateObj = new Date();
        }


        Query<PatientPrescription> q = QueryProvider.getPatientPrescriptionQuery();
        q.fetch("medication")
                .where()
                .gt("dateTaken", sqlFormat.format(startDateObj))
                .lt("dateTaken", sqlFormat.format(endDateObj))
                .findList();

        List<? extends IPatientPrescription> patientMedication = prescriptionRepository.find(q);

        String unitOfMeasurement = "";
        for (IPatientPrescription prescription : patientMedication) {

            Integer key = prescription.getMedication().getId();

            // Build prescription name map
            if( !resultMap.containsKey((float)key) ){

                resultMap.put((float)key, prescription.getMedication().getName());
            }

            Float count = 1.0f;
            if( resultItems.containsKey(key) ){

                count = resultItems.get(key) + 1.0f;
            }
            resultItems.put(
                    key,
                    count
            );

        }

        resultObj.setDataType("prescribedMeds");
        resultObj.setUnitOfMeasurement(unitOfMeasurement);
        resultObj.setDataset(resultItems);
        resultObj.setValueMap(resultMap);

        return resultObj;
    }


    // @TODO - need to make this work correctly
    // want Medication Name (xAxis) vs Total Dispensed (yAxis)
    // Need to tweak some things, this is different than # of patients
    public ResearchResult getDispensedMedications(ResearchFilterItem filters){

        String startDateString = filters.getStartDate();
        String endDateString = filters.getEndDate();

        ResearchResult resultObj = new ResearchResult();
        Map<Integer, Float> resultItems = new HashMap<>();
        Map<Float, String> resultMap = new HashMap<>();

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

            startDateObj = new Date();
            endDateObj = new Date();
        }

        Query<PatientPrescription> q = QueryProvider.getPatientPrescriptionQuery();
        q.fetch("medication")
                .where()
                .gt("dateTaken", sqlFormat.format(startDateObj))
                .lt("dateTaken", sqlFormat.format(endDateObj))
                .findList();

        List<? extends IPatientPrescription> patientMedication = prescriptionRepository.find(q);

        String unitOfMeasurement = "";
        for (IPatientPrescription prescription : patientMedication) {

            Integer key = prescription.getMedication().getId();

            // Build prescription name map
            if( !resultMap.containsKey((float)key) ){

                resultMap.put((float)key, prescription.getMedication().getName());
            }

            Float count = (float)prescription.getAmount();
            if( resultItems.containsKey(key) ){

                count = resultItems.get(key) + count;
            }
            resultItems.put(
                    key,
                    count
            );

        }

        resultObj.setDataType("dispensedMeds");
        resultObj.setUnitOfMeasurement(unitOfMeasurement);
        resultObj.setDataset(resultItems);
        resultObj.setValueMap(resultMap);

        return resultObj;

    }

}



// Sorts Strings in format "float - float" by using the first float of the string
class GroupedCompare implements Comparator<String>{

    @Override
    public int compare(String s1, String s2){

        String[] s1Matches = s1.split("-");
        String s1First;
        Float s1Val = 0.0f;
        if( s1Matches.length > 1 ) {
            s1First = s1Matches[0];
        }
        else{
            s1First = s1;
        }
        s1Val = Float.parseFloat(s1First);

        String[] s2Matches = s2.split("-");
        String s2First;
        Float s2Val = 0.0f;
        if( s2Matches.length > 1 ) {
            s2First = s2Matches[0];
        }
        else{
            s2First = s2;
        }
        s2Val = Float.parseFloat(s2First);

        if( s1Val < s2Val ){

            return -1;
        }
        else if( s1Val > s2Val ){

            return 1;
        }
        return 0;
    }
}
