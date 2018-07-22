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

import io.ebean.ExpressionList;
import io.ebean.Query;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.ITabService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.TabFieldItem;
import femr.common.models.TabItem;
import femr.data.IDataModelMapper;
import femr.data.daos.IRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;

import java.util.*;
import java.util.stream.Collectors;

public class TabService implements ITabService {

    private final IRepository<IChiefComplaint> chiefComplaintRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;
    private final IRepository<ITab> tabRepository;
    private final IRepository<ITabField> tabFieldRepository;
    private final IRepository<ITabFieldType> tabFieldTypeRepository;
    private final IRepository<ITabFieldSize> tabFieldSizeRepository;
    private final IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public TabService(IRepository<IChiefComplaint> chiefComplaintRepository,
                      IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository,
                      IRepository<ITab> tabRepository,
                      IRepository<ITabField> tabFieldRepository,
                      IRepository<ITabFieldType> tabFieldTypeRepository,
                      IRepository<ITabFieldSize> tabFieldSizeRepository,
                      IDataModelMapper DataModelMapper,
                      @Named("identified") IItemModelMapper itemModelMapper) {

        this.chiefComplaintRepository = chiefComplaintRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
        this.tabRepository = tabRepository;
        this.tabFieldRepository = tabFieldRepository;
        this.tabFieldTypeRepository = tabFieldTypeRepository;
        this.tabFieldSizeRepository = tabFieldSizeRepository;
        this.dataModelMapper = DataModelMapper;
        this.itemModelMapper = itemModelMapper;
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

        TabItem tabItem = itemModelMapper.createTabItem(tab.getName(), tab.getIsCustom(), tab.getLeftColumnSize(), tab.getRightColumnSize());
        response.setResponseObject(tabItem);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<TabItem> createTab(TabItem newTab, int userId) {
        ServiceResponse<TabItem> response = new ServiceResponse<>();
        if (newTab == null || StringUtils.isNullOrWhiteSpace(newTab.getName())) {
            response.addError("", "bad parameters, wtf are you doing?");
            return response;
        }
        ITab tab = dataModelMapper.createTab(dateUtils.getCurrentDateTime(), newTab.getLeftColumnSize(), newTab.getRightColumnSize(), newTab.getName(), false, userId);

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
    public ServiceResponse<List<TabItem>> retrieveCustomTabs(boolean isDeleted) {
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
            tabItems.add(itemModelMapper.createTabItem(t.getName(), t.getIsCustom(), t.getLeftColumnSize(), t.getRightColumnSize()));
        }
        response.setResponseObject(tabItems);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<TabFieldItem>> retrieveTabFieldsByTabName(String tabName, boolean isDeleted) {
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

                customFieldItems.add(itemModelMapper.createTabFieldItem(tf.getName(),
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

            TabFieldItem tabFieldItem = itemModelMapper.createTabFieldItem(tabField.getName(),
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
    public ServiceResponse<TabItem> updateTab(TabItem tabItem, int userId) {
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
            TabItem newTabItem = itemModelMapper.createTabItem(newTab.getName(), newTab.getIsCustom(), newTab.getLeftColumnSize(), newTab.getRightColumnSize());
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
    public ServiceResponse<TabFieldItem> updateTabField(TabFieldItem tabFieldItem) {
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

            TabFieldItem newTabFieldItem = itemModelMapper.createTabFieldItem(tabField.getName(),
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

        ITabField customField = dataModelMapper.createTabField(tabFieldItem.getName(), tabFieldItem.getOrder(), tabFieldItem.getPlaceholder(), false, tabFieldSize.getId(), tabFieldType.getId(), tab.getId());

        try {
            customField = tabFieldRepository.create(customField);
            String size = null;

            if (customField.getTabFieldSize() != null)
                size = customField.getTabFieldSize().getName();

            TabFieldItem newTabFieldItem = itemModelMapper.createTabFieldItem(customField.getName(),
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
    public ServiceResponse<List<String>> retrieveTypes() {
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
    public ServiceResponse<List<String>> retrieveSizes() {
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
    public ServiceResponse<TabFieldMultiMap> retrieveTabFieldMultiMap(int encounterId) {

        ServiceResponse<TabFieldMultiMap> response = new ServiceResponse<>();
        TabFieldMultiMap tabFieldMultiMap = mapTabFields(encounterId);
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
    public ServiceResponse<TabFieldMultiMap> findTabFieldMultiMap(int encounterId, String tabFieldName, String chiefComplaintName) {
        ServiceResponse<TabFieldMultiMap> response = new ServiceResponse<>();
        TabFieldMultiMap tabFieldMultiMap = new TabFieldMultiMap();

        Query<PatientEncounterTabField> patientEncounterTabFieldQuery = QueryProvider.getPatientEncounterTabFieldQuery()
                .where()
                .eq("tabField.name", tabFieldName)
                .eq("patient_encounter_id", encounterId)
                .order()
                .desc("date_taken");

        List<? extends IPatientEncounterTabField> patientEncounterTabFields = patientEncounterTabFieldRepository.find(patientEncounterTabFieldQuery);

        for (IPatientEncounterTabField tf : patientEncounterTabFields) {
            if (tf.getChiefComplaint() != null && tf.getChiefComplaint().getValue().equals(chiefComplaintName)) {
                //Only add tabFields for the request chief complaint
                tabFieldMultiMap.put(tabFieldName, tf.getDateTaken().toString().trim(), null, itemModelMapper.createTabFieldItem(tf.getTabField().getName(), tf.getTabField().getTabFieldType().getName(), "", 0, "", tf.getTabFieldValue(), null, false, tf.getUserName()));
            } else if (chiefComplaintName == null || chiefComplaintName.isEmpty()) {
                //No chief complaint, so put all matching fields
                tabFieldMultiMap.put(tabFieldName, tf.getDateTaken().toString().trim(), null, itemModelMapper.createTabFieldItem(tf.getTabField().getName(), tf.getTabField().getTabFieldType().getName(), "", 0, "", tf.getTabFieldValue(), null, false, tf.getUserName()));
            }
        }

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
    public ServiceResponse<List<TabItem>> retrieveAvailableTabs(boolean isDeleted) {

        ServiceResponse<List<TabItem>> response = new ServiceResponse<>();
        List<TabItem> tabItems = new ArrayList<>();
        ExpressionList<Tab> tabExpressionList = QueryProvider.getTabQuery()
                .where()
                .eq("isDeleted", isDeleted);

        try {

            List<? extends ITab> tabs = tabRepository.find(tabExpressionList);
            for (ITab tab : tabs) {
                tabItems.add(itemModelMapper.createTabItem(tab.getName(), tab.getIsCustom(), tab.getLeftColumnSize(), tab.getRightColumnSize()));
            }
            response.setResponseObject(tabItems);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * Maps all tab fields to their tab. Values and chief complaints are also recorded.
     * @param encounterId id of the patient encounter
     * @return {@link TabFieldMultiMap}
     */
    private TabFieldMultiMap mapTabFields(int encounterId) {

        TabFieldMultiMap tabFieldMultiMap = new TabFieldMultiMap();
        String tabFieldName;
        Query<ChiefComplaint> chiefComplaintExpressionList;
        ExpressionList<TabField> tabFieldQuery;
        Query<PatientEncounterTabField> patientEncounterTabFieldQuery;

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

                        tabFieldMultiMap.put(tabFieldName, petf.getDateTaken().toString().trim(), chiefComplaint, itemModelMapper.createTabFieldItem(petf.getTabField().getName(), petf.getTabField().getTabFieldType().getName(), tabFieldSize, petf.getTabField().getOrder(), petf.getTabField().getPlaceholder(), petf.getTabFieldValue(), chiefComplaint, isCustom, petf.getUserName()));
                    } else {

                        tabFieldMultiMap.put(tabFieldName, petf.getDateTaken().toString().trim(), null, itemModelMapper.createTabFieldItem(petf.getTabField().getName(), petf.getTabField().getTabFieldType().getName(), tabFieldSize, petf.getTabField().getOrder(), petf.getTabField().getPlaceholder(), petf.getTabFieldValue(), null, isCustom, petf.getUserName()));
                    }
                } else {

                    tabFieldMultiMap.put(tabFieldName, petf.getDateTaken().toString().trim(), null, itemModelMapper.createTabFieldItem(petf.getTabField().getName(), petf.getTabField().getTabFieldType().getName(), tabFieldSize, petf.getTabField().getOrder(), petf.getTabField().getPlaceholder(), petf.getTabFieldValue(), chiefComplaint, isCustom, petf.getUserName()));
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
                                tabFieldMultiMap.put(tf.getName(), null, cc.getValue(), itemModelMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tabFieldSize, tf.getOrder(), tf.getPlaceholder(), null, null, isCustom));
                            }
                        }
                    } else {
                        tabFieldMultiMap.put(tf.getName(), null, null, itemModelMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tabFieldSize, tf.getOrder(), tf.getPlaceholder(), null, null, isCustom));
                    }

                } else {
                    if (!tabFieldMultiMap.containsTabField(tf.getName())) {
                        tabFieldMultiMap.put(tf.getName(), null, null, itemModelMapper.createTabFieldItem(tf.getName(), tf.getTabFieldType().getName(), tabFieldSize, tf.getOrder(), tf.getPlaceholder(), null, null, isCustom));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Map<String, List<String>>> retrieveTabFieldToTabMapping(boolean isTabDeleted, boolean isTabFieldDeleted){

        ServiceResponse<Map<String, List<String>>> response = new ServiceResponse<>();
        Map<String, List<String>> responseObject = new HashMap<>();
        ExpressionList<? extends Tab> tabQuery = QueryProvider.getTabQuery()
                .where()
                .eq("isDeleted", isTabDeleted);

        try{

            List<? extends ITab> tabs = tabRepository.find(tabQuery);
            for (ITab tab : tabs){

                List<String> tabFieldNames = tab.getTabFields()
                        .stream()
                        .filter(tf -> tf.getIsDeleted().equals(isTabFieldDeleted))
                        .map(TabField::getName)
                        .collect(Collectors.toCollection(ArrayList::new));

                responseObject.put(tab.getName().toLowerCase(), tabFieldNames);
            }

            response.setResponseObject(responseObject);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }


}
