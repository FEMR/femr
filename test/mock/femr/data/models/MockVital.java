package mock.femr.data.models;

import femr.data.models.core.IVital;

public class MockVital implements IVital {

    int id = 1;
    String name = "respiratoryRate";
    String dataType = "int";
    String measurementUnit = "breaths/minute";
    boolean isDeleted = false;

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getData_type() {
        return this.dataType;
    }

    @Override
    public void setData_type(String data_type) {
        this.dataType = data_type;
    }

    @Override
    public String getUnitOfMeasurement() {
        return this.measurementUnit;
    }

    @Override
    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.measurementUnit = unitOfMeasurement;
    }

    @Override
    public Boolean getDeleted() {
        return this.isDeleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.isDeleted = deleted;
    }
}
