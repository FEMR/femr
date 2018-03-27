package femr.ui.controllers.helpers;

import femr.common.dtos.ServiceResponse;
import femr.common.models.TabFieldItem;
import femr.common.models.TabItem;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.stringhelpers.StringUtils;

import java.util.*;

/**
 * Helps logically arrange fields for the views after retrieving data from the service
 */
public class FieldHelper {

    //READ THE METHOD NAME LOL
    public static Map<String, List<TabFieldItem>> structurePMHFieldsForView(TabFieldMultiMap tabFieldMultiMap, List<String> fields) {

        if (tabFieldMultiMap == null || fields.isEmpty()) {

            return null;
        }

        Map<String, List<TabFieldItem>> chiefComplaintFieldMap = new HashMap<>();
        List<TabFieldItem> tabFieldItemsForChiefComplaint = new ArrayList<>();
        TabFieldItem tabFieldItem;

        //get pmh fields
        for (String field : fields){

            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty(field, null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
        }
        chiefComplaintFieldMap.put(null, tabFieldItemsForChiefComplaint);

        return chiefComplaintFieldMap;
    }

    //READ THE METHOD NAME LOL
    public static Map<String, List<TabFieldItem>> structureTreatmentFieldsForView(TabFieldMultiMap tabFieldMultiMap, List<String> fields) {

        if (tabFieldMultiMap == null || fields.isEmpty()) {

            return null;
        }

        Map<String, List<TabFieldItem>> chiefComplaintFieldMap = new HashMap<>();
        List<TabFieldItem> tabFieldItemsForChiefComplaint = new ArrayList<>();
        TabFieldItem tabFieldItem;

        for (String field : fields){
            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty(field, null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
        }
        chiefComplaintFieldMap.put(null, tabFieldItemsForChiefComplaint);

        return chiefComplaintFieldMap;
    }

    /**
     * Can't be static because of the comparator implementation.
     *
     * @param tabFieldMultiMap
     * @return
     */
    public Map<String, List<TabFieldItem>> structureDynamicFieldsForView(TabFieldMultiMap tabFieldMultiMap, List<String> customFields) {

        if (tabFieldMultiMap == null || customFields.isEmpty()) {

            return null;
        }

        Map<String, List<TabFieldItem>> chiefComplaintFieldMap = new HashMap<>();
        List<TabFieldItem> tabFieldItemsForChiefComplaint = new ArrayList<>();
        TabFieldItem tabFieldItem;

        for (String customField : customFields) {

            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty(customField, null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
        }

        Collections.sort(tabFieldItemsForChiefComplaint, new TabFieldSortOrderComparator());
        chiefComplaintFieldMap.put(null, tabFieldItemsForChiefComplaint);


        return chiefComplaintFieldMap;
    }

    //READ THE METHOD NAME LOL
    public static Map<String, List<TabFieldItem>> structureHPIFieldsForView(TabFieldMultiMap tabFieldMultiMap, List<String> fields) {

        if (tabFieldMultiMap == null || fields.isEmpty()) {

            return null;
        }

        Map<String, List<TabFieldItem>> chiefComplaintFieldMap = new HashMap<>();
        List<TabFieldItem> tabFieldItemsForChiefComplaint = new ArrayList<>();
        TabFieldItem tabFieldItem;

        List<String> availableChiefComplaints = tabFieldMultiMap.getChiefComplaintList();

        if (availableChiefComplaints.size() == 0) {
            //no chief complaint - needs at least one set of fields which wouldn't be
            for (String field : fields){

                tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty(field, null);
                tabFieldItemsForChiefComplaint.add(tabFieldItem);
            }
            chiefComplaintFieldMap.put(null, tabFieldItemsForChiefComplaint);
        } else if (availableChiefComplaints.size() > 0) {
            //one or more chief complaints. each chief complaint in HPI requires a new set of fields
            for (String chiefComplaint : availableChiefComplaints) {

                tabFieldItemsForChiefComplaint = new ArrayList<>();
                for (String field : fields){

                    tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty(field, chiefComplaint);
                    tabFieldItemsForChiefComplaint.add(tabFieldItem);
                }
                chiefComplaintFieldMap.put(chiefComplaint, tabFieldItemsForChiefComplaint);
            }
        }

        return chiefComplaintFieldMap;
    }

    public static List<TabItem> applyIndicesToFieldsForView(List<TabItem> tabItems) {

        if (tabItems == null) {

            return null;
        }

        int index = 0;
        List<TabFieldItem> tabFieldItems;
        //iterate over the map, assigning indices to each field
        for (TabItem tabItem : tabItems) {

            for (String key : tabItem.getFields().keySet()) {

                tabFieldItems = tabItem.getFields().get(key);
                for (TabFieldItem tfi : tabFieldItems) {

                    tfi.setIndex(index++);
                }
            }
        }
        String test = null;
        return tabItems;
    }

    class TabFieldSortOrderComparator implements Comparator<TabFieldItem> {

        @Override
        public int compare(TabFieldItem o1, TabFieldItem o2) {
            return o1.getOrder().compareTo(o2.getOrder());
        }
    }
}
