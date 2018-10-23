package com.tkmsoft.akarat.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MyRetrofitClient {

    public static Retrofit getBase()
    {
        return new Retrofit.Builder()
                .baseUrl("http://3qaronline.net/api/auth/").addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static Retrofit categories()
    {
        return new Retrofit.Builder().baseUrl("http://3qaronline.net/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static Retrofit order()
    {
        return new Retrofit.Builder().baseUrl("http://3qaronline.net/api/order/")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static Retrofit show()
    {OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100,TimeUnit.SECONDS).build();
        return new Retrofit.Builder().baseUrl("http://3qaronline.net/api/show/").client(client)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}