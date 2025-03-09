package femr.business.helpers;

import org.hl7.fhir.r5.model.CodeableConcept;
import org.hl7.fhir.r5.model.DecimalType;
import org.hl7.fhir.r5.model.Quantity;

/**
 * Easy access to codes we use to encode concepts in FHIR.
 */
public class FhirCodeableConcepts {
    private FhirCodeableConcepts() {}

    /**
     * Adds FHIR coding for diastolic blood pressure
     * @return bloodPressureDiastolic
     */

    public static CodeableConcept getBloodPressureDiastolic(){
        CodeableConcept bloodPressureDiastolic = new CodeableConcept();
        bloodPressureDiastolic.setText("Diastolic blood pressure");
        bloodPressureDiastolic.addCoding("http://loinc.org", "8462-4", "Diastolic blood pressure");
        return bloodPressureDiastolic;

    }

    /**
     * Gets and sets value for diastolic reading
     * @param value
     * @return diastolic
     */

    public static Quantity getDiastolic(Float value){
        Quantity diastolic = new Quantity();
        diastolic.setValue(value);
        diastolic.setSystem("http://unitsofmeasure.org");
        diastolic.setCode("mm[Hg]");
        diastolic.setUnit("mmHg");
        return diastolic;

    }

    /**
     * Adds FHIR coding for systolic blood pressure
     * @return bloodPressureSystolic
     */

    public static CodeableConcept getBloodPressureSystolic() {
        CodeableConcept bloodPressureSystolic = new CodeableConcept();
        bloodPressureSystolic.setText("Systolic blood pressure");
        bloodPressureSystolic.addCoding("http://loinc.org", "8480-6", "Systolic blood pressure");
        return bloodPressureSystolic;

    }

    /**
     * Gets and sets value for systolic reading
     * @param value
     * @return systolic
     */

    public static Quantity getSystolic(Float value){
        Quantity systolic = new Quantity();
        systolic.setValue(value);
        systolic.setSystem("http://unitsofmeasure.org");
        systolic.setCode("mm[Hg]");
        systolic.setUnit("mmHg");
        return systolic;

    }

    /**
     * Adds FHIR coding for body temperature
     * @return bodyTemperature
     */

    public static CodeableConcept getBodyTemperature(){
        CodeableConcept bodyTemperature = new CodeableConcept();
        bodyTemperature.setText("Body temperature");
        bodyTemperature.addCoding("http://loinc.org", "8310-5", "Body temperature");
        return bodyTemperature;
    }

    /**
     * Adds FHIR coding for bodyweight
     * @return bodyWeight
     */

    public static CodeableConcept getBodyWeight(){
        CodeableConcept bodyWeight = new CodeableConcept();
        bodyWeight.setText("Body Weight");
        bodyWeight.addCoding("http://loinc.org", "29463-7", "Body Weight");
        return bodyWeight;

    }

    /**
     * Gets and sets weight in pounds
     * @param value
     * @return weight
     */

    public static Quantity getWeightPounds(Float value){
        Quantity weight = new Quantity();
        weight.setValue(value);
        weight.setSystem("http://unitsofmeasure.org");
        weight.setCode("[lb_av]");
        weight.setUnit("lbs");
        return weight;
    }

    /**
     * Gets and sets body temperature in Celsius
     * @param value
     * @return temperature
     */
    public static Quantity getCelsius(Float value){
        Quantity temperature = new Quantity();
        temperature.setValue(value);
        temperature.setSystem("http://unitsofmeasure.org");
        temperature.setCode("Cel");
        temperature.setUnit("degrees C");
        return temperature;

    }

    public static CodeableConcept getRespiratoryRate() {
        CodeableConcept respiratoryRate = new CodeableConcept();
        respiratoryRate.setText("Respiratory rate");
        respiratoryRate.addCoding("http://loinc.org", "9279-1", "Respiratory rate");

        return respiratoryRate;
    }

    public static Quantity getBreathsPerMinute(Float value) {
        Quantity breathsPerMinute = new Quantity();
        breathsPerMinute.setValue(value);
        breathsPerMinute.setSystem("http://unitsofmeasure.org");
        breathsPerMinute.setCode("/min");
        breathsPerMinute.setUnit("breaths/minute");

        return breathsPerMinute;

    }
}
