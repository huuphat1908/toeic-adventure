package com.example.toeic_adventure.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.VocabularyAdapter;
import com.example.toeic_adventure.model.Vocabulary;

import java.util.ArrayList;

public class VocabularyActivity  extends Fragment {
    ListView lvVocabulary;
    View view;
    ArrayList<Vocabulary> arrayVocabulary;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VocabularyActivity() {

    }

    private ArrayList<Vocabulary> initArrayVocabulary() {
        ArrayList<Vocabulary> array = new ArrayList<Vocabulary>();
        array.add(new Vocabulary(
                1,
                "Contract",
                "Hợp đồng"
        ));
        array.add(new Vocabulary(
                2,
                "Marketing",
                "Tiếp thị"
        ));
        array.add(new Vocabulary(
                3,
                "Warranties",
                "Bảo hiểm, đảm bảo"
        ));
        array.add(new Vocabulary(
                4,
                "Business Planning",
                "Chiến lược kinh doanh"
        ));
        array.add(new Vocabulary(
                5,
                "Conferences",
                "Hội nghị"
        ));
        array.add(new Vocabulary(
                6,
                "Conferences",
                "Hội nghị"
        ));
        array.add(new Vocabulary(
                7,
                "Conferences",
                "Hội nghị"
        ));
        array.add(new Vocabulary(
                8,
                "Conferences",
                "Hội nghị"
        ));
        array.add(new Vocabulary(
                9,
                "Conferences",
                "Hội nghị"
        ));
        array.add(new Vocabulary(
                10,
                "Conferences",
                "Hội nghị"
        ));
        array.add(new Vocabulary(
                11,
                "Conferences",
                "Hội nghị"
        ));
        array.add(new Vocabulary(
                12,
                "Conferences",
                "Hội nghị"
        ));

        return array;
    }

    public static VocabularyActivity newInstance(String param1, String param2) {
        VocabularyActivity fragment = new VocabularyActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Init view from fragment_skill_test layout
        if (view == null)
        {
            view = inflater.inflate(R.layout.fragment_vocabulary, container,false);
        }
        else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }

        // Init adapter and set it to Skill Test List View
        arrayVocabulary = initArrayVocabulary();
        lvVocabulary = (ListView) view.findViewById(R.id.listViewVocabulary);
        lvVocabulary.setDivider(null);
        VocabularyAdapter adapter = new VocabularyAdapter(
                getContext(),
                R.layout.vocabulary_item,
                arrayVocabulary
        );
        lvVocabulary.setAdapter(adapter);

        lvVocabulary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Vocabulary item = (Vocabulary) parent.getItemAtPosition(position);
                Intent vocabularyDetailActivity = new Intent(v.getContext(), VocabularyDetailActivity.class);
                vocabularyDetailActivity.putExtra("title", arrayVocabulary.get(position).Name);
                startActivity(vocabularyDetailActivity);
//                Toast.makeText(getContext(), "You have click item " + position + item.Name, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
