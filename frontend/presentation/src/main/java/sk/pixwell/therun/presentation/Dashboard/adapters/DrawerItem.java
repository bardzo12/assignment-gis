package sk.pixwell.therun.presentation.Dashboard.adapters;

/**
 * Created by Tomáš Baránek on 27.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
public class DrawerItem {

    String ItemName;
    Boolean selected = false;
    //int imgResID;

    public DrawerItem(String itemName) {
        super();
        ItemName = itemName;
        //this.imgResID = imgResID;
    }

    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    /*public int getImgResID() {
        return imgResID;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }*/

}
