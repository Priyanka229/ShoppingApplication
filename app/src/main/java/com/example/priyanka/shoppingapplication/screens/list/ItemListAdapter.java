package com.example.priyanka.shoppingapplication.screens.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.priyanka.shoppingapplication.R;
import com.example.priyanka.shoppingapplication.models.ShoppingItem;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<ShoppingItem> mDataList;
    private ItemListAdapterCallbacks mCallback;


    public ItemListAdapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setDataList(List<ShoppingItem> list) {
        this.mDataList = list;
    }

    @Override
    public ItemListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemListViewHolder(mLayoutInflater.inflate(R.layout.adapter_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemListViewHolder holder, int position) {
        holder.bindData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    class ItemListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mItemImageIv;
        private TextView mTitleTv;
        private TextView mRatingTv;
        private TextView mPriceTv;
        private TextView mExclusiveOfferAvailableTv;


        public ItemListViewHolder(View itemView) {
            super(itemView);

            mItemImageIv = itemView.findViewById(R.id.image_iv);
            mTitleTv = itemView.findViewById(R.id.title_tv);
            mRatingTv = itemView.findViewById(R.id.rating_tv);
            mPriceTv = itemView.findViewById(R.id.price_tv);
            mExclusiveOfferAvailableTv = itemView.findViewById(R.id.exclusive_available_tv);
        }

        public void bindData(ShoppingItem shoppingItem) {
            if (shoppingItem != null) {
                // set image
                if (!TextUtils.isEmpty(shoppingItem.getImgUrl())) {
                    Glide.with(itemView.getContext())
                            .load(shoppingItem.getImgUrl())
                            .into(mItemImageIv);
                }

                // set name
                mTitleTv.setText(shoppingItem.getTitle());

                // set rating
                mRatingTv.setText(shoppingItem.getRating());

                // set price
                mPriceTv.setText(shoppingItem.getPrice());

                // set exclusive offer text
                mExclusiveOfferAvailableTv.setText(shoppingItem.getExchangeText());

                // set on click
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            if (mCallback != null) {
                mCallback.onItemClick(getAdapterPosition());
            }
        }
    }

    public interface ItemListAdapterCallbacks {
        void onItemClick(int position);
    }

    public void setCallback(ItemListAdapterCallbacks callback) {
        this.mCallback = callback;
    }
}
