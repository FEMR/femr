package femr.business.helpers;

import org.hl7.fhir.r5.model.BooleanType;
import org.hl7.fhir.r5.model.CodeableConcept;
import org.hl7.fhir.r5.model.Quantity;

/**
 * Easy access to codes we use to encode concepts in FHIR.
 */
public class FhirCodeableConcepts {
    private FhirCodeableConcepts() {}

    private static final String LOINC = "http://loinc.org";
    private static final String UNITS_OF_MEASURE = "http://unitsofmeasure.org";


    /**
     * get FHIR coding for blood glucose (mass per volume units)
     * @return blood glucose concept
     */
    public static CodeableConcept getClinicalInformationConcept(){
        CodeableConcept bloodGlucose = new CodeableConcept();
        bloodGlucose.setText("Clinical information");
        bloodGlucose.addCoding(LOINC, "55752-0", "Clinical information");
        return bloodGlucose;
    }

    /**
     * get FHIR coding for blood glucose (mass per volume units)
     * @return blood glucose concept
     */
    public static CodeableConcept getBloodGlucoseConceptMassPerVolume(){
        CodeableConcept bloodGlucose = new CodeableConcept();
        bloodGlucose.setText("Glucose in Blood");
        bloodGlucose.addCoding(LOINC, "2339-0", "Glucose [Mass/volume] in Blood");
        return bloodGlucose;
    }

    /**
     * create quantity with unit mg/dL
     * @return mg per deciliter quantity
     */
    public static Quantity getmgPerdLQuantity(Float value){
        Quantity quantity = new Quantity();
        quantity.setValue(value);
        quantity.setSystem(UNITS_OF_MEASURE);
        quantity.setCode("mg/dL");
        quantity.setUnit("mg/dL");
        return quantity;
    }


    /**
     * get FHIR coding for Weeks Pregnant
     * @return weeks pregnant concept
     */
    public static CodeableConcept getGestationalAge(){
        CodeableConcept weeksPregnant = new CodeableConcept();
        weeksPregnant.setText("Weeks Pregnant");
        weeksPregnant.addCoding(LOINC, "18185-9", "Gestational age");
        return weeksPregnant;
    }

    /**
     * get FHIR coding for Weeks Pregnant
     * @return weeks pregnant concept
     */
    public static Quantity getTimeWeeksQuantity(Float value){
        Quantity quantity = new Quantity();
        quantity.setValue(value);
        quantity.setSystem(UNITS_OF_MEASURE);
        quantity.setCode("wk");
        quantity.setUnit("week");
        return quantity;
    }

    /**
     * Adds FHIR coding for body height
     * @return body height concept
     */
    public static CodeableConcept getBodyHeight(){
        CodeableConcept o2Sat = new CodeableConcept();
        o2Sat.setText("Body height");
        o2Sat.addCoding(LOINC, "8302-2", "Body height");
        return o2Sat;
    }

    /**
     * Adds FHIR coding for inches
     * @return quantity of unit inches
     */
    public static Quantity getQuantityInches(Float value){
        Quantity quantity = new Quantity();
        quantity.setValue(value);
        quantity.setSystem(UNITS_OF_MEASURE);
        quantity.setCode("[in_i]");
        quantity.setUnit("in");
        return quantity;
    }


    /**
     * Adds FHIR coding for oxygen saturation
     * @return oxygen saturation concept
     */
    public static CodeableConcept getOxygenSaturation(){
        CodeableConcept o2Sat = new CodeableConcept();
        o2Sat.setText("Oxygen Saturation");
        o2Sat.addCoding(LOINC, "2708-6", "Oxygen saturation in Arterial blood");
        o2Sat.addCoding(LOINC, "59408-5", "Oxygen saturation in Arterial blood by Pulse oximetry");
        return o2Sat;
    }

    /**
     * Creates a Quantity of unit %O2
     * @param value the value of the quantity
     * @return the %O2 Quantity
     */
    public static Quantity getOxygenSaturationQuantity(Float value){
        Quantity quantity = new Quantity();
        quantity.setValue(value);
        quantity.setSystem(UNITS_OF_MEASURE);
        quantity.setCode("%");
        quantity.setUnit("%O2");
        return quantity;
    }

    /**
     * Adds FHIR coding for diastolic blood pressure
     * @return bloodPressureDiastolic
     */
    public static CodeableConcept getHeartRate(){
        CodeableConcept bloodPressureDiastolic = new CodeableConcept();
        bloodPressureDiastolic.setText("Heart Rate");
        bloodPressureDiastolic.addCoding(LOINC, "8867-4", "Heart Rate");
        return bloodPressureDiastolic;
    }

    /**
     * Creates a Quantity of unit mmHg
     * @param value the value of the quantity
     * @return diastolic
     */
    public static Quantity getBPMQuantity(Float value){
        Quantity quantity = new Quantity();
        quantity.setValue(value);
        quantity.setSystem(UNITS_OF_MEASURE);
        quantity.setCode("/min");
        quantity.setUnit("bpm");
        return quantity;
    }

    /**
     * Adds FHIR coding for systolic blood pressure
     * @return bloodPressureSystolic
     */
    public static CodeableConcept getBloodPressureSystolic() {
        CodeableConcept bloodPressureSystolic = new CodeableConcept();
        bloodPressureSystolic.setText("Systolic blood pressure");
        bloodPressureSystolic.addCoding(LOINC, "8480-6", "Systolic blood pressure");
        return bloodPressureSystolic;

    }

