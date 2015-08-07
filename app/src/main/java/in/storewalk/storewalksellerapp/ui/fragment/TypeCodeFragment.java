package in.storewalk.storewalksellerapp.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.storewalk.storewalksellerapp.R;

/**
 * Created by Sathish Mun on 04-07-2015.
 */
public class TypeCodeFragment extends Fragment {


    public TypeCodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type_code, container, false);
    }


}
