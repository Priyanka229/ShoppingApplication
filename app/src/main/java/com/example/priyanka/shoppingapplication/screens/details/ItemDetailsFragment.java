package com.example.priyanka.shoppingapplication.screens.details;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.priyanka.shoppingapplication.MainActivity;
import com.example.priyanka.shoppingapplication.R;
import com.example.priyanka.shoppingapplication.db.DBHelper;
import com.example.priyanka.shoppingapplication.models.OrderedItem;
import com.example.priyanka.shoppingapplication.models.ShoppingItem;


public class ItemDetailsFragment extends Fragment implements View.OnClickListener {
    private ShoppingItem mShoppingItem;

    private Handler mNotificationHandler;
    private Runnable mNotificationRunnable;

    public ItemDetailsFragment() {
        mNotificationHandler = new Handler();
        mNotificationRunnable = new Runnable() {
            @Override
            public void run() {
                if (getView() == null || getActivity() == null) return;

                hideLoader();
                showNotification();
                saveOrderIntoDataBase();
            }
        };
    }

    public void setShoppingItemData(ShoppingItem shoppingItemData) {
        this.mShoppingItem = shoppingItemData;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set image
        ImageView itemImageView = view.findViewById(R.id.image_iv);
        if (!TextUtils.isEmpty(mShoppingItem.getImgUrl())) {
            Glide.with(getActivity())
                    .load(mShoppingItem.getImgUrl())
                    .into(itemImageView);
        }

        // set title
        TextView titleTv = view.findViewById(R.id.title_tv);
        titleTv.setText(mShoppingItem.getTitle());

        // set rating
        TextView ratingTv = view.findViewById(R.id.rating_tv);
        ratingTv.setText(mShoppingItem.getRating());

        // set price
        TextView priceTv = view.findViewById(R.id.price_tv);
        priceTv.setText(mShoppingItem.getPrice());

        // add offers
        LinearLayout offerTextWrapper = view.findViewById(R.id.offer_text_wrapper);
        offerTextWrapper.removeAllViews();
        for (int i = 0; i < mShoppingItem.getOfferList().size(); i++) {
            String offerText = mShoppingItem.getOfferList().get(i);
            View offerView = LayoutInflater.from(getActivity()).inflate(R.layout.offer_text_item, null);

            if (!TextUtils.isEmpty(offerText) && offerView != null) {
                TextView offerTv = offerView.findViewById(R.id.offer_text_tv);
                offerTv.setText(offerText);

                offerTextWrapper.addView(offerView);
            }
        }

        // add offers
        LinearLayout descriptionTextWrapper = view.findViewById(R.id.description_text_wrapper);
        descriptionTextWrapper.removeAllViews();
        for (int i = 0; i < mShoppingItem.getDetailsList().size(); i++) {
            String offerText = mShoppingItem.getDetailsList().get(i);
            View descriptionView = LayoutInflater.from(getActivity()).inflate(R.layout.description_text_item, null);

            if (!TextUtils.isEmpty(offerText) && descriptionView != null) {
                TextView offerTv = descriptionView.findViewById(R.id.description_text_tv);
                offerTv.setText(offerText);

                descriptionTextWrapper.addView(descriptionView);
            }
        }

        Button button = view.findViewById(R.id.button_bt);
        button.setOnClickListener(this);

        OrderedItem orderedItem = DBHelper.getInstance(getActivity()).getOrderedItem(mShoppingItem.getProductId());
        setBuyButtonEnabled(button, orderedItem == null || !orderedItem.isOrdered());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_bt:
                setBuyButtonEnabled(v, false);
                showLoader();
                waitAndShowNotification();
                break;
        }
    }

    private void showLoader() {
       if (getView() != null) {
           getView().findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
       }
    }

    private void hideLoader() {
        if (getView() != null) {
            getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }

    private void waitAndShowNotification() {
        mNotificationHandler.removeCallbacksAndMessages(null);
        mNotificationHandler.postDelayed(mNotificationRunnable, 2000);
    }

    public void showNotification() {
        int smallIcon = R.drawable.shopping;
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.shopping);

        NotificationCompat.BigTextStyle notificationBig = new NotificationCompat.BigTextStyle();
        notificationBig.bigText("Item is on it's way to you...");
        notificationBig.setBigContentTitle(mShoppingItem.getTitle());

        Intent myIntent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity().getApplicationContext())
                .setContentText("Item is on it's way to you...").setContentTitle(mShoppingItem.getTitle())
                .setSmallIcon(smallIcon).setAutoCancel(true)
                .setTicker(mShoppingItem.getTitle())
                .setLargeIcon(largeIcon).
                        setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent).setStyle(notificationBig);

        Notification notification = notificationBuilder.build();

        //Notify User
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private void saveOrderIntoDataBase() {
        try {
            DBHelper.getInstance(getActivity()).insertFavouriteMovie(new OrderedItem(true, mShoppingItem.getProductId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBuyButtonEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (enabled) {
            view.setBackgroundColor(Color.parseColor("#F44336"));
        } else {
            view.setBackgroundColor(Color.parseColor("#696969"));
        }
    }
}
