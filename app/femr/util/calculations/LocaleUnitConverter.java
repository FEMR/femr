package femr.util.calculations;

import femr.common.models.PatientItem;
import femr.common.models.VitalItem;
import femr.util.DataStructure.Mapping.VitalMultiMap;

import java.math.BigDecimal; //Importing for converting
import java.util.List;
import java.util.Map;

/**
 * Created by owner1 on 3/11/2015.
 */
public class LocaleUnitConverter {
    /**
     * Converts all imperial values in a VitalMultiMap to metric values
     * @param vitalMap MultiMap to get imperial values from and store metric values into
     * @return VitalMultiMap with metric values
     */
    public static VitalMultiMap toMetric(VitalMultiMap vitalMap) {

        for (int dateIndex = 0; dateIndex < vitalMap.getDateListChronological().size(); dateIndex++) {

            // Get imperial temperature(F)
            String tempC = vitalMap.get("temperature", vitalMap.getDate(dateIndex));

            // If temp is not null convert to metric(C) and store in map as temperatureCelsius
            if (tempC != null) {
                vitalMap.put("temperatureCelsius", vitalMap.getDate(dateIndex), getCelcius(tempC));  // temperature is in Fahrenheit
             }

            // Get imperial height from Map
            String feetS = vitalMap.get("heightFeet", vitalMap.getDate(dateIndex));
            String inchesS = vitalMap.get("heightInches", vitalMap.getDate(dateIndex));

            if (feetS != null && inchesS != null) {
                // Convert Height to Metric
                Integer meters = getMeters(feetS, inchesS);
                Integer cm = getCentimetres(feetS, inchesS);

                // Store metric height in multimap as heightMeters and heightCm
                vitalMap.put("heightMeters", vitalMap.getDate(dateIndex), meters); // puts it back into map
                vitalMap.put("heightCm", vitalMap.getDate(dateIndex), String.format("%02d", cm)); // puts it back into map
            }

            // Get the imperial weight
            String lbs = vitalMap.get("weight", vitalMap.getDate(dateIndex));
            if (lbs != null) {
                // Convert to metric and store as weightKgs
                vitalMap.put("weightKgs", vitalMap.getDate(dateIndex), getKgs(lbs)); // puts it back into map

            }
        }

        return vitalMap;
    }


    /**
     * Converts all imperial values in a PatientItem to metric values
     * @param patient PatientItem to get imperial values from and store metric values into
     * @return PatientItem with metric values
     */
    public static PatientItem toMetric(PatientItem patient) {
        if (patient == null) return patient;

        // Store separate height variables temporarily
        // Wish getHeightFeet() and getHeightInches() were'nt stored as Integer in PatientItem.
        // Causes issues with precision when value stored in database as a non whole number
        if (patient.getHeightFeet() != null && patient.getHeightInches() != null) {
            Integer feet = patient.getHeightFeet();
            Integer inches = patient.getHeightInches();

            //added for femr-136 - dual unit display
            patient.setHeightFeetDual(patient.getHeightFeet());
            patient.setHeightInchesDual(patient.getHeightInches());

            // Overwrite patient height feet with meters
            patient.setHeightFeet(LocaleUnitConverter.getMeters(feet, inches));

            // Overwrite patient height inches with centimeters
            patient.setHeightInches(LocaleUnitConverter.getCentimetres(feet, inches));
        }

        // Overwrite patients weight with kg
        if (patient.getWeight() != null) {
            patient.setWeight(LocaleUnitConverter.getKgs(patient.getWeight()).floatValue());
            //added for femr-136 - dual unit display
            patient.setWeightDual(LocaleUnitConverter.getLbs(patient.getWeight()));
        }
        return patient;
    }

    /**
     * Added for femr-136 - dual unit display
     * Converts all imperial values in a PatientItem to metric values for dual unit display
     * @param patient PatientItem to get imperial values from and store metric values into
     * @return PatientItem with metric values
     */

