package mock.femr.data.models;
import femr.data.models.core.IRole;

public class MockRole implements IRole {
    private int id = -1;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return null;
    }

    public void setName(String name) {
    }
}
