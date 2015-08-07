package in.storewalk.storewalksellerapp.ui.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import in.storewalk.storewalksellerapp.R;
import in.storewalk.storewalksellerapp.adapter.BillingAdapter;
import in.storewalk.storewalksellerapp.adapter.BillingAdapter.onBillingAdapterListener;
import in.storewalk.storewalksellerapp.ui.fragment.BarcodeFragment;
import in.storewalk.storewalksellerapp.ui.fragment.BarcodeFragment.onBarcodeListener;
import in.storewalk.storewalksellerapp.ui.fragment.TypeCodeFragment;


/**
 * Created by Sathish Mun on 04-07-2015.
 */

public class BillingActivity extends AppCompatActivity implements onBarcodeListener, onBillingAdapterListener {

    // to check the two buttons in the bottom whether it is opened or closed
    boolean stateScan = false;
    boolean stateType = false;

    LinearLayout linearLayout;
    TextView mTotal;
    LinearLayout.LayoutParams params;
    String result;
    String mProductData;
    String mProductPrice;
    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private BillingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set content view
        setContentView(R.layout.activity_billing);

        // set up toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //initialize the variables
        linearLayout = (LinearLayout) findViewById(R.id.show_fragment);
        params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        linearLayout.setVisibility(View.GONE);
        mTotal = (TextView) findViewById(R.id.price_total);

        //getting the result from the barcode scanner
        BarcodeFragment barcodeFragment = new BarcodeFragment();
        result = barcodeFragment.resultBarcode;

        //set Views
        initViews();

        //mAdapter.addItem("first", 20000);
        mTotal.setText("0");
    }

    // initialize all the views
    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerItems);
        mAdapter = new BillingAdapter(BillingActivity.this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void addItem(View view) {
        //check if the EditText has valid contents
        mProductData = "";
        mProductPrice = "";
        mAdapter.addItem(mProductData, mProductPrice);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_view__main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // when scan button is clicked
    public void scan(View v) {

        //initialize the fragment and the manager
        BarcodeFragment frag = new BarcodeFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // initialize the bundle to send data to scanner to switch off the camera
        Bundle bundle = new Bundle();

        // to check the whether the scanner is opened or  closed
        if (linearLayout.getVisibility() != View.VISIBLE || stateType) {

            // changing the state
            stateScan = true;
            stateType = false;

            // setting the layout to be visible
            linearLayout.setVisibility(View.VISIBLE);
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            linearLayout.setLayoutParams(params);

            // sending data to switch on the camera
            bundle.putBoolean("switching", true);
            frag.setArguments(bundle);


            ft.replace(R.id.show_fragment, frag, "fragment");
            ft.commit();

        } else {
            if (frag != null) {

                // changing the state
                stateScan = false;

                // closing the fragment
                linearLayout.setVisibility(View.GONE);
                linearLayout.setLayoutParams(params);

                // sending the data to the scanner to switch off the camera
                bundle.putBoolean("switching", false);
                frag.setArguments(bundle);

                ft.replace(R.id.show_fragment, frag, "fragment");
                ft.commit();
            }
        }

    }

    // when enter code button is clicked
    public void type(View v) {

        // setting up the fragment
        TypeCodeFragment frag = new TypeCodeFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        // checking the status whether the button is opened or closed
        if (linearLayout.getVisibility() != View.VISIBLE || stateScan) {

            // changing the state
            stateType = true;
            stateScan = false;

            // setting the layout visible to the user
            linearLayout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            params.height = 250;
            linearLayout.setLayoutParams(params);

            ft.replace(R.id.show_fragment, frag, "fragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();

        } else {
            if (frag != null) {

                // changing the state
                stateType = false;

                // setting the layout invisible to the user
                linearLayout.setVisibility(View.GONE);
                params.height = 0;
                linearLayout.setLayoutParams(params);

                ft.remove(frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

            }
        }
    }


    //setting up interface calls
    @Override
    public void barcodeData(String data) {
        mAdapter.addItem(data, "1000");
        Toast.makeText(getBaseContext(), "Item Added Successflly", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void totalPriceData(int data) {
        mTotal.setText(String.valueOf(data));

    }
}
