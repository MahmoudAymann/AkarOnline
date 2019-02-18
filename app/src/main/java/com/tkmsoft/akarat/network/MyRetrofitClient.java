package com.tkmsoft.akarat.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyRetrofitClient {

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    public static Retrofit getAuth() {
        return new Retrofit.Builder()
                .baseUrl("http://3qaronline.net/api/auth/").addConverterFactory(GsonConverterFactory.create())
                .client(getClient()).build();
    }

    public static Retrofit getBase() {
        return new Retrofit.Builder().baseUrl("http://3qaronline.net/api/")
                .addConverterFactory(GsonConverterFactory.create()).client(getClient()).build();
    }

    public static Retrofit getOrder() {
        return new Retrofit.Builder().baseUrl("http://3qaronline.net/api/order/")
                .addConverterFactory(GsonConverterFactory.create()).client(getClient()).build();
    }

    public static Retrofit getShow() {
        return new Retrofit.Builder().baseUrl("http://3qaronline.net/api/show/")
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}