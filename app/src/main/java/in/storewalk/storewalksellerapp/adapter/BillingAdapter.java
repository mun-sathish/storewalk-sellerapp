package in.storewalk.storewalksellerapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import in.storewalk.storewalksellerapp.R;

/**
 * Created by Sathish Mun on 11-07-2015.
 */
public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.Holder> {

    // array list to store the product and its price
    private ArrayList<String> mListData = new ArrayList<>();
    private ArrayList<String> mListPrice = new ArrayList<>();

    // array list to check for the row whether it is editable or non-editable by the user
    private ArrayList<Boolean> mListStates = new ArrayList<>();


    onBillingAdapterListener billingAdapterListener;
    View row;
    private LayoutInflater mLayoutInflater;
    int mSendPrice = 0;


    // setting up the constructor of the adapter
    public BillingAdapter(Context context, onBillingAdapterListener billingAdapterListener) {
        this.billingAdapterListener = billingAdapterListener;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        row = mLayoutInflater.inflate(R.layout.custom_row_item_animations, parent, false);

        // setting up holder
        Holder holder = new Holder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final String data = mListData.get(position).toString();
        final String price = mListPrice.get(position).toString();

        holder.textDataItem.setText(data);
        holder.priceDataItem.setText(price);


        // checks whether the row should be editable or non-editable by the user
        if (mListStates.get(position)) {
            enableRow(holder);
        } else {
            disableRow(holder);
        }


        //dynamic changing of the total price
        sendPriceData();

        //set up onCLick on Buttons
        holder.buttonAdd.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // to set the price value and also disabling the row
                        String addText = holder.textDataItem.getText().toString();
                        String addVal = holder.priceDataItem.getText().toString();
                        if (!addText.isEmpty() && !addVal.isEmpty()) {
                            mListData.set(position, addText);
                            mListPrice.set(position, addVal);
                            mSendPrice = mSendPrice + Integer.parseInt(addVal);

                            sendPriceData();

                            mListStates.set(position, false);
                            disableRow(holder);


                        }

                        // to show the user that the field is empty
                        else {
//                            holder.textDataItem.setError(null);
//                            holder.priceDataItem.setError(null);
//                            if (holder.textDataItem.getText().toString().trim().equals(""))
//                                holder.textDataItem.setError("Enter Valid Product");
//
//                            if (holder.priceDataItem.getText().toString().trim().equals(""))
//                                holder.priceDataItem.setError("Enter Valid Price");
                        }

                    }
                });


        holder.buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //checking whether the particular which is needed by the user is clicked
                if (view.getId() == holder.buttonReset.getId()) {

                    // setting that the row should be editable by the user at that position
                    mListStates.set(position, true);
                    enableRow(holder);

                    // removing the row at the respective position
                    removeItem(position);

                    // subtracting the price from the total amount if its present
                    String delText = holder.textDataItem.getText().toString();
                    String delVal = holder.priceDataItem.getText().toString();
                    if (!delVal.isEmpty() && mSendPrice != 0 && !delText.isEmpty()) {
                        mSendPrice = mSendPrice - Integer.parseInt(delVal);
                        sendPriceData();
                    }
                }

            }
        });

    }

    // to make the row non - editable by the user
    public void disableRow(Holder holderDisable) {
        holderDisable.textDataItem.setEnabled(false);
        holderDisable.priceDataItem.setEnabled(false);
        holderDisable.buttonAdd.setEnabled(false);
        holderDisable.buttonAdd.setAlpha(40);
        holderDisable.buttonAdd.bringToFront();
    }

    //to make the row editable by the user
    public void enableRow(Holder holderEnable) {
        holderEnable.textDataItem.setEnabled(true);
        holderEnable.priceDataItem.setEnabled(true);
        holderEnable.buttonAdd.setEnabled(true);
        holderEnable.buttonAdd.setAlpha(255);
        holderEnable.buttonAdd.bringToFront();
    }

    //to add the item to the list and also the price value if its there by default(i.e like scanning QRcode)
    public void addItem(String product, String price) {
        mListData.add(product);
        mListPrice.add(price);

        int priceValue;
        if (price == "") {
            priceValue = 0;
            mListStates.add(true);
        } else {
            priceValue = Integer.parseInt(price);
            mListStates.add(false);
        }

        mSendPrice = mSendPrice + priceValue;
        notifyItemInserted(getItemCount());
        notifyDataSetChanged();


    }

    public void removeItem(String product, String price) {
        int position = mListData.indexOf(product);
        if (position != -1) {
            mListData.remove(product);
            mListPrice.remove(price);

            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        if (position != -1) {

            // removing data from the list
            mListData.remove(position);
            mListPrice.remove(position);
            mListStates.remove(position);


            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
    }

    //getting the size of the row
    @Override
    public int getItemCount() {

        return mListPrice.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        EditText textDataItem;
        ImageButton buttonAdd;
        ImageButton buttonReset;
        EditText priceDataItem;

        public Holder(View itemView) {
            super(itemView);

            //initialize the elements of the adapter
            textDataItem = (EditText) itemView.findViewById(R.id.text_item);
            buttonAdd = (ImageButton) itemView.findViewById(R.id.button_add);
            priceDataItem = (EditText) itemView.findViewById(R.id.price_item);
            buttonReset = (ImageButton) itemView.findViewById(R.id.button_reset);


        }
    }


    //set up interface
    public interface onBillingAdapterListener {
        public void totalPriceData(int data);
    }

    //function to send the total price amount to the billing activity
    public void sendPriceData() { //    billing.getTotal(mSendPrice);
        billingAdapterListener.totalPriceData(mSendPrice);
    }


}
