package com.example.toeic_adventure.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.VocabularyDetailAdapter;
import com.example.toeic_adventure.model.VocabularyDetail;

import java.util.ArrayList;

public class VocabularyDetailActivity extends AppCompatActivity {

    ListView lvVocabularyDetail;
    ArrayList<VocabularyDetail> arrayVocabularyDetail;

    private ArrayList<VocabularyDetail> initArrayVocabularyDetail() {
        ArrayList<VocabularyDetail> array = new ArrayList<VocabularyDetail>();
        array.add(new VocabularyDetail(
                1,
                "Contract",
                "Phiên âm",
                "Hợp đồng"
        ));
        array.add(new VocabularyDetail(
                2,
                "Marketing",
                "Phiên âm",
                "Tiếp thị"
        ));
        array.add(new VocabularyDetail(
                3,
                "Warranties",
                "Phiên âm",
                "Bảo hiểm, đảm bảo"
        ));
        array.add(new VocabularyDetail(
                4,
                "Business Planning",
                "Phiên âm",
                "Chiến lược kinh doanh"
        ));
        array.add(new VocabularyDetail(
                5,
                "Conferences",
                "Phiên âm",
                "Hội nghị"
        ));
        array.add(new VocabularyDetail(
                6,
                "Conferences",
                "Phiên âm",
                "Hội nghị"
        ));
        array.add(new VocabularyDetail(
                7,
                "Conferences",
                "Phiên âm",
                "Hội nghị"
        ));
        array.add(new VocabularyDetail(
                8,
                "Conferences",
                "Phiên âm",
                "Hội nghị"
        ));
        array.add(new VocabularyDetail(
                9,
                "Conferences",
                "Phiên âm",
                "Hội nghị"
        ));
        array.add(new VocabularyDetail(
                10,
                "Conferences",
                "Phiên âm",
                "Hội nghị"
        ));
        array.add(new VocabularyDetail(
                11,
                "Conferences",
                "Phiên âm",
                "Hội nghị"
        ));
        array.add(new VocabularyDetail(
                12,
                "Conferences",
                "Phiên âm",
                "Hội nghị"
        ));

        return array;
    }
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vacabulary_detail);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();

        setActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.back_icon);

        arrayVocabularyDetail = initArrayVocabularyDetail();
        lvVocabularyDetail = (ListView) findViewById(R.id.listViewVocabularyDetail);
        VocabularyDetailAdapter adapter = new VocabularyDetailAdapter(
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
    }
}
