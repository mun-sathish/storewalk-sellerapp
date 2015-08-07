package in.storewalk.storewalksellerapp.dto;

import java.io.Serializable;

/**
 * Created by Sathish Mun on 04-07-2015.
 */
public class BrowseProductDTO implements Serializable {

    ItemDetailsDTO firstItemInfo;
    ItemDetailsDTO secondItemInfo;

    //constructor
    public BrowseProductDTO() {
        firstItemInfo = new ItemDetailsDTO();
        secondItemInfo = new ItemDetailsDTO();
    }

    public ItemDetailsDTO getFirstItemInfo() {
        return firstItemInfo;
    }

    public void setFirstItemInfo(ItemDetailsDTO firstItemInfo) {
        this.firstItemInfo = firstItemInfo;
    }

    public ItemDetailsDTO getSecondItemInfo() {
        return secondItemInfo;
    }

    public void setSecondItemInfo(ItemDetailsDTO secondItemInfo) {
        this.secondItemInfo = secondItemInfo;
    }

}
