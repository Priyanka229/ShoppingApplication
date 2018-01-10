package com.example.priyanka.shoppingapplication.screens.details;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.priyanka.shoppingapplication.R;
import com.example.priyanka.shoppingapplication.models.ShoppingItem;

import java.util.List;


public class ItemPagerFragment extends DialogFragment {
    private List<ShoppingItem> mDataList;
    private int mSelectedPostion;

    public void setDataList(List<ShoppingItem> list) {
        this.mDataList = list;
    }

    public void setSelectedPosition(int position) {
        this.mSelectedPostion = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_pager, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewPager viewPager = getView().findViewById(R.id.view_pager);
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(customPagerAdapter);

        viewPager.setCurrentItem(mSelectedPostion);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setDimAmount(new Float(0.85));
        getDialog().setCanceledOnTouchOutside(false);

        // set dialog width
        Rect displayRectangle = new Rect();
        Window window = getDialog().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        getDialog().getWindow().setLayout((int) (displayRectangle.width() * 1.0), WindowManager.LayoutParams.WRAP_CONTENT);
    }

    class CustomPagerAdapter extends FragmentPagerAdapter {

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ItemDetailsFragment fragment = new ItemDetailsFragment();
            fragment.setShoppingItemData(mDataList.get(position));
            return fragment;
        }

        @Override
        public int getCount() {
            return mDataList == null ? 0 : mDataList.size();
        }
    }
}


