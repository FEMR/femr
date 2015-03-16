package femr.util.calculations;

import femr.util.DataStructure.Mapping.VitalMultiMap;

import java.math.BigDecimal;

/**
 * Created by owner1 on 3/11/2015.
 */
public class VitalUnitConverter {
    public static VitalMultiMap toMetric(VitalMultiMap vitalMap) {
        for (int dateIndex = 0; dateIndex < vitalMap.getDateListChronological().size(); dateIndex++) {
            // You are only converting temperature here, need to do the same for the other vitals within this loop
            // Other than the OutOfBoundsException I was getting, this seems to be working fine
            String tempC = vitalMap.get("temperature", vitalMap.getDate(dateIndex));
            vitalMap.put("temperature", vitalMap.getDate(dateIndex), getCelcius(tempC));

            /* Get Height from Map */
            String feetS = vitalMap.get("heightFeet", vitalMap.getDate(dateIndex));
            String inchesS = vitalMap.get("heightInches", vitalMap.getDate(dateIndex));

            /* Convert Height to Metric */
            Integer meters = getMeters(feetS, inchesS);
            Integer cm = getCentimetres(feetS, inchesS);


            /* Update Height in map */
            vitalMap.put("heightMeters", vitalMap.getDate(dateIndex), meters); // puts it back into ma
            vitalMap.put("heightCm", vitalMap.getDate(dateIndex), String.format("%02d", cm)); // puts it back into map

            String lbs = vitalMap.get("weight", vitalMap.getDate(dateIndex));
            vitalMap.put("weightKgs", vitalMap.getDate(dateIndex), getKgs(lbs)); // puts it back into map
        }

        return vitalMap;
    }

    public static BigDecimal getCelcius(Float Fahrenheit) {
        return roundFloat((Fahrenheit - 32) * 5 / 9, 2); // (°F - 32) x 5/9 = °C
    }

    public static BigDecimal getCelcius(String FahrenheitString) {
        Float Fahrenheit = Float.parseFloat(FahrenheitString);
        return getCelcius(Fahrenheit);
    }

    public static Integer getMeters(Integer Feet, Integer Inches) {
        /* Calculate total inches (feet*12)+inches */
        Float totalInches = (float)(Inches + Feet * 12);
        return (int)Math.floor(totalInches * 0.0254f);
    }

    public static Integer getMeters(Float Feet, Float Inches) {
        return getMeters(Math.round(Feet), Math.round(Inches));
    }

    public static Integer getMeters(String Feet, String Inches) {
        if (Feet == null || Inches == null)
            return 0;
        return getMeters(Float.parseFloat(Feet), Float.parseFloat(Inches));
    }

    public static Integer getCentimetres(Integer Feet, Integer Inches) {
        return getCentimetres((float)Feet, (float)Inches);
    }

    public static Integer getCentimetres(Float Feet, Float Inches) {
        /* Calculate total inches (feet*12)+inches */
        Float totalInches = Inches + Feet * 12;

        /* Convert inches to cm */
        Integer cm = Math.round(totalInches * 2.54f);

        return cm % 100;
    }

    public static Integer getCentimetres(String Feet, String Inches) {
        if (Feet == null || Inches == null)
            return 0;
        return getCentimetres(Float.parseFloat(Feet), Float.parseFloat(Inches));
    }

    public static BigDecimal getKgs(Float lbs) {
        return roundFloat(lbs / 2.2046f, 2);
    }
    public static BigDecimal getKgs(String lbs) {
        if (lbs == null)
            return BigDecimal.ZERO;
        return getKgs(Float.parseFloat(lbs));
    }

    public static Float getLbs(Float kgs) {
        return kgs * 2.2046f;
    }

    private static BigDecimal roundFloat(Float number, int decimalPlaces) {
        BigDecimal bd = new BigDecimal(Float.toString(number));
        bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public static Float getFeet(Float Metres, Float Centimetres) {
        Float totalCm = Metres * 100 + Centimetres;
        Float totalInches = totalCm * 0.39370f;
        return (float)Math.floor(totalInches / 12);
    }

    public static Float getInches(Float Metres, Float Centimetres) {
        Float totalCm = Metres * 100 + Centimetres;
        Float totalInches = totalCm * 0.39370f;
        return totalInches % 12;
    }
}