    public static PatientItem forDualUnitDisplay(PatientItem patient) {
        if (patient == null) return patient;

        // Store separate height variables temporarily
        // Wish getHeightFeet() and getHeightInches() were'nt stored as Integer in PatientItem.
        // Causes issues with precision when value stored in database as a non whole number
        if (patient.getHeightFeet() != null && patient.getHeightInches() != null) {
            Integer feet = patient.getHeightFeet();
            Integer inches = patient.getHeightInches();

            // Overwrite patient height feet with meters
            patient.setHeightFeetDual(LocaleUnitConverter.getMeters(feet, inches));

            // Overwrite patient height inches with centimeters
            patient.setHeightInchesDual(LocaleUnitConverter.getCentimetres(feet, inches));
        }

        // Overwrite patients weight with kg
        if (patient.getWeight() != null) {
            patient.setWeightDual(LocaleUnitConverter.getKgs(patient.getWeight()).floatValue());
        }
        return patient;
    }


    /**
     * Converts vitals to imperial (Map<String, Float> used when user is submitting vitals
     * @param vitalMap Map of all patient vitals
     * @return The Map with the vitals converted to imperial
     */
    public static Map<String, Float> toImperial(Map<String, Float> vitalMap) {
        if (vitalMap.containsKey("temperature"))
            vitalMap.put("temperature", getFahrenheit(vitalMap.get("temperature")));

        if (vitalMap.containsKey("heightFeet") && vitalMap.containsKey("heightInches")) {
            Float heightMetres = vitalMap.get("heightFeet");
            Float heightCentimetres = vitalMap.get("heightInches");

            // AS - Convert and store in original height variables
            vitalMap.put("heightFeet" ,getFeet(heightMetres, heightCentimetres));
            vitalMap.put("heightInches", getInches(heightMetres, heightCentimetres));
        }

        if (vitalMap.containsKey("weight"))
            vitalMap.put("weight", getLbs(vitalMap.get("weight")));

        return vitalMap;
    }

    /**
     * Converts a Fahrenheit temperature to celcius
     * @param Fahrenheit The temperature to convert to celcius
     * @return The temperature in celcius (As BigDecimal for precision)
     */
    public static BigDecimal getCelcius(Float Fahrenheit) {
        return roundFloat((Fahrenheit - 32) * 5 / 9, 2); // (°F - 32) x 5/9 = °C
    }

    /**
     * Overload for getCelcius(Float) that accepts Fahrenheit as a string
     * @param FahrenheitString the temperature as a string
     * @return The temperature in celcius (As BigDecimal for precision)
     */
    public static BigDecimal getCelcius(String FahrenheitString) {
        Float Fahrenheit = Float.parseFloat(FahrenheitString);
        return getCelcius(Fahrenheit);
    }

    /**
     * Converts a Celcius temperature to Fahrenheit
     * @param Celcius Temperature to convert to Fahrenheit
     * @return The temperature in Fahrenheit
     */
    public static float getFahrenheit(float Celcius) {
        return Celcius * 9/5 + 32;
    }

    /**
     * Converts a height from Feet & inches into meters portion of a height (2.43)
     * @param Feet Height in feet
     * @param Inches Height in inches
     * @return Height in meters (ie 2.43 returns 2))
     */
    public static Integer getMeters(Integer Feet, Integer Inches) {
        /* Calculate total inches (feet*12)+inches */
        Float totalInches = (float)(Inches + Feet * 12);
        return (int)Math.floor((totalInches * 0.0254f));
    }

    /**
     * Overload method for getMeters(Integer, Integer).
     * Converts a height from Feet & inches into meters portion of a height (2.43)
     * @param Feet Height in feet
     * @param Inches Height in inches
     * @return Height in meters (ie 2.43 returns 2))
     */
    public static Integer getMeters(Float Feet, Float Inches) {
        return getMeters(Math.round(Feet), Math.round(Inches));
    }

