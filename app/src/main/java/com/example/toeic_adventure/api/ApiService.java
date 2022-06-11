package com.example.toeic_adventure.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.toeic_adventure.activity.MainActivity;
import com.example.toeic_adventure.model.FullTestAnswer;
import com.example.toeic_adventure.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy--MM-dd HH:mm:ss")
            .create();

    SharedPreferences localStorage = MainActivity.localStorage;
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + MainActivity.token)
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    ApiService apiService = new Retrofit.Builder()
            .client(client)
            .baseUrl(Url.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @FormUrlEncoded
    @POST("/auth/login")
    Call<Object> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/auth/register")
    Call<User> register(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/auth/send-verification-email")
    Call<Boolean> sendVerificationEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("/auth/verify-email")
    Call<Boolean> verifyEmail(@Field("email") String email, @Field("code") String code);

    @FormUrlEncoded
    @POST("/auth/forgot-password")
    Call<Boolean> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("/auth/reset-password")
    Call<Boolean> resetPassword(@Field("email") String email, @Field("code") String code, @Field("newPassword") String newPassword);

    @GET("/skill-tests/count-by-parts")
    Call<Object> getSkillTestCollection(@Query("difficultyLevel") String level);

    @GET("/skill-tests")
    Call<Object> getSkillTestList(@Query("part") String part, @Query("difficultyLevel") String level);

    @GET("/skill-tests/{id}")
    Call<Object> getSkillTest(@Path("id") String id);

    @GET("/{url}")
    Call<Object> getSkillTestFile(@Path("url") String url);

    @FormUrlEncoded
    @POST("/histories/skill-test")
    Call<Object> submitSkillTestAnswer(@Field("correctSentences") int correctSentences, @Field("test") String test, @Field("totalSentences") int totalSentences);

    @GET("/collections")
    Call<Object> getFullTestCollection();

    @GET("/full-tests")
    Call<Object> getFullTestList(@Query("testCollection") String testCollection);

    @GET("/full-tests/{id}")
    Call<Object> getFullTest(@Path("id") String id);

    @GET("/{url}")
    Call<Object> getFullTestFile(@Path("url") String url);

    @FormUrlEncoded
    @POST("/histories/full-test")
    Call<Object> submitFullTestAnswer(@Field("correctSentences") Object correctSentences, @Field("test") String test);

    @GET("/users/profile")
    Call<Object> getProfileUser();

    @GET("/vocabulary-themes?page=1&pageSize=20")
    Call<Object> getVocabularyTheme();

    @GET("/vocabulary")
    Call<Object> getVocabularyDetail(@Query("theme") String theme);
}
