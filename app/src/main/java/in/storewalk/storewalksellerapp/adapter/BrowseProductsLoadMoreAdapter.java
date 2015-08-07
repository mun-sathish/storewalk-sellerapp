package in.storewalk.storewalksellerapp.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import in.storewalk.storewalksellerapp.R;
import in.storewalk.storewalksellerapp.dto.BrowseProductDTO;
import in.storewalk.storewalksellerapp.network.AppController;
import in.storewalk.storewalksellerapp.ui.widget.FadeInNetworkImageView;


/**
 * Created by Sathish Mun on 04-07-2015.
 */
public class BrowseProductsLoadMoreAdapter extends UltimateViewAdapter {
    Activity activity;

    // list for all kinds of filter needed
    private List<BrowseProductDTO> productItems, productItemsAvailable, productItemsNotAvailable;

    BrowseProductDTO itmDetail;
    int available = 2;

    // initialize the adapter
    public BrowseProductsLoadMoreAdapter(Activity activity, List<BrowseProductDTO> productItems, List<BrowseProductDTO> productItemsAvailable, List<BrowseProductDTO> productItemsNotAvailable) {
        this.activity = activity;
        this.productItems = productItems;
        this.productItemsAvailable = productItemsAvailable;
        this.productItemsNotAvailable = productItemsNotAvailable;


    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_browse_product, parent, false);
        return new ViewHolder(v);
    }


    //get count of the item
    @Override
    public int getAdapterItemCount() {
        if (available == 2) {
            return productItems.size();
        } else if (available == 1) {
            return productItemsAvailable.size();
        } else {
            return productItemsNotAvailable.size();
        }
    }


    @Override
    public long generateHeaderId(int position) {
        if (position == 0) {
            return position;
        } else {
            return -1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ((customHeaderView != null ? position <= getAdapterItemCount() : position < getAdapterItemCount())
                && (customHeaderView == null || position > 0)) {
            try {

                ImageLoader imageLoader = AppController.getInstance().getImageLoader();

                if (available == 2) {
                    itmDetail = productItems.get(position - 1);
                } else if (available == 1) {
                    itmDetail = productItemsAvailable.get(position - 1);
                } else {
                    itmDetail = productItemsNotAvailable.get(position - 1);
                }


                // thumbnail image

                ((ViewHolder) holder).firstProduct.setImageUrl(itmDetail.getFirstItemInfo().getIconImageUrl().get(0), imageLoader);
                ((ViewHolder) holder).secondProduct.setImageUrl(itmDetail.getSecondItemInfo().getIconImageUrl().get(0), imageLoader);
                if (itmDetail.getFirstItemInfo().getAvailable()) {
                    ((ViewHolder) holder).firstProductBrand.setText(itmDetail.getFirstItemInfo().getBrand());
                } else {
                    SpannableStringBuilder sb = new SpannableStringBuilder(itmDetail.getFirstItemInfo().getBrand() + " (Out of Stock)");
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 0, 0));
                    sb.setSpan(fcs, itmDetail.getFirstItemInfo().getBrand().length(), itmDetail.getFirstItemInfo().getBrand().length() + 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    ((ViewHolder) holder).firstProductBrand.setText(sb);
                }
                if (itmDetail.getSecondItemInfo().getAvailable()) {
                    ((ViewHolder) holder).secondProductBrand.setText(itmDetail.getSecondItemInfo().getBrand());
                } else {
                    SpannableStringBuilder sb = new SpannableStringBuilder(itmDetail.getSecondItemInfo().getBrand() + " (Out of Stock)");
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 0, 0));
                    sb.setSpan(fcs, itmDetail.getSecondItemInfo().getBrand().length(), itmDetail.getSecondItemInfo().getBrand().length() + 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    ((ViewHolder) holder).secondProductBrand.setText(sb);
                }
                ((ViewHolder) holder).firstProductPrice.setText("\u20B9 " + Integer.toString(itmDetail.getFirstItemInfo().getPrice()).replace("//0", ""));
                ((ViewHolder) holder).secondProductPrice.setText("\u20B9 " + Integer.toString(itmDetail.getSecondItemInfo().getPrice()).replace("//.0", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_browse_product, parent, false);
        return new RecyclerView.ViewHolder(v) {
        };
    }


    @Override
    public <T> void insert(List<T> list, T object, int position) {
        list.add(object);
        notifyItemInserted(position);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    // checking which filter is pressed by the user to display it accordingly
    public void availableData(int data) {
        if (data == 2) {
            available = 2;
        } else if (data == 1) {
            available = 1;
        } else if (data == 0) {
            available = 0;
        }
    }

    class ViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener {
        FadeInNetworkImageView firstProduct;
        FadeInNetworkImageView secondProduct;
        TextView firstProductPrice, secondProductPrice;
        TextView firstProductBrand, secondProductBrand;


        //intialize the custom layout
        public ViewHolder(View itemView) {
            super(itemView);
            firstProduct = (FadeInNetworkImageView)
                    itemView.findViewById(R.id.first_product_image);
            secondProduct = (FadeInNetworkImageView) itemView
                    .findViewById(R.id.second_product_image);
            firstProductPrice = (TextView) itemView.findViewById(R.id.first_product_price);
            secondProductPrice = (TextView) itemView.findViewById(R.id.second_product_price);
            firstProductBrand = (TextView) itemView.findViewById(R.id.first_product_brand);
            secondProductBrand = (TextView) itemView.findViewById(R.id.second_product_brand);
            firstProduct.setOnClickListener(this);
            secondProduct.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // CODE TO WRITE WHEN ITEM IS CLICKED

        }
    }


}