    /**
     * Overload method for getMeters(Integer, Integer).
     * Converts a height from Feet & inches into meters portion of a height (2.43)
     * @param Feet Height in feet
     * @param Inches Height in inches
     * @return Height in meters (ie 2.43 returns 2)
     */
    public static Integer getMeters(String Feet, String Inches) {
        if (Feet == null || Inches == null)
            return 0;
        return getMeters(Math.round(Float.parseFloat(Feet)), Math.round(Float.parseFloat(Inches)));
    }

    /**
     * Converts a height from Feet & inches into centimeters portion of a height (1.55)
     * @param Feet Height in feet
     * @param Inches Height inches
     * @return The height in centimeters (ie 1.55 returns 55)
     */
    public static Integer getCentimetres(Float Feet, Float Inches) {
        /* Calculate total inches (feet*12)+inches */
        Float totalInches = Inches + Feet * 12;

        /* Convert inches to cm */
        Integer cm = Math.round(totalInches * 2.54f);

        return cm % 100;
    }

    /**
     * Overload method for getCentimetres(Float, Float).
     * Converts a height from Feet & inches into centimeters portion of a height (1.55)
     * @param Feet Height in feet
     * @param Inches Height inches
     * @return The height in centimeters (ie 1.55 returns 55)
     */
    public static Integer getCentimetres(Integer Feet, Integer Inches) {
        return getCentimetres((float)Feet, (float)Inches);
    }

    /**
     * Overload method for getCentimetres(Float, Float).
     * Converts a height from Feet & inches into centimeters portion of a height (1.55)
     * @param Feet Height in feet
     * @param Inches Height inches
     * @return The height in centimeters (ie 1.55 returns 55)
     */
    public static Integer getCentimetres(String Feet, String Inches) {
        if (Feet == null || Inches == null)
            return 0;
        return getCentimetres(Float.parseFloat(Feet), Float.parseFloat(Inches));
    }

    /**
     * Converts a height in Metres and centimetres to feet portion of a height (5'11")
     * @param Metres Height in metres
     * @param Centimetres Height in centimetres
     * @return Height in feet (ie 5'11" returns 5')
     */
    public static Float getFeet(Float Metres, Float Centimetres) {
        Float totalCm = Metres * 100 + Centimetres;
        Float totalInches = totalCm * 0.39370f;
        return (float)Math.floor(totalInches / 12);
    }

    /**
     * Converts a height in Metres and centimetres to inches portion of a height (5'11")
     * @param Metres Height in metres
     * @param Centimetres Height in centimetres
     * @return Height in inches (ie 5'11" returns 11")
     */
    public static Float getInches(Float Metres, Float Centimetres) {
        Float totalCm = Metres * 100 + Centimetres;
        Float totalInches = totalCm * 0.39370f;
        return totalInches % 12;
    }

    /**
     * Converts weight in pounds(lbs) to kilograms(KG)
     * @param lbs Weight in pounds
     * @return Weight in kilograms
     */
    public static BigDecimal getKgs(Float lbs) {
        return roundFloat(lbs / 2.2046f, 2);
    }

    /**
     * Overload method for getKgs(Float)
     * Converts weight in pounds(lbs) to kilograms(kg)
     * @param lbs Weight in pounds
     * @return Weight in kilograms
     */
    public static BigDecimal getKgs(String lbs) {
        if (lbs == null)
            return BigDecimal.ZERO;
      //  return getKgs(Float.parseFloat(lbs));
        return getKgs(Float.parseFloat(lbs));

    }

    /**
     * Convert weight in Kilograms(kg) to pounds(lbs)
     * @param kgs Weight in kilograms
     * @return Weight in pounds
     */
    public static Float getLbs(Float kgs) {
        return kgs * 2.2046f;
    }

    /**
     * Rounds a float to 2 decimal places. Needed to resolve issues with
     * floats given by existing code
     * @param number Number to round
     * @param decimalPlaces Number of decimal places to rund to
     * @return Rounded number as a BigDecimal
     */
    private static BigDecimal roundFloat(Float number, int decimalPlaces) {
        BigDecimal bd = new BigDecimal(Float.toString(number));
        bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        return bd;
    }
}
