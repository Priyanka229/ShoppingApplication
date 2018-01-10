package com.example.priyanka.shoppingapplication.network;

import com.example.priyanka.shoppingapplication.models.ShoppingItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ShoppingApi {
    @GET("bins/kiwdd")
    Call<List<ShoppingItem>> getItemList();
}
