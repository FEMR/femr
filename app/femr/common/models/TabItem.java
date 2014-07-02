package femr.common.models;

public class TabItem {
    private String name;
    private int leftColumnSize;
    private int rightColumnSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeftColumnSize() {
        return leftColumnSize;
    }

    public void setLeftColumnSize(int leftColumnSize) {
        this.leftColumnSize = leftColumnSize;
    }

    public int getRightColumnSize() {
        return rightColumnSize;
    }

    public void setRightColumnSize(int rightColumnSize) {
        this.rightColumnSize = rightColumnSize;
    }
}
