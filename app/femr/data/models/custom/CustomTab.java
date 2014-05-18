package femr.data.models.custom;

import femr.common.models.custom.ICustomTab;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "custom_tabs")
public class CustomTab implements ICustomTab {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "user_id", unique = false, nullable = false)
    private int userId;
    @Column(name = "date_created", nullable = false)
    private DateTime dateCreated;
    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted;
    @Column(name = "left_column_size", nullable = false)
    private int leftColumnSize;
    @Column(name = "right_column_size", nullable = false)
    private int rightColumnSize;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public DateTime getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public int getLeftColumnSize() {
        return leftColumnSize;
    }

    @Override
    public void setLeftColumnSize(int leftColumnSize) {
        this.leftColumnSize = leftColumnSize;
    }

    @Override
    public int getRightColumnSize() {
        return rightColumnSize;
    }

    @Override
    public void setRightColumnSize(int rightColumnSize) {
        this.rightColumnSize = rightColumnSize;
    }
}
