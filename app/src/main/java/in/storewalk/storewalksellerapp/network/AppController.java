/**
 * StoreWalk Android App
 * Copy Right (c) LAB Desk Technologies Inc.
 * Author: Ateendra Singh
 * Created: January, 2014
 * Version: 0.1.0
 * Description:
 */

package in.storewalk.storewalksellerapp.network;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import in.storewalk.storewalksellerapp.util.LruBitmapCache;
//import com.crashlytics.android.Crashlytics;
//import com.flurry.android.FlurryAgent;
//import in.labdesk.storewalk.BuildConfig;
//import in.labdesk.storewalk.util.StoreWalkConst;
//import io.fabric.sdk.android.Fabric;

/**
 * have one centralized place for your Queue, and the best place to
 * initialize queue is in your Application class
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    /**
     * A singleton instance of the application class for easy access in other places
     */

    private static AppController mInstance;
    /**
     * Global request queue for Volley
     */

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    /**
     * @return ApplicationController singleton instance
     */


    public static synchronized AppController getInstance() {

        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /**work with crash analytics only in release mode*/
   /*     Crashlytics crashlytics = new Crashlytics.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, crashlytics);

        // configure Flurry
        if(!BuildConfig.DEBUG) {

            FlurryAgent.setLogEnabled(true);
            FlurryAgent.setLogEvents(true);
            FlurryAgent.setLogLevel(Log.VERBOSE);

            // init Flurry
            FlurryAgent.init(this, StoreWalkConst.FLURRY_KEY);

//            //app virality
//            AppviralityAPI.init(this);
        }

        // initialize the singleton
   */
        mInstance = this;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */

    public RequestQueue getRequestQueue() {

        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        //TO DO: add log

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */


    public <T> void addToRequestQueue(Request<T> req) {

        // set the default tag if tag is empty
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
