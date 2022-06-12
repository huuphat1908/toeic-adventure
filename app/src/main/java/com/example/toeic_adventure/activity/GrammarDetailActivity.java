package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.api.ApiService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrammarDetailActivity extends AppCompatActivity {
    Toolbar myToolbar;
    WebView wvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_detail);
        initView();
        fetchGrammarDetail();
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        myToolbar = findViewById(R.id.toolbar);
        wvContent = (WebView) findViewById(R.id.wvContent);
        setActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.back_icon);
        myToolbar.setTitle(getIntent().getStringExtra("name"));
    }

    private void fetchGrammarDetail() {
        ApiService.apiService.getGrammarDetail(getIntent().getStringExtra("id")).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                        wvContent.loadData(resObj.getJSONArray("results").getJSONObject(0).getString("content"),
                                "text/html; charset=utf-8", "UTF-8");
                    } else {
                        Toast.makeText(GrammarDetailActivity.this, "Bad request", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(GrammarDetailActivity.this, "JSON object error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(GrammarDetailActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}