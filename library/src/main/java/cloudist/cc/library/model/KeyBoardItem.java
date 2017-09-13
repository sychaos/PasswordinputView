package cloudist.cc.library.model;

/**
 * Created by cloudist on 2017/9/13.
 */

public class KeyBoardItem {

    private int itemType;
    private int value;

    public KeyBoardItem(int itemType) {
        this.itemType = itemType;
        this.value = 0;
    }

    public KeyBoardItem(int itemType, int value) {
        this.itemType = itemType;
        this.value = value;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
