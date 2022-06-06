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
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.model.FullTestCollection;
import com.example.toeic_adventure.model.Vocabulary;
import com.example.toeic_adventure.model.VocabularyDetail;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VocabularyActivity  extends Fragment {
    ListView lvVocabulary;
    View view;
    ArrayList<Vocabulary> arrayVocabularyTheme;
    VocabularyAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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

        arrayVocabularyTheme = new ArrayList<Vocabulary>();
        fetchFullTestCollection();
        lvVocabulary = (ListView) view.findViewById(R.id.listViewVocabulary);
        lvVocabulary.setDivider(null);
        adapter = new VocabularyAdapter(
                getContext(),
                R.layout.vocabulary_item,
                arrayVocabularyTheme
        );
        lvVocabulary.setAdapter(adapter);

        lvVocabulary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent vocabularyDetailActivity = new Intent(v.getContext(), VocabularyDetailActivity.class);
                vocabularyDetailActivity.putExtra("title", arrayVocabularyTheme.get(position).Name);
                startActivity(vocabularyDetailActivity);
            }
        });

        return view;
    }

    private void fetchFullTestCollection() {
        ApiService.apiService.getVocabularyTheme().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    arrayVocabularyTheme.clear();
                    JSONArray results = resObj.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject arrayVocabularyThemeItem = results.getJSONObject(i);
                        arrayVocabularyTheme.add(new Vocabulary(
                                i+1,
                                arrayVocabularyThemeItem.getString("enName"),
                                arrayVocabularyThemeItem.getString("vnName")
                        ));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "JSON object error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getContext(), "Unknown error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
