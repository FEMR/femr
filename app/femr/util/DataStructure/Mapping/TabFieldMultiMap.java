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
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.keyvalue.MultiKey;

import java.util.*;

/**
 * Contains all available tab fields and their values. (Includes old and new). In the following format:
 * =key1,key2,key3,value
 * where
 * <ul>
 * <li>key1 = name of the field</li>
 * <li>key2 = date the field was recorded (null if empty field)</li>
 * <li>key3 = chief complaint the field belongs to (null if n/a)</li>
 * <li>key = TabFieldItem matching the keys</li>
 * </ul>
 *
 * If you need to sort any fields by sort order then your on your own! muhahahaha
 */
public class TabFieldMultiMap extends AbstractMultiMap {

    //a list of all chief complaints available in the multimap - not in any order
    private final List<String> chiefComplaintList = new ArrayList<>();
    //keeps track of an order for the chief complaints
    private final Map<Integer, String> chiefComplaintListOrderMap = new TreeMap<>();
    //tracks the names of custom fields that have been added by a superuser
    private final List<String> customFieldNames = new ArrayList<>();

    /**
     * Puts a value into the map and associates the name, date, and chief complaint
     * as the two keys to the value.
     *
     * @param fieldName      The name of the tab field, can not be null
     * @param date           The date the tab field was taken, can be null if empty field
     * @param value          The value of the tab field
     * @param chiefComplaint chiefcomplaint that it belongs to (can be null)
     */
    //Suppress the warnings that were introduced after the move from Apache Collections to Apache Collections4.
    //TabFieldMultiMap and VitalMultiMap both use AbstractMultiMap's generic MultiKeyMap with different types.
    @SuppressWarnings("unchecked")
    public void put(String fieldName, String date, String chiefComplaint, Object value) {

        if (!(value instanceof TabFieldItem) || StringUtils.isNullOrWhiteSpace(fieldName)) {
            //don't do a gd thing
        } else {

            TabFieldItem tabFieldItem = (TabFieldItem) value;
            map.put(fieldName, date, chiefComplaint, value);
            //check if the date is already in the comprehensive date list, if so don't add it
            if (!dateList.contains(date) && StringUtils.isNotNullOrWhiteSpace(date)) {
                dateList.add(date);
            }
            //check if the chief complaint is already in the comprehensive chief complaint list, if so don't add it
            if (!chiefComplaintList.contains(chiefComplaint) && StringUtils.isNotNullOrWhiteSpace(chiefComplaint)) {
                chiefComplaintList.add(chiefComplaint);
            }
            if (tabFieldItem.getIsCustom() != null && tabFieldItem.getIsCustom() && !customFieldNames.contains(tabFieldItem.getName())) {
                customFieldNames.add(tabFieldItem.getName());
            }
        }
    }

    /**
     * Sets a sort order for the chief complaints. If this doesn't happen then they can appear
     * in a random order
     *
     * @param chiefComplaint the chief complaint (it must already exist in the multi map)
     * @param sortOrder      the chief complaints sort order
     * @return true if successfull, false if you f*cked up
     */
    public boolean setChiefComplaintOrder(String chiefComplaint, Integer sortOrder) {

        if (StringUtils.isNullOrWhiteSpace(chiefComplaint))
            return false;
        if (!chiefComplaintList.contains(chiefComplaint))
            return false;
        for (Integer key : chiefComplaintListOrderMap.keySet()) {
            if (key == sortOrder) {
                return false;
            }
        }

        chiefComplaintListOrderMap.put(sortOrder, chiefComplaint);
        return true;
    }

    /**
     * Finds any field value
     *
     * @param fieldName      the name of the tab field
     * @param date           the date the tab field was taken
     * @param chiefComplaint chiefcomplaint that it belongs to (can be null)
     * @return the tab field or null if not found
     */
    public TabFieldItem get(String fieldName, String date, String chiefComplaint) {

        if (map.containsKey(fieldName, date, chiefComplaint)) {

            return (TabFieldItem) map.get(fieldName, date, chiefComplaint);
        }

        return null;
    }

    /**
     * Finds the most recent field value
     *
     * @param fieldName      the name of the tab field
     * @param chiefComplaint chiefcomplaint that it belongs to (can be null)
     * @return the tab field with or without a value or null if it doesn't exist
     */
    public TabFieldItem getMostRecentOrEmpty(String fieldName, String chiefComplaint) {

        List<String> dateList = this.getDateList();
        TabFieldItem tabFieldItem = null;

        try {
            //datelist is already sorted :)
            for (String s : dateList) {
                if (map.containsKey(fieldName, s, chiefComplaint)) {

                    tabFieldItem = (TabFieldItem) map.get(fieldName, s, chiefComplaint);
                    break;
                }
            }
            //no field exists with a date, find the blank field
            if (tabFieldItem == null) {

                tabFieldItem = (TabFieldItem) map.get(fieldName, null, chiefComplaint);
            }
        } catch (Exception ex) {
            //death
            tabFieldItem = null;
        }

        return tabFieldItem;
    }

    /**
     * Get the available chief complaints in proper sort order
     *
     * @return a string list of chief complaints inside the map
     */
    public List<String> getChiefComplaintList() {

        List<String> orderedChiefComplaints = new ArrayList<>();
        for (Integer key : chiefComplaintListOrderMap.keySet()) {

            orderedChiefComplaints.add(chiefComplaintListOrderMap.get(key));
        }

        for (String chiefComplaint : chiefComplaintList) {

            if (!orderedChiefComplaints.contains(chiefComplaint))
                orderedChiefComplaints.add(chiefComplaint);
        }

        return orderedChiefComplaints;
    }

    /**
     * Returns a list of available custom fields
     *
     * @return all strings, yo
     */
    public List<String> getCustomFieldNameList() {

        return customFieldNames;
    }

    /**
     * Checks to see if the map contains an entry for a field
     *
     * @param fieldName name of the field
     * @return true if the field has an entry, false otherwise
     */
    public boolean containsTabField(String fieldName) {

        MapIterator multiMapIterator = this.getMultiMapIterator();
        while (multiMapIterator.hasNext()) {

            multiMapIterator.next();
            MultiKey mk = (MultiKey) multiMapIterator.getKey();
            if (mk.getKey(0) != null) {

                if (fieldName.equals(mk.getKey(0))) {

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if the map contains any entries for a field
     *
     * @param fieldName      name of the field
     * @param chiefComplaint chiefcomplaint that it belongs to (can be null)
     * @return true if the field has an entry, false otherwise
     */
    public boolean containsTabField(String fieldName, String chiefComplaint) {

        MapIterator multiMapIterator = this.getMultiMapIterator();
        while (multiMapIterator.hasNext()) {

            multiMapIterator.next();
            MultiKey mk = (MultiKey) multiMapIterator.getKey();
            if (mk.getKey(0) != null && mk.getKey(2) != null) {

                if (fieldName.equals(mk.getKey(0)) && chiefComplaint.equals(mk.getKey(2))) {

                    return true;
                }
            }
        }
        return false;
    }
}
