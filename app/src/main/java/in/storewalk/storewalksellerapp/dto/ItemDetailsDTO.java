package in.storewalk.storewalksellerapp.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Sathish Mun on 04-07-2015.
 */


public class ItemDetailsDTO implements Serializable {

    private String storeId;
    private String brand;
    private String type;
    private String city, state;
    private String gender;
    private String locality, currency;
    private List<String> iconImageUrl;
    private List<String> tags;
    private List<String> offers;
    private List<String> fullResImageUrl;
    private List<String> sizes;
    private List<String> color;
    private List<HashMap<String, Double>> geoCodes;
    private String itemCode, title, styleCode;
    private double discount;
    private int price;

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    private int oldPrice;
    private String zip;
    private boolean available;
    private String thumbnailUrl;
    private String description;
    private String fabric;
    private List<StoreDetailsDTO> storesDetails;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<String> getIconImageUrl() {
        return iconImageUrl;
    }

    public void setIconImageUrl(List<String> imageUrl) {
        this.iconImageUrl = imageUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getOffers() {
        return offers;
    }

    public void setOffers(List<String> offers) {
        this.offers = offers;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<HashMap<String, Double>> getGeoCodes() {
        return geoCodes;
    }

    public void setGeoCodes(List<HashMap<String, Double>> geoCodes) {
        this.geoCodes = geoCodes;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        if (available == 1)
            this.available = true;
        else
            this.available = false;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getFabric() {
        return this.fabric;
    }

    public List<String> getFullResImageUrl() {
        return fullResImageUrl;
    }

    public void setFullResImageUrl(List<String> fullResImageUrl) {
        this.fullResImageUrl = fullResImageUrl;
    }

    public List<StoreDetailsDTO> getStoresDetails() {
        return storesDetails;
    }

    public void setStoresDetails(List<StoreDetailsDTO> storesDetails) {
        this.storesDetails = storesDetails;
    }

    public String getStyleCode() {
        return this.styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }
}

