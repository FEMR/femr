package femr.data.models;

public interface IPmhField {
    int getId();

    String getName();

    void setName(String name);

    Boolean getDeleted();

    void setDeleted(Boolean deleted);
}
