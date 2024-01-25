package femr.data.models.core;

public interface IPageElement {
    int getElementId();

    String getDescription();

    void setDescription(String description);

    IPage getPage();

    void setPage(IPage page);
}
