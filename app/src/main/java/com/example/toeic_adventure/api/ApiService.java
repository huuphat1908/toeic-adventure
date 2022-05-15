package com.example.toeic_adventure.api;

import com.example.toeic_adventure.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy--MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://20.89.240.175/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @FormUrlEncoded
    @POST("auth/login")
    Call<Object> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/register")
    Call<User> register(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/send-verification-email")
    Call<Boolean> sendVerificationEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("auth/verify-email")
    Call<Boolean> verifyEmail(@Field("email") String email, @Field("code") String code);


}
