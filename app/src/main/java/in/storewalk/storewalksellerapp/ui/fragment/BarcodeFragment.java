package in.storewalk.storewalksellerapp.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Sathish Mun on 04-07-2015.
 */

// This Scanner is the simple scanner which scans and gives the result value under handleResult(Result result) function
public class BarcodeFragment extends Fragment implements ZXingScannerView.ResultHandler {

    public String resultBarcode = "";
    String resultFormat = "";
    onBarcodeListener barcodeListener;
    private ZXingScannerView mScannerView;


    public BarcodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mScannerView.stopCamera();
    }


    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();               // Stop camera on pause
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mScannerView = new ZXingScannerView(getActivity());

        Boolean state = this.getArguments().getBoolean("switching");
        if (!state)
            mScannerView.stopCamera();


        View layout = mScannerView;
        return layout;
    }


    @Override
    public void handleResult(Result result) {
        mScannerView.startCamera();
        resultBarcode = result.getText();
        resultFormat = result.getBarcodeFormat().toString();
        barcodeListener.barcodeData(resultBarcode);
        mScannerView.stopCamera();
        Thread t = new Thread() {

            public void run() {
                try {
                    Thread.sleep(500);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        mScannerView.startCamera();

    }

    // to send data between barcode fragment and BillingActivity activity

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            barcodeListener = (onBarcodeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onBarcodetListener");
        }
    }


    // set up interface
    public interface onBarcodeListener {
        public void barcodeData(String data);

    }
}
