package com.example.priyanka.shoppingapplication.screens.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.priyanka.shoppingapplication.R;
import com.example.priyanka.shoppingapplication.models.ShoppingItem;
import com.example.priyanka.shoppingapplication.network.ShoppingClient;
import com.example.priyanka.shoppingapplication.screens.details.ItemPagerFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemListFragment extends Fragment implements ItemListAdapter.ItemListAdapterCallbacks {
    private ItemListAdapter mAdapter;
    private List<ShoppingItem> mDataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ItemListAdapter(getActivity());
        mAdapter.setCallback(this);
        recyclerView.setAdapter(mAdapter);

        makeApiCall();
    }

    private void makeApiCall() {
        showLoader();

        Call<List<ShoppingItem>> call = ShoppingClient.getShoppingApi().getItemList();
        call.enqueue(new Callback<List<ShoppingItem>>() {
            @Override
            public void onResponse(Call<List<ShoppingItem>> call, Response<List<ShoppingItem>> response) {
                if (getView() == null || getActivity() == null) return;

                hideLoader();

                mDataList = response.body();
                if (mAdapter != null) {
                    mAdapter.setDataList(mDataList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ShoppingItem>> call, Throwable t) {
                hideLoader();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        ItemPagerFragment fragment = new ItemPagerFragment();
        fragment.setSelectedPosition(position);
        fragment.setDataList(mDataList);

        fragment.show(getFragmentManager(), "dialog");
    }

    public void showLoader() {
        if (getView() != null) {
            getView().findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        }
    }

    public void hideLoader() {
        if (getView() != null) {
            getView().findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }
}
