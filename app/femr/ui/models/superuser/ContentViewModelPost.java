package femr.ui.models.superuser;

/**
 * Created by kevin on 5/18/14.
 */
public class ContentViewModelPost {
    private String addName;
    private String addType;
    private String addSize;
    private Integer addOrder;
    private String addPlaceholder;
    private String toggleName;

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getAddSize() {
        return addSize;
    }

    public void setAddSize(String addSize) {
        this.addSize = addSize;
    }

    public String getToggleName() {
        return toggleName;
    }

    public void setToggleName(String toggleName) {
        this.toggleName = toggleName;
    }

    public Integer getAddOrder() {
        return addOrder;
    }

    public void setAddOrder(Integer addOrder) {
        this.addOrder = addOrder;
    }

    public String getAddPlaceholder() {
        return addPlaceholder;
    }

    public void setAddPlaceholder(String addPlaceholder) {
        this.addPlaceholder = addPlaceholder;
    }
}
