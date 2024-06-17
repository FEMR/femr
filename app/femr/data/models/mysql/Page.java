package femr.data.models.mysql;

import femr.data.models.core.*;

import javax.persistence.*;

@Entity
@Table(name = "pages")
public class Page implements IPage {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public int getId() { return id; }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) { this.name = name; }
}
