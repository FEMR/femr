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
package femr.business.services.system;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.google.inject.Inject;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.ITabService;
import femr.common.ItemMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.SettingItem;
import femr.common.models.TabFieldItem;
import femr.common.models.TabItem;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.*;

public class TabService implements ITabService {

    private final IRepository<IChiefComplaint> chiefComplaintRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final IRepository<ISystemSetting> systemSettingRepository;
    private final IRepository<ITab> tabRepository;
    private final IRepository<ITabField> tabFieldRepository;
    private final IRepository<ITabFieldType> tabFieldTypeRepository;
    private final IRepository<ITabFieldSize> tabFieldSizeRepository;
    private final DomainMapper domainMapper;

    @Inject
    public TabService(IRepository<IChiefComplaint> chiefComplaintRepository,
                      IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                      IRepository<ISystemSetting> systemSettingRepository,
                      IRepository<ITab> tabRepository,
                      IRepository<ITabField> tabFieldRepository,
                      IRepository<ITabFieldType> tabFieldTypeRepository,
                      IRepository<ITabFieldSize> tabFieldSizeRepository,
                      DomainMapper domainMapper) {
        this.chiefComplaintRepository = chiefComplaintRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
        this.systemSettingRepository = systemSettingRepository;
        this.tabRepository = tabRepository;
        this.tabFieldRepository = tabFieldRepository;
        this.tabFieldTypeRepository = tabFieldTypeRepository;
        this.tabFieldSizeRepository = tabFieldSizeRepository;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabItem> toggleTab(String name) {
        ServiceResponse<TabItem> response = new ServiceResponse<>();
        if (StringUtils.isNullOrWhiteSpace(name)) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }
        ExpressionList<Tab> query = QueryProvider.getTabQuery()
                .where()
                .eq("name", name);
        ITab tab;
        try {
            tab = tabRepository.findOne(query);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        tab.setIsDeleted(!tab.getIsDeleted());
        try {
            tab = tabRepository.update(tab);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        TabItem tabItem = DomainMapper.createTabItem(tab);
        response.setResponseObject(tabItem);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabItem> createTab(TabItem newTab, int userId) {
        ServiceResponse<TabItem> response = new ServiceResponse<>();
        if (newTab == null || StringUtils.isNullOrWhiteSpace(newTab.getName()) || userId < 1) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }
        ITab tab = domainMapper.createTab(newTab, false, userId);

        ExpressionList<Tab> query = QueryProvider.getTabQuery()
                .where()
                .eq("name", tab.getName());

        //verify the tab doesn't already exist
        try {
            ITab doesTabExist = tabRepository.findOne(query);
            if (doesTabExist != null) {
                response.addError("", "tab already exists");
                return response;
            }
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        //create the tab
        try {
            tabRepository.create(tab);
            response.setResponseObject(newTab);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabItem>> getCustomTabs(Boolean isDeleted) {
        ServiceResponse<List<TabItem>> response = new ServiceResponse<>();

        ExpressionList<Tab> query = QueryProvider.getTabQuery()
                .where()
                .eq("isDeleted", isDeleted)
                .eq("isCustom", true);

        List<? extends ITab> tabs = new ArrayList<>();
        try {
            tabs = tabRepository.find(query);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        List<TabItem> tabItems = new ArrayList<>();
        for (ITab t : tabs) {
            tabItems.add(DomainMapper.createTabItem(t));
        }
        response.setResponseObject(tabItems);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabFieldItem>> getTabFieldsByTabName(String tabName, Boolean isDeleted) {
        ServiceResponse<List<TabFieldItem>> response = new ServiceResponse<>();
        if (StringUtils.isNullOrWhiteSpace(tabName)) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }
        Query<TabField> query = QueryProvider.getTabFieldQuery()
                .fetch("tab")
                .where()
                .eq("isDeleted", isDeleted)
                .eq("tab.name", tabName)
                .order()
                .asc("sort_order");

        try {
            List<? extends ITabField> tabFields = tabFieldRepository.find(query);
            List<TabFieldItem> customFieldItems = new ArrayList<>();
            for (ITabField tf : tabFields) {
                customFieldItems.add(DomainMapper.createTabFieldItem(tf));
            }
            response.setResponseObject(customFieldItems);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Map<String, List<TabFieldItem>>> getTabFields(int encounterId) {
        ServiceResponse<Map<String, List<TabFieldItem>>> response = new ServiceResponse<>();
        if (encounterId < 1) {
            response.addError("", "encounterId must be greater than 0");
            return response;
        }
        Map<String, List<TabFieldItem>> customFieldMap = new HashMap<>();
        ExpressionList<Tab> query = QueryProvider.getTabQuery()
                .where()
                .eq("isDeleted", false);
        try {
            //
            List<? extends ITab> customTabs = tabRepository.find(query);
            for (ITab ct : customTabs) {
                Query<TabField> query2 = QueryProvider.getTabFieldQuery()
                        .fetch("tab")
                        .where()
                        .eq("isDeleted", false)
                        .eq("tab.name", ct.getName())
                        .order()
                        .asc("sort_order");


                List<? extends ITabField> customFields = tabFieldRepository.find(query2);
                List<TabFieldItem> customFieldItems = new ArrayList<>();
                for (ITabField cf : customFields) {
                    Query<PatientEncounterTabField> query3 = QueryProvider.getPatientEncounterTabFieldQuery()
                            .where()
                            .eq("tabField", cf)
                            .eq("patient_encounter_id", encounterId)
                            .order()
                            .desc("date_taken");

                    List<? extends IPatientEncounterTabField> patientEncounterCustomField = patientEncounterTabFieldRepository.find(query3);
                    if (patientEncounterCustomField != null && patientEncounterCustomField.size() > 0) {
                        customFieldItems.add(DomainMapper.createTabFieldItem(patientEncounterCustomField.get(0)));
                    } else {
                        customFieldItems.add(DomainMapper.createTabFieldItem(cf));
                    }


                }
                customFieldMap.put(ct.getName(), customFieldItems);

            }
            response.setResponseObject(customFieldMap);
        } catch (Exception ex) {
            response.addError("", "error");
            return response;
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabFieldItem> toggleTabField(String fieldName, String tabName) {

        //TODO: properly mark tabs as custom
        ServiceResponse<TabFieldItem> response = new ServiceResponse<>();
        if (StringUtils.isNullOrWhiteSpace(fieldName) || StringUtils.isNullOrWhiteSpace(tabName)) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }
        ExpressionList<TabField> query = QueryProvider.getTabFieldQuery()
                .fetch("tab")
                .where()
                .eq("name", fieldName)
                .eq("tab.name", tabName);

        try {
            ITabField tabField = tabFieldRepository.findOne(query);
            tabField.setIsDeleted(!tabField.getIsDeleted());
            //delete the sort order when the tab gets deactivated
            if (tabField.getIsDeleted() == true) tabField.setOrder(null);
            tabField = tabFieldRepository.update(tabField);
            TabFieldItem tabFieldItem = domainMapper.createTabFieldItem(tabField);
            response.setResponseObject(tabFieldItem);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabItem> editTab(TabItem tabItem, int userId) {
        ServiceResponse<TabItem> response = new ServiceResponse<>();
        if (tabItem == null || StringUtils.isNullOrWhiteSpace(tabItem.getName()) || userId < 1) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }

        ExpressionList<Tab> query = QueryProvider.getTabQuery()
                .where()
                .eq("name", tabItem.getName());
        try {
            ITab newTab = tabRepository.findOne(query);
            newTab.setDateCreated(DateTime.now());
            newTab.setLeftColumnSize(tabItem.getLeftColumnSize());
            newTab.setRightColumnSize(tabItem.getRightColumnSize());
            newTab.setUserId(userId);
            newTab = tabRepository.update(newTab);
            TabItem newTabItem = domainMapper.createTabItem(newTab);
            response.setResponseObject(newTabItem);
        } catch (Exception ex) {
            response.addError("", "error");
        }
        return response;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabFieldItem> editTabField(TabFieldItem tabFieldItem) {
        ServiceResponse<TabFieldItem> response = new ServiceResponse<>();
        if (tabFieldItem == null || tabFieldItem.getName() == null) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }

        ExpressionList<TabField> query = QueryProvider.getTabFieldQuery()
                .where()
                .eq("name", tabFieldItem.getName());

        try {
            ITabField tabField = tabFieldRepository.findOne(query);
            if (tabFieldItem.getPlaceholder() != null) tabField.setPlaceholder(tabFieldItem.getPlaceholder());
            if (tabFieldItem.getOrder() != null) tabField.setOrder(tabFieldItem.getOrder());
            //check size update
            if (StringUtils.isNotNullOrWhiteSpace(tabFieldItem.getSize())) {
                ExpressionList<TabFieldSize> sizeQuery = QueryProvider.getTabFieldSizeQuery()
                        .where()
                        .eq("name", tabFieldItem.getSize());
                ITabFieldSize tabFieldSize = tabFieldSizeRepository.findOne(sizeQuery);
                tabField.setTabFieldSize(tabFieldSize);
            }
            //check type update
            if (StringUtils.isNotNullOrWhiteSpace(tabFieldItem.getType())) {
                ExpressionList<TabFieldType> typeQuery = QueryProvider.getTabFieldTypeQuery()
                        .where()
                        .eq("name", tabFieldItem.getType());
                ITabFieldType tabFieldType = tabFieldTypeRepository.findOne(typeQuery);
                tabField.setTabFieldType(tabFieldType);
            }
            tabField = tabFieldRepository.update(tabField);

            TabFieldItem newTabFieldItem = domainMapper.createTabFieldItem(tabField);
            response.setResponseObject(newTabFieldItem);

        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabFieldItem> createTabField(TabFieldItem tabFieldItem, int userId, String tabName) {
        ServiceResponse<TabFieldItem> response = new ServiceResponse<>();
        if (tabFieldItem == null ||
                StringUtils.isNullOrWhiteSpace(tabFieldItem.getName()) ||
                StringUtils.isNullOrWhiteSpace(tabFieldItem.getSize()) ||
                StringUtils.isNullOrWhiteSpace(tabFieldItem.getType()) ||
                StringUtils.isNullOrWhiteSpace(tabName)) {
            response.addError("", "you're TabFieldItem sucks, fix it up and try again");
            return response;
        }

        //get tabs
        ExpressionList<Tab> tabQuery = QueryProvider.getTabQuery()
                .where()
                .eq("name", tabName);

        //verify the tab exists?
        ITab tab;
        try {
            tab = tabRepository.findOne(tabQuery);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }


        //get field type
        ExpressionList<TabFieldType> typeQuery = QueryProvider.getTabFieldTypeQuery()
                .where()
                .eq("name", tabFieldItem.getType());

        ITabFieldType tabFieldType;
        try {
            tabFieldType = tabFieldTypeRepository.findOne(typeQuery);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }


        //get field size
        ExpressionList<TabFieldSize> sizeQuery = QueryProvider.getTabFieldSizeQuery()
                .where()
                .eq("name", tabFieldItem.getSize());

        ITabFieldSize tabFieldSize;
        try {
            tabFieldSize = tabFieldSizeRepository.findOne(sizeQuery);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        ITabField customField = domainMapper.createTabField(tabFieldItem, false, tabFieldSize, tabFieldType, tab);

        try {
            customField = tabFieldRepository.create(customField);
            TabFieldItem newTabFieldItem = domainMapper.createTabFieldItem(customField);
            response.setResponseObject(newTabFieldItem);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> getTypes() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();
        List<String> fields = new ArrayList<>();

        try {
            List<? extends ITabFieldType> tabFieldTypes = tabFieldTypeRepository.findAll(TabFieldType.class);
            for (ITabFieldType cft : tabFieldTypes) {
                fields.add(cft.getName());
            }
            response.setResponseObject(fields);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<String>> getSizes() {
        ServiceResponse<List<String>> response = new ServiceResponse<>();
        List<String> fields = new ArrayList<>();

        try {
            List<? extends ITabFieldSize> tabFieldSizes = tabFieldSizeRepository.findAll(TabFieldSize.class);
            for (ITabFieldSize cfs : tabFieldSizes) {
                fields.add(cfs.getName());
            }
            response.setResponseObject(fields);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> doesTabFieldExist(String fieldName) {
        ServiceResponse<Boolean> response = new ServiceResponse<>();
        ExpressionList<TabField> query = QueryProvider.getTabFieldQuery()
                .where()
                .eq("name", fieldName);
        try {
            ITabField customField = tabFieldRepository.findOne(query);
            if (customField == null) {
                response.setResponseObject(false);
            } else {
                response.setResponseObject(true);
            }

        } catch (Exception ex) {
            response.setResponseObject(false);
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> doesTabExist(String tabName) {
        ServiceResponse<Boolean> response = new ServiceResponse<>();
        ExpressionList<Tab> query = QueryProvider.getTabQuery()
                .where()
                .eq("name", tabName);
        try {
            ITab customTab = tabRepository.findOne(query);
            if (customTab == null) {
                response.setResponseObject(false);
            } else {
                response.setResponseObject(true);
            }
        } catch (Exception ex) {
            response.setResponseObject(false);
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabItem>> findAllTabsAndFieldsByEncounterId(int encounterId, boolean isActive) {

        ServiceResponse<List<TabItem>> response = new ServiceResponse<>();
        List<TabItem> tabItems = new ArrayList<>();
        TabItem tabItem;

        ExpressionList<Tab> tabQuery = QueryProvider.getTabQuery()
                .where()
                .eq("isDeleted", false);

        try {
            List<? extends ISystemSetting> systemSettings = systemSettingRepository.findAll(SystemSetting.class);
            SettingItem settingItem = ItemMapper.createSettingItem(systemSettings);
            List<? extends ITab> allTabs = tabRepository.find(tabQuery);

            for (ITab t : allTabs) {

                //create a tab item that will store chief complaints and fields
                tabItem = ItemMapper.createTabItem(t.getName(), t.getIsCustom(), t.getLeftColumnSize(), t.getRightColumnSize());

                //does this work..?
                Collections.sort(t.getTabFields(), new Comparator<ITabField>() {

                    @Override
                    public int compare(ITabField o1, ITabField o2) {
                        if (o1.getOrder() == null && o2.getOrder() != null)
                            return -1;
                        if (o1.getOrder() == null && o2.getOrder() == null)
                            return 0;
                        if (o1.getOrder() != null && o2.getOrder() == null)
                            return 1;

                        return o1.getOrder().compareTo(o2.getOrder());
                    }
                });

                //goes through each tab field that belongs to a specific tab
                for (ITabField tf : t.getTabFields()) {

                    //get available chief complaints
                    ExpressionList<ChiefComplaint> chiefComplaintQuery = QueryProvider.getChiefComplaintQuery()
                            .where()
                            .eq("patient_encounter_id", encounterId);
                    List<? extends IChiefComplaint> chiefComplaints = chiefComplaintRepository.find(chiefComplaintQuery);
                    //get a list of values that have been recorded for a specific tab field
                    Query<PatientEncounterTabField> patientEncounterTabFieldQuery = QueryProvider.getPatientEncounterTabFieldQuery()
                            .where()
                            .eq("tabField", tf)
                            .eq("patient_encounter_id", encounterId)
                            .order()
                            .desc("date_taken");
                    List<? extends IPatientEncounterTabField> patientEncounterFieldsWithValue = patientEncounterTabFieldRepository.find(patientEncounterTabFieldQuery);

                    List<TabFieldItem> tabFieldItemsForChiefComplaint;

                    //separate the tab fields by chief complaint
                    Map<String, List<TabFieldItem>> separatedTabFieldsByChiefComplaint = new HashMap<>();
                    if (patientEncounterFieldsWithValue != null && patientEncounterFieldsWithValue.size() > 0) {
                        //the field has value(s)
                        separatedTabFieldsByChiefComplaint = separateTabFieldsByChiefComplaint(patientEncounterFieldsWithValue);
                    } else {
                        //the field has no values
                        List<TabFieldItem> tabFieldItems = new ArrayList<>();
                        String size = null;
                        if (tf.getTabFieldSize() != null) {
                            size = tf.getTabFieldSize().getName();
                        }
                        TabFieldItem tabFieldItem = ItemMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), size, tf.getOrder(), tf.getPlaceholder());
                        tabFieldItems.add(tabFieldItem);
                        if (t.getName().toLowerCase().equals("hpi")) {
                            //if the tab is HPI, separate the empty fields by chief complaint
                            //(each chief complaint gets the field)
                            if (chiefComplaints == null || chiefComplaints.size() < 1) {
                                //no chief complaints exist, add the field to the null key
                                separatedTabFieldsByChiefComplaint.put(null, tabFieldItems);
                            } else {
                                for (IChiefComplaint cc : chiefComplaints) {
                                    separatedTabFieldsByChiefComplaint.put(cc.getValue(), tabFieldItems);
                                }
                            }
                        } else {
                            separatedTabFieldsByChiefComplaint.put(null, tabFieldItems);
                        }


                    }


                    //iterate over all tab fields that have a value
                    for (Map.Entry<String, List<TabFieldItem>> tabFieldItems : separatedTabFieldsByChiefComplaint.entrySet()) {
                        String key_chiefComplaint = tabFieldItems.getKey();
                        List<TabFieldItem> tabFieldItemsWithValueForChiefComplaint = tabFieldItems.getValue();

                        if (tabFieldItemsWithValueForChiefComplaint.size() > 0) {
                            //pull the fields out of the map going back to the UI
                            tabFieldItemsForChiefComplaint = tabItem.getFields(key_chiefComplaint);
                            if (tabFieldItemsForChiefComplaint == null || tabFieldItemsForChiefComplaint.size() == 0) {

                                tabFieldItemsForChiefComplaint = new ArrayList<>();
                            }
                            tabFieldItemsForChiefComplaint.add(tabFieldItemsWithValueForChiefComplaint.get(0));
                            tabItem.setFields(key_chiefComplaint, tabFieldItemsForChiefComplaint);
                        } else {
                            tabFieldItemsForChiefComplaint = tabItem.getFields(key_chiefComplaint);
                            if (tabFieldItemsForChiefComplaint == null || tabFieldItemsForChiefComplaint.size() == 0) {

                                tabFieldItemsForChiefComplaint = new ArrayList<>();
                            }
                            tabFieldItemsForChiefComplaint.add(ItemMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tf.getTabFieldSize().getName(), tf.getOrder(), tf.getPlaceholder()));
                            tabItem.setFields(key_chiefComplaint, tabFieldItemsForChiefComplaint);
                        }

                    }
                }

                tabItems.add(tabItem);
            }
            response.setResponseObject(tabItems);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }

    private Map<String, List<TabFieldItem>> separateTabFieldsByChiefComplaint(List<? extends IPatientEncounterTabField> patientEncounterTabFieldsWithValue) {
        Map<String, List<TabFieldItem>> mappedTabFields = new HashMap<>();
        List<TabFieldItem> patientEncounterTabFieldItemsForMap;
        TabFieldItem tabFieldItem;

        for (IPatientEncounterTabField petf : patientEncounterTabFieldsWithValue) {

            String size = null;
            String chiefComplaint = null;
            if (petf.getTabField().getTabFieldSize() != null)
                size = petf.getTabField().getTabFieldSize().getName();
            if (petf.getChiefComplaint() != null)
                chiefComplaint = petf.getChiefComplaint().getValue();
            tabFieldItem = ItemMapper.createTabFieldItem(petf.getTabField().getName(),
                    petf.getTabField().getTabFieldType().getName(),
                    size,
                    petf.getTabField().getOrder(),
                    petf.getTabField().getPlaceholder(),
                    petf.getTabFieldValue(),
                    chiefComplaint);

            if (!mappedTabFields.containsKey(chiefComplaint)) {
                //create a new entry
                patientEncounterTabFieldItemsForMap = new ArrayList<>();
                patientEncounterTabFieldItemsForMap.add(tabFieldItem);
                mappedTabFields.put(chiefComplaint, patientEncounterTabFieldItemsForMap);
            } else {

                patientEncounterTabFieldItemsForMap = mappedTabFields.get(chiefComplaint);
                patientEncounterTabFieldItemsForMap.add(tabFieldItem);
                mappedTabFields.put(chiefComplaint, patientEncounterTabFieldItemsForMap);
            }
        }

        return mappedTabFields;
    }

}