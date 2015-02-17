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

import femr.common.models.TabFieldItem;
import femr.util.stringhelpers.StringUtils;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.keyvalue.MultiKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all available tab fields and their values. (Includes old and new)
 */
public class TabFieldMultiMap extends AbstractMultiMap {

    private final List<String> chiefComplaintList = new ArrayList<>();

    /**
     * Puts a value into the map and associatres the name and date as the two keys to the value.
     * For the love of all things holy, use empty strings for keys instead of null.
     *
     * @param tabFieldName   The name of the tab field
     * @param date           The date the tab field was taken
     * @param value          The value of the tab field
     * @param chiefComplaint chiefcomplaint that it belongs to (can be null)
     */
    public void put(String tabFieldName, String date, String chiefComplaint, Object value) {
        //TODO: enforce a type for value
      //  if (!(value instanceof TabFieldItem)){
            //don't insert that shit
        //}else{
            map.put(tabFieldName, date, chiefComplaint, value);
            // check if the dated is already in the dateList if so don't add it
            if (!dateList.contains(date) && StringUtils.isNotNullOrWhiteSpace(date)) {
                dateList.add(date);
            }
            if (!chiefComplaintList.contains(chiefComplaint) && StringUtils.isNotNullOrWhiteSpace(chiefComplaint)) {
                chiefComplaintList.add(chiefComplaint);
            }
        //}
    }

    /**
     * Given the tab field name and date and chief complaint return the tab field value
     * if the keys do not exist it returns null
     *
     * @param tabFieldName   the name of the tab field
     * @param date           the date the tab field was taken
     * @param chiefComplaint chiefcomplaint that it belongs to (can be null)
     * @return the tab field or null if not found
     */
    public TabFieldItem get(String tabFieldName, String date, String chiefComplaint) {

        if (map.containsKey(tabFieldName, date, chiefComplaint)) {

            return (TabFieldItem) map.get(tabFieldName, date, chiefComplaint);
        }

        return null;
    }

    /**
     * Given the tab field name and chief complaint return the most recent tab field value
     * if the keys do not exist it returns null
     *
     * @param tabFieldName   the name of the tab field
     * @param chiefComplaint chiefcomplaint that it belongs to (can be null)
     * @return the tab field with or without a value or null if it doesn't exist
     */
    public TabFieldItem getMostRecentOrEmpty(String tabFieldName, String chiefComplaint) {
        List<String> dateList = this.getDateList();

        TabFieldItem tabFieldItem = null;


        try {
            //datelist is already sorted :)
            for (String s : dateList) {
                if (map.containsKey(tabFieldName, s, chiefComplaint)) {

                    tabFieldItem = (TabFieldItem) map.get(tabFieldName, s, chiefComplaint);
                    break;
                }

            }
            //no field exists with a date, find the blank field
            if (tabFieldItem == null) {
                tabFieldItem = (TabFieldItem) map.get(tabFieldName, null, chiefComplaint);
            }
        } catch (Exception ex) {
            //death
            tabFieldItem = null;
        }


        return tabFieldItem;
    }

    /**
     * Get the available chief complaints
     *
     * @return
     */
    public List<String> getChiefComplaintList() {

        return chiefComplaintList;
    }

    /**
     * Checks to see if the map contains any values for a tab field
     *
     * @param name name of the field
     * @return true if the field has an entry, false otherwise
     */
    public boolean containsTabField(String name) {

        MapIterator multiMapIterator = this.getMultiMapIterator();
        while (multiMapIterator.hasNext()) {

            multiMapIterator.next();
            MultiKey mk = (MultiKey) multiMapIterator.getKey();
            if (mk.getKey(0) != null) {

                if (name.equals(mk.getKey(0))) {

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if the map contains any values for a tab field
     *
     * @param name name of the field
     * @return true if the field has an entry, false otherwise
     */
    public boolean containsTabField(String name, String chiefComplaint) {

        MapIterator multiMapIterator = this.getMultiMapIterator();
        while (multiMapIterator.hasNext()) {

            multiMapIterator.next();
            MultiKey mk = (MultiKey) multiMapIterator.getKey();
            if (mk.getKey(0) != null && mk.getKey(2) != null) {

                if (name.equals(mk.getKey(0)) && chiefComplaint.equals(mk.getKey(2))) {

                    return true;
                }
            }
        }
        return false;
    }

}
