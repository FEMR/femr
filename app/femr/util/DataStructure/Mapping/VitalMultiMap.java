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
package femr.util.DataStructure.Mapping;

/**
 * Contains all available vital fields and their values. (Includes old and new)
 */
public class VitalMultiMap extends AbstractMultiMap {

    /**
     * Puts a value into the map and associates the name and date as the two keys to the value
     *
     * @param vitalName The name of the vital
     * @param date      The date the vital was taken
     * @param value     The value of the vital
     */
    //Suppress the warnings that were introduced after the move from Apache Collections to Apache Collections4.
    //TabFieldMultiMap and VitalMultiMap both use AbstractMultiMap's generic MultiKeyMap with different types.
    @SuppressWarnings("unchecked")
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
