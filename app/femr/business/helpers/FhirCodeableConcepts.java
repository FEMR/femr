package femr.business.helpers;

import org.hl7.fhir.r5.model.CodeableConcept;
import org.hl7.fhir.r5.model.DecimalType;
import org.hl7.fhir.r5.model.Quantity;

/**
 * Easy access to codes we use to encode concepts in FHIR.
 */
public class FhirCodeableConcepts {
    private FhirCodeableConcepts() {}

    private static final String LOINC = "http://loinc.org";
    private static final String UNITS_OF_MEASURE = "http://unitsofmeasure.org";

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
}
