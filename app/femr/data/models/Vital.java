package femr.data.models;

import femr.common.models.IVital;

import javax.persistence.*;

public class Vital implements IVital {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "data_type", nullable = true)
    private String data_type;
    @Column(name = "unit_of_measurement", nullable = true)
    private String unitOfMeasurement;

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
    public String getData_type() {
        return data_type;
    }

    @Override
    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    @Override
    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    @Override
    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }
}
