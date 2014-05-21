package femr.data.models.custom;

import femr.common.models.custom.ICustomFieldSize;
import femr.common.models.custom.ICustomFieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by kevin on 5/18/14.
 */
@Entity
@Table(name = "custom_field_sizes")
public class CustomFieldSize implements ICustomFieldSize {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;

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
}
