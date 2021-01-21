package mock.femr.data.models;

import femr.data.models.core.IConceptPrescriptionAdministration;

public class MockPrescriptionAdministration implements IConceptPrescriptionAdministration {

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

    @Override
    public void setName(String name) {

    }

    @Override
    public float getDailyModifier() {
        return 0;
    }

    @Override
    public void setDailyModifier(float modifier) {

    }
}
