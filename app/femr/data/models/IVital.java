package femr.data.models;

public interface IVital {
    void setId(int id);

    int getId();

    String getName();

    void setName(String name);

    String getData_type();

    void setData_type(String data_type);

    String getUnitOfMeasurement();

    void setUnitOfMeasurement(String unitOfMeasurement);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);
}
