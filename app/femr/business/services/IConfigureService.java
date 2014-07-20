package femr.business.services;

import femr.common.dto.ServiceResponse;
import femr.data.models.ISystemSetting;

import java.util.List;

/**
 * Created by kevin on 7/28/14.
 */
public interface IConfigureService {
    /**
     * Get all user settings
     * @return
     */
    ServiceResponse<List<? extends ISystemSetting>> getCurrentSettings();

    /**
     * Update more than one system setting
     * @param systemSettings the updated settings
     * @return
     */
    ServiceResponse<List<? extends ISystemSetting>> updateSystemSettings(List<? extends ISystemSetting> systemSettings);

    /**
     * Get a system setting by name
     * @param settingName the name
     * @return the setting
     */
    ServiceResponse<ISystemSetting> getSystemSetting(String settingName);

    /**
     * Update one system setting
     * @param systemSetting
     * @return
     */
    ServiceResponse<ISystemSetting> updateSystemSetting(ISystemSetting systemSetting);
}
