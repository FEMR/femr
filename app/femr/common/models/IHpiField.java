package femr.common.models;

public interface IHpiField {
    int getId();

    String getName();

    void setName(String name);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);
}
