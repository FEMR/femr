package femr.util.DataStructure.Mapping;

/**
 * Contains all available vital fields and their values. (Includes old and new)
 */
public class VitalMultiMap extends AbstractMultiMap {

    /**
     * Puts a value into the map and associatres the name and date as the two keys to the value
     *
     * @param vitalName The name of the vital
     * @param date      The date the vital was taken
     * @param value     The value of the vital
     */
    public void put(String vitalName, String date, Object value) {
        map.put(vitalName, date, value);
        // check if the dated is already in the dateList if so don't add it
        if (!dateList.contains(date)) {
            dateList.add(date);
        }
    }

    /**
     * Given the vital name and date taken return the vital value
     * if the keys do not exist it returns null
     *
     * @param vitalName the name of the vital
     * @param date      the date the vital was taken
     * @return the value of the vital as on type Object or null if not found
     */
    public String get(String vitalName, String date) {
        if (map.containsKey(vitalName, date)) {
            return map.get(vitalName, date).toString();
        }
        return null;
    }
}
