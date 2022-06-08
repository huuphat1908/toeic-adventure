package com.example.toeic_adventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.SkillTestListAdapter;
import com.example.toeic_adventure.api.ApiService;
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
                String part = intent.getStringExtra("part");
                switch (part) {
                    case "1":
                        Intent skillTestPart1Intent = new Intent(v.getContext(), SkillTestPart1Activity.class);
                        skillTestPart1Intent.putExtra("id", item.id);
                        startActivity(skillTestPart1Intent);
                        break;
                    case "2":
                        Intent skillTestPart2Intent = new Intent(v.getContext(), SkillTestPart2Activity.class);
                        skillTestPart2Intent.putExtra("id", item.id);
                        startActivity(skillTestPart2Intent);
                        break;
                    case "3":
                        Intent skillTestPart3Intent = new Intent(v.getContext(), SkillTestPart3Activity.class);
                        skillTestPart3Intent.putExtra("id", item.id);
                        startActivity(skillTestPart3Intent);
                        break;
                    case "4":
                        Intent skillTestPart4Intent = new Intent(v.getContext(), SkillTestPart4Activity.class);
                        skillTestPart4Intent.putExtra("id", item.id);
                        startActivity(skillTestPart4Intent);
                        break;
                    case "5":
                        Intent skillTestPart5Intent = new Intent(v.getContext(), SkillTestPart5Activity.class);
                        skillTestPart5Intent.putExtra("id", item.id);
                        startActivity(skillTestPart5Intent);
                        break;
                    case "6":
                        Intent skillTestPart6Intent = new Intent(v.getContext(), SkillTestPart6Activity.class);
                        skillTestPart6Intent.putExtra("id", item.id);
                        startActivity(skillTestPart6Intent);
                        break;
                    case "7":
                        Intent skillTestPart7Intent = new Intent(v.getContext(), SkillTestPart7Activity.class);
                        skillTestPart7Intent.putExtra("id", item.id);
                        startActivity(skillTestPart7Intent);
                        break;
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fetchSkillTestList();
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
                        arraySkillTestList.add(new SkillTestList(skillTestList.getInt("score"), skillTestList.getInt("totalSentences"), skillTestList.getString("id")));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SkillTestListActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(SkillTestListActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}