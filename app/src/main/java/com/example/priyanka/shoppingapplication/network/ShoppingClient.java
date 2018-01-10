package com.example.priyanka.shoppingapplication.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShoppingClient {
    private static final String BASE_URL = "https://api.myjson.com/";
    private static Retrofit mRetrofit;

    private static Retrofit getShoppingRetrofit() {
        if (mRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return mRetrofit;
    }

    public static ShoppingApi getShoppingApi() {

        Retrofit retrofit = getShoppingRetrofit();
        ShoppingApi shoppingApi = retrofit.create(ShoppingApi.class);

        return shoppingApi;
    }
}
