package femr.common.models;

import org.joda.time.DateTime;

/**
 * Created by kevin on 5/31/14.
 */
public interface ITab {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    int getUserId();

    void setUserId(int userId);

    DateTime getDateCreated();

    void setDateCreated(DateTime dateCreated);

    Boolean getIsDeleted();

    void setIsDeleted(Boolean isDeleted);

    int getLeftColumnSize();

    void setLeftColumnSize(int leftColumnSize);

    int getRightColumnSize();

    void setRightColumnSize(int rightColumnSize);
}
