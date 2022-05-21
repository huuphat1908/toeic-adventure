package com.example.toeic_adventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.SkillTestListAdapter;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.model.SkillTestCollection;
import com.example.toeic_adventure.model.SkillTestList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillTestListActivity extends AppCompatActivity {
    Toolbar myToolbar;
    ListView lvSkillTestList;
    ArrayList<SkillTestList> arraySkillTestList;
    SkillTestListAdapter adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_test_list);
        myToolbar = findViewById(R.id.toolbar);
        intent = getIntent();

        setActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.back_icon);

        lvSkillTestList = (ListView) findViewById(R.id.lvSkillTestList);
        arraySkillTestList = new ArrayList<SkillTestList>();
        adapter = new SkillTestListAdapter(
                SkillTestListActivity.this,
                R.layout.skill_test_list_item,
                arraySkillTestList
        );
        lvSkillTestList.setAdapter(adapter);
        fetchSkillTestList();
        String title = intent.getStringExtra("title");
        myToolbar.setTitle(title);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               finish();
           }
        });

        lvSkillTestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                SkillTestList item = (SkillTestList) parent.getItemAtPosition(position);
                Intent skillTestIntent = new Intent(v.getContext(), SkillTestActivity.class);
                skillTestIntent.putExtra("id", item.id);
                startActivity(skillTestIntent);
            }
        });
    }

    private void fetchSkillTestList() {
        String part = intent.getStringExtra("part");
        String difficultLevel = intent.getStringExtra("level");
        ApiService.apiService.getSkillTestList(part, difficultLevel).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    arraySkillTestList.clear();
                    JSONArray results = resObj.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject skillTestList = results.getJSONObject(i);
                        arraySkillTestList.add(new SkillTestList(1, skillTestList.getString("id")));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }
}