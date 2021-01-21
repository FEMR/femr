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

import femr.util.stringhelpers.StringUtils;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This DataStructure is designed for allowing us to search for info by date and name at the same time.
 * This means it stores all information including multiple recordings at different times (during the same encounter).
 * we can also get a list of dates
 */
public abstract class AbstractMultiMap {
    protected final MultiKeyMap map = new MultiKeyMap();
    protected final List<String> dateList = new LinkedList<>();

    /**
     * Get an iterator for iterating over the map
     *
     * @return the gd iterator
     */
    public MapIterator getMultiMapIterator() {
        return map.mapIterator();
    }

    public int getSize() {
        return map.size();
    }

    /**
     * Gets a list of Date Keys and sorts them in descending order
     *
     * @return A List of dates as type string
     */
    public List<String> getDateList() {
        Collections.sort(dateList, Collections.reverseOrder());
        return dateList;
    }

    /**
     * Gets a list of Date Keys and sorts them in ascending order
     *
     * @return A List of dates as type string
     */
    public List<String> getDateListChronological() {
        Collections.sort(dateList);
        return dateList;
    }

    /**
     * gets the date for a given index
     *
     * @param i The index of the date
     * @return The date as a string
     */
    public String getDate(int i) {
        // check that the index specified
        if (dateList.size() <= i || i < 0) {
            return null;
        }
        return dateList.get(i);
    }

    /**
     * Returns the date and time in human readable form
     *
     * @param i the index of the date to return
     * @return The date in a nice formate or the original date formate if it fails
     */
    public String getFormatedDateTime(int i) {
        // check that the index specified
        if (dateList.size() <= i || i < 0) {
            return null;
        }
        return StringUtils.FormatDateTime(dateList.get(i));
    }

}
