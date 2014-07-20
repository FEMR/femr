package femr.data.models;

/**
 * Created by kevin on 7/28/14.
 */
public interface ISystemSetting {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    boolean isActive();

    void setActive(boolean isActive);
}
