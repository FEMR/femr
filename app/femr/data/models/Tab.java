package femr.data.models;

import org.joda.time.DateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tabs")
public class Tab implements ITab {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "user_id", nullable = true)
    private Integer userId;
    @Column(name = "date_created", nullable = false)
    private DateTime dateCreated;
    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;
    @Column(name = "left_column_size", nullable = false)
    private int leftColumnSize;
    @Column(name = "right_column_size", nullable = false)
    private int rightColumnSize;
    @Column(name = "isCustom", nullable = false)
    private boolean isCustom;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
    public Integer getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Integer userId) {
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
    public boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(boolean isDeleted) {
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

    @Override
    public boolean getIsCustom() {
        return isCustom;
    }

    @Override
    public void setIsCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }
}