    /**
     * Adds FHIR coding for diastolic blood pressure
     * @return bloodPressureDiastolic
     */
    public static CodeableConcept getBloodPressureDiastolic(){
        CodeableConcept bloodPressureDiastolic = new CodeableConcept();
        bloodPressureDiastolic.setText("Diastolic blood pressure");
        bloodPressureDiastolic.addCoding(LOINC, "8462-4", "Diastolic blood pressure");
        return bloodPressureDiastolic;

    }

    /**
     * Creates a Quantity of unit mmHg
     * @param value the value of the quantity
     * @return diastolic
     */
    public static Quantity getQuantityMmHG(Float value){
        Quantity quantity = new Quantity();
        quantity.setValue(value);
        quantity.setSystem(UNITS_OF_MEASURE);
        quantity.setCode("mm[Hg]");
        quantity.setUnit("mmHg");
        return quantity;
    }

    /**
     * Adds FHIR coding for body temperature
     * @return bodyTemperature
     */
    public static CodeableConcept getBodyTemperature(){
        CodeableConcept bodyTemperature = new CodeableConcept();
        bodyTemperature.setText("Body temperature");
        bodyTemperature.addCoding(LOINC, "8310-5", "Body temperature");
        return bodyTemperature;
    }

    /**
     * Adds FHIR coding for bodyWeight
     * @return bodyWeight
     */
    public static CodeableConcept getBodyWeight(){
        CodeableConcept bodyWeight = new CodeableConcept();
        bodyWeight.setText("Body Weight");
        bodyWeight.addCoding(LOINC, "29463-7", "Body Weight");
        return bodyWeight;

    }

    /**
     * Creates a Quantity of unit US LBS
     * @param value the value of the quantity
     * @return weight
     */
    public static Quantity getWeightPounds(Float value){
        Quantity weight = new Quantity();
        weight.setValue(value);
        weight.setSystem(UNITS_OF_MEASURE);
        weight.setCode("[lb_av]");
        weight.setUnit("lbs");
        return weight;
    }

    /**
     * Creates a Quantity of unit Celsius
     * @param value the value of the Quantity
     * @return temperature
     */
    public static Quantity getCelsius(Float value){
        Quantity temperature = new Quantity();
        temperature.setValue(value);
        temperature.setSystem(UNITS_OF_MEASURE);
        temperature.setCode("Cel");
        temperature.setUnit("degrees C");
        return temperature;

    }

    /**
     * @return the Concept with LOINC code of Respiratory Rate
     */
    public static CodeableConcept getRespiratoryRate() {
        CodeableConcept respiratoryRate = new CodeableConcept();
        respiratoryRate.setText("Respiratory rate");
        respiratoryRate.addCoding(LOINC, "9279-1", "Respiratory rate");

        return respiratoryRate;
    }

    /**
     * @param value the value of the quantity
     * @return the Concept with unit breaths/minute
     */
    public static Quantity getBreathsPerMinute(Float value) {
        Quantity breathsPerMinute = new Quantity();
        breathsPerMinute.setValue(value);
        breathsPerMinute.setSystem(UNITS_OF_MEASURE);
        breathsPerMinute.setCode("/min");
        breathsPerMinute.setUnit("breaths/minute");

        return breathsPerMinute;

    }

    public static CodeableConcept getTobaccoCoding(){
        CodeableConcept tobaccoHistory = new CodeableConcept();
        tobaccoHistory.setText("Tobacco Use");
        tobaccoHistory.addCoding("http://loinc.org", "72166-2", "Tobacco Use");

        return tobaccoHistory;
    }

    public static CodeableConcept getAlcoholCoding(){
        CodeableConcept alcHistory = new CodeableConcept();
        alcHistory.setText("Alcohol Use");
        alcHistory.addCoding("http://loinc.org", "74013-4", "Alcohol Use");

        return alcHistory;
    }

    public static CodeableConcept getDiabetesCoding(){
        CodeableConcept alcHistory = new CodeableConcept();
        alcHistory.setText("Diabetes Mellitus");
        alcHistory.addCoding("http://loinc.org", "45636-8", "Diabetes Mellitus");

        return alcHistory;
    }

    public static CodeableConcept getHypertensionCoding(){
        CodeableConcept hypertensionHistory = new CodeableConcept();
        hypertensionHistory.setText("Hypertension");
        hypertensionHistory.addCoding("http://loinc.org", "45643-4", "Hypertension");

        return hypertensionHistory;
    }

    public static CodeableConcept getHighCholesterolCoding(){
        CodeableConcept cholesterolHistory = new CodeableConcept();
        cholesterolHistory.setText("High Cholesterol");
        cholesterolHistory.addCoding("http://loinc.org", "LA10526-4", "High Cholesterol");

        return cholesterolHistory;
    }

    public static BooleanType getTobaccoHistory(boolean value){
        BooleanType tobaccoUse = new BooleanType();
        tobaccoUse.setValue(value);

        return tobaccoUse;
    }

    public static BooleanType getAlcoholHistory(boolean value){
        BooleanType alcoholUse = new BooleanType();
        alcoholUse.setValue(value);

        return alcoholUse;
    }

    public static BooleanType getHighCholesterolHistory(boolean value){
        BooleanType highCholesterol = new BooleanType();
        highCholesterol.setValue(value);

        return highCholesterol;
    }






}
