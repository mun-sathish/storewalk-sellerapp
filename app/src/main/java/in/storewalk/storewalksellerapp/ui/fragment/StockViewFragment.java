package in.storewalk.storewalksellerapp.ui.fragment;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.storewalk.storewalksellerapp.R;
import in.storewalk.storewalksellerapp.adapter.BrowseProductsLoadMoreAdapter;
import in.storewalk.storewalksellerapp.dto.BrowseProductDTO;
import in.storewalk.storewalksellerapp.network.AppController;
import in.storewalk.storewalksellerapp.util.JSONListConverterUtil;
import in.storewalk.storewalksellerapp.util.ProgressDialogUtil;


/**
 * Created by Sathish Mun on 04-07-2015.
 */
public class StockViewFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    public static final String URL_BASE = "http://52.74.35.166:1123/storewalk";
    public static final String ERROR_JSON_PARSER = "Error! Please try later.";
    public static final String ERROR_SERVER = "Unable to process your request,please try again later";
    private static final String TAG = StockViewFragment.class.getSimpleName();
    private static JSONObject param_params = null;
    private final int LOAD_THRESHOLD = 20;
    JSONObject params = null;
    TextView oops_text;
    ImageView oops_image;
    RelativeLayout oops_layout;
    boolean state_filter = false;
    LinearLayout linearLayout;
    LinearLayout.LayoutParams filter_params;
    View layout;
    RelativeLayout filterBtn;
    RadioGroup rg;
    RadioButton all, inStock, outOfStock;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolBar;
    private Button filterButton;
    private LinearLayout row;
    private TextView loadMore;
    private UltimateRecyclerView recyclerView;
    private BrowseProductsLoadMoreAdapter adapter;
    private SharedPreferences preference;
    private Button cartItemCountButton;
    private Intent filterIntent;
    private List<BrowseProductDTO> itemDetailsList = new ArrayList<>();
    private List<BrowseProductDTO> itemDetailsListAvailable = new ArrayList<>();
    private List<BrowseProductDTO> itemdetailslistNotAvailable = new ArrayList<>();
    private String queryValue;
    private Dialog dialog = null;


    public StockViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        filterIntent = null;


//        JSONObject jsonObject;
//        try {
//            //              if (Json_parsing.param_params == null) {
//            jsonObject = new JSONObject();
//            jsonObject.put("available", "1");
//
//            Instock_Fragment.param_params = jsonObject;
//            //            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        ProgressDialogUtil.showDialog(getActivity(), "Great Products... a moment away!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.stock_view_activity, container, false);


        oops_text = (TextView) layout.findViewById(R.id.oops_text);
        oops_image = (ImageView) layout.findViewById(R.id.oops_image);
        oops_layout = (RelativeLayout) layout.findViewById(R.id.oops_layout);
        row = (LinearLayout) layout.findViewById(R.id.filter_btn_row);
        row.setOnClickListener(this);

        linearLayout = (LinearLayout) layout.findViewById(R.id.show_fragment);
        filter_params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        linearLayout.setVisibility(View.GONE);

        Button filterBtn = (Button) layout.findViewById(R.id.filter_btn);
        filterBtn.setOnClickListener(this);


        initRecyclerView();

        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {               //gets triggered when it is swiped down
                if (itemDetailsList.isEmpty() || itemDetailsListAvailable.isEmpty() || itemdetailslistNotAvailable.isEmpty()) {

                    populateJson();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        params = getParams(0, LOAD_THRESHOLD);
        populateJson();
        return layout;
    }

    private void initRecyclerView() {   //set recycler view

        recyclerView = (UltimateRecyclerView) layout.findViewById(R.id.listView_products);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);
        adapter = new BrowseProductsLoadMoreAdapter(getActivity(), itemDetailsList, itemDetailsListAvailable, itemdetailslistNotAvailable);
        //set adapter
        recyclerView.setAdapter(adapter);
        //custom header view
        //   View header = LayoutInflater.from(getActivity())
        //           .inflate(R.layout.list_row_custom_header, null);
        UltimateRecyclerView.CustomRelativeWrapper wrapper = new UltimateRecyclerView.CustomRelativeWrapper(getActivity().getApplicationContext());
        //   wrapper.addView(header);
        adapter.setCustomHeaderView(wrapper);
        recyclerView.enableLoadmore();
        //custom progressbar
        adapter.setCustomLoadMoreView(LayoutInflater.from(getActivity())
                .inflate(R.layout.custom_bottom_progressbar, null));
        loadMore = (TextView) adapter.getCustomLoadMoreView().findViewById(R.id.load_more);
        recyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(final int i, final int lastRow) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (lastRow > 1) {
                            //Set params
                            int pos = 2 * (lastRow - 1);
                            params = getParams(pos, LOAD_THRESHOLD);
                            populateJson();
                        }
                    }
                }, 1000);
            }
        });

        //show or hide views on scroll
        recyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {


            }

            @Override
            public void onDownMotionEvent() {
            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {

                if (observableScrollState == ObservableScrollState.DOWN) {

                    showViews();
                } else if (observableScrollState == ObservableScrollState.UP) {
                    hideViews();


                } else if (observableScrollState == ObservableScrollState.STOP) {

                }
            }
        });
