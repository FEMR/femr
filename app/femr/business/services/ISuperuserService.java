package femr.business.services;


import femr.business.dtos.ServiceResponse;
import femr.common.models.custom.ICustomField;
import femr.common.models.custom.ICustomTab;
import femr.ui.models.data.CustomFieldItem;

import java.util.List;

public interface ISuperuserService {
    ServiceResponse<List<? extends ICustomTab>> getCustomMedicalTabs(Boolean isDeleted);
    ServiceResponse<ICustomTab> getCustomMedicalTab(String name);
    ServiceResponse<ICustomTab> createCustomMedicalTab(ICustomTab newTab);
    ServiceResponse<ICustomTab> removeCustomMedicalTab(String name);
    ServiceResponse<List<CustomFieldItem>> getCustomFields(Boolean isDeleted);
    ServiceResponse<CustomFieldItem> createCustomField(CustomFieldItem customFieldItem, int userId, String tabName);
}
