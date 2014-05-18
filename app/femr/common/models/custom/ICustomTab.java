package femr.common.models.custom;

import org.joda.time.DateTime;

public interface ICustomTab {
    int getId();

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
