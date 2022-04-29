package com.example.toeic_adventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.SkillTestListAdapter;
import com.example.toeic_adventure.model.SkillTestItem;

import java.util.ArrayList;

public class SkillTestListActivity extends AppCompatActivity {

    Toolbar myToolbar;
    ListView lvSkillTestItem;
    ArrayList<SkillTestItem> arraySkillTest;

    private ArrayList<SkillTestItem> initSkillTestList() {
        ArrayList<SkillTestItem> arraySkillTest = new ArrayList<SkillTestItem>();
        arraySkillTest.add(new SkillTestItem(0));
        arraySkillTest.add(new SkillTestItem(1));
        arraySkillTest.add(new SkillTestItem(2));
        arraySkillTest.add(new SkillTestItem(3));
        arraySkillTest.add(new SkillTestItem(4));
        arraySkillTest.add(new SkillTestItem(5));
        arraySkillTest.add(new SkillTestItem(6));
        arraySkillTest.add(new SkillTestItem(7));
        arraySkillTest.add(new SkillTestItem(8));
        arraySkillTest.add(new SkillTestItem(9));
        arraySkillTest.add(new SkillTestItem(10));
        arraySkillTest.add(new SkillTestItem(10));
        arraySkillTest.add(new SkillTestItem(10));
        arraySkillTest.add(new SkillTestItem(10));
        arraySkillTest.add(new SkillTestItem(10));
        return arraySkillTest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_test_list);
        myToolbar = findViewById(R.id.toolbar);
        setActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.back_icon);

        lvSkillTestItem = (ListView) findViewById(R.id.lvSkillTestList);
        arraySkillTest = initSkillTestList();
        SkillTestListAdapter adapter = new SkillTestListAdapter(
                SkillTestListActivity.this,
                R.layout.skill_test_list_item,
                arraySkillTest
        );
        lvSkillTestItem.setAdapter(adapter);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        myToolbar.setTitle(title);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               finish();
           }
        });


    }
}