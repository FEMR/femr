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
import femr.common.models.TabFieldItem;
import femr.common.models.TabItem;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
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

        TabItem tabItem = ItemMapper.createTabItem(tab.getName(), tab.getIsCustom(), tab.getLeftColumnSize(), tab.getRightColumnSize());
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
            tabItems.add(ItemMapper.createTabItem(t.getName(), t.getIsCustom(), t.getLeftColumnSize(), t.getRightColumnSize()));
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
            String size = null;
            for (ITabField tf : tabFields) {

                if (tf.getTabFieldSize() != null)
                    size = tf.getTabFieldSize().getName();

                customFieldItems.add(ItemMapper.createTabFieldItem(tf.getName(),
                        tf.getTabFieldType().getName(),
                        size,
                        tf.getOrder(),
                        tf.getPlaceholder(),
                        null,
                        null,
                        true));
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
            if (tabField.getIsDeleted()) tabField.setOrder(null);
            tabField = tabFieldRepository.update(tabField);
            String size = null;
            if (tabField.getTabFieldSize() != null)
                size = tabField.getTabFieldSize().getName();

            TabFieldItem tabFieldItem = ItemMapper.createTabFieldItem(tabField.getName(),
                    tabField.getTabFieldType().getName(),
                    size,
                    tabField.getOrder(),
                    tabField.getPlaceholder(),
                    null,
                    null,
                    true);
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
            TabItem newTabItem = ItemMapper.createTabItem(newTab.getName(), newTab.getIsCustom(), newTab.getLeftColumnSize(), newTab.getRightColumnSize());
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

            String size = null;

            if (tabField.getTabFieldSize() != null)
                size = tabField.getTabFieldSize().getName();

            TabFieldItem newTabFieldItem = ItemMapper.createTabFieldItem(tabField.getName(),
                    tabField.getTabFieldType().getName(),
                    size,
                    tabField.getOrder(),
                    tabField.getPlaceholder(),
                    null,
                    null,
                    true);

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
            String size = null;

            if (customField.getTabFieldSize() != null)
                size = customField.getTabFieldSize().getName();

            TabFieldItem newTabFieldItem = ItemMapper.createTabFieldItem(customField.getName(),
                    customField.getTabFieldType().getName(),
                    size,
                    customField.getOrder(),
                    customField.getPlaceholder(),
                    null,
                    null,
                    true);

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
    public ServiceResponse<TabFieldMultiMap> findTabFieldMultiMap(int encounterId) {

        ServiceResponse<TabFieldMultiMap> response = new ServiceResponse<>();
        TabFieldMultiMap tabFieldMultiMap = mapTabFields(encounterId, null);
        if (tabFieldMultiMap != null)
            response.setResponseObject(tabFieldMultiMap);
        else
            response.addError("", "there was an issue building the multi map");
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabItem>> findAvailableTabs(boolean isDeleted) {

        ServiceResponse<List<TabItem>> response = new ServiceResponse<>();
        List<TabItem> tabItems = new ArrayList<>();
        ExpressionList<Tab> tabExpressionList = QueryProvider.getTabQuery()
                .where()
                .eq("isDeleted", isDeleted);

        try {

            List<? extends ITab> tabs = tabRepository.find(tabExpressionList);
            for (ITab tab : tabs) {
                tabItems.add(ItemMapper.createTabItem(tab.getName(), tab.getIsCustom(), tab.getLeftColumnSize(), tab.getRightColumnSize()));
            }
            response.setResponseObject(tabItems);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }


    private TabFieldMultiMap mapTabFields(int encounterId, String tabName) {

        TabFieldMultiMap tabFieldMultiMap = new TabFieldMultiMap();
        String tabFieldName;
        Query<ChiefComplaint> chiefComplaintExpressionList;
        ExpressionList<TabField> tabFieldQuery;
        Query<PatientEncounterTabField> patientEncounterTabFieldQuery;

        if (StringUtils.isNullOrWhiteSpace(tabName)) {//do all tab fields, don't filter by tab name

            //get all tab fields!
            tabFieldQuery = QueryProvider.getTabFieldQuery()
                    .where()
                    .eq("isDeleted", false);

            //get all tab fields with values!
            patientEncounterTabFieldQuery = QueryProvider.getPatientEncounterTabFieldQuery()
                    .where()
                    .eq("patient_encounter_id", encounterId)
                    .order()
                    .desc("date_taken");
        } else { //only get tab fields for a specific tab

            //get all tab fields that belong to a tab
            tabFieldQuery = QueryProvider.getTabFieldQuery()
                    .fetch("tab")
                    .where()
                    .eq("isDeleted", false)
                    .eq("tab.name", tabName);

            //get all tab fields that have value which belong to a tab
            patientEncounterTabFieldQuery = QueryProvider.getPatientEncounterTabFieldQuery()
                    .fetch("tabField")
                    .fetch("tabField.tab")
                    .where()
                    .eq("tabField.tab.name", tabName)
                    .eq("patient_encounter_id", encounterId)
                    .order()
                    .desc("date_taken");
        }

        //need all chief complaints regardless
        chiefComplaintExpressionList = QueryProvider.getChiefComplaintQuery()
                .where()
                .eq("patient_encounter_id", encounterId)
                .order()
                .asc("sortOrder");


        try {

            List<? extends ITabField> tabFields = tabFieldRepository.find(tabFieldQuery);
            List<? extends IPatientEncounterTabField> patientEncounterTabFields = patientEncounterTabFieldRepository.find(patientEncounterTabFieldQuery);
            List<? extends IChiefComplaint> chiefComplaints = chiefComplaintRepository.find(chiefComplaintExpressionList);
            //Collections.reverse(patientEncounterTabFields);

            //all fields that have values
            for (IPatientEncounterTabField petf : patientEncounterTabFields) {
                String tabFieldSize = null;
                String chiefComplaint = null;
                boolean isCustom = petf.getTabField().getTab().getIsCustom();
                if (petf.getTabField().getTabFieldSize() != null)
                    tabFieldSize = petf.getTabField().getTabFieldSize().getName();
                if (petf.getChiefComplaint() != null)
                    chiefComplaint = petf.getChiefComplaint().getValue();

                tabFieldName = petf.getTabField().getName();

                if (petf.getTabField().getTab().getName().equals("HPI")) {

                    if (chiefComplaints != null && chiefComplaints.size() > 0) {

                        tabFieldMultiMap.put(tabFieldName, petf.getDateTaken().toString().trim(), chiefComplaint, ItemMapper.createTabFieldItem(petf.getTabField().getName(), petf.getTabField().getTabFieldType().getName(), tabFieldSize, petf.getTabField().getOrder(), petf.getTabField().getPlaceholder(), petf.getTabFieldValue(), chiefComplaint, isCustom));
                    } else {

                        tabFieldMultiMap.put(tabFieldName, petf.getDateTaken().toString().trim(), null, ItemMapper.createTabFieldItem(petf.getTabField().getName(), petf.getTabField().getTabFieldType().getName(), tabFieldSize, petf.getTabField().getOrder(), petf.getTabField().getPlaceholder(), petf.getTabFieldValue(), null, isCustom));
                    }
                } else {

                    tabFieldMultiMap.put(tabFieldName, petf.getDateTaken().toString().trim(), null, ItemMapper.createTabFieldItem(petf.getTabField().getName(), petf.getTabField().getTabFieldType().getName(), tabFieldSize, petf.getTabField().getOrder(), petf.getTabField().getPlaceholder(), petf.getTabFieldValue(), chiefComplaint, isCustom));
                }


            }

            //all empty fields

            for (ITabField tf : tabFields) {
                String tabFieldSize = null;
                boolean isCustom = tf.getTab().getIsCustom();
                if (tf.getTabFieldSize() != null)
                    tabFieldSize = tf.getTabFieldSize().getName();

                //hpi gets special treatment for each chief complaint
                if (tf.getTab().getName().equals("HPI")) {

                    if (chiefComplaints != null && chiefComplaints.size() > 0) {
                        for (IChiefComplaint cc : chiefComplaints) {
                            if (!tabFieldMultiMap.containsTabField(tf.getName(), cc.getValue())) {
                                tabFieldMultiMap.put(tf.getName(), null, cc.getValue(), ItemMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tabFieldSize, tf.getOrder(), tf.getPlaceholder(), null, null, isCustom));
                            }
                        }
                    } else {
                        tabFieldMultiMap.put(tf.getName(), null, null, ItemMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tabFieldSize, tf.getOrder(), tf.getPlaceholder(), null, null, isCustom));
                    }

                } else {
                    if (!tabFieldMultiMap.containsTabField(tf.getName())) {
                        tabFieldMultiMap.put(tf.getName(), null, null, ItemMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tabFieldSize, tf.getOrder(), tf.getPlaceholder(), null, null, isCustom));
                    }
                }
            }

            //sort the chief complaints
            for (IChiefComplaint cc : chiefComplaints) {
                tabFieldMultiMap.setChiefComplaintOrder(cc.getValue(), cc.getSortOrder());
            }
        } catch (Exception ex) {

            return null;
        }

        return tabFieldMultiMap;
    }


}
