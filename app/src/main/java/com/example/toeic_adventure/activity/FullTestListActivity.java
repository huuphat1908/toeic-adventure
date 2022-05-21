package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.FullTestListAdapter;
import com.example.toeic_adventure.adapter.SkillTestListAdapter;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.model.FullTestList;
import com.example.toeic_adventure.model.SkillTestList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullTestListActivity extends AppCompatActivity {
    Intent intent;
    Toolbar myToolbar;
    ListView lvFullTestList;
    ArrayList<FullTestList> arrayFullTestList;
    FullTestListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_test_list);
        myToolbar = findViewById(R.id.toolbar);
        intent = getIntent();

        setActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.back_icon);
        myToolbar.setTitle(intent.getStringExtra("title"));
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvFullTestList = (ListView) findViewById(R.id.lvFullTestList);
        arrayFullTestList = new ArrayList<FullTestList>();
        adapter = new FullTestListAdapter(
                FullTestListActivity.this,
                R.layout.full_test_list_item,
                arrayFullTestList
        );
        lvFullTestList.setAdapter(adapter);
        fetchFullTestList();

        lvFullTestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FullTestList item = (FullTestList) parent.getItemAtPosition(position);
                Intent fullTestIntent = new Intent(v.getContext(), FullTestActivity.class);
                fullTestIntent.putExtra("id", item.id);
                startActivity(fullTestIntent);
            }
        });
    }

    private void fetchFullTestList() {
        String id = intent.getStringExtra("id");
        ApiService.apiService.getFullTestList(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    arrayFullTestList.clear();
                    JSONArray results = resObj.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject fullTestList = results.getJSONObject(i);
                        arrayFullTestList.add(new FullTestList(1, fullTestList.getString("id")));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(FullTestListActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(FullTestListActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}