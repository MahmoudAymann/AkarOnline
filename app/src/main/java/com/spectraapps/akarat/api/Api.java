package com.spectraapps.akarat.api;

import com.spectraapps.akarat.model.AddModel;
import com.spectraapps.akarat.model.AkarsModel;
import com.spectraapps.akarat.model.CityModel;
import com.spectraapps.akarat.model.LoginModel;
import com.spectraapps.akarat.model.OrderModel;
import com.spectraapps.akarat.model.ProfileModel;
import com.spectraapps.akarat.model.RegisterModel;
import com.spectraapps.akarat.model.UserInfModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> login(@Field("email") String email,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<RegisterModel> register(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("phone") String phone,
                                 @Field("password") String password,
                                 @Field("password_confirmation") String password_confirmation);

    @FormUrlEncoded
    @POST("add")
    Call<OrderModel> getorder(@Field("api_token") String api_token,
                              @Field("name") String name,
                              @Field("type_id") String type_id,
                              @Field("category_id") String category_id,
                              @Field("city_id") String city_id,
                              @Field("disrict_id") String disrict_id,
                              @Field("rooms") String rooms,
                              @Field("bathrooms") String bathrooms,
                              @Field("garage") String garage,
                              @Field("area") String area,
                              @Field("price") String price,
                              @Field("address") String address,
                              @Field("condition") String condition,
                              @Field("lat") String lat,
                              @Field("long") String longg
    );

    @GET("categories")
    Call<AkarsModel> getCategories();

    @GET("cities")
    Call<CityModel> getcity();


    @FormUrlEncoded
    @POST("user_info")
    Call<UserInfModel> inf(@Field("api_token") String api_token);

    @Multipart
    @POST("add")
    Call<AddModel> offerAkarAll(@Part("api_token") RequestBody api_token,
                                @Part("name") RequestBody name,
                                @Part("type_id") RequestBody type_id,
                                @Part("category_id") RequestBody category_id,
                                @Part("city_id") RequestBody city_id,
                                @Part("disrict_id") RequestBody disrict_id,
                                @Part("rooms") RequestBody rooms,
                                @Part("bathrooms") RequestBody bathrooms,
                                @Part("garage") RequestBody garage,
                                @Part("area") RequestBody area,
                                @Part("price") RequestBody price,
                                @Part("address") RequestBody address,
                                @Part("condition") RequestBody condition,
                                @Part("lat") RequestBody lat,
                                @Part("long") RequestBody long1,
                                @Part("about") RequestBody about,
                                @Part MultipartBody.Part file1,
                                @Part MultipartBody.Part file2,
                                @Part MultipartBody.Part file3
    );

    @Multipart
    @POST("add")
    Call<AddModel> offerAkar2(@Part("api_token") RequestBody api_token,
                             @Part("name") RequestBody name,
                             @Part("type_id") RequestBody type_id,
                             @Part("category_id") RequestBody category_id,
                             @Part("city_id") RequestBody city_id,
                             @Part("disrict_id") RequestBody disrict_id,
                             @Part("rooms") RequestBody rooms,
                             @Part("bathrooms") RequestBody bathrooms,
                             @Part("garage") RequestBody garage,
                             @Part("area") RequestBody area,
                             @Part("price") RequestBody price,
                             @Part("address") RequestBody address,
                             @Part("condition") RequestBody condition,
                             @Part("lat") RequestBody lat,
                             @Part("long") RequestBody long1,
                             @Part("about") RequestBody about,
                             @Part MultipartBody.Part file1,
                             @Part MultipartBody.Part file2
    );

    @Multipart
    @POST("updateProfile")
    Call<ProfileModel> profile(@Part("name") RequestBody name,
                               @Part("email") RequestBody email,
                               @Part("phone") RequestBody phone,
                               @Part("old_password") RequestBody old_password,
                               @Part("password") RequestBody password,
                               @Part("api_token") RequestBody api_token,
                               @Part MultipartBody.Part file1

    );

    @FormUrlEncoded
    @POST("updateProfile")
    Call<ProfileModel> profileNoImage(@Field("name") String name,
                                      @Field("email") String email,
                                      @Field("phone") String phone,
                                      @Field("old_password") String old_password,
                                      @Field("password") String password,
                                      @Field("api_token") String api_token
    );

}

