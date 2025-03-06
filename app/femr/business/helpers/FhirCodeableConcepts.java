package femr.business.helpers;

import org.hl7.fhir.r5.model.CodeableConcept;
import org.hl7.fhir.r5.model.DecimalType;
import org.hl7.fhir.r5.model.Quantity;

/**
 * Easy access to codes we use to encode concepts in FHIR.
 */
public class FhirCodeableConcepts {
    private FhirCodeableConcepts() {}


    public static CodeableConcept getBodyTemperature(){
        CodeableConcept bodyTemperature = new CodeableConcept();
        bodyTemperature.setText("Body temperature");
        bodyTemperature.addCoding("http://loinc.org", "8310-5", "Body temperature");
        return bodyTemperature;
    }

    public static Quantity getTemperature(Float value){
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
