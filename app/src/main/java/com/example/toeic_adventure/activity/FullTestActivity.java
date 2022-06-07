package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.api.ApiService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullTestActivity extends AppCompatActivity {
    String fullTestId;
    Toolbar myToolbar;
    TextView tvPart1, tvPart2, tvPart3, tvPart4, tvPart5, tvPart6, tvPart7;
    JSONArray questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_test);

        initView();
        fetchTest();

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void fetchTest() {
        Intent intent = getIntent();
        fullTestId = intent.getStringExtra("id");
        ApiService.apiService.getFullTest(fullTestId).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    JSONObject jsonFile = resObj.getJSONObject("jsonFile");
                    String url = jsonFile.getString("url");
                    ApiService.apiService.getSkillTestFile(url).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            try {
                                JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                                questions = resObj.getJSONArray("questions");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(FullTestActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(FullTestActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(FullTestActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        myToolbar = findViewById(R.id.toolbar);
        setActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.back_icon);
        tvPart1 = (TextView) findViewById(R.id.tvPart1);
        tvPart2 = (TextView) findViewById(R.id.tvPart2);
        tvPart3 = (TextView) findViewById(R.id.tvPart3);
        tvPart4 = (TextView) findViewById(R.id.tvPart4);
        tvPart5 = (TextView) findViewById(R.id.tvPart5);
        tvPart6 = (TextView) findViewById(R.id.tvPart6);
        tvPart7 = (TextView) findViewById(R.id.tvPart7);
    }
}