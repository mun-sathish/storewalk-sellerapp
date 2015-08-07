package in.storewalk.storewalksellerapp.ui.fragment;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import in.storewalk.storewalksellerapp.R;
import in.storewalk.storewalksellerapp.ui.activities.BillingActivity;

import static in.storewalk.storewalksellerapp.R.id.layout_enter_name;
import static in.storewalk.storewalksellerapp.R.id.layout_enter_no;

/**
 * Created by Sathish Mun on 29-07-2015.
 */
public class EnterPhNoFragment extends android.support.v4.app.Fragment implements OnItemClickListener, OnItemSelectedListener, OnClickListener {

    // Store contacts values in these arraylist
    public static ArrayList<String> phoneValueArr = new ArrayList<String>();
    public static ArrayList<String> nameValueArr = new ArrayList<String>();

    //EditText enterNo;
    AutoCompleteTextView enterNo = null;
    EditText toNumber = null;
    String toNumberValue = "";
    EditText mContactName;
    LinearLayout enterNameLayout;
    RelativeLayout enterNoLayout;
    private ArrayAdapter<String> adapter;


    public EnterPhNoFragment() {
        // Required empty public constructor
    }

    // this function is used to add the contacts to their contact list providing the proper arguments
    public static void Insert2Contacts(Context ctx, String nameSurname,
                                       String telephone) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        int rawContactInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(
                        ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, telephone).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
                        rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nameSurname)
                .build());
        try {
            ContentProviderResult[] res = ctx.getContentResolver()
                    .applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {


        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.activity_enter_ph_no, container, false);


        // initialize the buttons
        Button one = (Button) layout.findViewById(R.id.one);
        Button two = (Button) layout.findViewById(R.id.two);
        Button three = (Button) layout.findViewById(R.id.three);
        Button four = (Button) layout.findViewById(R.id.four);
        Button five = (Button) layout.findViewById(R.id.five);
        Button six = (Button) layout.findViewById(R.id.six);
        Button seven = (Button) layout.findViewById(R.id.seven);
        Button eight = (Button) layout.findViewById(R.id.eight);
        Button nine = (Button) layout.findViewById(R.id.nine);
        Button zero = (Button) layout.findViewById(R.id.zero);
        Button clear = (Button) layout.findViewById(R.id.clear);
        Button refresh = (Button) layout.findViewById(R.id.refresh);
        Button done = (Button) layout.findViewById(R.id.done);
        Button add = (Button) layout.findViewById(R.id.add);
        Button backspace = (Button) layout.findViewById(R.id.backspace);
        Button butnAdd = (Button) layout.findViewById(R.id.button_add);

        // set up the onClickListener for each buttons
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        clear.setOnClickListener(this);
        refresh.setOnClickListener(this);
        done.setOnClickListener(this);
        add.setOnClickListener(this);
        backspace.setOnClickListener(this);
        butnAdd.setOnClickListener(this);


        // intialize the remaining elements in the layout
        enterNo = (AutoCompleteTextView) layout.findViewById(R.id.edit_EnterNo);
        enterNameLayout = (LinearLayout) layout.findViewById(layout_enter_name);
        enterNoLayout = (RelativeLayout) layout.findViewById(layout_enter_no);
        mContactName = (EditText) layout.findViewById(R.id.enterName);

        //initially the layout for entering the name is hidden from the view
        enterNameLayout.setVisibility(View.GONE);

        // setting the color for the dropdown menu that appears
        enterNo.setDropDownBackgroundResource(R.color.DimGray);

        //Create adapter
        adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());

        // setting the max value can be selected by the user which is 1
        enterNo.setThreshold(1);

        //Set adapter to AutoCompleteTextView
        enterNo.setAdapter(adapter);
        enterNo.setOnItemSelectedListener(this);
        enterNo.setOnItemClickListener(this);


        // Read contact data and add data to ArrayAdapter
        // ArrayAdapter used by AutoCompleteTextView
        readContactData();

        return layout;
    }


    private void readContactData() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        Context context = getActivity().getApplicationContext();
        Cursor people = context.getContentResolver()

                .query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        people.moveToFirst();
        do {
            String name = people.getString(indexName);
            String phoneNumber = people.getString(indexNumber);


            // Add contacts names to adapter
            adapter.add(name);

            // Add ArrayList names to adapter
            phoneValueArr.add(phoneNumber.toString());
            nameValueArr.add(name.toString());
            // Do work...
        } while (people.moveToNext());


    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // Get Array index value for selected name
        int i = nameValueArr.indexOf("" + arg0.getItemAtPosition(arg2));

        // If name exist in name ArrayList
        if (i >= 0) {

            // Get Phone Number
            toNumberValue = phoneValueArr.get(i);

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

            // Show Alert
            Toast.makeText(getActivity().getBaseContext(),
                    "Position:" + arg2 + " Name:" + arg0.getItemAtPosition(arg2) + " Number:" + toNumberValue,
                    Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }


    // setting up the onClick method to perform the respective operations to be performed by the buttons
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.one:
                enterNo.append("1");
                break;
            case R.id.two:
                enterNo.append("2");
                break;
            case R.id.three:
                enterNo.append("3");
                break;
            case R.id.four:
                enterNo.append("4");
                break;
            case R.id.five:
                enterNo.append("5");
                break;
            case R.id.six:
                enterNo.append("6");
                break;
            case R.id.seven:
                enterNo.append("7");
                break;
            case R.id.eight:
                enterNo.append("8");
                break;
            case R.id.nine:
                enterNo.append("9");
                break;
            case R.id.clear:
                enterNo.setText("");
                break;
            case R.id.zero:
                enterNo.append("0");
                break;
            case R.id.refresh:
                break;


            case R.id.done:
                Intent n = new Intent(getActivity().getApplicationContext(), BillingActivity.class);
                startActivity(n);
                break;

            case R.id.add:

                // checks whether the edittext is empty
                if (!enterNo.getText().toString().isEmpty()) {

                    // changing the name layout to be visible
                    if (enterNameLayout.getVisibility() == View.GONE)
                        enterNameLayout.setVisibility(View.VISIBLE);

                    // changing the number layout to be invisible
                    if (enterNoLayout.getVisibility() == View.VISIBLE)
                        enterNoLayout.setVisibility(View.GONE);

                    // clearing the contents of the edittext boxes
                    enterNo.setText("");
                    mContactName.setText("");

                    // popping up the soft keyboard for the user to type the name
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .showSoftInput(mContactName, InputMethodManager.SHOW_FORCED);
                } else {

                    Toast.makeText(getActivity().getBaseContext(), "Enter a number", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.backspace:
                enterNo.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                break;

            case R.id.button_add:
                String name = mContactName.getText().toString();
                String phone = enterNo.getText().toString();

                if (!name.isEmpty()) {
                    Insert2Contacts(getActivity().getApplicationContext(), name, phone);

                    Toast.makeText(getActivity().getBaseContext(), "Contacts added successfully!!!", Toast.LENGTH_LONG).show();


                    // changing the respective layouts to be visible or invisible
                    if (enterNameLayout.getVisibility() == View.VISIBLE)
                        enterNameLayout.setVisibility(View.GONE);
                    if (enterNoLayout.getVisibility() == View.GONE)
                        enterNoLayout.setVisibility(View.VISIBLE);

                    // hiding the soft keyboard from the user
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    // clearing the contents in the edittext
                    mContactName.setText("");

                    // jumping to the next activity
                    Intent t = new Intent(getActivity().getApplicationContext(), BillingActivity.class);
                    startActivity(t);

                } else {
                    Toast.makeText(getActivity().getBaseContext(), "Enter a name", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
