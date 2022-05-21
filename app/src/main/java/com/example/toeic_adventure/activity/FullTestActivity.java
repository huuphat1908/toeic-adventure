package com.example.toeic_adventure.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.FullTestCollectionAdapter;
import com.example.toeic_adventure.adapter.SkillTestAdapter;
import com.example.toeic_adventure.api.ApiService;
import com.example.toeic_adventure.model.FullTestCollection;
import com.example.toeic_adventure.model.SkillTest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullTestActivity extends Fragment {
    View rootView;
    ListView lvFullTestCollection;
    ArrayList<FullTestCollection> arrayFullTestCollection;
    FullTestCollectionAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FullTestActivity() { }

    public static FullTestActivity newInstance(String param1, String param2) {
        FullTestActivity fragment = new FullTestActivity();
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
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_full_test, container, false);
        arrayFullTestCollection = new ArrayList<FullTestCollection>();
        fetchFullTestCollection();
        lvFullTestCollection = (ListView) rootView.findViewById(R.id.lvFullTestCollection);
        lvFullTestCollection.setDivider(null);
        adapter = new FullTestCollectionAdapter(
                getContext(),
                R.layout.fulltestcollection_item,
                arrayFullTestCollection
        );
        lvFullTestCollection.setAdapter(adapter);

        return rootView;
    }

    private void fetchFullTestCollection() {
        ApiService.apiService.getFullTestCollection().enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    JSONObject resObj = new JSONObject(new Gson().toJson(response.body()));
                    arrayFullTestCollection.clear();
                    JSONArray results = resObj.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject fullTestCollection = results.getJSONObject(i);
                        JSONObject thumbnail = fullTestCollection.getJSONObject("thumbnail");
                        arrayFullTestCollection.add(new FullTestCollection(
                                fullTestCollection.getString("name"),
                                thumbnail.getString("url")
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