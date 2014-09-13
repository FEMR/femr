package femr.util.DataStructure.Mapping;

import java.util.List;

/**
 * Contains all available tab fields and their values. (Includes old and new)
 */
public class TabFieldMultiMap extends AbstractMultiMap {
    /**
     * Puts a value into the map and associatres the name and date as the two keys to the value
     *
     * @param tabFieldName   The name of the vital
     * @param date           The date the vital was taken
     * @param value          The value of the vital
     * @param chiefComplaint chiefcomplaint that it belongs to (can be null)
     */
    public void put(String tabFieldName, String date, String chiefComplaint, Object value) {
        map.put(tabFieldName, date, chiefComplaint, value);
        // check if the dated is already in the dateList if so don't add it
        if (!dateList.contains(date)) {
            dateList.add(date);
        }
    }

    /**
     * Given the tab field name and date and chief complaint return the tab field value
     * if the keys do not exist it returns null
     *
     * @param tabFieldName   the name of the vital
     * @param date           the date the vital was taken
     * @param chiefComplaint chiefcomplaint that it belongs to (can be null)
     * @return the value of the vital as on type Object or null if not found
     */
    public String get(String tabFieldName, String date, String chiefComplaint) {
        if (map.containsKey(tabFieldName, date, chiefComplaint)) {
            return map.get(tabFieldName, date, chiefComplaint).toString();
        }
        return null;
    }

    /**
     * Given the tab field name and chief complaint return the most recent tab field value
     * if the keys do not exist it returns null
     *
     * @param tabFieldName   the name of the vital
     * @param chiefComplaint chiefcomplaint that it belongs to (can be null)
     * @return the value of the vital as on type Object or null if not found
     */
    public String getMostRecent(String tabFieldName, String chiefComplaint){
        Object value;
        List<String> dateList = this.getDateList();

        for (String s : dateList){
            if (map.containsKey(tabFieldName, s, chiefComplaint)){
                value = map.get(tabFieldName, s, chiefComplaint);
                return value.toString();
            }
        }

        return null;
    }
}