/*
        if (swipeRefreshLayout.isRefreshing()) {   //after been refreshed.. it is set to false.. i.e stopped
            swipeRefreshLayout.setRefreshing(false);
        }
*/

    }

    private void hideViews() {
        //   toolBar.animate().translationY(-toolBar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        //  recyclerView.hideToolbar(toolBar, recyclerView, getScreenHeight());
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) row.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        row.animate().translationY(row.getHeight() + fabBottomMargin).setInterpolator(
                new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        //recyclerView.showToolbar(toolBar, recyclerView, getScreenHeight());
        //  toolBar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        row.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    private JSONObject getParams(int from, int size) {
        if (StockViewFragment.param_params == null) {
            StockViewFragment.param_params = new JSONObject();
        }
        JSONObject params = new JSONObject();
        try {
/*            if (queryValue != null) {
                params.put("query", queryValue);
            } else
 */
            params.put("query", "");
            params.put("param", StockViewFragment.param_params);
            params.put("token", this.getString(R.string.developer_token));
            params.put("from", from);
            params.put("size", size);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return params;
    }

    private void populateJson() {

        // Creating volley request obj
        JsonObjectRequest getItemDetailsReq = new JsonObjectRequest(Request.Method.POST,
                URL_BASE + "/query", params,
                new Response.Listener<JSONObject>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.e(TAG, response.toString());
                        oops_layout.setBackgroundColor(getResources().getColor(R.color.Transparent));

                        oops_text.setText("");
                        oops_image.setImageDrawable(null);
                        ProgressDialogUtil.hidePDialog();
                        // Parsing json
                        //TO DO make it work for just one JSON result
                        try {
                            JSONObject hitsJSON = response.getJSONObject("hits");
                            JSONArray hitsArray = hitsJSON.getJSONArray("hits");


                            if (hitsArray.length() % 2 == 1) {
                                hitsArray.put(hitsArray.get(0));
                            }

                            if (hitsArray.length() < LOAD_THRESHOLD) {
                                recyclerView.disableLoadmore();
                            }

                            int j = 0;

                            //parse all incoming results of the query
                            for (int i = 0; i < hitsArray.length() - 1; ) {

                                JSONObject singleObj1 = hitsArray.getJSONObject(i);
                                JSONObject singleObj2 = hitsArray.getJSONObject(i + 1);

                                JSONObject sourceObj1 = singleObj1.getJSONObject("_source");
                                JSONObject sourceObj2 = singleObj2.getJSONObject("_source");

                                BrowseProductDTO details = new BrowseProductDTO();

                                //get images
                                if (sourceObj1.has("icons") && sourceObj2.has("icons")) {
                                    JSONArray imageArray = sourceObj1.getJSONArray("icons");
                                    JSONArray imageArray2 = sourceObj2.getJSONArray("icons");
                                    List<String> imageUrlArray1 = JSONListConverterUtil.toStringList(imageArray);
                                    List<String> imageUrlArray2 = JSONListConverterUtil.toStringList(imageArray2);
                                    details.getFirstItemInfo().setIconImageUrl(imageUrlArray1);
                                    details.getSecondItemInfo().setIconImageUrl(imageUrlArray2);
                                }

                                //availability
                                if (sourceObj1.has("available") && sourceObj2.has("available")) {
                                    details.getFirstItemInfo().setAvailable(sourceObj1.getInt("available"));
                                    details.getSecondItemInfo().setAvailable(sourceObj2.getInt("available"));
                                }

                                //city
                                if (sourceObj1.has("city") && sourceObj2.has("city")) {
                                    details.getFirstItemInfo().setCity(sourceObj1.getString("city"));
                                    details.getSecondItemInfo().setCity(sourceObj2.getString("city"));
                                }

                                //price
                                if (sourceObj1.has("price") && sourceObj2.has("price")) {

                                    details.getFirstItemInfo().setPrice(sourceObj1.getInt("price"));
                                    details.getSecondItemInfo().setPrice(sourceObj2.getInt("price"));
                                }

                                if (sourceObj1.has("old_price") && sourceObj2.has("old_price")) {
                                    details.getFirstItemInfo().setOldPrice(sourceObj1.getInt("old_price"));
                                    details.getSecondItemInfo().setOldPrice(sourceObj2.getInt("old_price"));
                                }

                                //brand
                                if (sourceObj1.has("brand") && sourceObj2.has("brand")) {
                                    details.getFirstItemInfo().setBrand(sourceObj1.getString("brand"));
                                    details.getSecondItemInfo().setBrand(sourceObj2.getString("brand"));
                                }

                                //locality
                                if (sourceObj1.has("locality") && sourceObj2.has("locality")) {
                                    details.getFirstItemInfo().setLocality(sourceObj1.getString("locality"));
                                    details.getSecondItemInfo().setLocality(sourceObj2.getString("locality"));
                                }

                                //itemcode
                                if (sourceObj1.has("itemCode") && sourceObj2.has("itemCode")) {
                                    details.getFirstItemInfo().setItemCode(sourceObj1.getString("itemCode"));
                                    details.getSecondItemInfo().setItemCode(sourceObj2.getString("itemCode"));
                                }


                                // adding item details to to itemDetails array
                                if (params.getInt("from") == 0) {
                                    itemDetailsList.add(details);
                                    if (sourceObj1.getInt("available") == 1 && sourceObj2.getInt("available") == 1) {
                                        itemDetailsListAvailable.add(details);
                                    } else {
                                        itemdetailslistNotAvailable.add(details);
                                    }
                                    adapter.notifyDataSetChanged();
                                } else {
                                    adapter.insert(itemDetailsList, details, params.getInt("from") + (j++));
                                    if (sourceObj1.getInt("available") == 1 && sourceObj2.getInt("available") == 1) {
                                        adapter.insert(itemDetailsListAvailable, details, params.getInt("from") + (j++));
                                    } else {
                                        adapter.insert(itemdetailslistNotAvailable, details, params.getInt("from") + (j++));
                                    }

                                }
                                i = i + 2;
                            } //end for loop

                        } catch (JSONException error) {
                            hideViews();
                            adapter.getCustomLoadMoreView().findViewById(R.id.bottom_progress_bar).setVisibility(View.GONE);
                            loadMore.setText(ERROR_JSON_PARSER);
                          /*  if(BuildConfig.DEBUG) {
                                ToastMakerUtil.display(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                            }else {
                                ToastMakerUtil.display(getApplicationContext(), ERROR_JSON_PARSER, Toast.LENGTH_LONG);
                            }
                      */
                        }

                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);


                Toast.makeText(getActivity().getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_LONG).show();
                oops_layout.setBackgroundColor(getResources().getColor(R.color.White));
                //      recyclerView.setLayoutManager(null);
                oops_image.setImageResource(R.drawable.oops);
                oops_text.setText("There is a connection problem!\n\t\t\t\tPlease try again later!");

                hideViews();
                // adapter.getCustomLoadMoreView().findViewById(R.id.bottom_progress_bar).setVisibility(View.GONE);
                loadMore.setText(ERROR_SERVER);
                loadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.getCustomLoadMoreView().findViewById(R.id.bottom_progress_bar).
                                setVisibility(View.VISIBLE);
                        loadMore.setText("");
                        populateJson();
                    }
                });
           /*     if(BuildConfig.DEBUG) {
                    ToastMakerUtil.display(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                }else {
                    ToastMakerUtil.display(getApplicationContext(), ERROR_SERVER, Toast.LENGTH_LONG);
                }
           */
                Log.e(TAG, "Error: " + error.getMessage());
                ProgressDialogUtil.hidePDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getItemDetailsReq);


    }

    public void filter_layout() {


        //itemSizes = itemDetailsDTO.getSizes();

        if (dialog == null) {
            dialog = new Dialog(getActivity(), R.style.DialogSlideAnim);
        }
        dialog.setContentView(R.layout.fragment_filter);
        initDialogView();


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        lp.dimAmount = 0.7f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }

    private void initDialogView() {

        dialog.setCanceledOnTouchOutside(true);

        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterBtn = (RelativeLayout) dialog.findViewById(R.id.filter_btn_row);
        rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

        all = (RadioButton) dialog.findViewById(R.id.radioButton_All);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.availableData(2);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        inStock = (RadioButton) dialog.findViewById(R.id.radioButton_Instock);
        inStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.availableData(1);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });


        outOfStock = (RadioButton) dialog.findViewById(R.id.radioButton_Outofstock);
        outOfStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.availableData(0);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.filter_btn:
                filter_layout();
                break;
        }
    }
/*
    @Override
    public void onRefresh() {   //gets triggered when actiivty is swiped down

        initRecyclerView();
        params = getParams(0, LOAD_THRESHOLD);
        populateJson();
    }

*/
}
