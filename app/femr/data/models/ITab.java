package femr.data.models;

import org.joda.time.DateTime;

/**
 * Created by kevin on 5/31/14.
 */
public interface ITab {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    Integer getUserId();

    void setUserId(Integer userId);

    DateTime getDateCreated();

    void setDateCreated(DateTime dateCreated);

    boolean getIsDeleted();

    void setIsDeleted(boolean isDeleted);

    int getLeftColumnSize();

    void setLeftColumnSize(int leftColumnSize);

    int getRightColumnSize();

    void setRightColumnSize(int rightColumnSize);

    boolean getIsCustom();

    void setIsCustom(boolean isCustom);
}
