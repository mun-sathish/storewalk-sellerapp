package in.storewalk.storewalksellerapp.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.storewalk.storewalksellerapp.dto.StoreDetailsDTO;

/**
 * Created by Sathish Mun on 04-07-2015.
 */
public class JSONListConverterUtil {

    public static List<String> toStringList(JSONArray array_) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array_.length(); i++) {
            list.add(array_.getString(i));
        }
        return list;
    }

    public static List<Integer> toIntegerList(JSONArray array_) throws JSONException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array_.length(); i++) {
            list.add(array_.getInt(i));
        }
        return list;
    }

    public static List<HashMap<String, Double>> toGeoCodesList(JSONArray array_) throws JSONException {
        List<HashMap<String, Double>> list = new ArrayList<>();
        for (int i = 0; i < array_.length(); i++) {
            HashMap<String, Double> latLong =
                    new HashMap<>();
            JSONObject geoCode = array_.getJSONObject(i);
            latLong.put("lat", geoCode.getDouble("lat"));
            latLong.put("lng", geoCode.getDouble("lng"));
            list.add(latLong);
        }
        return list;
    }

    public static List<StoreDetailsDTO> toStoreDetailsList(JSONArray array) throws JSONException {
        List<StoreDetailsDTO> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            StoreDetailsDTO storeDetails = new StoreDetailsDTO();
            JSONObject object = array.getJSONObject(i);
            storeDetails.setStoreName(object.getString("storeName"));
            storeDetails.setAddress(object.getString("address"));
            storeDetails.setLocality(object.getString("locality"));
            storeDetails.setZip(object.getString("zip"));
            storeDetails.setContact(object.getString("contact"));
            list.add(storeDetails);
        }
        return list;
    }
}
