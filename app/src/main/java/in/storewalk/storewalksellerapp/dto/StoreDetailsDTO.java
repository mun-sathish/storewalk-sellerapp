package in.storewalk.storewalksellerapp.dto;

/**
 * Created by Sathish Mun on 04-07-2015.
 */

public class StoreDetailsDTO {
    private String storeName;
    private String address;
    private String locality;
    private String zip;
    private String contact;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "{" +
                "\"storeName\": \"" + storeName + "\"" +
                ",\"address\": \"" + address + "\"" +
                ",\"locality\": \"" + locality + "\"" +
                ",\"zip\": \"" + zip + "\"" +
                ",\"contact\": \"" + contact + "\"" +
                "}";
    }
}
