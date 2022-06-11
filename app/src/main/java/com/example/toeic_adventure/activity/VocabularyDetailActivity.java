package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.VocabularyDetailAdapter;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.api.Url;
import com.example.toeic_adventure.model.VocabularyDetail;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VocabularyDetailActivity extends AppCompatActivity {

    ListView lvVocabularyDetail;
    ArrayList<VocabularyDetail> arrayVocabularyDetail;
    VocabularyDetailAdapter adapter;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vacabulary_detail);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        ImageView playAudio = findViewById(R.id.audioVocabulary);
        Intent intent = getIntent();

        setActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.back_icon);

        arrayVocabularyDetail = new ArrayList<VocabularyDetail>();
        fetchVocabularyDetail();

        lvVocabularyDetail = (ListView) findViewById(R.id.listViewVocabularyDetail);
        adapter = new VocabularyDetailAdapter(
                VocabularyDetailActivity.this,
                R.layout.vacabulary_item_detail,
                arrayVocabularyDetail
        );
        lvVocabularyDetail.setAdapter(adapter);

        String title = intent.getStringExtra("title");
        myToolbar.setTitle(title);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lvVocabularyDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(Url.baseUrl + arrayVocabularyDetail.get(position).Audio);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    Toast.makeText(VocabularyDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }

    private void fetchVocabularyDetail() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        ApiService.apiService.getVocabularyDetail(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    arrayVocabularyDetail.clear();
                    JSONArray results = resObj.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject arrayVocabularyThemeItem = results.getJSONObject(i);
                        JSONObject audioItem = arrayVocabularyThemeItem.getJSONObject("audio");
                        arrayVocabularyDetail.add(new VocabularyDetail(
                                i+1,
                                arrayVocabularyThemeItem.getString("word"),
                                arrayVocabularyThemeItem.getString("phonetic"),
                                arrayVocabularyThemeItem.getString("meaning"),
                                audioItem.getString("url")
                        ));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(VocabularyDetailActivity.this, "JSON object error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(VocabularyDetailActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
