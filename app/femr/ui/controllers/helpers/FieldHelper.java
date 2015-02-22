package femr.ui.controllers.helpers;

import femr.common.models.TabFieldItem;
import femr.common.models.TabItem;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helps logically arrange fields for the views after retrieving data from the service
 */
public class FieldHelper {

    //READ THE METHOD NAME LOL
    public static Map<String, List<TabFieldItem>> structurePMHFieldsForView(TabFieldMultiMap tabFieldMultiMap) {

        if (tabFieldMultiMap == null) {

            return null;
        }

        Map<String, List<TabFieldItem>> chiefComplaintFieldMap = new HashMap<>();
        List<TabFieldItem> tabFieldItemsForChiefComplaint = new ArrayList<>();
        TabFieldItem tabFieldItem;

        //get non HPI fields
        tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("medicalSurgicalHistory", null);
        tabFieldItemsForChiefComplaint.add(tabFieldItem);
        tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("socialHistory", null);
        tabFieldItemsForChiefComplaint.add(tabFieldItem);
        tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("currentMedication", null);
        tabFieldItemsForChiefComplaint.add(tabFieldItem);
        tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("familyHistory", null);
        tabFieldItemsForChiefComplaint.add(tabFieldItem);
        chiefComplaintFieldMap.put(null, tabFieldItemsForChiefComplaint);

        return chiefComplaintFieldMap;
    }

    //READ THE METHOD NAME LOL
    public static Map<String, List<TabFieldItem>> structureTreatmentFieldsForView(TabFieldMultiMap tabFieldMultiMap) {

        if (tabFieldMultiMap == null) {

            return null;
        }

        Map<String, List<TabFieldItem>> chiefComplaintFieldMap = new HashMap<>();
        List<TabFieldItem> tabFieldItemsForChiefComplaint = new ArrayList<>();
        TabFieldItem tabFieldItem;

        tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("assessment", null);
        tabFieldItemsForChiefComplaint.add(tabFieldItem);
        tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("treatment", null);
        tabFieldItemsForChiefComplaint.add(tabFieldItem);
        chiefComplaintFieldMap.put(null, tabFieldItemsForChiefComplaint);

        return chiefComplaintFieldMap;
    }

    //READ THE METHOD NAME LOL
    public static Map<String, List<TabFieldItem>> structureDynamicFieldsForView(TabFieldMultiMap tabFieldMultiMap) {

        if (tabFieldMultiMap == null) {

            return null;
        }

        Map<String, List<TabFieldItem>> chiefComplaintFieldMap = new HashMap<>();
        List<String> customFields = tabFieldMultiMap.getCustomFieldNameList();
        List<TabFieldItem> tabFieldItemsForChiefComplaint = new ArrayList<>();
        TabFieldItem tabFieldItem;

        for (String customField : customFields) {

            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty(customField, null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
        }

        chiefComplaintFieldMap.put(null, tabFieldItemsForChiefComplaint);


        return chiefComplaintFieldMap;
    }

    //READ THE METHOD NAME LOL
    public static Map<String, List<TabFieldItem>> structureHPIFieldsForView(TabFieldMultiMap tabFieldMultiMap) {

        if (tabFieldMultiMap == null) {

            return null;
        }

        Map<String, List<TabFieldItem>> chiefComplaintFieldMap = new HashMap<>();
        List<TabFieldItem> tabFieldItemsForChiefComplaint = new ArrayList<>();
        TabFieldItem tabFieldItem;

        List<String> availableChiefComplaints = tabFieldMultiMap.getChiefComplaintList();

        if (availableChiefComplaints.size() == 0) {
            //no chief complaint - needs at least one set of fields which wouldn't be
            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("onset", null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("radiation", null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("quality", null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("severity", null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("provokes", null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("palliates", null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("timeOfDay", null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("narrative", null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
            tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("physicalExamination", null);
            tabFieldItemsForChiefComplaint.add(tabFieldItem);
            chiefComplaintFieldMap.put(null, tabFieldItemsForChiefComplaint);

        } else if (availableChiefComplaints.size() > 0) {
            //one or more chief complaints
            for (String chiefComplaint : availableChiefComplaints) {
                tabFieldItemsForChiefComplaint = new ArrayList<>();
                tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("onset", chiefComplaint);
                tabFieldItemsForChiefComplaint.add(tabFieldItem);
                tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("radiation", chiefComplaint);
                tabFieldItemsForChiefComplaint.add(tabFieldItem);
                tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("quality", chiefComplaint);
                tabFieldItemsForChiefComplaint.add(tabFieldItem);
                tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("severity", chiefComplaint);
                tabFieldItemsForChiefComplaint.add(tabFieldItem);
                tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("provokes", chiefComplaint);
                tabFieldItemsForChiefComplaint.add(tabFieldItem);
                tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("palliates", chiefComplaint);
                tabFieldItemsForChiefComplaint.add(tabFieldItem);
                tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("timeOfDay", chiefComplaint);
                tabFieldItemsForChiefComplaint.add(tabFieldItem);
                tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("narrative", chiefComplaint);
                tabFieldItemsForChiefComplaint.add(tabFieldItem);
                tabFieldItem = tabFieldMultiMap.getMostRecentOrEmpty("physicalExamination", chiefComplaint);
                tabFieldItemsForChiefComplaint.add(tabFieldItem);
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
}
