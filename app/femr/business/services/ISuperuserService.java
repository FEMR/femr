package femr.business.services;


import femr.business.dtos.ServiceResponse;
import femr.common.models.custom.ICustomTab;
import femr.ui.models.data.custom.CustomFieldItem;

import java.util.List;

public interface ISuperuserService {
    ServiceResponse<List<? extends ICustomTab>> getCustomMedicalTabs(Boolean isDeleted);
    ServiceResponse<ICustomTab> createCustomMedicalTab(ICustomTab newTab);
    ServiceResponse<ICustomTab> removeCustomMedicalTab(String name);

    ServiceResponse<List<CustomFieldItem>> getCustomFields(Boolean isDeleted);
    ServiceResponse<Boolean> doesCustomFieldExist(String fieldName);
    ServiceResponse<CustomFieldItem> createCustomField(CustomFieldItem customFieldItem, int userId, String tabName);
    ServiceResponse<CustomFieldItem> toggleCustomField(String fieldName, String tabName);
    ServiceResponse<CustomFieldItem> editCustomField(CustomFieldItem customFieldItem, int userId);

    ServiceResponse<List<String>> getTypes();
    ServiceResponse<List<String>> getSizes();
}
