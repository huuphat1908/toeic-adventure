package com.example.toeic_adventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class TestProgressActivity extends AppCompatActivity {

    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_progress);
        myToolbar = findViewById(R.id.toolbar);
        setActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.back_icon);

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