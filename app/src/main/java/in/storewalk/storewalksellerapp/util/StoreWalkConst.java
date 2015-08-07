package in.storewalk.storewalksellerapp.util;

/**
 * Created by Sathish Mun on 04-07-2015.
 */

public class StoreWalkConst {

    public static final String KEY_SIGNUP = "signup";
    public static final String KEY_SOCIAL_FACEBOOK = "facebook";
    public static final String ERROR_NETWORK_CONNECTIVITY = "No internet connection. Please connect to a network";
    public static final String ERROR_SERVER = "Unable to process your request,please try again later";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_SIGNUP_PASSWORD = "password";
    public static final String KEY_USERNAME = "";
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_STATUS = "status";
    public static final String STATUS_PASS = "PASS";
    public static final String STATUS_ERROR = "ERROR";
    public static final String KEY_LOGIN_STATUS = "loginStatus";
    public static final String KEY_GUEST_STATUS = "guestStatus";
    public static final String KEY_LOGIN_SOCIAL = "socialLogin";
    public static final String KEY_USER_INFO = "userInfo";
    public static final String STATUS_FAIL = "FAIL";
    public static final String KEY_MESSAGE = "message";
    //public static final String ERROR_SERVER = "Something went wrong... Please try later";
    public static final String ERROR_JSON_PARSER = "Error! Please try later.";
    public static final String EMPTY_JSON = "{}";

    /**
     * JSON Keys *
     */
    public static final String KEY_PRODUCT_URL = "url";
    public static final String KEY_PRODUCT_NAME = "name";
    public static final String KEY_PRODUCT_PRICE = "price";
    public static final String KEY_CART_ITEMS = "cartItems";
    public static final String KEY_PRODUCT_ID = "itemID";
    public static final String KEY_STORE_ID = "itemID";
    public static final String KEY_CART_JSON = "cartJSON";
    public static final String KEY_RESERVE_CART_JSON = "reserveCartJSON";
    public static final String KEY_EXPIRED_CART_JSON = "expiredCartJSON";
    public static final String KEY_CART_ID = "cartId";
    public static final String KEY_USER_ID = "userID";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_WALLET_ID = "walletId";
    public static final String KEY_ECASH = "eCash";

    public static final String RESERVE = "R";
    public static final String SHORT_LIST = "L";

    /* SOCIAL LOGIN*/
    public static final String FACEBOOK = "facebook";
    public static final String GOOGLE = "google";

    /**
     * URLS *
     */
    //public static final String URL_BASE = "https://storewalk.herokuapp.com/storewalk";
    //public static final String URL_BASE = "http://10.0.1.9:1123/storewalk";
    public static final String URL_BASE = "http://52.74.35.166:1123/storewalk";
    //public static final String URL_BASE = "http://10.0.1.14:1123/storewalk";
    public static final String URL_AUTHENTICATE = URL_BASE + "/auth/login";
    public static final String URL_AUTO_SIGNUP = URL_BASE + "/auth/auto";
    public static final String URL_LOGOUT = URL_BASE + "/auth/logout";
    public static final String URL_SET_MOBILE = URL_BASE + "/auth/mobile";
    public static final String URL_SOCIAL_LOGIN = URL_BASE + "/auth/slogin";
    public static final String URL_REGISTER = URL_BASE + "/auth/signup";
    public static final String URL_ADD_TO_CART = URL_BASE + "/cart";
    public static final String URL_REMOVE_FROM_CART = URL_BASE + "/cart/delete";
    public static final String URL_GET_ITEM_DETAILS = URL_BASE + "/query/item";
    public static final String URL_SET_RESERVE = URL_BASE + "/cart/checkout";
    public static final String URL_GET_WALLET = URL_BASE + "/cart/discount";
    public static final String URL_CANCEL_RESERVATION = URL_BASE + "/cart/transact";
    public static final String URL_COLLECTIONS = URL_BASE + "/query/collections";

    /**
     * Texts *
     */
    public static final String TEXT_LOGOUT = "Storewalk logout";

    /**
     * Messages *
     */
    public static final String MSC_LOGOUT = "Logging out...!";

    public static final String FLURRY_KEY = "C6Z6336KQYX2BJBRFHD3"; //DO NOT MODIFY

    public static final String APPVIRALITY_KEY = "6ec0db29087e44e386c4a4a8005cf81d";
    public static final String EXPIRED_MESSAGE = "Your reservation has expired";
    public static final String DEFAULT_RESERVATION_MESSAGE = "Items are reserved till\n8.00 PM - tomorrow";
    public static final int STORE_BANNER = 1;
    /**
     * Discount *
     */
    public static final String COUPON_CODE = "SW15";
    public static int SHORTEST_DISTANCE = Integer.MAX_VALUE;
}