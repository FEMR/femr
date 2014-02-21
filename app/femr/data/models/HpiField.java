package femr.data.models;

import femr.common.models.IHpiField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hpi_fields")
public class HpiField implements IHpiField {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = false, nullable = false)
    private String name;
    @Column(name = "isDeleted", nullable = false)
    private Boolean deleted;

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
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
