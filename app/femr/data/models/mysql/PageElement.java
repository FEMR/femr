package femr.data.models.mysql;

import femr.data.models.core.IMissionCity;
import femr.data.models.core.IMissionCountry;
import femr.data.models.core.IPage;
import femr.data.models.core.IPageElement;

import javax.persistence.*;

@Entity
@Table(name = "page_elements")
public class PageElement implements IPageElement {
    @Id
    @Column(name = "element_id", unique = true, nullable = false)
    private int elementId;
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "page_id", nullable = false)
    private Page page;

    @Override
    public int getElementId() { return elementId; }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) { this.description = description; }

    @Override
    public IPage getPage() {
        return page;
    }

    @Override
    public void setPage(IPage page) { this.page = (Page) page; }
}

