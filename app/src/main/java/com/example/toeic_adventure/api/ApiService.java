package com.example.toeic_adventure.api;

import com.example.toeic_adventure.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
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

    @POST("auth/")
    Call<User> login(@Field("email") String email, @Field("password") String password);
    Call<User> register(@Field("email") String email, @Field("password") String password);
}
