package femr.util.DataStructure.Mapping;

import femr.util.stringhelpers.StringUtils;
import org.apache.commons.collections.map.MultiKeyMap;

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
        if (dateList.size() < i || i < 0) {
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
        if (dateList.size() < i || i < 0) {
            return null;
        }
        return StringUtils.FormatDateTime(dateList.get(i));
    }
}
